package proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class administracion_clientes extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();

    public administracion_clientes(String usuario, Connection conexion) {
        this.usuario = usuario;
        this.conexion = conexion;
        
        setSize(1080, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color colorDeFondo = new Color(15, 82, 15, 255);
        getContentPane().setBackground(colorDeFondo);

        JPanel barraMenu = new JPanel(new BorderLayout());
        barraMenu.setBackground(new Color(213, 232, 212, 255));
        barraMenu.setPreferredSize(new Dimension(1050, 80));


        ImageIcon icono = new ImageIcon("imagenes/casa.png");
        icono = new ImageIcon(icono.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH));
        JLabel iconoLabel = new JLabel(icono);
        barraMenu.add(iconoLabel, BorderLayout.WEST);
        
        iconoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Pantalla_administrador Pantalla_administrador = new Pantalla_administrador(usuario, conexion);
                Pantalla_administrador.setVisible(true);
                dispose(); 
            }
        });

        JLabel labelTexto = new JLabel("Administracion de clientes / usuarios");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);


        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(255, 255, 255, 255));
        
        JLabel textoCentrado = new JLabel("Que deseas hacer?");
        textoCentrado.setFont(new Font("Arial", Font.BOLD, 20));
        textoCentrado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInferior.add(Box.createVerticalStrut(20));  
        panelInferior.add(textoCentrado);
        panelInferior.add(Box.createVerticalStrut(20)); 
        
        String[] opciones = {"Ver clientes", "Crear cliente", "Modificar cliente", "Eliminar cliente"};
        for (String opcion : opciones) {
            JButton botonOpcion = new JButton(opcion);
            botonOpcion.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInferior.add(botonOpcion);
            panelInferior.add(Box.createVerticalStrut(10));
            botonOpcion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (opcion) {
                        case "Ver clientes":
                            new pantalla_Verclientes(usuario, conexion);
                            dispose();
                            break;
                        case "Crear cliente":
                        	new pantalla_Crearclientes(usuario, conexion);
                            dispose();
                            break;
                        case "Modificar cliente":
                        	new pantalla_Editarclientes(usuario, conexion);
                            dispose();
                            break;
                        case "Eliminar cliente":
                        	new pantalla_Eliminarclientes(usuario, conexion);
                            dispose();
                            break;
                    }
                }
            });
        }


        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }
        


}

    
