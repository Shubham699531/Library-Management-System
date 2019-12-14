package com.cg.lms.repo;

import java.util.Date;

import com.cg.lms.dto.Transactions;

public interface TransactionRepo {
	
	Transactions borrowABook(int bookId, int studentId);
	
	double returnDueAmount(int transactionId);

	boolean reviewABook(int transactionId, String review);

	Transactions returnABook(int bookId, int studentId, int transactionId, Date returnDate);

}
