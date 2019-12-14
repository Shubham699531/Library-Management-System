package com.cg.lms.repo;

import java.util.Date;
import java.util.List;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookCopiesNotAvailableException;

public interface TransactionRepo {
	
	Transactions borrowABook(int bookId, int studentId) throws BookCopiesNotAvailableException;
	
	double returnDueAmount(int transactionId);

	boolean reviewABook(int transactionId, String review);

	Transactions returnABook(int transactionId, Date returnDate);
	
	List<Book> getListOfBooksTakenByStudent(int studentId);
	
	List<Student> getListOfPeopleTakingABook(int bookId);

}
