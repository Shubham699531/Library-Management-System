package com.cg.lms.dto;

public class CustomPopularityObject {

	private Book book;
	private int count;
	
	public CustomPopularityObject() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomPopularityObject(Book book, int count) {
		super();
		this.book = book;
		this.count = count;
	}

	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
