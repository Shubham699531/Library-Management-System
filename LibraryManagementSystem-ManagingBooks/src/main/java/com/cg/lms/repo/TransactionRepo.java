package com.cg.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.lms.dto.Transactions;

@Repository
public interface TransactionRepo extends JpaRepository<Transactions, Integer>{
	
	List<Transactions> findTransactionByBookId(@Param("bookId") int bookId);

}
