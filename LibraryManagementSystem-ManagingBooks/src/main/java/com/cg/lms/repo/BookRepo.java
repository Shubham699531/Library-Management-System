package com.cg.lms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.lms.dto.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
	
	Book findBookByName(@Param("bookName") String bookName);

}
