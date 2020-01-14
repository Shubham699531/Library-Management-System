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
	
	@EventListener //Listens for an event emitted as method parameter
	public void onAppReady(ApplicationReadyEvent event) { //Here, Event Listener is
//		listening on Application Ready Event, which emits an event when 
//		the application is fully loaded and ready to accept service requests
		if (repo.findAll().size() == 0) { //Checking the size of books table in database,
//			prevents duplication of values
			try {
//				Gives read and write privileges of JSON/ POJO
				ObjectMapper mapper = new ObjectMapper();
				
//				Reading values from books.json file and storing in books array
				Book[] books = mapper.readValue(getClass()
						.getClassLoader().getResource
						("books.json"), Book[].class);
				
//				Calling repository method for saving books in database
				for(Book book: books) {
					repo.save(book);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
	}
}
