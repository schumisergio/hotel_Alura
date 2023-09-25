package com.alura.hotel.modelo;

import java.util.Date;

public class Reservas {

	private int id;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private String nacionalidad;
	private String telefono;
	private int idReserva;
	
	public Reservas(int id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono,
			int idReserva) {

		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}
	
	
}
