package com.cg.lms.repo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;

@Repository
@Transactional
public class TransactionRepoImpl implements TransactionRepo {
	static final int fixedDaysToReturnBook = 15;

	@Autowired
	private EntityManager mgr;

	@Override
	public Transactions borrowABook(int bookId, int studentId) {
		
		Book b = mgr.createNamedQuery("findBookById", Book.class).setParameter("bookId", bookId).getSingleResult();
		Student s = mgr.createNamedQuery("findStudentById", Student.class).setParameter("studentId", studentId).getSingleResult();
		if(b.getBookStatus().equalsIgnoreCase("free") && s!=null && b.getNoOfCopies()>=1) {
			Transactions transaction = new Transactions();
			Date dateOfIssue = new Date();
			transaction.setDateOfIssue(dateOfIssue);
			s.setIntrests(b.getBookGenre());
			transaction.setStudent(s);
			
			b.setBookStatus("issued");
//			if(b.getNoOfCopies()>=1) {
				b.setNoOfCopies((b.getNoOfCopies()-1));
				transaction.setBook(b);
				
				LocalDate dateOfIssueLocal = dateOfIssue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate dateOfReturnLocal = dateOfIssueLocal.plusDays(fixedDaysToReturnBook);
				Date dateOfReturn = Date.from(dateOfReturnLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
				transaction.setDateOfReturn(dateOfReturn);
//				mgr.merge(b);
//				mgr.merge(s);
				mgr.persist(transaction);
				return transaction;
//			}
//			else {
//				System.out.println("Not enough copies of this book!");
//				return null;
//			}
		}
		else {
			return null;
		}
	}

	@Override
	public Transactions returnABook(int bookId, int studentId, int transactionId, Date returnDate) {
//		Transactions transaction = new Transactions();
		Transactions transaction = mgr.createNamedQuery("findTransactionById", Transactions.class).setParameter("transactionId", transactionId).getSingleResult();
		Date dateOfIssue = transaction.getDateOfIssue();
//		Date dateOfReturn = transaction.getDateOfReturn();
//		transaction.setDateOfReturn(new Date());
		transaction.setDateOfReturn(returnDate);
		long diffInMillies = Math.abs(dateOfIssue.getTime() - returnDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double amount = returnDueAmount((int)diff);
//        transaction.setDateOfReturn(new Date());
		transaction.setAmount(amount);
//		transaction.setStudent(mgr.createNamedQuery("findStudentById", Student.class).setParameter("studentId", studentId).getSingleResult());
//		transaction.setBook(mgr.createNamedQuery("findBookById", Book.class).setParameter("bookId", bookId).getSingleResult());
		if(transaction.getBook().getBookStatus().equalsIgnoreCase("issued")) {
			transaction.getBook().setBookStatus("free");
			transaction.getBook().setNoOfCopies((transaction.getBook().getNoOfCopies())+1);
			return mgr.merge(transaction);
		}
		else {
			return null;
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

}
