package com.cg.lms.dto;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cg.lms.repo.UsersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DemoDataEntry {

	@Autowired
	private UsersRepo repo;
	
	@EventListener //Listens for an event emitted as method parameter
	public void onAppReady(ApplicationReadyEvent event) { //Here, Event Listener is
//		listening on Application Ready Event, which emits an event when 
//		the application is fully loaded and ready to accept service requests
		if (repo.listAllLibrarians().size() == 0) { //Checking the size of librarian table in database,
//			prevents duplication of values
			try {
//				Gives read and write privileges of JSON/ POJO
				ObjectMapper mapper = new ObjectMapper();
			
//				Reading values from librarians.json file and storing in books array
				Librarian[] librarians = mapper.readValue(getClass()
						.getClassLoader().getResource
						("librarians.json"), Librarian[].class);
				
//				Calling repository method for saving librarians in database
				for(Librarian librarian: librarians) {
					repo.registerLibrarian(librarian);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
	}
}
