package com.cg.lms.repo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.lms.dto.Student;
import com.cg.lms.exception.InvalidLoginException;

@Repository
@Transactional
public class UsersRepoImpl implements UsersRepo{
	
	@Autowired
	private EntityManager mgr;

	@Override
	public Student registerStudent(Student s) {
		 mgr.persist(s);
		return s;
	}

	@Override
	public Student validateStudentLogin(String userName, String password) throws InvalidLoginException {
		Student student = null;
		try {
			student = mgr.createNamedQuery("validateLogin", Student.class).setParameter("userName", userName).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			throw new InvalidLoginException("Invalid Login Credentials.");
		}
		return student;
	}

}
