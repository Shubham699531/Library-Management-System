package com.cg.lms.repo;

import java.util.List;

import com.cg.lms.dto.Librarian;
import com.cg.lms.dto.Student;
import com.cg.lms.exception.InvalidLoginException;

public interface UsersRepo {
	
	Student registerStudent(Student s);
	
	Librarian registerLibrarian(Librarian l);
	
	List<Librarian> listAllLibrarians();
	
	Object validateLogin(String userName, String password) throws InvalidLoginException;
	
	//Student validateStudentLogin(String userName, String password) throws InvalidLoginException;

}
