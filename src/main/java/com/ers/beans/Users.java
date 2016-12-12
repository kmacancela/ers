package com.ers.beans;

public class Users {
	private int usersId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private UserRoles role;
	
	public String fullName(){
		return firstName + " " + lastName;
	}
	
	public Users() {
		super();
	}

	public Users(int usersId, String username, String password, String firstName, String lastName, String email,
			UserRoles role) {
		super();
		this.usersId = usersId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRoleId(UserRoles role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "ERSUsers [usersId=" + usersId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", role=" + role.getUserRole() + "]";
	}
}

