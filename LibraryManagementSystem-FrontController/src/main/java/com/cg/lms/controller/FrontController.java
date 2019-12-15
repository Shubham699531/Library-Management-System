package com.cg.lms.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.Student;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyReturnedException;
import com.cg.lms.exception.BookCopiesNotAvailableException;
import com.cg.lms.exception.BookNotFoundException;
import com.cg.lms.exception.InvalidLoginException;
import com.cg.lms.exception.SameBookAlreadyTakenException;

@RestController
@RequestMapping(value = "/front")
@CrossOrigin(origins = "http://localhost:4200")
public class FrontController {
	
	@Autowired
	private RestTemplate template;
	
	//http://localhost:8880/front/register
		@PostMapping(value = "/register")
		Student registerStudent(@RequestBody Student s) {
			return template.postForObject("http://localhost:8883/users/register", s, Student.class);
		}
		
		//http://localhost:8880/front/validateLogin?userName=1&password="XYZ"
		@GetMapping(value = "/validateLogin")
		Student validateStudentLogin(@RequestParam String userName, @RequestParam String password) throws InvalidLoginException {
			return template.getForObject("http://localhost:8883/users/validateLogin?userName=" + userName + "&password=" + password, Student.class);
		}
		
		//http://localhost:8881/front/add
		@PostMapping(value = "/add")
		Book addBook(@RequestBody Book b) {
			return template.postForObject("http://localhost:8881/book/add", b, Book.class);
		}

		// http://localhost:8880/front/getById?bookId=1
		@GetMapping(value = "/getById")
		Book getBookById(@RequestParam int bookId) throws BookNotFoundException {
			return template.getForObject("http://localhost:8881/book/getById?bookId=" + bookId, Book.class);
		}

		// http://localhost:8880/front/getByName?bookName=xyz
		@GetMapping(value = "/getByName")
		Book getBookByName(@RequestParam String bookName) throws BookNotFoundException {
			return template.getForObject("http://localhost:8881/book/getByName?bookName=" + bookName, Book.class);
		}

		// http://localhost:8880/front/getByGenre?genre=xyz
		@GetMapping(value = "/getByGenre")
		Book getBookByGenre(@RequestParam String genre) throws BookNotFoundException {
			return template.getForObject("http://localhost:8881/book/getByGenre?genre=" + genre, Book.class);
		}

		// http://localhost:8880/front/getByAuthor?author=xyz
		@GetMapping(value = "/getByAuthor")
		Book getBookByAuthor(@RequestParam String author) throws BookNotFoundException {
			return template.getForObject("http://localhost:8881/book/getByAuthor?author=" + author, Book.class);
		}
		
		// http://localhost:8880/front/getAll
		@GetMapping(value = "/getAll")
		List<Book> getAllBooks() throws BookNotFoundException {
			return Arrays.asList(template.getForObject("http://localhost:8881/book/getAll", Book[].class));
		}

		// http://localhost:8880/front/delete/{bookId}
		@GetMapping(value = "/delete/{bookId}")
		boolean deleteABook(@PathVariable int bookId) throws BookNotFoundException {
			return template.getForObject("http://localhost:8881/book/delete/{bookId}", boolean.class);
		}
		
		//http://localhost:8880/front/borrow?bookId=1&studentId=1
		@GetMapping(value = "/borrow")
		Transactions borrowABook(@RequestParam int bookId, @RequestParam int studentId) throws BookCopiesNotAvailableException, SameBookAlreadyTakenException {
			return template.getForObject("http://localhost:8882/transaction/borrow?bookId=" + bookId + "&studentId=" + studentId, Transactions.class);
		}
		
		//http://localhost:8880/front/return?transactionId=1&returnDate=2019/11/26
		@GetMapping(value = "/return")
		Transactions returnABook(@RequestParam int transactionId, @RequestParam(value = "returnDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnDate) throws BookAlreadyReturnedException {
			return template.getForObject("http://localhost:8882/transaction/return?transactionId=" + transactionId + "&returnDate=" + returnDate, Transactions.class);
		}
		
		//http://localhost:8880/front/review?transactionId=1&review=XYZ
		@GetMapping(value = "/review")
		boolean reviewABook(@RequestParam int transactionId, @RequestParam String review) {
			return template.getForObject("http://localhost:8882/transaction/review?transactionId=" + transactionId + "&review=" + review, boolean.class);
		}
		
		//http://localhost:8880/front/getListOfBooks?studentId=1
		@GetMapping(value = "/getListOfBooks")
		List<Book> getListOfBooksTakenByStudent(@RequestParam int studentId){
			return Arrays.asList(template.getForObject("http://localhost:8882/transaction/getListOfBooks?studentId=" + studentId, Book[].class));
		}
		
		//http://localhost:8880/front/getListOfStudents?bookId=1
		@GetMapping(value = "/getListOfStudents")
		List<Student> getListOfPeopleTakingABook(@RequestParam int bookId){
			return Arrays.asList(template.getForObject("http://localhost:8882/transaction/getListOfStudents?bookId=" + bookId, Student[].class));
		}
	
}
