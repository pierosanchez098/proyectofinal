
package proyectofinal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class historico extends JFrame {
	
	private String nombreUsuario;
	private Connection conexion = book4u.obtenerConexion();


	
	public historico(String nombreUsuario, Connection conexion) {
		
		this.nombreUsuario = nombreUsuario;
		this.conexion = conexion;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(15, 82, 15));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(26, 31, 1032, 642);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(214, 232, 212));
		panel_1.setBounds(0, 0, 1080, 155);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel etiqueta2 = new JLabel();
		ImageIcon imagen  = new ImageIcon("imagenes/Logo_Book4U.jpg"); 
		etiqueta2.setBounds(05, 0, 170, 150);
		etiqueta2.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH)));
		panel_1.add(etiqueta2);
		
		
		JLabel lblNewLabel_1 = new JLabel("Mi Historial");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 34));
		lblNewLabel_1.setBounds(440, 49, 360, 49);
		panel_1.add(lblNewLabel_1);
		
		
		
		JLabel pie = new JLabel("Historial de reservas hechas:");
		pie.setFont(new Font("Arial", Font.BOLD, 22));
		pie.setBounds(128, 160, 321, 34);
		panel.add(pie);
		
		JLabel cifraActualizable = new JLabel("*Cifra de equivalencia*");
		cifraActualizable.setBounds(825, 243, 117, 52);
		panel.add(cifraActualizable);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(15, 82, 15));
		panel_2.setBounds(599, 160, 163, 43);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Número de créditos gastados:");
		lblNewLabel_3.setBounds(0, 0, 163, 43);
		panel_2.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBackground(new Color(15, 82, 15));
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(new Color(15, 82, 15));
		panel_2_1.setBounds(809, 160, 136, 43);
		panel.add(panel_2_1);
		panel_2_1.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Equivalencia real");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setBackground(new Color(255, 255, 255));
		lblNewLabel_4.setBounds(0, 0, 136, 43);
		panel_2_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Reserva hecha de cliente");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(185, 227, 179, 85);
		panel.add(lblNewLabel_5);
		
		JLabel cifraActualizable_1 = new JLabel("*Cifra de equivalencia*");
		cifraActualizable_1.setBounds(825, 341, 117, 52);
		panel.add(cifraActualizable_1);
		
		JLabel cifraActualizable_2 = new JLabel("*Cifra de equivalencia*");
		cifraActualizable_2.setBounds(825, 437, 117, 52);
		panel.add(cifraActualizable_2);
		
		JLabel cifraActualizable_3 = new JLabel("*Cifra de equivalencia*");
		cifraActualizable_3.setBounds(825, 522, 117, 52);
		panel.add(cifraActualizable_3);
		
		JLabel cifraActualizable_3_1 = new JLabel("*Cifra de equivalencia*");
		cifraActualizable_3_1.setBounds(624, 522, 117, 52);
		panel.add(cifraActualizable_3_1);
		
		JLabel cifraActualizable_4 = new JLabel("*Cifra de equivalencia*");
		cifraActualizable_4.setBounds(624, 243, 117, 52);
		panel.add(cifraActualizable_4);
		
		JLabel cifraActualizable_1_1 = new JLabel("*Cifra de equivalencia*");
		cifraActualizable_1_1.setBounds(624, 341, 117, 52);
		panel.add(cifraActualizable_1_1);
		
		JLabel cifraActualizable_2_1 = new JLabel("*Cifra de equivalencia*");
		cifraActualizable_2_1.setBounds(624, 437, 117, 52);
		panel.add(cifraActualizable_2_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("Reserva hecha de cliente");
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5_1.setBounds(185, 322, 179, 85);
		panel.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("Reserva hecha de cliente");
		lblNewLabel_5_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5_2.setBounds(185, 417, 179, 85);
		panel.add(lblNewLabel_5_2);
		
		JLabel lblNewLabel_5_3 = new JLabel("Reserva hecha de cliente");
		lblNewLabel_5_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5_3.setBounds(185, 511, 179, 85);
		panel.add(lblNewLabel_5_3);
	}
}
