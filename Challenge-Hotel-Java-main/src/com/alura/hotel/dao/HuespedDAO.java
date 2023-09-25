package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.Huesped;
import com.alura.hotel.modelo.Reserva;

public class HuespedDAO {

	private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Huesped huesped, Reserva reserva) {

		System.out.println("ESTE ES EL METODO GUARDAR DE HUESPED");
		
		try {
			PreparedStatement statement2;
			statement2 = con.prepareStatement("INSERT INTO RESERVAS "
					+ "(id, fecha_entrada, fecha_salida, valor, forma_Pago)" + " VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			try (statement2) {
				statement2.setString(1, reserva.getId());
				statement2.setString(2, reserva.getFechaEntrada());
				statement2.setString(3, reserva.getFechaSalida());
				statement2.setInt(4, reserva.getValor());
				statement2.setString(5, reserva.getFomaPago());

				statement2.execute();

				final ResultSet resultSet = statement2.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {
						huesped.setId(resultSet.getInt(1));

						System.out.println(String.format("Fue insertado el huesped: %s", huesped));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			PreparedStatement statement;
			statement = con.prepareStatement("INSERT INTO HUESPEDES "
					+ "(nombre, apellido, fecha_Nacimiento, nacionalidad, telefono, id_reserva)" + " VALUES (?, ?, ?, ?, ?,?)",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setString(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setString(6, huesped.getIdReserva());
				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {
						huesped.setId(resultSet.getInt(1));

					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		

	}

	public List<Huesped> listar() {
		List<Huesped> resultado = new ArrayList<>();

		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, NOMBRE, APELLIDO, FECHA_NACIMIENTO , NACIONALIDAD, TELEFONO, ID_RESERVA FROM HUESPEDES");

			try (statement) {
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						resultado.add(new Huesped(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
								resultSet.getString("APELLIDO"), resultSet.getDate("FECHA_NACIMIENTO"),
								resultSet.getString("NACIONALIDAD"), resultSet.getString("TELEFONO"), resultSet.getString("ID_RESERVA")));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return resultado;
	}

	public int modificarHuespedes(Integer id, String nombre, String apellido, String nacionalidad, String telefono, String idReserva) {
		
		System.out.println(idReserva);
		
		try {
			final PreparedStatement statement = con
					.prepareStatement("UPDATE HUESPEDES SET " + " NOMBRE = ?, " + " APELLIDO = ?,"
							+ " NACIONALIDAD = ?," + " TELEFONO = ?," + " ID_RESERVA = ?"+" WHERE ID = ?");

			
			
			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setString(3, nacionalidad);
				statement.setString(4, telefono);
				statement.setString(5, idReserva);
				statement.setInt(6, id);
				
				System.out.println(statement);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huesped> listarBusqueda(String textoBuscado) {
		List<Huesped> resultado = new ArrayList<>();

		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, NOMBRE, APELLIDO, FECHA_NACIMIENTO , NACIONALIDAD, TELEFONO, ID_RESERVA FROM HUESPEDES WHERE NOMBRE LIKE '%' ? '%'");

			try (statement) {
				statement.setString(1, textoBuscado);
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						resultado.add(new Huesped(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
								resultSet.getString("APELLIDO"), resultSet.getDate("FECHA_NACIMIENTO"),
								resultSet.getString("NACIONALIDAD"), resultSet.getString("TELEFONO"), resultSet.getString("ID_RESERVA")));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return resultado;
	}

//	public int borrarHuespedes(Integer id) {
//		try {
//			final PreparedStatement statement = con.prepareStatement("DELETE FROM HUESPEDES WHERE ID = ?");
//
//			try (statement) {
//				statement.setInt(1, id);
//				statement.execute();
//
//				int updateCount = statement.getUpdateCount();
//
//				return updateCount;
//			}
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}


	public int borrarHuespedes(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE HUESPEDES, RESERVAS "
																		+ "FROM HUESPEDES "
																		+ "JOIN RESERVAS "
																		+ "ON HUESPEDES.ID_RESERVA = RESERVAS.ID "
																		+ "WHERE HUESPEDES.ID = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
