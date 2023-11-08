package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pantalla_login extends JFrame {
    private Connection conexion = book4u.obtenerConexion();

    public Pantalla_login(Connection conexion) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("a14e9b1870f7dfab1e2c26659ee36b74.jpg");
        JLabel background = new JLabel(image);
        background.setBounds(0, 0, 1080, 720);
        background.setLayout(null);

        // Crear un panel blanco para los componentes de inicio de sesión
        JPanel panelLimon = new JPanel();
        panelLimon.setLayout(null);
        panelLimon.setBackground(new Color(214, 232, 213, 255));

        int panelWidth = 300;
        int panelHeight = 500;
        int panelX = (background.getWidth() - panelWidth) / 2;
        int panelY = (background.getHeight() - panelHeight) / 2;
        panelLimon.setBounds(panelX, panelY, panelWidth, panelHeight);

        // Imagen (redimensionada)
        ImageIcon logoImage = new ImageIcon("C:\\proyectofinal\\src\\proyectofinal\\Logo_Book4U_NO_fondo.png");
        Image img = logoImage.getImage();
        Image newImg = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        logoImage = new ImageIcon(newImg);
        JLabel imageLabel = new JLabel(logoImage);
        imageLabel.setBounds(80, 50, 150, 100);
        panelLimon.add(imageLabel);

        // Etiqueta de Iniciar sesión
        JLabel TituloLogin = new JLabel("Iniciar sesión");
        TituloLogin.setBounds(90, 180, 150, 25);
        panelLimon.add(TituloLogin);
        TituloLogin.setFont(new Font("Arial", Font.BOLD, 20));

        // Etiqueta de Usuario
        JLabel UsuarioLabel = new JLabel("Usuario");
        UsuarioLabel.setBounds(50, 250, 150, 25);
        panelLimon.add(UsuarioLabel);

        // Etiqueta de Contraseña
        JLabel ContrasenaLabel = new JLabel("Contraseña");
        ContrasenaLabel.setBounds(50, 290, 80, 25);
        panelLimon.add(ContrasenaLabel);

        // Campo de texto para Usuario
        JTextField usuarioCampo = new JTextField();
        usuarioCampo.setBounds(150, 250, 120, 25);
        panelLimon.add(usuarioCampo);

        // Campo de contraseña
        JPasswordField claveCampo = new JPasswordField();
        claveCampo.setBounds(150, 290, 120, 25);
        panelLimon.add(claveCampo);

        // Botón de Iniciar sesión
        JButton BotonEntrar = new JButton("Entrar");
        BotonEntrar.setBounds(100, 350, 100, 30);
        panelLimon.add(BotonEntrar);

        // Panel para el mensaje y el botón de registrarse
        JPanel panelRegistro = new JPanel(new GridLayout(1, 2, 5, 0)); // 1 fila, 2 columnas
        panelRegistro.setBounds(50, 425, 200, 25); // Ajustar el ancho

        // Label ¿Aún no te has registrado? con fuente más pequeña
        JLabel textoRegistro = new JLabel("¿Aún no te has registrado?");
        textoRegistro.setFont(new Font("Arial", Font.PLAIN, 8)); // Tamaño de fuente más pequeño
        panelRegistro.add(textoRegistro);

        // Botón para registrarse
        JButton botonRegistro = new JButton("Registrarse");
        panelRegistro.add(botonRegistro);

        panelLimon.add(panelRegistro);

        background.add(panelLimon);

        BotonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioCampo.getText();
                String contrasena = new String(claveCampo.getPassword());
                if (autenticarUsuario(usuario, contrasena)) {
                    MenuPrincipalFrame menuPrincipalFrame = new MenuPrincipalFrame(usuario, conexion);
                    menuPrincipalFrame.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pantalla_registro Pantalla_registro = new Pantalla_registro(conexion);
                Pantalla_registro.setVisible(true);
                dispose();
            }
        });

        setTitle("BOOK4U");
        setResizable(false);
        setSize(1080, 720);
        setVisible(true);
        add(background);

        ImageIcon icono = new ImageIcon("C:\\proyectofinal\\src\\proyectofinal\\Logo_Book4U_NO_fondo.png");
        setIconImage(icono.getImage());
        getContentPane().setBackground(new Color(0x0F520F));

        // Líneas para centrar el JFrame en la pantalla
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (pantalla.width - getWidth()) / 2;
        int y = (pantalla.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private boolean autenticarUsuario(String nombreUsuario, String clave) {
        String consulta = "SELECT * FROM usuario WHERE (nombre = ? OR correo = ?) AND contrasenya = ?";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nombreUsuario);
            sentencia.setString(2, nombreUsuario);
            sentencia.setString(3, clave);
            ResultSet resultado = sentencia.executeQuery();
            return resultado.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
