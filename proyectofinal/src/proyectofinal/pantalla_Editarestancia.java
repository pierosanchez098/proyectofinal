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
    private JTextField tipoTextField;
    private JTextField precioxDiaTextField;
    private JTextField valoracionTextField;
    private JTextField ubicacionTextField;
    private JTextField informacionTextField;
    private JTextField telefonoTextField;
    private JTextField disponibilidadTextField;
    private JTextField npersonasTextField;
    private JTextField nombreTextField;
    private JTextField imagenTextField;
    private JTextField precioCreditosTextField;

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
        estanciasComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosEstanciaSeleccionada((String) estanciasComboBox.getSelectedItem());
            }
        });

        tipoTextField = new JTextField();
        precioxDiaTextField = new JTextField();
        valoracionTextField = new JTextField();
        ubicacionTextField = new JTextField();
        informacionTextField = new JTextField();
        telefonoTextField = new JTextField();
        disponibilidadTextField = new JTextField();
        npersonasTextField = new JTextField();
        nombreTextField = new JTextField();
        imagenTextField = new JTextField();
        precioCreditosTextField = new JTextField();

        panelInferior.add(new JLabel("Seleccionar Estancia:"));
        panelInferior.add(estanciasComboBox);
        panelInferior.add(new JLabel("Tipo:"));
        panelInferior.add(tipoTextField);
        panelInferior.add(new JLabel("Precio por Día:"));
        panelInferior.add(precioxDiaTextField);
        panelInferior.add(new JLabel("Valoración:"));
        panelInferior.add(valoracionTextField);
        panelInferior.add(new JLabel("Ubicación:"));
        panelInferior.add(ubicacionTextField);
        panelInferior.add(new JLabel("Información:"));
        panelInferior.add(informacionTextField);
        panelInferior.add(new JLabel("Teléfono:"));
        panelInferior.add(telefonoTextField);
        panelInferior.add(new JLabel("Disponibilidad:"));
        panelInferior.add(disponibilidadTextField);
        panelInferior.add(new JLabel("Número de Personas:"));
        panelInferior.add(npersonasTextField);
        panelInferior.add(new JLabel("Nombre:"));
        panelInferior.add(nombreTextField);
        panelInferior.add(new JLabel("Imagen:"));
        panelInferior.add(imagenTextField);
        panelInferior.add(new JLabel("Precio en Créditos:"));
        panelInferior.add(precioCreditosTextField);

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

    private void cargarDatosEstanciaSeleccionada(String nombreEstancia) {
        String consulta = "SELECT * FROM estancia WHERE nombre = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, nombreEstancia);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                tipoTextField.setText(resultado.getString("tipo_estancia"));
                precioxDiaTextField.setText(resultado.getString("precioxdia"));
                valoracionTextField.setText(resultado.getString("valoracion"));
                ubicacionTextField.setText(resultado.getString("ubicacion"));
                informacionTextField.setText(resultado.getString("informacion"));
                telefonoTextField.setText(resultado.getString("telefono_est"));
                disponibilidadTextField.setText(resultado.getString("disponibilidad"));
                npersonasTextField.setText(resultado.getString("npersonas"));
                nombreTextField.setText(resultado.getString("nombre"));
                imagenTextField.setText(resultado.getString("imagen"));
                precioCreditosTextField.setText(resultado.getString("precio_creditos"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void editarEstancia() {
        String nombreEstancia = (String) estanciasComboBox.getSelectedItem();
        String tipo = tipoTextField.getText();
        String precioxDia = precioxDiaTextField.getText();
        String valoracion = valoracionTextField.getText();
        String ubicacion = ubicacionTextField.getText();
        String informacion = informacionTextField.getText();
        String telefono = telefonoTextField.getText();
        String disponibilidad = disponibilidadTextField.getText();
        String npersonas = npersonasTextField.getText();
        String nombre = nombreTextField.getText();
        String imagen = imagenTextField.getText();
        String precioCreditos = precioCreditosTextField.getText();

        String consulta = "UPDATE estancia SET tipo_estancia=?, precioxdia=?, valoracion=?, ubicacion=?, informacion=?, telefono_est=?, disponibilidad=?, npersonas=?, nombre=?, imagen=?, precio_creditos=? WHERE nombre=?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setString(1, tipo);
            sentencia.setString(2, precioxDia);
            sentencia.setString(3, valoracion);
            sentencia.setString(4, ubicacion);
            sentencia.setString(5, informacion);
            sentencia.setString(6, telefono);
            sentencia.setString(7, disponibilidad);
            sentencia.setString(8, npersonas);
            sentencia.setString(9, nombre);
            sentencia.setString(10, imagen);
            sentencia.setString(11, precioCreditos);
            sentencia.setString(12, nombreEstancia);

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

