package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.Empleados;

public class LoginDAO {

    private Connection con;

    public LoginDAO(Connection con) {
        this.con = con;
    }
    
   // public List<Empleados> listar() {
    public boolean listar(String usuario, String clave) {
        List<Empleados> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = con
                    .prepareStatement("SELECT  * FROM EMPLEADO WHERE USER = ? AND PASS = ?");
    
            try (statement) {
            	statement.setString(1, usuario);
				statement.setString(2, clave);
                statement.execute();
    
                final ResultSet resultSet = statement.getResultSet();
    
                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Empleados(
                                resultSet.getString("USER"),
                                resultSet.getString("PASS")));
                   
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    if (resultado.isEmpty()) {
    	return false;
    }
    else {
    	return true;
    	}
    
    }

  }

//        public List<Empleados> listar(Empleados empleado) {
//        List<Empleados> resultado = new ArrayList<>();
//        
//        String variable1 = empleado.toString();
//
//
//        
//        
//        if (variable1.equals("ADMIN")) {
//        	System.out.println("lo considera igual");
//        }
//        else {
//        	System.out.println("los considera diferentesADMIN");
//        }
//        
//        
//        try {
//            String sql = "SELECT USER, PASS "
//            + " FROM EMPLEADO"; // WHERE USER = ?";
//            System.out.println(sql);
//            
//            final PreparedStatement statement = con.prepareStatement(
//                    sql);
//    
//            try (statement) {
//               // statement.setString(1, empleado.getUser());
//                
//                System.out.println("EMPLEADO GET USER (dE LOGIN DAO ->>>" + empleado.getUser());
//                statement.execute();
//    
//                final ResultSet resultSet = statement.getResultSet();
//    
//                try (resultSet) {
//                    while (resultSet.next()) {
//                        resultado.add(new Empleados(
//                                
//                        		resultSet.getString("USER"),
//                                resultSet.getString("PASS")));
//                    
//                        System.out.println("imprimiendo resultado en dao" + resultado);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        
//        System.out.println("este es el resultado que se va a devovler>>> " + resultado);
//        return resultado;
//        
//        
//    }
//
//}
