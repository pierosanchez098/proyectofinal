package proyectofinal;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class misReservasPantalla extends JFrame {


	private String nombreUsuario;
	private Connection conexion = book4u.obtenerConexion();

	public misReservasPantalla(String nombreUsuario, Connection conexion) {

	this.nombreUsuario = nombreUsuario;
	this.conexion = conexion;



	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1080, 720);
    setLocationRelativeTo(null);

    JPanel contentPane = new JPanel();
    contentPane.setBackground(new Color(15, 82, 15));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    panel.setLayout(null);
    contentPane.add(panel, BorderLayout.CENTER);

    JPanel panel_1 = new JPanel();
    panel_1.setBackground(new Color(214, 232, 212));
    panel_1.setBounds(0, 0, 1012, 121);
    panel.add(panel_1);
    panel_1.setLayout(new FlowLayout(FlowLayout.LEFT)); // Usamos FlowLayout para alinear a la izquierda

    ImageIcon icono = new ImageIcon("imagenes/casa.png");
    icono = new ImageIcon(icono.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH));
    JLabel iconoLabel = new JLabel(icono);
    panel_1.add(iconoLabel);

    JLabel lblNewLabel_1 = new JLabel("Mis reservas");
    lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 34));
    panel_1.add(lblNewLabel_1);

JLabel pie = new JLabel("Reservas en pie:");
pie.setFont(new Font("Arial", Font.BOLD, 22));
pie.setBounds(128, 160, 179, 34);
panel.add(pie);

JLabel cifraActualizable = new JLabel("*Cifra actualizable*");
cifraActualizable.setBounds(825, 243, 91, 52);
panel.add(cifraActualizable);

JLabel cifraActualizable_2 = new JLabel("*Cifra actualizable*");
cifraActualizable_2.setBounds(825, 367, 91, 52);
panel.add(cifraActualizable_2);

JLabel cifraActualizable_3 = new JLabel("*Cifra actualizable*");
cifraActualizable_3.setBounds(825, 507, 91, 52);
panel.add(cifraActualizable_3);

JButton btnDisponible = new JButton("Disponible");
btnDisponible.setBackground(new Color(221, 232, 251));
btnDisponible.setBounds(626, 243, 104, 45);
panel.add(btnDisponible);

JButton btnNoDisponible = new JButton("No disponible");
btnNoDisponible.setBackground(new Color(255, 0, 0));
btnNoDisponible.setBounds(626, 374, 104, 45);
panel.add(btnNoDisponible);

JButton btnDisponible_2 = new JButton("Disponible\r\n");
btnDisponible_2.setBackground(new Color(221, 232, 251));
btnDisponible_2.setBounds(626, 514, 104, 45);
panel.add(btnDisponible_2);

JPanel panel_2 = new JPanel();
panel_2.setBackground(new Color(15, 82, 15));
panel_2.setBounds(607, 160, 136, 43);
panel.add(panel_2);
panel_2.setLayout(null);

JLabel lblNewLabel_3 = new JLabel("Disponible para modificar");
lblNewLabel_3.setForeground(new Color(255, 255, 255));
lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
lblNewLabel_3.setBounds(0, 0, 136, 43);
panel_2.add(lblNewLabel_3);
lblNewLabel_3.setBackground(new Color(15, 82, 15));

JPanel panel_2_1 = new JPanel();
panel_2_1.setBackground(new Color(15, 82, 15));
panel_2_1.setBounds(809, 160, 136, 43);
panel.add(panel_2_1);
panel_2_1.setLayout(null);

JLabel lblNewLabel_4 = new JLabel("Precio");
lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
lblNewLabel_4.setForeground(new Color(255, 255, 255));
lblNewLabel_4.setBackground(new Color(255, 255, 255));
lblNewLabel_4.setBounds(0, 0, 136, 43);
panel_2_1.add(lblNewLabel_4);
}


}
