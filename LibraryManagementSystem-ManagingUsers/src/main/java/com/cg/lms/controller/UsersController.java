package com.cg.lms.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.lms.dto.CustomLoginObject;
import com.cg.lms.dto.Librarian;
import com.cg.lms.dto.Student;
import com.cg.lms.exception.InvalidLoginException;
import com.cg.lms.repo.UsersRepo;

@Transactional
@RestController
@RequestMapping(value = "/users")
public class UsersController {
	
	@Autowired
	private UsersRepo repo;
	
	//http://localhost:8883/users/register
	@PostMapping(value = "/register")
	Student registerStudent(@RequestBody Student s){
		return repo.registerStudent(s);
	}
	
	//http://localhost:8883/users/registerLib
		@PostMapping(value = "/registerLib")
		Librarian registerLibrarian(@RequestBody Librarian librarian){
			return repo.registerLibrarian(librarian);
		}
	
	//http://localhost:8883/users/validateLogin?userName=1&password="XYZ"
	@GetMapping(value = "/validateLogin")
	CustomLoginObject validateLogin(@RequestParam String userName, @RequestParam String password) throws InvalidLoginException {
		return repo.validateLogin(userName, password);	
	}

}
