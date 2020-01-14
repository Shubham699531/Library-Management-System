package com.cg.lms.repo;

import java.util.List;

import com.cg.lms.dto.CustomLoginObject;
import com.cg.lms.dto.Librarian;
import com.cg.lms.dto.Student;
import com.cg.lms.exception.InvalidLoginException;

public interface UsersRepo {
	
	/**
	 * 
	 * @param student
	 * @return the registered student details
	 */
	Student registerStudent(Student student);
	
	/**
	 * 
	 * @param librarian
	 * @return the registered librarian details
	 */
	Librarian registerLibrarian(Librarian librarian);
	
	/**
	 * 
	 * @return list of all librarians 
	 */
	List<Librarian> listAllLibrarians();
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return object containing details of student or librarian, if login was successful 
	 * @throws InvalidLoginException
	 * 			when login credentials are not correct
	 */
	CustomLoginObject validateLogin(String userName, String password) throws InvalidLoginException;
	
}
