package com.cg.lms.repo;

//import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.lms.dto.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
	
	Set<Book> findBookByName(@Param("bookName") String bookName);
	
	Set<Book> findBookByGenre(@Param("genre") String genre);
	
	Set<Book> findBookByAuthor(@Param("author") String author);
	
	Set<Book> findAllBooks();

}
