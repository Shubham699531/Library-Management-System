package com.cg.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.lms.dto.Book;
import com.cg.lms.exception.BookNotFoundException;
import com.cg.lms.repo.BookRepo;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	private BookRepo repo;
	
	//http://localhost:8881/book/add
	@PostMapping(value = "/add")
	Book addBook(@RequestBody Book b) {
		b.setBookStatus("free");
		return repo.save(b);
	}
	
	//http://localhost:8881/book/getById?bookId=1
	@GetMapping(value = "/getById")
	Book getBookById(@RequestParam int bookId) throws BookNotFoundException {
		Book book = repo.findById(bookId).get();
		if(book == null) {
			throw new BookNotFoundException("Book not found with id: " + bookId);
		}
		else {
			return book;
		}
	}
	
	//http://localhost:8881/book/getByName?bookName="xyz"
	@GetMapping(value = "/getByName")
	Book getBookByName(@RequestParam String bookName) throws BookNotFoundException {
		Book book = repo.findBookByName(bookName);
		if(book == null) {
			System.out.println("No book found with this name: " + bookName);
			throw new BookNotFoundException("No book found with this name: " + bookName);
		}
		else {
			return book;
		}
	}
	
	//http://localhost:8881/book/getAll
	@GetMapping(value = "/getAll")
	List<Book> getAllBooks() throws BookNotFoundException {
		List<Book> listOfBooks = repo.findAll();
		if(listOfBooks.size()==0) {
			System.out.println("No books available currently!");
			throw new BookNotFoundException("No books available currently!");
		}
		else {
			return listOfBooks;
		}	
	}
	
	//http://localhost:8881/book/delete/{bookId}
	@GetMapping(value = "/delete/{bookId}")
	boolean deleteABook(@PathVariable int bookId) throws BookNotFoundException {
		Book b = getBookById(bookId);
		if(b == null || b.getBookStatus().equalsIgnoreCase("removed")) {
			System.out.println("Book: " + b.getBookName() + " already removed!");
			return false;
		}
		else {
			b.setBookStatus("removed");
			repo.save(b);
			return true;
		}		
	}

}

