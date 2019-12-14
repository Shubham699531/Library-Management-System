package com.cg.lms.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	//http://localhost:8881/book/getById?bookId=
	@GetMapping(value = "/getById")
	Book getBookById(@RequestParam int bookId) {
		return repo.findById(bookId).get();
	}
	
	//http://localhost:8881/book/getByName?bookName=
	@GetMapping(value = "/getByName")
	Book getBookByName(String bookName) {
		return repo.findBookByName(bookName);
	}
	
	//http://localhost:8881/book/getAll
	@GetMapping(value = "/getAll")
	List<Book> getAllBooks() {
		return repo.findAll();
	}
	
	//http://localhost:8881/book/delete/{bookId}
	@GetMapping(value = "/delete/{bookId}")
	boolean deleteABook(@PathVariable int bookId) {
		Book b = getBookById(bookId);
		if(b == null || b.getBookStatus().equalsIgnoreCase("removed")) {
			return false;
		}
		else {
			b.setBookStatus("removed");
			repo.save(b);
			return true;
		}		
	}

}
