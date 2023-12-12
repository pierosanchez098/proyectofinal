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
import java.util.Vector;

public class pantalla_Verreserva extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JTable tablaReservas;

    public pantalla_Verreserva(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Ver reservas");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        // Crear modelo de tabla
        DefaultTableModel modeloTabla = new DefaultTableModel();
        tablaReservas = new JTable(modeloTabla);

        // Agregar columnas al modelo de tabla
        String[] columnas = {"ID Reserva", "ID Cliente", "ID Estancia", "Fecha Inicio", "Fecha Fin", "Pagado",
                             "Precio Total", "Personas", "Descripción", "Dirección", "Nombre", "Imagen",
                             "Precio Créditos Total", "Créditos Estancia", "Estado"};
        for (String columna : columnas) {
            modeloTabla.addColumn(columna);
        }

        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        panelInferior.add(scrollPane, BorderLayout.CENTER);

        cargarReservas();

        add(barraMenu, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.CENTER);
        setVisible(true);
    }

    private void cargarReservas() {
        String consulta = "SELECT * FROM reserva";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(resultado.getInt("id_reserva"));
                fila.add(resultado.getInt("id_cliente"));
                fila.add(resultado.getInt("id_estancia"));
                fila.add(resultado.getDate("fechai"));
                fila.add(resultado.getDate("fechaf"));
                fila.add(resultado.getString("pagado"));
                fila.add(resultado.getInt("preciototal"));
                fila.add(resultado.getInt("personas"));
                fila.add(resultado.getString("descripcion"));
                fila.add(resultado.getString("direccion"));
                fila.add(resultado.getString("nombre"));
                fila.add(resultado.getString("imagen"));
                fila.add(resultado.getInt("precio_creditostotal"));
                fila.add(resultado.getInt("creditos_estancia"));
                fila.add(resultado.getString("estado"));

                DefaultTableModel modeloTabla = (DefaultTableModel) tablaReservas.getModel();
                modeloTabla.addRow(fila);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

