package proyectofinal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class pantalla_Eliminarreserva extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JTable table;

    public pantalla_Eliminarreserva(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Eliminar reservas");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        String[] columnNames = {"ID Reserva", "ID Cliente", "ID Estancia", "Fecha Inicio", "Fecha Fin",
                "Pagado", "Precio Total", "Personas", "Descripción", "Dirección", "Nombre", "Imagen",
                "Precio Créditos Total", "Créditos Estancia", "Estado"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable();
        table.setModel(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton eliminarButton = new JButton("Eliminar Reserva");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarReserva();
            }
        });

        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.add(eliminarButton, BorderLayout.SOUTH);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);

        cargarReservas();
    }

    private void cargarReservas() {
        try {
            String consulta = "SELECT * FROM reserva";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("id_reserva"),
                        resultSet.getInt("id_cliente"),
                        resultSet.getInt("id_estancia"),
                        resultSet.getDate("fechai"),
                        resultSet.getDate("fechaf"),
                        resultSet.getString("pagado"),
                        resultSet.getInt("preciototal"),
                        resultSet.getInt("personas"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("direccion"),
                        resultSet.getString("nombre"),
                        resultSet.getString("imagen"),
                        resultSet.getInt("precio_creditostotal"),
                        resultSet.getInt("creditos_estancia"),
                        resultSet.getString("estado")
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void eliminarReserva() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una reserva para eliminar");
            return;
        }

        int idReserva = (int) table.getValueAt(selectedRow, 0);

        try {
            String consulta = "DELETE FROM reserva WHERE id_reserva=?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, idReserva);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Reserva eliminada exitosamente");
                cargarReservas(); 
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la reserva");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la reserva");
        }
    }

}
