package com.cg.lms.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "book_id_gen", sequenceName = "book_id_gen", allocationSize = 1)
@NamedQuery(name = "Book.findBookByName", query = "SELECT b FROM Book b WHERE b.bookName like :bookName")
@NamedQuery(name = "Book.findBookByGenre", query = "SELECT b FROM Book b WHERE b.bookGenre like :genre")
@NamedQuery(name = "Book.findBookByAuthor", query = "SELECT b FROM Book b WHERE b.bookAuthor like :author")
@NamedQuery(name = "Book.findAllBooks", query = "SELECT b FROM Book b")
public class Book {
	
	@Id
	@GeneratedValue(generator = "book_id_gen")
	private int bookId;
	@Column(length = 50)
	private String bookName;
	@Column(length = 50)
	private String bookGenre;
	private double bookPrice;
	@Column(length = 50, unique = true)
	private String ISBN;
	//Status only needed for "available" and "removed"
	//Nothing to do with issued/not-issued
	@Column(length = 50)
	private String bookStatus;
	@Column(length = 50)
	private String bookAuthor;
	@Column(length = 50)
	private String bookDescription;
	private int noOfPages;
	private int noOfCopies;

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
	
}
