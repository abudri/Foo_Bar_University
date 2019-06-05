// built with Source > generate getters/setters, and construtors, ecplise helped generate lots of code

package com.luv2code.web.jdbc;

public class Student {

	private int id;
	private String firstName;
	private String lastname;
	private String email;
	
	public Student(int id, String firstName, String lastname, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastname = lastname;
		this.email = email;
	}

	public Student(String firstName, String lastname, String email) { // this is another construtor withouth id instance variable
		super();
		this.firstName = firstName;
		this.lastname = lastname;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override  // generated with Source > generate toString
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastname=" + lastname + ", email=" + email + "]";
	}
}
