package com.mychurch.user;

public class User {
	private String userName;
	private String password;
	private boolean admin;
	public User(String u, String p, int a){
		userName = u;
		password = p;
		admin = setAdmin(a);
	}
	private boolean setAdmin(int a){
		if(a == 1) return true;
		return false;
	}
	public boolean isPassword(String attempt){
		if(password.equals(attempt)) return true;
		return false;
	}
	public String getUserName(){
		return userName;
	}
	public boolean isAdmin(){
		if(admin) return true;
		return false;
	}
}
