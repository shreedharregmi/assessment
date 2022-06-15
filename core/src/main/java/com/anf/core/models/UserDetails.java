package com.anf.core.models;

public class UserDetails {
	private String firstName;
	private String lastName;
	private int age;
	private String contry;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public UserDetails(String firstName, String lastName, int age, String contry) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.contry = contry;
	}

}
