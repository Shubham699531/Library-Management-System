package com.cg.lms.repo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.CustomPopularityObject;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyReturnedException;
import com.cg.lms.exception.BookCopiesNotAvailableException;
import com.cg.lms.exception.SameBookAlreadyTakenException;

public interface TransactionRepo {
	/**
	 * 
	 * @param bookId
	 * @param studentId
	 * @return
	 * @throws BookCopiesNotAvailableException
	 * @throws SameBookAlreadyTakenException
	 */
	Transactions borrowABook(int bookId, int studentId) throws BookCopiesNotAvailableException, SameBookAlreadyTakenException;
	
	/**
	 * 
	 * @param transactionId
	 * @return
	 */
	double returnDueAmount(int transactionId);
	
	/**
	 * 
	 * @param transactionId
	 * @param review
	 * @return
	 */
	boolean reviewABook(int transactionId, String review);
	
	/**
	 * 
	 * @param studentId
	 * @param bookId
	 * @param returnDate
	 * @return
	 * @throws BookAlreadyReturnedException
	 * @throws ParseException
	 */
	Transactions returnABook(int studentId, int bookId, String returnDate) throws BookAlreadyReturnedException, ParseException;
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	List<Transactions> getListOfBooksTakenByStudent(int studentId);
	
	/**
	 * 
	 * @param bookId
	 * @return
	 */
	List<Transactions> getListOfPeopleTakingABook(int bookId);
	
	/**
	 * 
	 * @return
	 */
	List<Transactions> listAllTransactions();
	
	
	List<CustomPopularityObject> viewPopularityOfBook();

}
