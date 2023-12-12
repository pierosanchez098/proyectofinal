package proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class pantalla_Verestancias extends JFrame {

private String usuario;
private Connection conexion = book4u.obtenerConexion();

public pantalla_Verestancias(String usuario, Connection conexion) {
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

    JLabel labelTexto = new JLabel("Ver estancias");
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
    modeloTabla.setColumnIdentifiers(new Object[]{"ID Estancia", "Tipo de estancia", "Precio por dia", "Valoracion (estrellas)", "Ubicacion", "Telefono", "Disponibilidad", "N. personas", "Nombre", "URL Imagen", "Precio en creditos"});

    String consulta = "SELECT * FROM estancia";

    try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            Object[] fila = {
                    resultado.getInt("id_estancia"),
                    resultado.getString("tipo_estancia"),
                    resultado.getString("precioxdia"),
                    resultado.getInt("valoracion"),
                    resultado.getString("ubicacion"),
                    resultado.getString("informacion"),
                    resultado.getInt("telefono_est"),
                    resultado.getString("disponibilidad"),
                    resultado.getInt("npersonas"),
                    resultado.getString("nombre"),
                    resultado.getString("imagen"),
                    resultado.getInt("precio_creditos"),
            };
            modeloTabla.addRow(fila);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
