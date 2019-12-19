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
	
	@EventListener
	public void onAppReady(ApplicationReadyEvent event) {
		if (repo.listAllLibrarians().size() == 0) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				
				Librarian[] librarians = mapper.readValue(getClass()
						.getClassLoader().getResource
						("librarians.json"), Librarian[].class);
				
				for(Librarian librarian: librarians) {
					repo.registerLibrarian(librarian);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
	}
}
