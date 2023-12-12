package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class pantalla_Eliminarestancia extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JComboBox<String> estanciasComboBox;

    public pantalla_Eliminarestancia(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Eliminar una estancia");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        // Obtener la lista de estancias
        String[] estancias = obtenerEstancias();
        estanciasComboBox = new JComboBox<>(estancias);

        JButton eliminarEstanciaButton = new JButton("Eliminar Estancia");
        eliminarEstanciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEstancia();
            }
        });

        panelInferior.add(new JLabel("Seleccionar Estancia:"));
        panelInferior.add(estanciasComboBox);
        panelInferior.add(new JLabel()); // Espacio en blanco
        panelInferior.add(eliminarEstanciaButton);

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

    private void eliminarEstancia() {
        String nombreEstancia = (String) estanciasComboBox.getSelectedItem();

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres eliminar la estancia?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String consulta = "DELETE FROM estancia WHERE nombre=?";
            try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
                sentencia.setString(1, nombreEstancia);
                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Estancia eliminada exitosamente");
                    // Actualizar la lista de estancias
                    String[] estancias = obtenerEstancias();
                    estanciasComboBox.setModel(new DefaultComboBoxModel<>(estancias));
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la estancia");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar la estancia");
            }
        }
    }
}
