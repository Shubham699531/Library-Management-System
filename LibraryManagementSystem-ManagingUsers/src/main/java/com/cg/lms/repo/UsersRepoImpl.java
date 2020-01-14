package com.cg.lms.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.lms.AES.AES;
import com.cg.lms.dto.CustomLoginObject;
import com.cg.lms.dto.Librarian;
import com.cg.lms.dto.Login;
import com.cg.lms.dto.Student;
import com.cg.lms.exception.InvalidLoginException;

@Repository
@Transactional
public class UsersRepoImpl implements UsersRepo{
	static final String key = "A";
	
	@Autowired
	private EntityManager mgr;

	@Override
	public Student registerStudent(Student student){
		Login login = new Login();
		//Encrypting username and password before saving in database
		login.setUserName(AES.encrypt(student.getUserName(), key));
		login.setPassword(AES.encrypt(student.getPassword(), key));
		login.setRole("student");
		mgr.persist(login);
		mgr.persist(student);

		return student;
	}

	@Override
	public CustomLoginObject validateLogin(String userName, String password) throws InvalidLoginException {
		 try {
			 CustomLoginObject custom = new CustomLoginObject();
			Login login = mgr.createNamedQuery("validateLogin", Login.class).setParameter("userName", AES.encrypt(userName, key)).setParameter("password", AES.encrypt(password, key)).getSingleResult();
			if(login.getRole().equalsIgnoreCase("student")) {
				Student student = mgr.createNamedQuery("returnStudentByUserName", Student.class).setParameter("userName", userName).getSingleResult();
				custom.setStudent(student);
				custom.setLibrarian(null);
				return custom;
			}
			else {
				Librarian librarian = mgr.createNamedQuery("returnLibrarianByUserName", Librarian.class).setParameter("userName", userName).getSingleResult();
				custom.setLibrarian(librarian);
				custom.setStudent(null);
				return custom;
			}
		} catch (NoResultException e) {
			throw new InvalidLoginException("Invalid login credentials.");
		}
	}

	@Override
	public Librarian registerLibrarian(Librarian librarian) {
		Login login = new Login();
		//Encrypting username and password before saving in database
		login.setUserName(AES.encrypt(librarian.getUserName(), key));
		login.setPassword(AES.encrypt(librarian.getPassword(), key));
		login.setRole("librarian");
		mgr.persist(login);
		mgr.persist(librarian);
		return librarian;
	}

	@Override
	public List<Librarian> listAllLibrarians() {
		return mgr.createNamedQuery("listAllLibrarians", Librarian.class).getResultList();
	}

}
