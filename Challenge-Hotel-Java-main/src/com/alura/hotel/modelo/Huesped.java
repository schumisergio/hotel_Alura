package com.alura.hotel.modelo;

import java.util.Date;

public class Huesped {

	private int id;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private String nacionalidad;
	private String telefono;
	private String idReserva;
	
	public Huesped(int id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, String idReserva) {

		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public Huesped(String nombre, String apellido, Date fechaNac, String nacionalidad, String telefono, String idReserva) {
			
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNac;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;		
		this.idReserva = idReserva;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento.toString();
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setId(int id) {
		this.id = id;
		
	}

	public int getId() {
		return id;
	}
	
	public String getIdReserva() {
		return idReserva;
		
	}
	
	 @Override
	    public String toString() {
	        return String.format(
	                "{ id: %d, nombre: %s, apellido: %s, fecha_nacimiento: %s, nacionalidad: %s, telefono: %s, idReserva: %s}",
	                this.id, this.nombre, this.apellido, this.fechaNacimiento.toString(), this.nacionalidad, this.telefono, this.idReserva);
	       
	    }
}
