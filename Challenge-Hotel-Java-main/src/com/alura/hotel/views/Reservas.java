package com.alura.hotel.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;

import com.alura.hotel.modelo.Reserva;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Reservas extends JFrame {

	private JPanel contentPane;
	public static JTextField txtValor;//private

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reservas frame = new Reservas();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Reservas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Reservas.class.getResource("/imagenes/calendario.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 540);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(0, 0, 900, 502);
		contentPane.add(panel);
		panel.setLayout(null);

		
		Calendar hoy = Calendar.getInstance();

		/*
		 * txtFechaS y txtFechaE tienen como fecha minima posible (.setrMinSelectableDate) a la fecha del dia de la reserva
		 * eso impide generar una reserva con fecha pasada.
		 */
		
		JDateChooser txtFechaE = new JDateChooser();
		txtFechaE.setBounds(88, 166, 235, 33);
		txtFechaE.setMinSelectableDate(hoy.getTime());
		panel.add(txtFechaE);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha de Check In");
		lblNewLabel_1.setBounds(88, 142, 133, 14);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Fecha de Check Out");
		lblNewLabel_1_1.setBounds(88, 210, 133, 14);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1_1);

		JDateChooser txtFechaS = new JDateChooser();
		txtFechaS.setBounds(88, 234, 235, 33);
		txtFechaS.getCalendarButton().setBackground(Color.WHITE);
		
		
		txtFechaS.setMinSelectableDate(hoy.getTime());

		panel.add(txtFechaS);

		/*
		 * se sobreescribio el propertyChange del txtFechaE para que desde el momento que 
		 * recibe el cambio de estado le pase a txtFechaS la fecha seleccionada (para facilitar
		 * la eleccion de fecha de salida
		 * 
		 */
		txtFechaE.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
			txtFechaS.setDate(txtFechaE.getDate());
			}
		});
	/*
	 * se sobreescribio el propertyChange del txtFechaS para que desde el momento que 
	 * recibe el cambio de estado genere el valor (se realizó sobre la salida porque 
	 * está inhabilitado hasta poner la fecha de entrada
	 * 
	 */
		txtFechaS.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {

				if (txtFechaS.getDate() != null && txtFechaE.getDate() != null) {

					java.util.Date fechaEntrada = txtFechaE.getDate();
					java.util.Date fechaSalida = txtFechaS.getDate();
					
					int controlFecha = fechaEntrada.compareTo(fechaSalida);

					if (controlFecha == 1) {
						JOptionPane.showMessageDialog(null,
								"Verifique las fechas, la entrada no puede ser posterior a la salida",
								"Error en la carga", 0);
						main(null);
					}

					java.sql.Date entrada = new java.sql.Date(fechaEntrada.getTime());
					java.sql.Date salida = new java.sql.Date(fechaSalida.getTime());

					int valorNoche = 100;
					long valorReserva = valorNoche * (calcularDias(entrada.toString(), salida.toString()));

					txtValor.setText(Long.toString(valorReserva));
			
				

				}
			
			}
		});

		txtValor = new JTextField();
		txtValor.setBounds(88, 303, 235, 33);
		txtValor.setEnabled(false);
		//txtValor.setText("100");
		panel.add(txtValor);
		txtValor.setColumns(10);

		JLabel lblNewLabel_1_1_1 = new JLabel("Valor de la Reserva");
		lblNewLabel_1_1_1.setBounds(88, 278, 133, 14);
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1_1_1);

		JComboBox txtFormaPago = new JComboBox();
		txtFormaPago.setBounds(88, 373, 235, 33);
		txtFormaPago.setFont(new Font("Arial", Font.PLAIN, 14));
		txtFormaPago.setModel(new DefaultComboBoxModel(
				new String[] { "Tarjeta de Crédito", "Tarjeta de Débito", "Dinero en efectivo" }));
		panel.add(txtFormaPago);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Forma de pago");
		lblNewLabel_1_1_1_1.setBounds(88, 347, 133, 24);
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1_1_1_1);

		JLabel lblNewLabel_4 = new JLabel("Sistema de Reservas");
		lblNewLabel_4.setBounds(108, 93, 199, 42);
		lblNewLabel_4.setForeground(new Color(65, 105, 225));
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblNewLabel_4);

		/*
		 * Boton Reservar: pasa como parámetro los valores obtenidos en este menu
		 * (txtFechaE.getDate(), txtFechaS.getDate, txtValor.getText, txtFormaPago.getSelectedItem.toString()) 
		 * para que en menu siguiente guarde el registro completo en las dos tablas de la base de datos
		 * 
		 */	
		
		JButton btnReservar = new JButton("Continuar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txtFechaE.getDate() == null) {
					
						JOptionPane.showMessageDialog(null,
								"Verifique las fechas, debe ingresar entrada y salida",
								"Error en la carga", 0);

					}
		
				else
				{
				
				
				RegistroHuesped huesped = new RegistroHuesped(txtFechaE.getDate(), txtFechaS.getDate(), txtValor.getText(), txtFormaPago.getSelectedItem().toString());
				huesped.setVisible(true);

				
				
				
				
				dispose();}
			}
		});
		btnReservar.setForeground(Color.WHITE);
		btnReservar.setBounds(183, 436, 140, 33);// (203, 436, 115, 33);//
		btnReservar.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/calendario.png")));
		btnReservar.setBackground(new Color(65, 105, 225));
		btnReservar.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(btnReservar);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(399, 0, 491, 502);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, -16, 500, 539);
		panel_1.add(lblNewLabel);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/reservas-img-2.png")));

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(15, 6, 104, 107);
		panel.add(lblNewLabel_2);
	}

	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	
	/*
	 * calcularDias(): recibe los parametros entrada y salida para realizar el calculo
	 * devuelve un long (realmente puse long porque pense en cobrar numeros redondos)
	 * que es la cantidad de dias que va a estar, deberia haber regresado un int como mucho
	 */
	public int calcularDias(String entrada, String salida) {

		LocalDate localDateEntrada = LocalDate.parse(entrada.toString());
		LocalDate localDateSalida = LocalDate.parse(salida.toString());
		int resultado = (int) ChronoUnit.DAYS.between(localDateEntrada, localDateSalida);
		System.out.println("dias en el medio" + resultado);

		return resultado;

	}

	public Reserva reserva(Date entrada, Date salida, int valor, String formaPago) {
		var reserva = new Reserva(entrada, salida, valor, formaPago);

		return reserva;
	}

}
