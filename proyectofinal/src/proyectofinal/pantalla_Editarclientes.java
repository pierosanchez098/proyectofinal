package proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class pantalla_Editarclientes extends JFrame {

	private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JComboBox<String> clientesComboBox;
    private JTextField nombreTextField;
    private JTextField apellidosTextField;
    private JTextField dniTextField;
    private JTextField contrasenyaTextField;
    private JTextField telefonoTextField;
    private JTextField domicilioTextField;
    private JTextField creditosTextField;
    private JTextField correoTextField;

    public pantalla_Editarclientes(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Editar clientes");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(9, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        String[] clientes = obtenerClientes();
        clientesComboBox = new JComboBox<>(clientes);
        clientesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosClienteSeleccionado((String) clientesComboBox.getSelectedItem());
            }
        });

        nombreTextField = new JTextField();
        apellidosTextField = new JTextField();
        dniTextField = new JTextField();
        contrasenyaTextField = new JTextField();
        telefonoTextField = new JTextField();
        domicilioTextField = new JTextField();
        creditosTextField = new JTextField();
        correoTextField = new JTextField();

        panelInferior.add(new JLabel("Seleccionar Cliente:"));
        panelInferior.add(clientesComboBox);
        panelInferior.add(new JLabel("Nombre:"));
        panelInferior.add(nombreTextField);
        panelInferior.add(new JLabel("Apellidos:"));
        panelInferior.add(apellidosTextField);
        panelInferior.add(new JLabel("DNI:"));
        panelInferior.add(dniTextField);
        panelInferior.add(new JLabel("Contraseña:"));
        panelInferior.add(contrasenyaTextField);
        panelInferior.add(new JLabel("Teléfono:"));
        panelInferior.add(telefonoTextField);
        panelInferior.add(new JLabel("Domicilio:"));
        panelInferior.add(domicilioTextField);
        panelInferior.add(new JLabel("Créditos:"));
        panelInferior.add(creditosTextField);
        panelInferior.add(new JLabel("Correo:"));
        panelInferior.add(correoTextField);

        JButton editarClienteButton = new JButton("Editar Cliente");
        editarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCliente();
            }
        });

        panelInferior.add(editarClienteButton);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private String[] obtenerClientes() {
        String consulta = "SELECT nombre FROM cliente";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            ResultSet resultado = sentencia.executeQuery();

            int cantidadClientes = 0;
            while (resultado.next()) {
                cantidadClientes++;
            }

            resultado = sentencia.executeQuery();
            String[] clientes = new String[cantidadClientes];
            int indice = 0;
            while (resultado.next()) {
                clientes[indice] = resultado.getString("nombre");
                indice++;
            }
            return clientes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new String[0];
        }
    }

    private void cargarDatosClienteSeleccionado(String nombreCliente) {
        String consulta = "SELECT * FROM cliente WHERE nombre = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nombreCliente);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                nombreTextField.setText(resultado.getString("nombre"));
                apellidosTextField.setText(resultado.getString("apellidos"));
                dniTextField.setText(resultado.getString("dni"));
                contrasenyaTextField.setText(resultado.getString("contrasenya"));
                telefonoTextField.setText(String.valueOf(resultado.getInt("telefono")));
                domicilioTextField.setText(resultado.getString("domicilio"));
                creditosTextField.setText(String.valueOf(resultado.getInt("creditos"))); 
                correoTextField.setText(resultado.getString("correo"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void editarCliente() {
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String dni = dniTextField.getText();
        String contrasenya = contrasenyaTextField.getText();
        int  telefono = Integer.parseInt(telefonoTextField.getText());
        String domicilio = domicilioTextField.getText();
        int creditos = Integer.parseInt(creditosTextField.getText()); 
        String correo = correoTextField.getText();

        String consulta = "UPDATE cliente SET apellidos=?, dni=?, contrasenya=?, telefono=?, domicilio=?, creditos=?, correo=? WHERE nombre=?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, apellidos);
            sentencia.setString(2, dni);
            sentencia.setString(3, contrasenya);
            sentencia.setInt(4, telefono);
            sentencia.setString(5, domicilio);
            sentencia.setInt(6, creditos); 
            sentencia.setString(7, correo);
            sentencia.setString(8, nombre);

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente editado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al editar el cliente");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al editar el cliente");
        }
    }

    
}