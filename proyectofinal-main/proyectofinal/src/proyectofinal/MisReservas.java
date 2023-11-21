package proyectofinal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MisReservas extends JFrame{
	private String nombreUsuario;
	private Connection conexion = book4u.obtenerConexion();
	public MisReservas(String nombreUsuario, Connection conexion) {

		this.nombreUsuario = nombreUsuario;
		this.conexion = conexion;

				setSize(1080, 720);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				//Color de fondo
				Color colorDeFondo = new Color(15, 82, 15, 255);
				getContentPane().setBackground(colorDeFondo);

				 // Panel superior (que cumple la funcion de una barra de menus) Color usado verde limon
		        JPanel barraMenu = new JPanel(new BorderLayout());
		        barraMenu.setBackground(new Color(213, 232, 212, 255)); 
		        barraMenu.setPreferredSize(new Dimension(1050, 80));

		        // Icono
		        ImageIcon icono = new ImageIcon("imagenes/casa.png");
		        icono = new ImageIcon(icono.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH));
		        JLabel iconoLabel = new JLabel(icono);
		        barraMenu.add(iconoLabel, BorderLayout.WEST);
		        
		        iconoLabel.addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseClicked(MouseEvent e) {
		                MenuPrincipalFrame menuPrincipalFrame = new MenuPrincipalFrame(nombreUsuario, conexion);
		                menuPrincipalFrame.setVisible(true);
		                dispose(); 
		            }
		        });
		        
		        JLabel labelTexto = new JLabel("Mis reservas");
		        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.PLAIN, 30));
		        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
		        barraMenu.add(labelTexto, BorderLayout.CENTER);
		        
		        JPanel panelInferior = new JPanel(new BorderLayout());
		        panelInferior.setBackground(new Color(255, 255, 255, 255));
		        
		        
		        add(barraMenu, BorderLayout.NORTH);
		        add(panelInferior, BorderLayout.CENTER);
		        setVisible(true);

		}

}
