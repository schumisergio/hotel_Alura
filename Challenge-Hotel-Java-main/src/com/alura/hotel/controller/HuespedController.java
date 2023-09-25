package com.alura.hotel.controller;

import java.util.List;

import com.alura.hotel.factory.ConnectionFactory;
import com.alura.hotel.modelo.Huesped;
import com.alura.hotel.modelo.Reserva;
import com.alura.hotel.dao.HuespedDAO;

public class HuespedController {
		
    private HuespedDAO huespedDao;
    
    public HuespedController () {
    	var factory = new ConnectionFactory();
    	this.huespedDao = new HuespedDAO(factory.recuperaConexion());
    }
    
    public void guardar (Huesped huesped, Reserva reserva) {
    	System.out.println("guardar del huesped controller");
    	huespedDao.guardar(huesped, reserva);
    }
    
    public List<Huesped> listar() {
        return huespedDao.listar();
    }

    public List<Huesped> listarBusqueda(String textoBuscado) {
        return huespedDao.listarBusqueda(textoBuscado);
    }
       
    public int modificarHuespedes(Integer id, String nombre, String apellido, String nacionalidad, String telefono, String idReserva) {
        return huespedDao.modificarHuespedes(id, nombre, apellido, nacionalidad, telefono, idReserva);
    }    
    
    public int borrarHuespedes(Integer id) {
        return huespedDao.borrarHuespedes(id);
    }
}
