package proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class port_benidorm extends JFrame{

	  private String nombreUsuario;

	  private Connection conexion = book4u.obtenerConexion();
	
	public port_benidorm(String nombreUsuario, Connection conexion) {
		
		 this.nombreUsuario = nombreUsuario;
		    this.conexion = conexion;
		    setSize(1080, 720);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       

	        // Color de fondo
	        Color colorDeFondo = new Color(15, 82, 15, 255);
	       getContentPane().setBackground(colorDeFondo);
	       JPanel panelInferior = new JPanel(new BorderLayout());
	       panelInferior.setBackground(new Color(255, 255, 255, 255));
	       this.getContentPane().add(panelInferior);

	       JLabel anuncio1 = new JLabel();
	       ImageIcon foto = new ImageIcon("imagenes/PortBenHotl2.jpg");
	       anuncio1.setIcon(new ImageIcon(foto.getImage().getScaledInstance(190, 170, Image.SCALE_SMOOTH)));

	       // Establecer un margen para el JLabel
	       anuncio1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

	       panelInferior.add(anuncio1, BorderLayout.WEST);
	       anuncio1.setVisible(true);

	        
	        
	       JButton VerAnuncio2=new JButton("Reservar");
	        VerAnuncio2.setBounds(720, 480, 150, 50);
	        VerAnuncio2.setBackground(new Color(213,232,212,255));
	        //ImageIcon botonimagen = new ImageIcon("Pictures/back5.png");
	        //atras.setIcon(new ImageIcon(botonimagen.getImage().getScaledInstance(atras.getWidth(),atras.getHeight(),Image.SCALE_SMOOTH )));
	        panelInferior.add(VerAnuncio2, BorderLayout.SOUTH);
	        VerAnuncio2.setVisible(true);
	       
	     // Panel superior (que cumple la funci�n de una barra de men�)
	        JPanel barraMenu = new JPanel(new BorderLayout());
	        barraMenu.setBackground(new Color(213, 232, 212, 255)); // Color verde lim�n
	        barraMenu.setPreferredSize(new Dimension(1050, 80));
	        this.getContentPane().add(barraMenu);

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
	        
	        
	        
	        JLabel labelTexto = new JLabel("Port Benidorm Hotel");
	        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.PLAIN, 30));
	        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
	        barraMenu.add(labelTexto, BorderLayout.CENTER);
	
	        // Agregar el panel superior y el panel inferior al JFrame
	        add(barraMenu, BorderLayout.NORTH);
	        add(panelInferior, BorderLayout.CENTER);
	        setVisible(true);
}


}
