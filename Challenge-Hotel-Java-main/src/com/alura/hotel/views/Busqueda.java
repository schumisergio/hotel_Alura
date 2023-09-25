package com.alura.hotel.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alura.hotel.controller.HuespedController;
import com.alura.hotel.controller.ReservaController;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;

public class Busqueda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;

	private DefaultTableModel modelo;
	private DefaultTableModel modelo2;

	ReservaController reservaController = new ReservaController();
	HuespedController huespedController = new HuespedController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(647, 85, 158, 31);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		/**
		 * Boton Buscar: llama a borrarTablaHuespedes() y borrarTablaReserva() para limpiar los jtable
		 * y a la variable textoBuscado (que contiene la cadena del cuadro de busqueda) la pasa como
		 * parámetro para realizar la consulta en la base de datos, luego llama a filtrarTablaReserva() y
		 * a filtrarTablaBusqueda() con el parametro textoBuscado
		 */
		
		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textoBuscado = txtBuscar.getText();

				borrarTablaHuesped();
				borrarTablaReserva();

				huespedController.listarBusqueda(textoBuscado);

				filtrarTablaReserva(textoBuscado);
				filtrarTablaHuesped(textoBuscado);

			}
		});
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/lupa2.png")));
		btnBuscar.setBounds(815, 75, 54, 41);
		contentPane.add(btnBuscar);

		JButton btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/editar-texto.png")));
		btnEditar.setBackground(SystemColor.menu);
		btnEditar.setBounds(587, 416, 54, 41);
		contentPane.add(btnEditar);

		
		/**
		 * Boton editar: primero llama a seleccionoHuesped() para ver si hay una fila seleccionada, luego
		 * hace un optional donde toma cual es el registro seleccionado, almacena sus datos en variables (id, nombre, apellido,
		 * nacionalidad, telefono, idReserva) y los pasa como parametros para el metodo "modificarHuespedes()
		 */
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (seleccionoHuesped()) {
					JOptionPane.showMessageDialog(null, "Por favor, elije un item");
					return;
				}

				Optional.ofNullable(modelo.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
						.ifPresentOrElse(fila -> {

							Integer id = Integer.valueOf(modelo.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
							String nombre = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 1);
							String apellido = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 2);

							String nacionalidad = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 4);
							String telefono = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 5);
							String idReserva = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 6);


							var filasModificadas = huespedController.modificarHuespedes(id, nombre, apellido,
									nacionalidad, telefono, idReserva);

							JOptionPane.showMessageDialog(null,
									String.format("%d item modificado con éxito!", filasModificadas));
						}, () -> JOptionPane.showMessageDialog(null, "Por favor, elije un item"));
				
			}

			
			
		});

		JLabel lblNewLabel_4 = new JLabel("Sistema de Búsqueda");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel_4.setBounds(155, 42, 258, 42);
		contentPane.add(lblNewLabel_4);

		JButton btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
		});
		btnSalir.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cerrar-sesion 32-px.png")));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setBounds(815, 416, 54, 41);
		contentPane.add(btnSalir);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(10, 127, 874, 265);
		contentPane.add(panel);

		tbHuespedes = new JTable();
		tbHuespedes.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/persona.png")), tbHuespedes,
				null);

		/**
		 * Jtable tbHuespedes: crea la jtable e inicializa sus columnas, crea el primer registro con los nombres de los campos.
		 * luego se llama a cargarTablaHuesped()
		 */
		
		modelo = (DefaultTableModel) tbHuespedes.getModel();
		modelo.addColumn("Nro de Huesped");
		modelo.addColumn("Nombre");
		modelo.addColumn("Apellido");
		modelo.addColumn("Fecha de Nacimiento");
		modelo.addColumn("Nacionalidad");
		modelo.addColumn("Nro de Teléfono");
		modelo.addColumn("Nro de Reserva");

		Object[] listita = { "huesped", "nombre", "apellido", "nacimiento", "nacionalidad", "telefono", "Nro de Reserva" };

		modelo.addRow(listita);

		cargarTablaHuesped();

		JTable tbReservas = new JTable();
		tbReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/calendario.png")), tbReservas,
				null);

		
		/**
		 * Jtable tbReservas: crea la tabla e inicializa sus columnas, crea el primer registro con los nombres de los campos.
		 * luego se llama a cargarTablaReserva()
		 */

		modelo2 = (DefaultTableModel) tbReservas.getModel();
		modelo2.addColumn("Codigo de Reservas");
		modelo2.addColumn("Fecha de Entrada");
		modelo2.addColumn("Fecha de Salida");
		modelo2.addColumn("Valor total");
		modelo2.addColumn("Forma de Pago");
	//	modelo2.addColumn("Cliente");

		Object[] listita2 = { "codigo", "entrada", "salida", "valor", "pago"};//, "cliente" };

		modelo2.addRow(listita2);

		
		cargarTablaReserva();

		JButton btnEliminar = new JButton("");
		btnEliminar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/deletar.png")));
		btnEliminar.setBackground(SystemColor.menu);
		btnEliminar.setBounds(651, 416, 54, 41);
		contentPane.add(btnEliminar);

		/**
		 * Boton eliminar: se fija si hay un registro seleccionado
		 * luego crea un optional con el registro y llama a HuespedController.borrarHuespedes() con el id como parametro
		 * para eliminar el registro.
		 * llama a borrarTablaHuesped() y a cargarTablaHUesped() para mostrar el resultado final y a los de TbReservas
		  */
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (seleccionoHuesped()) {
					JOptionPane.showMessageDialog(null, "Por favor, elije un item");
					return;
				}

				Optional.ofNullable(modelo.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
						.ifPresentOrElse(fila -> {
							Integer id = Integer.valueOf(modelo.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());				
							var filasModificadas = huespedController.borrarHuespedes(id);
							borrarTablaHuesped();
							cargarTablaHuesped();
							borrarTablaReserva();
							cargarTablaReserva();
							
							JOptionPane.showMessageDialog(null,
									String.format("item eliminado con éxito!", filasModificadas));
						}, () -> JOptionPane.showMessageDialog(null, "Por favor, elije un item"));

			}

		});

		
		/**
		 * Boton Cancelar: borra y vuelve a cargar las tablas
		  */
		JButton btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cancelar.png")));
		btnCancelar.setBackground(SystemColor.menu);
		btnCancelar.setBounds(713, 416, 54, 41);
		contentPane.add(btnCancelar);

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarTablaReserva();
				borrarTablaHuesped();
				cargarTablaReserva();
				cargarTablaHuesped();
			}
		});

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(25, 10, 104, 107);
		contentPane.add(lblNewLabel_2);
		setResizable(false);
	}

	
	/*
	 * cargarTablaHuesped(): llama a huespedController.listar() para realizar la carga de la Jtable tbHuespedes
	 */
	private void cargarTablaHuesped() {
		var huespedes = this.huespedController.listar();

		huespedes.forEach(
				huesped -> modelo.addRow(new Object[] { huesped.getId(), huesped.getNombre(), huesped.getApellido(),
						huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(), huesped.getIdReserva() }));
	}

	/*
	 * cargarTablaReserva(): llama a reservaController.listar() para realizar la carga de la Jtable tbReserva
	 */
	private void cargarTablaReserva() {
		var reservas = this.reservaController.listar();
		
		reservas.forEach(reserva -> 
			modelo2.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
				reserva.getFechaSalida(), reserva.getValor(), reserva.getFomaPago() }));
	
	
	}

	
	/*
	 * filtrarTablaHuesped(): recibe como parametro el texto a buscar, llama a reservaController.listarBusqueda() 
	 * pasando el textoBuscado como parametro y despues realiza la carga de la JTable
	 */

	private void filtrarTablaHuesped(String textoBuscado) {
		var huespedes = this.huespedController.listarBusqueda(textoBuscado);

		huespedes.forEach(
				huesped -> modelo.addRow(new Object[] { huesped.getId(), huesped.getNombre(), huesped.getApellido(),
						huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(), huesped.getIdReserva() }));
	}
	/*
	 * filtrarTablaReserva(): recibe como parametro el texto a buscar, llama a reservaController.listarBusqueda() 
	 * pasando el textoBuscado como parametro y despues realiza la carga de la JTable
	 */
	private void filtrarTablaReserva(String textoBuscado) {
		var reservas = this.reservaController.listarBusqueda(textoBuscado);

		reservas.forEach(reserva -> modelo2.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
				reserva.getFechaSalida(), reserva.getValor(), reserva.getFomaPago() }));

	
	}

	/*
	 * borrarTablaHuesped() borra todos los registros de la Jtable menos el "0" que es el agredado manualmente (los rotulos)
	 */
	private void borrarTablaHuesped() {
		modelo.setRowCount(1);
	}
	
	/*
	 * borrarTablaReserva() borra todos los registros de la Jtable menos el "0" que es el agredado manualmente (los rotulos)
	 */
	private void borrarTablaReserva() {
		modelo2.setRowCount(1);

	}

	/*
	 * seleccionoHuesped(): retorna true en caso de tener seleccionado algun registro, sirve para validar modificaciones y eliminaciones 
	 * en la JTale tbHuespedes
	 */
	private boolean seleccionoHuesped() {
		return (tbHuespedes.getSelectedRowCount() == 0 || tbHuespedes.getSelectedColumnCount() == 0);

	}

	private boolean seleccionoReserva() {
		return (tbReservas.getSelectedRowCount()==0 || tbReservas.getSelectedColumnCount()==0);
	}
	
}
