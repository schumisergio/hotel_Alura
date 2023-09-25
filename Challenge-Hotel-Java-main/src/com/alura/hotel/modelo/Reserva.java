package com.alura.hotel.modelo;

import java.util.Date;

public class Reserva {
	private String id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private int valor;
	private String formaPago;
	
	
	public Reserva(java.sql.Date entrada, java.sql.Date salida, int valor, String formaPago) {
		this.fechaEntrada = entrada;
		this.fechaSalida = salida;
		this.valor = valor;
		this.formaPago = formaPago;

}


	public Reserva(String idReserva, java.sql.Date entrada, java.sql.Date salida, int valor,
			String formaPago) {
		this.id = idReserva;
		this.fechaEntrada = entrada;
		this.fechaSalida = salida;
		this.valor = valor;
		this.formaPago = formaPago;


	}

	public String getFechaEntrada() {
		return fechaEntrada.toString();
	}


	public String getFechaSalida() {
		return fechaSalida.toString();
	}


	public int getValor() {
		return valor;
	}


	public String getFomaPago() {
		return formaPago;
	}


	public void setId(String id) {
		this.id = id;
		
	}


	public String getId() {
		return id;
	}

	 @Override
	    public String toString() {
	        return String.format(
	                "{ id: %s, fecha_entrada: %s, fecha_salida: %s, valor: %d, formaPago: %s}",
	                this.id, this.fechaEntrada, this.fechaSalida, this.valor, this.formaPago);
	       
	    }

	 
}
