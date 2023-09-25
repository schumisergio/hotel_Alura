package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.Reserva;

public class ReservaDAO {

	private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Reserva reserva) {

		try {
			PreparedStatement statement;
			statement = con.prepareStatement("INSERT INTO RESERVAS"
					+ "(fecha_entrada, fecha_salida, valor, forma_pago)" + " VALUES (?, ?, ?, ?, )",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				statement.setString(1, reserva.getFechaEntrada());
				statement.setString(2, reserva.getFechaSalida());
				statement.setInt(3, reserva.getValor());
				statement.setString(4, reserva.getFomaPago());
				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {

					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> listar() {

		List<Reserva> resultado = new ArrayList<>();

		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVAS");

			try (statement) {
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						resultado.add(new Reserva(resultSet.getString("ID"), resultSet.getDate("FECHA_ENTRADA"),
								resultSet.getDate("FECHA_SALIDA"), resultSet.getInt("VALOR"),
								resultSet.getString("FORMA_PAGO")));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return resultado;
	}

	public List<Reserva> listarBusqueda(String textoBuscado) {
		List<Reserva> resultado = new ArrayList<>();

		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVAS WHERE ID LIKE '%' ? '%'");

			try (statement) {
				statement.setString(1, textoBuscado);
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						resultado.add(new Reserva(resultSet.getString("ID"), resultSet.getDate("FECHA_ENTRADA"),
								resultSet.getDate("FECHA_SALIDA"), resultSet.getInt("VALOR"),
								resultSet.getString("FORMA_PAGO")));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return resultado;
	}

	public int borrarReservas(String id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM RESERVAS WHERE ID = ?");

			try (statement) {
				statement.setString(1, id);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
