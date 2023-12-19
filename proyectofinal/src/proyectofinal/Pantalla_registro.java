package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Pantalla_registro extends JFrame {
    private Connection conexion;

    public Pantalla_registro(Connection conexion) {
        this.conexion = conexion;

        ImageIcon image = new ImageIcon("a14e9b1870f7dfab1e2c26659ee36b74.jpg");
        JLabel background = new JLabel(image);
        background.setBounds(0, 0, 1080, 720);

        JPanel panelLimon = new JPanel();
        panelLimon.setLayout(null);
        panelLimon.setBackground(new Color(214,232,213,255));

        int panelWidth = 900;
        int panelHeight = 500;
        int panelX = (background.getWidth() - panelWidth) / 2;
        int panelY = (background.getHeight() - panelHeight) / 2;
        panelLimon.setBounds(panelX, panelY, panelWidth, panelHeight);

        ImageIcon logoImage = new ImageIcon("imagenes/Logo_Book4U_NO_fondo.png");
        Image img = logoImage.getImage();
        Image newImg = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        logoImage = new ImageIcon(newImg);
        JLabel imageLabel = new JLabel(logoImage);
        imageLabel.setBounds(80, 50, 150, 100);
        panelLimon.add(imageLabel);

        JLabel panelBlanco = new JLabel("Registrar usuario");
        panelBlanco.setBounds(375, 180, 200, 25); 
        panelBlanco.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel nombreUsuario = new JLabel("Nombre:");
        nombreUsuario.setBounds(50, 250, 150, 25);
        JTextField nombreField = new JTextField();
        nombreField.setBounds(200, 250, 120, 25);

        JLabel apellidoUsuario = new JLabel("Apellidos:");
        apellidoUsuario.setBounds(50, 280, 150, 25);
        JTextField apellidoField = new JTextField();
        apellidoField.setBounds(200, 280, 120, 25);

        JLabel dniUsuario = new JLabel("DNI:");
        dniUsuario.setBounds(50, 310, 150, 25);
        JTextField dniField = new JTextField();
        dniField.setBounds(200, 310, 120, 25);

        JLabel domicilioUsuario = new JLabel("Domicilio:");
        domicilioUsuario.setBounds(50, 340, 150, 25);
        JTextField domicilioField = new JTextField();
        domicilioField.setBounds(200, 340, 120, 25);

        JLabel telefonoUsuario = new JLabel("Teléfono:");
        telefonoUsuario.setBounds(50, 370, 150, 25);
        JTextField telefonoField = new JTextField();
        telefonoField.setBounds(200, 370, 120, 25);

        JLabel correoUsuario = new JLabel("Correo:");
        correoUsuario.setBounds(50, 400, 150, 25);
        JTextField correoField = new JTextField();
        correoField.setBounds(200, 400, 120, 25);

        JLabel contrasenaLabel = new JLabel("Contrasena:");
        contrasenaLabel.setBounds(50, 430, 80, 25);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 430, 120, 25);

        JButton botondeRegistro = new JButton("Registrarse");
        botondeRegistro.setBounds(375, 350, 100, 30);
        panelLimon.add(botondeRegistro);

        botondeRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nombre = nombreField.getText();
                String apellidos = apellidoField.getText();
                String dni = dniField.getText();
                String domicilio = domicilioField.getText();
                int telefono = Integer.parseInt(telefonoField.getText());
                String correo = correoField.getText();
                char[] password = passwordField.getPassword();
                String contrasena = new String(password);

             
                try {
                    if (existeUsuario(nombre, correo)) {
                        JOptionPane.showMessageDialog(null, "El nombre o correo ya existen en la base de datos", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String sql = "INSERT INTO cliente (id_cliente, nombre, apellidos, dni, domicilio, telefono, correo, contrasenya) VALUES (mi_secuencia.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
                        preparedStatement.setString(1, nombre);
                        preparedStatement.setString(2, apellidos);
                        preparedStatement.setString(3, dni);
                        preparedStatement.setString(4, domicilio);
                        preparedStatement.setInt(5, telefono);
                        preparedStatement.setString(6, correo);
                        preparedStatement.setString(7, contrasena);

                        int filasAfectadas = preparedStatement.executeUpdate();

                        if (filasAfectadas > 0) {
                            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new Pantalla_login(conexion);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        JPanel InicioSesionpanel = new JPanel(new GridLayout(1, 2, 5, 0));
        InicioSesionpanel.setBounds(350, 425, 200, 25);

        JLabel SesionLabel = new JLabel("Ya tienes una cuenta?");
        SesionLabel.setFont(new Font("Arial", Font.PLAIN, 8));

        JButton iniciarVolver = new JButton("Iniciar sesion");
        
        iniciarVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Pantalla_login(conexion);
            }
        });


        InicioSesionpanel.add(SesionLabel);
        InicioSesionpanel.add(iniciarVolver);

        panelLimon.add(imageLabel);
        panelLimon.add(panelBlanco);
        panelLimon.add(nombreUsuario);
        panelLimon.add(nombreField);
        panelLimon.add(apellidoUsuario);
        panelLimon.add(apellidoField);
        panelLimon.add(dniUsuario);
        panelLimon.add(dniField);
        panelLimon.add(domicilioUsuario);
        panelLimon.add(domicilioField);
        panelLimon.add(telefonoUsuario);
        panelLimon.add(telefonoField);
        panelLimon.add(correoUsuario);
        panelLimon.add(correoField);
        panelLimon.add(contrasenaLabel);
        panelLimon.add(passwordField);
        panelLimon.add(botondeRegistro);
        panelLimon.add(InicioSesionpanel);

        background.add(panelLimon);

        setTitle("BOOK4U");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1080, 720);
        setVisible(true);
        add(background);

        ImageIcon appIcon = new ImageIcon("imagenes/Logo_Book4U_NO_fondo.png");
        setIconImage(appIcon.getImage());
        getContentPane().setBackground(new Color(0x0F520F));
        
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (pantalla.width - getWidth()) / 2;
        int y = (pantalla.height - getHeight()) / 2;
        setLocation(x, y);
        
        
        
    }
    
    private boolean existeUsuario(String nombre, String correo) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM cliente WHERE nombre = ? OR correo = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, correo);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }
}