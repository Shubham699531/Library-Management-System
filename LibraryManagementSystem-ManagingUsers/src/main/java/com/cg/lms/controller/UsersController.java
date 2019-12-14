package com.cg.lms.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.lms.dto.Student;
import com.cg.lms.repo.UsersRepo;

@Transactional
@RestController
@RequestMapping(value = "/users")
public class UsersController {
	
	@Autowired
	private UsersRepo repo;
	
	//http://localhost:8883/users/register
	@PostMapping(value = "/register")
	Student registerStudent(@RequestBody Student s) {
		return repo.registerStudent(s);
	}
	
	//http://localhost:8883/users/validateLogin?userName=1&password="XYZ"
	@GetMapping(value = "/validateLogin")
	Student validateStudentLogin(@RequestParam String userName, @RequestParam String password) {
		return repo.validateStudentLogin(userName, password);
	}

}
