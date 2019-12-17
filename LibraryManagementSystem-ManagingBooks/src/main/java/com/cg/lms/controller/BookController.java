package com.cg.lms.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

/**
 * 
 * @author Shubham
 *
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	private BookRepo repo;

	/**
	 * 
	 * @param b
	 * @return
	 */
	// http://localhost:8881/book/add
	@PostMapping(value = "/add")
	Book addBook(@RequestBody Book b) {
		b.setBookStatus("available");
		return repo.save(b);
	}

	/**
	 * 
	 * @param bookId
	 * @return
	 * @throws BookNotFoundException
	 */
	// http://localhost:8881/book/getById?bookId=1
	@GetMapping(value = "/getById")
	Book getBookById(@RequestParam int bookId) throws BookNotFoundException {
		Book book = repo.findById(bookId).get();
		if (book == null) {
			throw new BookNotFoundException("Book not found with id: " + bookId);
		} else {
			return book;
		}
	}


	/**
	 * 
	 * @param bookId
	 * @return
	 * @throws BookNotFoundException
	 */
	// http://localhost:8881/book/delete/{bookId}
	@GetMapping(value = "/delete/{bookId}")
	boolean deleteABook(@PathVariable int bookId) throws BookNotFoundException {
		Book b = getBookById(bookId);
		if (b == null || b.getBookStatus().equalsIgnoreCase("removed")) {
			System.out.println("Book: " + b.getBookName() + " already removed!");
			return false;
		} else {
			b.setBookStatus("removed");
			repo.save(b);
			return true;
		}
	}
	//http://localhost:8881/book/search
	@GetMapping(value = "/search")
	Set<Book> generalizedSearch(@RequestParam String something) throws BookNotFoundException{
		if(something.equals("")) {
			Set<Book> findAllBooks = repo.findAllBooks();
			if(findAllBooks.size()==0) {
				throw new BookNotFoundException("No books available currently.");
			}
			else {
				return findAllBooks;
			}
			
		}
		else {
			Set<Book> searchNamedBooks = repo.findBookByName("%" + something + "%");
			Set<Book> searchGenreBooks = repo.findBookByGenre("%" + something + "%");
			Set<Book> searchAuthoredBooks = repo.findBookByAuthor("%" + something + "%");
			
			Set<Book> finalSet = new HashSet<>();
			finalSet.addAll(searchNamedBooks);
			finalSet.addAll(searchGenreBooks);
			finalSet.addAll(searchAuthoredBooks);
			if(finalSet.size()==0) {
				throw new BookNotFoundException("Sorry, No Results Found.");
			}
			else {
				return finalSet;
			}
			
		}
		
	}

}
