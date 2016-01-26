package org.chocolate.shop.entity;

public class User {

	private String uid;
	private String username;
	private String email;
	private String password;
	private String role;
	private boolean ban;

	public User() {
		super();
	}

	public User(String uid, String username, String email, String password, String role, boolean ban) {
		super();
		this.uid = uid;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.ban = ban;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isBan() {
		return ban;
	}

	public void setBan(boolean ban) {
		this.ban = ban;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", email=" + email + ", password=" + password + ", role=" + role + ", ban=" + ban
				+ "]";
	}

}
