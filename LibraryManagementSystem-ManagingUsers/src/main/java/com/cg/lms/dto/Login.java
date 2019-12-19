package com.cg.lms.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "login_id_gen", sequenceName = "login_id_gen", allocationSize = 1)
@NamedQuery(name = "validateLogin", query = "FROM Login WHERE userName =:userName and password =:password")
public class Login {
	
	@Id
	@GeneratedValue(generator = "login_id_gen")
	private int loginId;
	private String userName;
	private String password;
	private String role;
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
