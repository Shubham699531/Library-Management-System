package com.cg.lms.repo;

import java.text.ParseException;
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

	Transactions returnABook(int studentId, int bookId, String returnDate) throws BookAlreadyReturnedException, ParseException;
	
	List<Transactions> getListOfBooksTakenByStudent(int studentId);
	
	List<Transactions> getListOfPeopleTakingABook(int bookId);
	
	List<Transactions> listAllTransactions();

}
