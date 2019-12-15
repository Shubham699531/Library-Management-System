package com.cg.lms.repo;

import com.cg.lms.dto.Student;
import com.cg.lms.exception.InvalidLoginException;

public interface UsersRepo {
	
	Student registerStudent(Student s);
	
	Student validateStudentLogin(String userName, String password) throws InvalidLoginException;

}
