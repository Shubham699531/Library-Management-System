package com.cg.lms.dto;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cg.lms.repo.BookRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DemoDataEntry {

	@Autowired
	private BookRepo repo;
	
	@EventListener
	public void onAppReady(ApplicationReadyEvent event) {
		if (repo.findAll().size() == 0) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				
				Book[] books = mapper.readValue(getClass()
						.getClassLoader().getResource
						("books.json"), Book[].class);
				
				for(Book book: books) {
					repo.save(book);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
	}
}
