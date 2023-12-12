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
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pantalla_Crearreserva extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();

    private JTextField idClienteTextField;
    private JTextField idEstanciaTextField;
    private JTextField fechaInicioTextField;
    private JTextField fechaFinTextField;
    private JTextField pagadoTextField;
    private JTextField precioTotalTextField;
    private JTextField personasTextField;
    private JTextField descripcionTextField;
    private JTextField direccionTextField;
    private JTextField nombreTextField;
    private JTextField imagenTextField;
    private JTextField precioCreditosTotalTextField;
    private JTextField creditosEstanciaTextField;
    private JTextField estadoTextField;

    public pantalla_Crearreserva(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Crear reservas");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(16, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        panelInferior.add(new JLabel("ID Cliente:"));
        idClienteTextField = new JTextField();
        panelInferior.add(idClienteTextField);

        panelInferior.add(new JLabel("ID Estancia:"));
        idEstanciaTextField = new JTextField();
        panelInferior.add(idEstanciaTextField);

        panelInferior.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"));
        fechaInicioTextField = new JTextField();
        panelInferior.add(fechaInicioTextField);

        panelInferior.add(new JLabel("Fecha Fin (yyyy-MM-dd):"));
        fechaFinTextField = new JTextField();
        panelInferior.add(fechaFinTextField);

        panelInferior.add(new JLabel("Pagado:"));
        pagadoTextField = new JTextField();
        panelInferior.add(pagadoTextField);

        panelInferior.add(new JLabel("Precio Total:"));
        precioTotalTextField = new JTextField();
        panelInferior.add(precioTotalTextField);

        panelInferior.add(new JLabel("Personas:"));
        personasTextField = new JTextField();
        panelInferior.add(personasTextField);

        panelInferior.add(new JLabel("Descripción:"));
        descripcionTextField = new JTextField();
        panelInferior.add(descripcionTextField);

        panelInferior.add(new JLabel("Dirección:"));
        direccionTextField = new JTextField();
        panelInferior.add(direccionTextField);

        panelInferior.add(new JLabel("Nombre:"));
        nombreTextField = new JTextField();
        panelInferior.add(nombreTextField);

        panelInferior.add(new JLabel("Imagen:"));
        imagenTextField = new JTextField();
        panelInferior.add(imagenTextField);

        panelInferior.add(new JLabel("Precio Créditos Total:"));
        precioCreditosTotalTextField = new JTextField();
        panelInferior.add(precioCreditosTotalTextField);

        panelInferior.add(new JLabel("Créditos Estancia:"));
        creditosEstanciaTextField = new JTextField();
        panelInferior.add(creditosEstanciaTextField);

        panelInferior.add(new JLabel("Estado:"));
        estadoTextField = new JTextField();
        panelInferior.add(estadoTextField);

        JButton crearReservaButton = new JButton("Crear Reserva");
        crearReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearReserva();
            }
        });
        panelInferior.add(crearReservaButton);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private void crearReserva() {
        try {
            int idCliente = Integer.parseInt(idClienteTextField.getText());
            int idEstancia = Integer.parseInt(idEstanciaTextField.getText());
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioTextField.getText());
            Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinTextField.getText());
            String pagado = pagadoTextField.getText();
            int precioTotal = Integer.parseInt(precioTotalTextField.getText());
            int personas = Integer.parseInt(personasTextField.getText());
            String descripcion = descripcionTextField.getText();
            String direccion = direccionTextField.getText();
            String nombre = nombreTextField.getText();
            String imagen = imagenTextField.getText();
            int precioCreditosTotal = Integer.parseInt(precioCreditosTotalTextField.getText());
            int creditosEstancia = Integer.parseInt(creditosEstanciaTextField.getText());
            String estado = estadoTextField.getText();

            // Crear la reserva en la base de datos
            String consulta = "INSERT INTO reserva (id_reserva, id_cliente, id_estancia, fechai, fechaf, pagado, preciototal, " +
                              "personas, descripcion, direccion, nombre, imagen, precio_creditostotal, " +
                              "creditos_estancia, estado) VALUES (secureserva.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
                sentencia.setInt(1, idCliente);
                sentencia.setInt(2, idEstancia);
                sentencia.setDate(3, new java.sql.Date(fechaInicio.getTime()));
                sentencia.setDate(4, new java.sql.Date(fechaFin.getTime()));
                sentencia.setString(5, pagado);
                sentencia.setInt(6, precioTotal);
                sentencia.setInt(7, personas);
                sentencia.setString(8, descripcion);
                sentencia.setString(9, direccion);
                sentencia.setString(10, nombre);
                sentencia.setString(11, imagen);
                sentencia.setInt(12, precioCreditosTotal);
                sentencia.setInt(13, creditosEstancia);
                sentencia.setString(14, estado);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Reserva creada exitosamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al crear la reserva");
                }
            }
        } catch (ParseException | NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear la reserva");
        }
    }

}

