package com.cg.lms.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "txn_gen", sequenceName = "TXN_GEN")
public class Transactions {
	
	@Id
	@GeneratedValue(generator = "txn_gen")
	private int transactionId;
//	private int bookId;
//	private int studentId;
	private Date dateOfIssue;
	private Date dateOfReturn;
	private double amount;
	@Column(length = 50)
	private String review;
	
	@ManyToOne
	@JoinColumn(name = "bookId")
	private Book book;
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
//	public int getBookId() {
//		return bookId;
//	}
//	public void setBookId(int bookId) {
//		this.bookId = bookId;
//	}
//	public int getStudentId() {
//		return studentId;
//	}
//	public void setStudentId(int studentId) {
//		this.studentId = studentId;
//	}
	public Date getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public Date getDateOfReturn() {
		return dateOfReturn;
	}
	public void setDateOfReturn(Date dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
}
