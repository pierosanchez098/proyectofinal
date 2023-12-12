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
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class pantalla_Crearclientes extends JFrame {

	private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JTextField nombreTextField;
    private JTextField apellidosTextField;
    private JTextField dniTextField;
    private JTextField contrasenyaTextField;
    private JTextField telefonoTextField;
    private JTextField domicilioTextField;
    private JTextField creditosTextField;
    private JTextField correoTextField;

    public pantalla_Crearclientes(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Crear un nuevo cliente");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(9, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        nombreTextField = new JTextField();
        apellidosTextField = new JTextField();
        dniTextField = new JTextField();
        contrasenyaTextField = new JTextField();
        telefonoTextField = new JTextField();
        domicilioTextField = new JTextField();
        creditosTextField = new JTextField();
        correoTextField = new JTextField();

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

        JButton crearClienteButton = new JButton("Crear Cliente");
        crearClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearNuevoCliente();
            }
        });

        panelInferior.add(crearClienteButton);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private void crearNuevoCliente() {
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String dni = dniTextField.getText();
        String contrasenya = contrasenyaTextField.getText();
        String telefono = telefonoTextField.getText();
        String domicilio = domicilioTextField.getText();
        double creditos = Double.parseDouble(creditosTextField.getText());
        String correo = correoTextField.getText();

        String consulta = "INSERT INTO cliente (id_cliente, nombre, apellidos, dni, contrasenya, telefono, domicilio, creditos, correo) VALUES (mi_secuencia.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, apellidos);
            sentencia.setString(3, dni);
            sentencia.setString(4, contrasenya);
            sentencia.setString(5, telefono);
            sentencia.setString(6, domicilio);
            sentencia.setDouble(7, creditos);
            sentencia.setString(8, correo);

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente creado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear el cliente");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear el cliente");
        }
    }
}