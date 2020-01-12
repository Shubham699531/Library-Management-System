package com.cg.lms.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyReturnedException;
import com.cg.lms.exception.BookCopiesNotAvailableException;
import com.cg.lms.exception.SameBookAlreadyTakenException;
import com.cg.lms.repo.TransactionRepo;

@Transactional
@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionRepo repo;
	
	//http://localhost:8882/transaction/borrow?bookId=1&studentId=1
	@GetMapping(value = "/borrow")
	Transactions borrowABook(@RequestParam int bookId, @RequestParam int studentId) throws BookCopiesNotAvailableException, SameBookAlreadyTakenException {
		return repo.borrowABook(bookId, studentId);
	}
	//http://localhost:8882/transaction/return?studentId=1&bookId=1&returnDate=2019/11/26
	@GetMapping(value = "/return")
	Transactions returnABook(@RequestParam int studentId, @RequestParam int bookId, @RequestParam String returnDate) throws BookAlreadyReturnedException, ParseException {
		return repo.returnABook(studentId, bookId, returnDate);
	}
	
	//http://localhost:8882/transaction/review?transactionId=1&review="XYZ"
	@GetMapping(value = "/review")
	boolean reviewABook(@RequestParam int transactionId, @RequestParam String review) {
		return repo.reviewABook(transactionId, review);
	}
	
	//http://localhost:8882/transaction/getListOfBooks?studentId=1
	@GetMapping(value = "/getListOfBooks")
	List<Transactions> getListOfBooksTakenByStudent(@RequestParam int studentId){
		return repo.getListOfBooksTakenByStudent(studentId);
	}
	
	//http://localhost:8882/transaction/getListOfStudents?bookId=1
	@GetMapping(value = "/getListOfStudents")
	List<Transactions> getListOfPeopleTakingABook(@RequestParam int bookId){
		return repo.getListOfPeopleTakingABook(bookId);
	}
	
	//http://localhost:8882/transaction/listAll
	@GetMapping(value = "/listAll")
	List<Transactions> viewAllTransactions(){
		return repo.listAllTransactions();
	}
}
