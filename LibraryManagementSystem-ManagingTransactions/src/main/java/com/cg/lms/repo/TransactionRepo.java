package com.cg.lms.repo;

import java.util.Date;
import java.util.List;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyReturnedException;
import com.cg.lms.exception.BookCopiesNotAvailableException;
import com.cg.lms.exception.SameBookAlreadyTakenException;

public interface TransactionRepo {
	
	Transactions borrowABook(int bookId, int studentId) throws BookCopiesNotAvailableException, SameBookAlreadyTakenException;
	
	double returnDueAmount(int transactionId);
	
	boolean reviewABook(int transactionId, String review);

	Transactions returnABook(int transactionId, Date returnDate) throws BookAlreadyReturnedException;
	
	List<Book> getListOfBooksTakenByStudent(int studentId);
	
	List<Student> getListOfPeopleTakingABook(int bookId);

}
