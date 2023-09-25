package com.alura.hotel.modelo;

public class Empleados {

	private String user;
	private String pass;
	
	
	
	public Empleados(String user, String pass) {
		this.user = user;
		this.pass = pass;

	}
	

	public String getUser() {
		return user;
	}
	public String getPass() {
		return pass;
	}
	
	  public String toString() {
	        return String.format(
	                "{ user: %s, pass: %s}",
	                this.user, this.pass);
	       
	    }
	
}
