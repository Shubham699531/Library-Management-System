package com.cg.lms.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "student_id_gen", sequenceName = "student_id_gen", allocationSize = 1)
public class Student {
	
	@Id
	@GeneratedValue(generator = "student_id_gen")
	private int studentId;
	@Column(length = 50)
	private String name;
	@Column(length = 50)
	private String gender;
	private Date dob;
	@Column(length = 50)
	private String contactNo;
	@Column(length = 50, unique = true)
	private String email;
	@Column(length = 50, unique = true)
	private String userName;
	@Column(length = 50)
	private String password;
	private String intrests;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIntrests() {
		return intrests;
	}

	public void setIntrests(String intrests) {
		this.intrests = intrests;
	}
	
	
}
