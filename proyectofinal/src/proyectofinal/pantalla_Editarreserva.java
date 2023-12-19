package proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pantalla_Editarreserva extends JFrame {

	private String usuario;
    private Connection conexion = book4u.obtenerConexion();

    private JComboBox<String> reservasComboBox;
    private JComboBox<String> columnasComboBox;
    private JTextField nuevoValorTextField;

    public pantalla_Editarreserva(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Editar reservas");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(12, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        String[] reservas = obtenerReservas();
        reservasComboBox = new JComboBox<>(reservas);

        String[] columnas = obtenerColumnas();
        columnasComboBox = new JComboBox<>(columnas);

        nuevoValorTextField = new JTextField();

        panelInferior.add(new JLabel("Seleccionar Reserva:"));
        panelInferior.add(reservasComboBox);
        panelInferior.add(new JLabel("Seleccionar Columna:"));
        panelInferior.add(columnasComboBox);
        panelInferior.add(new JLabel("Nuevo Valor:"));
        panelInferior.add(nuevoValorTextField);

        JButton editarReservaButton = new JButton("Editar Reserva");
        editarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarReserva();
            }
        });

        panelInferior.add(editarReservaButton);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private String[] obtenerReservas() {
        String consulta = "SELECT id_reserva FROM reserva";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            ResultSet resultado = sentencia.executeQuery();

            int cantidadReservas = 0;
            while (resultado.next()) {
                cantidadReservas++;
            }

            resultado = sentencia.executeQuery();
            String[] reservas = new String[cantidadReservas];
            int indice = 0;
            while (resultado.next()) {
                reservas[indice] = String.valueOf(resultado.getInt("id_reserva"));
                indice++;
            }
            return reservas;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new String[0];
        }
    }

    private String[] obtenerColumnas() {
        String[] columnas = {"id_cliente", "id_estancia", "fechai", "fechaf", "pagado", "preciototal",
                             "personas", "descripcion", "direccion", "nombre", "imagen",
                             "precio_creditostotal", "creditos_estancia", "estado"};
        return columnas;
    }



    private void editarReserva() {
        String idReserva = (String) reservasComboBox.getSelectedItem();
        String columna = (String) columnasComboBox.getSelectedItem();
        String nuevoValor = nuevoValorTextField.getText();

        String consulta = "UPDATE reserva SET " + columna + "=? WHERE id_reserva=?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nuevoValor);
            sentencia.setInt(2, Integer.parseInt(idReserva));

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Reserva editada exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al editar la reserva");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al editar la reserva");
        }
    }
}