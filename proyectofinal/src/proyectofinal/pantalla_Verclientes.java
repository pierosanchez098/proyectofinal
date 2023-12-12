package proyectofinal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pantalla_Verclientes extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();

    public pantalla_Verclientes(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Ver clientes");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        DefaultTableModel modeloTabla = new DefaultTableModel();
        JTable tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panelInferior.add(scrollPane, BorderLayout.CENTER);

        cargarClientes(modeloTabla);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private void cargarClientes(DefaultTableModel modeloTabla) {
        modeloTabla.setColumnIdentifiers(new Object[]{"ID Cliente", "Nombre", "Apellidos", "DNI", "Contraseña", "Teléfono", "Domicilio", "Créditos", "Correo"});

        String consulta = "SELECT * FROM cliente";

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Object[] fila = {
                        resultado.getInt("id_cliente"),
                        resultado.getString("nombre"),
                        resultado.getString("apellidos"),
                        resultado.getString("dni"),
                        resultado.getString("contrasenya"),
                        resultado.getInt("telefono"),
                        resultado.getString("domicilio"),
                        resultado.getInt("creditos"),
                        resultado.getString("correo")
                };
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    