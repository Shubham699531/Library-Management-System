package com.cg.lms.repo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyReturnedException;
import com.cg.lms.exception.BookCopiesNotAvailableException;
import com.cg.lms.exception.SameBookAlreadyTakenException;

@Repository
@Transactional
public class TransactionRepoImpl implements TransactionRepo {
	static final int fixedDaysToReturnBook = 15;

	@Autowired
	private EntityManager mgr;

	@Override
	public Transactions borrowABook(int bookId, int studentId) throws BookCopiesNotAvailableException, SameBookAlreadyTakenException {

		Book b = mgr.createNamedQuery("findBookById", Book.class).setParameter("bookId", bookId).getSingleResult();
		Student s = mgr.createNamedQuery("findStudentById", Student.class).setParameter("studentId", studentId)
				.getSingleResult();
		
		List<Transactions> getTransactionsForAStudentAndBook = mgr.
				createNamedQuery("getTransactionsForAStudentAndBook", Transactions.class)
				.setParameter("bookId", bookId).setParameter("studentId", studentId)
				.getResultList();
		if(getTransactionsForAStudentAndBook.size()==0) {
			if (b.getBookStatus().equalsIgnoreCase("available") && b.getNoOfCopies() >= 1) {
				
				Transactions transaction = new Transactions();
				Date dateOfIssue = new Date();
				transaction.setDateOfIssue(dateOfIssue);
				s.setIntrests(b.getBookGenre());
				transaction.setStudent(s);

				b.setNoOfCopies((b.getNoOfCopies() - 1));
				transaction.setBook(b);

				LocalDate dateOfIssueLocal = dateOfIssue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate dateOfReturnLocal = dateOfIssueLocal.plusDays(fixedDaysToReturnBook);
				Date dateOfReturn = Date.from(dateOfReturnLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
				transaction.setDateOfReturn(dateOfReturn);
				transaction.setTransactionStatus("open");
				mgr.persist(transaction);
				return transaction;
			}
			else {
				throw new BookCopiesNotAvailableException("There are no copies of book: " + b.getBookName() + " available.");
			}
		}
		else {
			System.out.println("You already have taken this book.");
			throw new SameBookAlreadyTakenException("You already have taken this book.");
		}
		
			
		
		}

	@Override
	public Transactions returnABook(int transactionId, Date returnDate) throws BookAlreadyReturnedException {
		Transactions transaction = mgr.createNamedQuery("findTransactionById", Transactions.class).setParameter("transactionId", transactionId).getSingleResult();
		Date dateOfIssue = transaction.getDateOfIssue();
		
		//In case of actual returning of book, this date will be system date
       //transaction.setDateOfReturn(new Date());
		transaction.setDateOfReturn(returnDate);
		
		//finding the overdue
		long diffInMillies = Math.abs(dateOfIssue.getTime() - returnDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        //calculating fine amount to be paid based on overdue
        double amount = returnDueAmount((int)diff);
		transaction.setAmount(amount);
		
		if(transaction.getTransactionStatus().equalsIgnoreCase("open")) {
			transaction.getBook().setNoOfCopies((transaction.getBook().getNoOfCopies())+1);
			transaction.setTransactionStatus("closed");
			return mgr.merge(transaction);
		}
		else {
			System.out.println("Book already returned.");
			throw new BookAlreadyReturnedException("Book has already been returned.");
		}
	}

	@Override
	public boolean reviewABook(int transactionId, String review) {
		Transactions transaction = mgr.createNamedQuery("findTransactionById", Transactions.class).setParameter("transactionId", transactionId).getSingleResult();
		transaction.setReview(review);
		mgr.merge(transaction);
		return true;
	}

	@Override
	public double returnDueAmount(int differenceInDays) {
		double amount = 0;
		if(differenceInDays>fixedDaysToReturnBook) {
			amount = 10*(differenceInDays-fixedDaysToReturnBook);
			return amount;
		}
		return amount;
	}

	@Override
	public List<Book> getListOfBooksTakenByStudent(int studentId) {
		//get all transactions involving a particular student
		List<Transactions> listOfAllTransactions = mgr.createNamedQuery("getListOfBooksTakenByStudent", Transactions.class).setParameter("studentId", studentId).getResultList();
		List<Book> listOfBooks = new ArrayList<>();
		for(Transactions txn : listOfAllTransactions) {
			listOfBooks.add(txn.getBook());
		}
		return listOfBooks;
	}

	@Override
	public List<Student> getListOfPeopleTakingABook(int bookId) {
		List<Transactions> listOfAllTransactions = mgr.createNamedQuery("getListOfPeopleTakingABook", Transactions.class).setParameter("bookId", bookId).getResultList();
		List<Student> listOfStudents = new ArrayList<>();
		for(Transactions txn : listOfAllTransactions) {
			listOfStudents.add(txn.getStudent());
		}
		return listOfStudents;
	}

}
