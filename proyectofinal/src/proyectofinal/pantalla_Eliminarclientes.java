package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pantalla_Eliminarclientes extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JComboBox<String> clientesComboBox;

    public pantalla_Eliminarclientes(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Eliminar un cliente");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(3, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        String[] clientes = obtenerClientes();
        clientesComboBox = new JComboBox<>(clientes);

        panelInferior.add(new JLabel("Seleccionar Cliente:"));
        panelInferior.add(clientesComboBox);

        JButton eliminarClienteButton = new JButton("Eliminar Cliente");
        eliminarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });

        panelInferior.add(eliminarClienteButton);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private String[] obtenerClientes() {
        String consulta = "SELECT nombre FROM cliente";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            ResultSet resultado = sentencia.executeQuery();

            int cantidadClientes = 0;
            while (resultado.next()) {
                cantidadClientes++;
            }

            resultado = sentencia.executeQuery();
            String[] clientes = new String[cantidadClientes];
            int indice = 0;
            while (resultado.next()) {
                clientes[indice] = resultado.getString("nombre");
                indice++;
            }
            return clientes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new String[0];
        }
    }

    private void eliminarCliente() {
        String nombreCliente = (String) clientesComboBox.getSelectedItem();

        String consulta = "DELETE FROM cliente WHERE nombre=?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nombreCliente);

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el cliente");
        }
    }
}
