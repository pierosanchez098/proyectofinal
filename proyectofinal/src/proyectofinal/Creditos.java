package proyectofinal;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Creditos extends JFrame {

    private JTextField cant;
    private String nombreUsuario;
	private Connection conexion = book4u.obtenerConexion();
    private JTextField titular;
    private JTextField numTarjeta;
    private JTextField fechaCad;
    private JTextField cvc;

    public Creditos(String nombreUsuario, Connection conexion) {
    	
    	this.nombreUsuario = nombreUsuario;
    	this.conexion = conexion;
 

    	getContentPane().setBackground(new Color(213, 232, 212, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 689, 496);
        getContentPane().setLayout(null);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        JButton btnNewButton = new JButton("Volver a mi perfil");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pantallaUsuario pantallaUsuario = new pantallaUsuario(nombreUsuario, conexion);
                pantallaUsuario.setVisible(true);
                dispose(); // Cierra la ventana actual (Creditos)
            }
        });

        btnNewButton.setBounds(260, 26, 150, 21); 
        getContentPane().add(btnNewButton);

        JLabel lblNewLabel = new JLabel("Valor: 10€ = 1 crédito");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        lblNewLabel.setBounds(268, 72, 160, 27);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("imagenes/62780 (1).png"));
        lblNewLabel_1.setBounds(290, 109, 85, 68);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_3 = new JLabel("Nombre del titular de la tarjeta:");
        lblNewLabel_3.setBounds(50, 200, 200, 15);
        getContentPane().add(lblNewLabel_3);

        // Campo "Nombre del titular de la tarjeta"
        titular = new JTextField();
        titular.setBounds(250, 200, 200, 19);
        getContentPane().add(titular);
        titular.setColumns(10);

        // Texto "Número de la tarjeta"
        JLabel lblNewLabel_4 = new JLabel("Número de la tarjeta:");
        lblNewLabel_4.setBounds(50, 240, 150, 15);
        getContentPane().add(lblNewLabel_4);

        // Campo "Número de la tarjeta"
        numTarjeta = new JTextField();
        numTarjeta.setBounds(250, 240, 200, 19);
        getContentPane().add(numTarjeta);
        numTarjeta.setColumns(10);

        // Texto "Fecha de caducidad"
        JLabel lblNewLabel_5 = new JLabel("Fecha de caducidad:");
        lblNewLabel_5.setBounds(50, 280, 150, 15);
        getContentPane().add(lblNewLabel_5);

        // Campo "Fecha de caducidad"
        fechaCad = new JTextField();
        fechaCad.setBounds(250, 280, 100, 19);
        getContentPane().add(fechaCad);
        fechaCad.setColumns(10);

        // Texto "CVC"
        JLabel lblNewLabel_6 = new JLabel("CVC:");
        lblNewLabel_6.setBounds(360, 280, 45, 15);
        getContentPane().add(lblNewLabel_6);

        // Campo "CVC"
        cvc = new JTextField();
        cvc.setBounds(410, 280, 40, 19);
        getContentPane().add(cvc);
        cvc.setColumns(10);

        // Campo de cantidad
        cant = new JTextField();
        cant.setBounds(250, 320, 100, 19);
        getContentPane().add(cant);
        cant.setColumns(10);

        // Texto "Cantidad"
        JLabel lblNewLabel_7 = new JLabel("Cantidad:");
        lblNewLabel_7.setBounds(50, 320, 100, 15);
        getContentPane().add(lblNewLabel_7);

        // Botón "Pagar"
        JButton pagar = new JButton("Pagar");
        pagar.setBounds(50, 390, 100, 21);
        getContentPane().add(pagar);
        

        // Agregar ActionListener al botón "Pagar"
        pagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarEnBaseDeDatos();
            }
        });
    }

    private void insertarEnBaseDeDatos() {
        try {
            String nombreTitular = titular.getText();
            String numeroTarjeta = numTarjeta.getText();
            String fechaCaducidad = fechaCad.getText();
            String cvcValue = cvc.getText();
            int cantidad = Integer.parseInt(cant.getText());

            if (nombreTitular.isEmpty() || numeroTarjeta.isEmpty() || fechaCaducidad.isEmpty() || cvcValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCliente = obtenerIdClienteDesdeUsuario(nombreUsuario);

            int creditosActuales = obtenerCreditosActuales(idCliente);

            int nuevoCredito = creditosActuales + cantidad;

            String consultaCreditos = "INSERT INTO creditos (id_creditos, id_cliente, titular, numTarjeta, fechaCad, cvc, cantidad) VALUES (secucreditos.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementCreditos = conexion.prepareStatement(consultaCreditos);
            preparedStatementCreditos.setInt(1, idCliente);
            preparedStatementCreditos.setString(2, nombreTitular);
            preparedStatementCreditos.setString(3, numeroTarjeta);
            preparedStatementCreditos.setString(4, fechaCaducidad);
            preparedStatementCreditos.setString(5, cvcValue);
            preparedStatementCreditos.setInt(6, cantidad);

            int filasAfectadasCreditos = preparedStatementCreditos.executeUpdate();

            String consultaCliente = "UPDATE cliente SET creditos = ? WHERE id_cliente = ?";
            PreparedStatement preparedStatementCliente = conexion.prepareStatement(consultaCliente);
            preparedStatementCliente.setInt(1, nuevoCredito);
            preparedStatementCliente.setInt(2, idCliente);

            int filasAfectadasCliente = preparedStatementCliente.executeUpdate();

            if (filasAfectadasCreditos > 0 && filasAfectadasCliente > 0) {
                JOptionPane.showMessageDialog(this, "Pago registrado y créditos actualizados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el pago o actualizar los créditos", "Error", JOptionPane.ERROR_MESSAGE);
            }

            preparedStatementCreditos.close();
            preparedStatementCliente.close();
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int obtenerCreditosActuales(int idCliente) {
        try {
            String consulta = "SELECT creditos FROM cliente WHERE id_cliente = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setInt(1, idCliente);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("creditos");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    
    private int obtenerIdClienteDesdeUsuario(String nombreUsuario) {
        try {
            String consulta = "SELECT id_cliente FROM cliente WHERE nombre = ? OR correo = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, nombreUsuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_cliente");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; 
    }
}