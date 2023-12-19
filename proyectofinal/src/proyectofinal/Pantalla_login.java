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

        JPanel panelLimon = new JPanel();
        panelLimon.setLayout(null);
        panelLimon.setBackground(new Color(214, 232, 213, 255));

        int panelWidth = 300;
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

        JLabel TituloLogin = new JLabel("Iniciar sesion");
        TituloLogin.setBounds(90, 180, 150, 25);
        panelLimon.add(TituloLogin);
        TituloLogin.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel UsuarioLabel = new JLabel("Usuario");
        UsuarioLabel.setBounds(50, 250, 150, 25);
        panelLimon.add(UsuarioLabel);

        JLabel ContrasenaLabel = new JLabel("Contrasena");
        ContrasenaLabel.setBounds(50, 290, 80, 25);
        panelLimon.add(ContrasenaLabel);

        JTextField usuarioCampo = new JTextField();
        usuarioCampo.setBounds(150, 250, 120, 25);
        panelLimon.add(usuarioCampo);

        JPasswordField claveCampo = new JPasswordField();
        claveCampo.setBounds(150, 290, 120, 25);
        panelLimon.add(claveCampo);

        JButton BotonEntrar = new JButton("Entrar");
        BotonEntrar.setBounds(100, 350, 100, 30);
        panelLimon.add(BotonEntrar);

        JPanel panelRegistro = new JPanel(new GridLayout(1, 2, 5, 0)); 
        panelRegistro.setBounds(50, 425, 200, 25); 

        JLabel textoRegistro = new JLabel("Aun no te has registrado?");
        textoRegistro.setFont(new Font("Arial", Font.PLAIN, 8)); 
        panelRegistro.add(textoRegistro);

        JButton botonRegistro = new JButton("Registrarse");
        panelRegistro.add(botonRegistro);

        panelLimon.add(panelRegistro);

        background.add(panelLimon);
        
        
        JLabel terminosCondicionesLabel = new JLabel("Al iniciar sesion estas aceptando los terminos y cookies del sitio");
        terminosCondicionesLabel.setBounds(10, 50, 300, 70);
        terminosCondicionesLabel.setForeground(Color.BLACK); 
        terminosCondicionesLabel.setFont(new Font("Arial", Font.PLAIN, 10)); 
        background.add(terminosCondicionesLabel, BorderLayout.SOUTH);
        
        
        BotonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioCampo.getText();
                String contrasena = new String(claveCampo.getPassword());

                if (usuario.equals("administrador") && contrasena.equals("administrador")) {
                    Pantalla_administrador pantallaAdministrador = new Pantalla_administrador(usuario, conexion);
                    pantallaAdministrador.setVisible(true);
                    dispose();
                    return;  
                }

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

        ImageIcon icono = new ImageIcon("imagenes/Logo_Book4U_NO_fondo.png");
        setIconImage(icono.getImage());
        getContentPane().setBackground(new Color(0x0F520F));

        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (pantalla.width - getWidth()) / 2;
        int y = (pantalla.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private boolean autenticarUsuario(String nombreUsuario, String clave) {
        String consulta = "SELECT * FROM cliente WHERE (nombre = ? OR correo = ?) AND contrasenya = ?";

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
