package com.init.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User {
	
	@Id
	@Column(name="email", nullable=false, length=30)
	private String email;
	
	@Column(name="name", nullable=false, length=30)
	private String name;	
	
	@Column(name="password", nullable=false, length=100)
	private String password;
	
	@Column(name="puntaje", length=100)
	private String puntaje;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(String puntaje) {
		this.puntaje = puntaje;
	}
	
}
