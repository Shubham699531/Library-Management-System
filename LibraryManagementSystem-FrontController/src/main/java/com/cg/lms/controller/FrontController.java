package com.cg.lms.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.CustomLoginObject;
import com.cg.lms.dto.CustomPopularityObject;
import com.cg.lms.dto.Librarian;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyReturnedException;
import com.cg.lms.exception.BookAlreadyTakenBySomeoneException;
import com.cg.lms.exception.BookCopiesNotAvailableException;
import com.cg.lms.exception.BookNotFoundException;
import com.cg.lms.exception.InvalidLoginException;
import com.cg.lms.exception.SameBookAlreadyTakenException;
/**
 * 
 * @author s54
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/front")
@CrossOrigin(origins = "http://localhost:4200")
public class FrontController {
	
	//Autowiring Rest Template for communicating between microservices.
	@Autowired
	private RestTemplate template;
	
	/**
	 * Register a new student
	 * @param student
	 * @return details of saved student
	 */
	//http://localhost:8880/front/register
		@PostMapping(value = "/register")
		Student registerStudent(@RequestBody Student student) {
			return template.postForObject("http://localhost:8883/users/register", student, Student.class);
		}
		
		/**
		 * Register a new librarian
		 * @param librarian
		 * @return details of saved librarian 
		 */
		//http://localhost:8880/front/registerLib
		@PostMapping(value = "/registerLib")
		Librarian registerLibrarian(@RequestBody Librarian librarian) {
			return template.postForObject("http://localhost:8883/users/registerLib", librarian, Librarian.class);
		}
		
		/**
		 * Validate login
		 * @param userName
		 * @param password
		 * @return details of student or librarian
		 * @throws InvalidLoginException
		 * 		when login credentials are incorrect
		 */
		//http://localhost:8880/front/validateLogin?userName=1&password="XYZ"
		@GetMapping(value = "/validateLogin")
		CustomLoginObject validateStudentLogin(@RequestParam String userName, @RequestParam String password) throws InvalidLoginException {
			CustomLoginObject user = template.getForObject("http://localhost:8883/users/validateLogin?userName=" + userName + "&password=" + password, CustomLoginObject.class);
			return user;
		}
		
		/**
		 * Adding a new book
		 * @param b
		 * @return details of saved book
		 */
		//http://localhost:8880/front/add
		@PostMapping(value = "/add")
		Book addBook(@RequestBody Book b) {
			return template.postForObject("http://localhost:8881/book/add", b, Book.class);
		}

		/**
		 * Search a book by Id
		 * @param bookId
		 * @return book with the given bookId
		 * @throws BookNotFoundException
		 * 		when book with given Id cannot be found
		 */
		// http://localhost:8880/front/getById?bookId=1
		@GetMapping(value = "/getById")
		Book getBookById(@RequestParam int bookId) throws BookNotFoundException {
			return template.getForObject("http://localhost:8881/book/getById?bookId=" + bookId, Book.class);
		}

		/**
		 * Search for book/books
		 * @param something
		 * @return search results of books
		 * @throws BookNotFoundException
		 * 			when no book matches the searched term
		 */
		// http://localhost:8880/front/search?something=xyz
		@GetMapping(value = "/search")
		List<Book> genralizedSearch(@RequestParam String something) throws BookNotFoundException {
			return Arrays.asList(template.getForObject("http://localhost:8881/book/search?something=" + something, Book[].class));
		}

		/**
		 * Remove a book
		 * @param bookId
		 * @return true, if the book was successfully deleted
		 * @throws BookNotFoundException
		 * 			when book with given Id cannot be found
		 * @throws BookAlreadyTakenBySomeoneException
		 * 			when the book to be removed is already taken by someone
		 */
		// http://localhost:8880/front/delete?bookId=1
		@GetMapping(value = "/delete")
		boolean deleteABook(@RequestParam int bookId) throws BookNotFoundException,BookAlreadyTakenBySomeoneException {
			return template.getForObject("http://localhost:8881/book/delete?bookId=" + bookId, boolean.class);
		}
		
		/**
		 * Borrow a book
		 * @param bookId
		 * @param studentId
		 * @return transaction details
		 * @throws BookCopiesNotAvailableException
		 * 			when no copies of the requested book is available 
		 * @throws SameBookAlreadyTakenException
		 * 			when same book is already taken by the student and not returned 
		 */
		//http://localhost:8880/front/borrow?bookId=1&studentId=1
		@GetMapping(value = "/borrow")
		Transactions borrowABook(@RequestParam int bookId, @RequestParam int studentId) throws BookCopiesNotAvailableException, SameBookAlreadyTakenException {
			return template.getForObject("http://localhost:8882/transaction/borrow?bookId=" + bookId + "&studentId=" + studentId, Transactions.class);
		}
		
		/**
		 * Return a book
		 * @param studentId
		 * @param bookId
		 * @param returnDate
		 * @return transaction details
		 * @throws BookAlreadyReturnedException
		 * 			when an attempt is made to return an already returned book
		 */
		//http://localhost:8880/front/return?studentId=1&bookId=1&returnDate=2019/11/26
		@GetMapping(value = "/return")
		Transactions returnABook(@RequestParam int studentId,@RequestParam int bookId, @RequestParam String returnDate) throws BookAlreadyReturnedException {
			return template.getForObject("http://localhost:8882/transaction/return?studentId=" + studentId + "&bookId="+ bookId + "&returnDate=" + returnDate, Transactions.class);
		}
		
		/**
		 * Review a book
		 * @param transactionId
		 * @param review
		 * @return true, if review was successfully submitted
		 */
		//http://localhost:8880/front/review?transactionId=1&review=XYZ
		@GetMapping(value = "/review")
		boolean reviewABook(@RequestParam int transactionId, @RequestParam String review) {
			return template.getForObject("http://localhost:8882/transaction/review?transactionId=" + transactionId + "&review=" + review, boolean.class);
		}
		
		/**
		 * Get list of all books taken by student in past
		 * @param studentId
		 * @return list of transactions containing all the borrowed books by that student
		 */
		//http://localhost:8880/front/getListOfBooks?studentId=1
		@GetMapping(value = "/getListOfBooks")
		List<Transactions> getListOfBooksTakenByStudent(@RequestParam int studentId){
			return Arrays.asList(template.getForObject("http://localhost:8882/transaction/getListOfBooks?studentId=" + studentId, Transactions[].class));
		}
		
		/**
		 * Get list of students taking a particular book in past
		 * @param bookId
		 * @return list of transactions containing all the students who have taken this book
		 */
		//http://localhost:8880/front/getListOfStudents?bookId=1
		@GetMapping(value = "/getListOfStudents")
		List<Transactions> getListOfPeopleTakingABook(@RequestParam int bookId){
			return Arrays.asList(template.getForObject("http://localhost:8882/transaction/getListOfStudents?bookId=" + bookId, Transactions[].class));
		}
		
		/**
		 * Retrieve list of all past transactions
		 * @return list of all transactions
		 */
		//http://localhost:8880/front/listAllTransactions
		@GetMapping(value = "listAllTransactions")
		List<Transactions> listAllTransactions() {
			return Arrays.asList(template.getForObject("http://localhost:8882/transaction/listAll", Transactions[].class));
		}
		
		/**
		 * Search for a book by interest
		 * @param interest
		 * @return list of books which suits to the interest of student
		 */
		//http://localhost:8880/front/getBooksByInterest?interest=abc
		@GetMapping(value = "getBooksByInterests")
		Set<Book> getBooksByInterest(@RequestParam String interest){
			return template.getForObject("http://localhost:8881/book/getBooksByInterest?interest=" + interest, Set.class);	
		}
		
		@GetMapping(value = "/viewPopularity")
		List<CustomPopularityObject> viewPopularityOfBooks(){
			return Arrays.asList(template.getForObject("http://localhost:8882/transaction/viewPopularity", CustomPopularityObject[].class));
		}
	
}
