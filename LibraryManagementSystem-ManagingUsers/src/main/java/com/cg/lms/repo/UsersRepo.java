package com.cg.lms.repo;

import com.cg.lms.dto.Student;

public interface UsersRepo {
	
	Student registerStudent(Student s);
	
	Student validateStudentLogin(String userName, String password);

}
