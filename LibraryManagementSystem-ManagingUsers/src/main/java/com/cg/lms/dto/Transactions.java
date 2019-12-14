package com.cg.lms.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "txn_id_gen", sequenceName = "txn_id_gen", allocationSize = 1)
@NamedQuery(name = "findTransactionById", query = "FROM Transactions WHERE transactionId =:transactionId")
public class Transactions {
	
	@Id
	@GeneratedValue(generator = "txn_id_gen")
	private int transactionId;
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
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}	
	
}

