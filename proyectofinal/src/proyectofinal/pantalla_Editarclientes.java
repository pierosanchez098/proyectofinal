package proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class pantalla_Editarclientes extends JFrame {

	private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JComboBox<String> clientesComboBox;
    private JComboBox<String> columnasComboBox;
    private JTextField nuevoValorTextField;
    

    public pantalla_Editarclientes(String usuario, Connection conexion) {
        this.usuario = usuario;
        this.conexion = conexion;

        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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

        JLabel labelTexto = new JLabel("Editar clientes / usuarios");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(12, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));
        
        JPanel panel = new JPanel();

        String[] clientes = obtenerClientes();
        clientesComboBox = new JComboBox<>(clientes);

        String[] columnas = obtenerColumnas();
        columnasComboBox = new JComboBox<>(columnas);

        nuevoValorTextField = new JTextField();
        nuevoValorTextField.setPreferredSize(new Dimension(200, 25));

        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCliente();
            }
        });

        panel.add(new JLabel("Seleccionar Cliente:"));
        panel.add(clientesComboBox, BorderLayout.NORTH);
        panel.add(new JLabel("Seleccionar Columna:"));
        panel.add(columnasComboBox, BorderLayout.CENTER);
        panel.add(new JLabel("Nuevo Valor:"));
        panel.add(nuevoValorTextField, BorderLayout.SOUTH);
        panel.add(new JLabel());
        panel.add(editarButton, BorderLayout.WEST);

        panelInferior.add(panel, BorderLayout.CENTER);
        setVisible(true);
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

    private String[] obtenerColumnas() {
        String[] columnas = {"nombre", "apellidos", "dni", "contrasenya", "telefono", "domicilio", "creditos", "correo"};
        return columnas;
    }

    private void editarCliente() {
        String nombre = (String) clientesComboBox.getSelectedItem();
        String columna = (String) columnasComboBox.getSelectedItem();
        String nuevoValor = nuevoValorTextField.getText();

        String consulta = "UPDATE cliente SET " + columna + "=? WHERE nombre=?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nuevoValor);
            sentencia.setString(2, nombre);

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente editado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al editar el cliente");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al editar el cliente");
        }
    }
}