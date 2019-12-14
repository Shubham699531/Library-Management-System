package com.cg.lms.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "book_seq", sequenceName = "BOOK_SEQ")
@NamedQuery(name = "Book.findBookByName", query = "SELECT b FROM Book b WHERE b.bookName =:bookName")
public class Book {
	
	public Book() {
		// TODO Auto-generated constructor stub
	}
	
	public Book(String bookName, String bookGenre, double bookPrice, String iSBN, String bookStatus,
			String bookAuthor, String bookDescription, int noOfPages, int noOfCopies) {
		super();
		this.bookName = bookName;
		this.bookGenre = bookGenre;
		this.bookPrice = bookPrice;
		this.ISBN = iSBN;
		this.bookStatus = bookStatus;
		this.bookAuthor = bookAuthor;
		this.bookDescription = bookDescription;
		this.noOfPages = noOfPages;
		this.noOfCopies = noOfCopies;
	}

	@Id
	@GeneratedValue(generator = "book_seq")
	private int bookId;
	@Column(length = 50)
	private String bookName;
	@Column(length = 50)
	private String bookGenre;
	private double bookPrice;
	@Column(length = 50, unique = true)
	private String ISBN;
	@Column(length = 50)
	private String bookStatus;
	@Column(length = 50)
	private String bookAuthor;
	@Column(length = 50)
	private String bookDescription;
	private int noOfPages;
	private int noOfCopies;
	
//	@OneToMany(mappedBy = "book")
//	private List<Transactions> bookTransactions;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookGenre() {
		return bookGenre;
	}

	public void setBookGenre(String bookGenre) {
		this.bookGenre = bookGenre;
	}

	public double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		this.ISBN = iSBN;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
//
//	public List<Transactions> getBookTransactions() {
//		return bookTransactions;
//	}
//
//	public void setBookTransactions(List<Transactions> bookTransactions) {
//		this.bookTransactions = bookTransactions;
//	}
	
}
