package com.cg.lms.controller;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.lms.dto.Book;
import com.cg.lms.dto.Transactions;
import com.cg.lms.exception.BookAlreadyTakenBySomeoneException;
import com.cg.lms.exception.BookNotFoundException;
import com.cg.lms.repo.BookRepo;
import com.cg.lms.repo.TransactionRepo;

/**
 * 
 * @author s54
 * @version 1.0
 * @apiNote Controller for managing books which include add, remove, retrieve books. 
 *
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	private BookRepo repo;
	
	@Autowired
	private TransactionRepo txnrepo;

	/**
	 * 
	 * @param b
	 * @return the persisted book
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
	 * @return book with the given Id
	 * @throws BookNotFoundException
	 * 		 when book with the given Id is not found
	 */
	// http://localhost:8881/book/getById?bookId=1
	@GetMapping(value = "/getById")
	Book getBookById(@RequestParam int bookId) throws BookNotFoundException {
		Book book = null;
		try {
			book = repo.findById(bookId).get();
			
		} catch (NoSuchElementException e) {
			throw new BookNotFoundException("Book not found with id: " + bookId);
		}
		return book;
	}


	/**
	 * 
	 * @param bookId
	 * @return true, if book is removed successfully 
	 * 			false, if book can't be removed
	 * @throws BookNotFoundException
	 * 			when book with given Id cannot be found
	 * @throws BookAlreadyTakenBySomeoneException 
	 * 			when an attempt is made to delete an already issued book
	 */
	// http://localhost:8881/book/delete?bookId=1
	@GetMapping(value = "/delete")
	boolean deleteABook(@RequestParam int bookId) throws BookNotFoundException, BookAlreadyTakenBySomeoneException {
		Book b = getBookById(bookId);
		List<Transactions> transactions = txnrepo.findTransactionByBookId(bookId);
		for(Transactions txn:transactions) {
			if(txn.getTransactionStatus().equalsIgnoreCase("open")) {
				throw new BookAlreadyTakenBySomeoneException("Book has already been taken by someone.");
			}
		}
		if (b == null || b.getBookStatus().equalsIgnoreCase("removed")) {
			System.out.println("Book: " + b.getBookName() + " already removed!");
			return false;
		} else {
			b.setBookStatus("removed");
			repo.save(b);
			return true;
		}
	}
	
	/**
	 * 
	 * @param interest
	 * @return list of books by interest
	 */
	//http://localhost:8881/book/getBooksByInterest
	@GetMapping(value = "getBooksByInterest")
	Set<Book> getBooksByInterest(@RequestParam String interest){
		Set<Book> booksBasedOnInterest = new HashSet<>();
		if(interest.contains("Love")) {
			booksBasedOnInterest.addAll(repo.findBookByGenre("Love"));
		}
		if(interest.contains("Fiction")) {
			booksBasedOnInterest.addAll(repo.findBookByGenre("Fiction"));
		}
		if(interest.contains("Crime")) {
		booksBasedOnInterest.addAll(repo.findBookByGenre("Crime"));
		}
		if(interest.contains("Mystery")) {
			booksBasedOnInterest.addAll(repo.findBookByGenre("Mystery"));
		}
		return booksBasedOnInterest;
	}
	
	/**
	 * 
	 * @param something
	 * @return set of results based on search 
	 * @throws BookNotFoundException
	 * 			when no results can be found
	 */
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
