package com.alura.hotel.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alura.hotel.controller.LoginController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	
	LoginController loginController = new LoginController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagenes/perfil-del-usuario.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/imagenes/hotel.png")));
		lblNewLabel.setBounds(-53, 0, 422, 499);
		contentPane.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(409, 181, 234, 33);
		contentPane.add(txtUsuario);
		
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Usuario");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(409, 156, 57, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(409, 261, 234, 33);
		contentPane.add(txtContrasena);
		
		
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Contraseña");
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1.setBounds(409, 236, 133, 14);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		
		
		/*
		 * boton login: toma el valor del txtUsuario y del txtContrasena y los asigna a las variables usuario y pass respectivamente
		 * luego, crea la variable boolean controlado y, le asigna el valor de loginController.listar() pasando las variables
		 * usuario y clave como parametros.
		 * en caso que el resultado sea true, carga el menu de usuario, caso contrario, muestra un mensaje de error
		 * avisando que debe verificar el usuario y la contraseña (sin especificar donde esta el error por un tema
		 * de seguridad) y vuelve a la pantalla de inicio.
		 */
		JButton btnLogin = new JButton("Login");
		btnLogin.setIcon(new ImageIcon(Login.class.getResource("/imagenes/perfil-del-usuario.png")));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String usuario = txtUsuario.getText();
				String clave = new String(txtContrasena.getPassword());
				
				boolean controlado = loginController.listar(usuario, clave);
				
				
				if (controlado) {
					MenuUsuario user = new MenuUsuario();
					user.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Verifique su USUARIO y CONTRASEÑA y vuelva a intentarlo", "Error de Login", 0, null);				
					dispose();
					MenuPrincipal.main(null);

				}
				
			}
		});
		btnLogin.setBounds(409, 322, 103, 33);
		contentPane.add(btnLogin);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(Login.class.getResource("/imagenes/cerrar-24px.png")));
	
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				dispose();
				MenuPrincipal.main(null);
			
			}
		});
		
		btnCancelar.setBounds(540, 322, 103, 33);
		contentPane.add(btnCancelar);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Genesys\\Documents\\imagenesAluraHotel\\Ha-100px.png"));
		lblNewLabel_1.setBounds(470, 30, 103, 94);
		contentPane.add(lblNewLabel_1);
	}

}
