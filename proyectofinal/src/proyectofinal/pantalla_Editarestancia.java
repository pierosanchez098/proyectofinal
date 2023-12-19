package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class pantalla_Editarestancia extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JComboBox<String> estanciasComboBox;
    private JComboBox<String> columnasComboBox;
    private JTextField nuevoValorTextField;

    public pantalla_Editarestancia(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Editar estancias");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(12, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        String[] estancias = obtenerEstancias();
        estanciasComboBox = new JComboBox<>(estancias);

        String[] columnas = obtenerColumnas();
        columnasComboBox = new JComboBox<>(columnas);

        nuevoValorTextField = new JTextField();

        panelInferior.add(new JLabel("Seleccionar Estancia:"));
        panelInferior.add(estanciasComboBox);
        panelInferior.add(new JLabel("Seleccionar Columna:"));
        panelInferior.add(columnasComboBox);
        panelInferior.add(new JLabel("Nuevo Valor:"));
        panelInferior.add(nuevoValorTextField);

        JButton editarEstanciaButton = new JButton("Editar Estancia");
        editarEstanciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarEstancia();
            }
        });

        panelInferior.add(editarEstanciaButton);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private String[] obtenerEstancias() {
        String consulta = "SELECT nombre FROM estancia";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            ResultSet resultado = sentencia.executeQuery();

            int cantidadEstancias = 0;
            while (resultado.next()) {
                cantidadEstancias++;
            }

            resultado = sentencia.executeQuery();
            String[] estancias = new String[cantidadEstancias];
            int indice = 0;
            while (resultado.next()) {
                estancias[indice] = resultado.getString("nombre");
                indice++;
            }
            return estancias;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new String[0];
        }
    }

    private String[] obtenerColumnas() {
        String[] columnas = {"tipo_estancia", "precioxdia", "valoracion", "ubicacion", "informacion",
                             "telefono_est", "disponibilidad", "npersonas", "nombre", "imagen", "precio_creditos"};
        return columnas;
    }



    private void editarEstancia() {
        String nombreEstancia = (String) estanciasComboBox.getSelectedItem();
        String columna = (String) columnasComboBox.getSelectedItem();
        String nuevoValor = nuevoValorTextField.getText();

        String consulta = "UPDATE estancia SET " + columna + "=? WHERE nombre=?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nuevoValor);
            sentencia.setString(2, nombreEstancia);

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Estancia editada exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al editar la estancia");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al editar la estancia");
        }
    }
}