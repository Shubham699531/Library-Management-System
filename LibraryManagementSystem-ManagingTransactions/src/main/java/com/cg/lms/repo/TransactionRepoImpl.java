package com.cg.lms.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.CustomPopularityObject;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyReturnedException;
import com.cg.lms.exception.BookCopiesNotAvailableException;
import com.cg.lms.exception.SameBookAlreadyTakenException;

@Repository
@Transactional
public class TransactionRepoImpl implements TransactionRepo {
	static final int fixedDaysToReturnBook = 15;
	Set<String> interests = new HashSet<String>();

	@Autowired
	private EntityManager mgr;

	@Override
	public Transactions borrowABook(int bookId, int studentId)
			throws BookCopiesNotAvailableException, SameBookAlreadyTakenException {

		// To check if a particular student has borrowed more than 2 books (>=3) and not
		// returned it yet,
		// If yes, don't issue him a new book
		// Ask him to return few books before trying again
		List<Transactions> listOfAllTransactions = mgr
				.createNamedQuery("getListOfBooksTakenByStudentAndNotReturned", Transactions.class)
				.setParameter("studentId", studentId).getResultList();
		List<Book> listOfBooks = new ArrayList<>();
		for (Transactions txn : listOfAllTransactions) {
			listOfBooks.add(txn.getBook());
		}

		if (listOfBooks.size() >= 3) {
			throw new BookCopiesNotAvailableException(
					"You already have 3 books. Kindly return some books and then try borrowing.");
		} else {
			Book b = null;
			Student s = null;
			try {
				b = mgr.createNamedQuery("findBookById", Book.class).setParameter("bookId", bookId).getSingleResult();
				s = mgr.createNamedQuery("findStudentById", Student.class).setParameter("studentId", studentId)
						.getSingleResult();

				List<Transactions> getTransactionsForAStudentAndBook = mgr
						.createNamedQuery("getTransactionsForAStudentAndBook", Transactions.class)
						.setParameter("bookId", bookId).setParameter("studentId", studentId).getResultList();

				// Check if this student has borrowed the enquired book previously,
				// If yes, then he MUST return the book before borrowing it again.
				if (getTransactionsForAStudentAndBook.size() == 0) {
					if (b.getBookStatus().equalsIgnoreCase("available") && b.getNoOfCopies() >= 1) {

						Transactions transaction = new Transactions();
						Date dateOfIssue = new Date();
						transaction.setDateOfIssue(dateOfIssue);
						StringBuilder sb = new StringBuilder();
//						Adding interests in a set
//						interests.add(b.getBookGenre());
//						System.out.println(interests);
//						s.getIntrests().concat(b.getBookGenre());
//						for(String interest: interests) {
//							sb.append(interest + " ");
//						}
						s.setIntrests(s.getIntrests().concat(b.getBookGenre()));
						System.out.println(s.getIntrests());
//						s.setIntrests(sb.toString());
//						if (s.getIntrests() != null) {
//							// Appending interests of the student based on the books
//							// he borrows, can be used in FRONT END for better results
//							sb.append(s.getIntrests() + " " + b.getBookGenre());
//						} else {
//							sb.append(b.getBookGenre());
//						}
//						s.setIntrests(sb.toString());
						transaction.setStudent(s);

						b.setNoOfCopies((b.getNoOfCopies() - 1));
						transaction.setBook(b);

						LocalDate dateOfIssueLocal = dateOfIssue.toInstant().atZone(ZoneId.systemDefault())
								.toLocalDate();
						LocalDate dateOfReturnLocal = dateOfIssueLocal.plusDays(fixedDaysToReturnBook);
						Date dateOfReturn = Date
								.from(dateOfReturnLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
						transaction.setDateOfReturn(dateOfReturn);

						// Opening a new transaction for a particular book by a particular student
						transaction.setTransactionStatus("open");

						// persisting the transaction
						mgr.persist(transaction);
						return transaction;
					} else {
						throw new BookCopiesNotAvailableException(
								"There are no copies of book: " + b.getBookName() + " available.");
					}
				} else {
					System.out.println("You already have taken this book.");
					throw new SameBookAlreadyTakenException("You already have taken this book. Kindly return to borrow"
							+ "it again.");
				}

			} catch (NoResultException e) {
				throw new BookCopiesNotAvailableException("Invalid book Id or student Id enetered.");
			}

		}

	}

	@Override
	public Transactions returnABook(int studentId, int bookId, String rd) throws BookAlreadyReturnedException, ParseException {
		Transactions transaction = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date returnDate = sdf.parse(rd);
		try {
			transaction = mgr.createNamedQuery("getTransactionsForAStudentAndBook", Transactions.class)
					.setParameter("studentId", studentId).setParameter("bookId", bookId).getSingleResult();

			Date dateOfIssue = transaction.getDateOfIssue();

			// In case of actual returning of book, this date will be system date
			// transaction.setDateOfReturn(new Date());
			transaction.setDateOfReturn(returnDate);

			// finding the overdue
			long diffInMillies = Math.abs(dateOfIssue.getTime() - returnDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

			// calculating fine amount to be paid based on overdue
			double amount = returnDueAmount((int) diff);
			transaction.setAmount(amount);

			//Checking if the book has already been returned, by checking the value of 
			//Transaction Status for OPEN or CLOSED
			//OPEN - Book has not been returned
			//CLOSED - Book has been returned
			if (transaction.getTransactionStatus().equalsIgnoreCase("open")) {
				transaction.getBook().setNoOfCopies((transaction.getBook().getNoOfCopies()) + 1);
				transaction.setTransactionStatus("closed");
				return mgr.merge(transaction);
			} else {
				throw new BookAlreadyReturnedException("Book has already been returned.");
			}

		} catch (NoResultException e) {
			// When wrong transactionId is enetered
			throw new BookAlreadyReturnedException("Invalid Transaction Id. Please check your transactionId.");
		}

	}

	@Override
	public boolean reviewABook(int transactionId, String review) {
		Transactions transaction = mgr.createNamedQuery("findTransactionById", Transactions.class)
				.setParameter("transactionId", transactionId).getSingleResult();
		transaction.setReview(review);
		mgr.merge(transaction);
		return true;
	}

	@Override
	public double returnDueAmount(int differenceInDays) {
		double amount = 0;
		//Calculating overdue Amount only when number of days is greater
		//than fixed number of days
		if (differenceInDays > fixedDaysToReturnBook) {
			amount = 10 * (differenceInDays - fixedDaysToReturnBook);
			return amount;
		}
		return amount;
	}

	@Override
	public List<Transactions> getListOfBooksTakenByStudent(int studentId) {
		// get all transactions involving a particular student
		List<Transactions> listOfAllTransactions = mgr
				.createNamedQuery("getListOfBooksTakenByStudent", Transactions.class)
				.setParameter("studentId", studentId).getResultList();
		return listOfAllTransactions;
	}

	@Override
	public List<Transactions> getListOfPeopleTakingABook(int bookId) {
		List<Transactions> listOfAllTransactions = mgr
				.createNamedQuery("getListOfPeopleTakingABook", Transactions.class).setParameter("bookId", bookId)
				.getResultList();
//		Getting list of people taking a particular book, can be used to DEFINE POPULARITY OF BOOK
//		in frontend
		return listOfAllTransactions;
	}

	@Override
	public List<Transactions> listAllTransactions() {
		return mgr.createNamedQuery("viewAllTransactions", Transactions.class).getResultList();
	}

//	@Override
//	public LinkedHashMap<Book, Integer> viewPopularityOfBook() {
//		List<Book> listOfAllBooks = mgr.createNamedQuery("findAllBooks", Book.class).getResultList();
//		HashMap<Book, Integer> popularityTable = new HashMap<>();
//		for(Book book : listOfAllBooks) {
//			popularityTable.put(book, getListOfPeopleTakingABook(book.getBookId()).size());
//		}
//		LinkedHashMap<Book, Integer> sortedpopularityTable = new LinkedHashMap<>();
//		popularityTable.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
//		forEachOrdered(x-> sortedpopularityTable.put(x.getKey(), x.getValue()));
//		
//		return sortedpopularityTable;
//	}
	
	@Override
	public List<CustomPopularityObject> viewPopularityOfBook() {
		List<Book> listOfAllBooks = mgr.createNamedQuery("findAllBooks", Book.class).getResultList();
		List<CustomPopularityObject> list = new ArrayList<>();
		for(Book book:listOfAllBooks) {
			list.add(new CustomPopularityObject(book, 
					getListOfPeopleTakingABook(book.getBookId()).size()));	
		}
		
		 list.sort(new Comparator<CustomPopularityObject>() {

			@Override
			public int compare(CustomPopularityObject o1, CustomPopularityObject o2) {
				if(o1.getCount()>o2.getCount()) {
					return -1;
				}
				else if(o1.getCount()<o2.getCount()) {
					return 1;
				}
				else {
					return 0;
				}
			}
		});
		 return list;
	}

}
