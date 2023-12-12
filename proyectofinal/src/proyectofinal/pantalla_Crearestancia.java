package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class pantalla_Crearestancia extends JFrame {

    private String usuario;
    private Connection conexion = book4u.obtenerConexion();
    private JTextField tipoEstanciaTextField;
    private JTextField precioXDiaTextField;
    private JTextField valoracionTextField;
    private JTextField ubicacionTextField;
    private JTextField informacionTextField;
    private JTextField telefonoEstTextField;
    private JTextField disponibilidadTextField;
    private JTextField nPersonasTextField;
    private JTextField nombreTextField;
    private JTextField imagenTextField;
    private JTextField precioCreditosTextField;

    public pantalla_Crearestancia(String usuario, Connection conexion) {
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

        JLabel labelTexto = new JLabel("Crear estancia");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(12, 2));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        tipoEstanciaTextField = new JTextField();
        precioXDiaTextField = new JTextField();
        valoracionTextField = new JTextField();
        ubicacionTextField = new JTextField();
        informacionTextField = new JTextField();
        telefonoEstTextField = new JTextField();
        disponibilidadTextField = new JTextField();
        nPersonasTextField = new JTextField();
        nombreTextField = new JTextField();
        imagenTextField = new JTextField();
        precioCreditosTextField = new JTextField();

        panelInferior.add(new JLabel("Tipo de Estancia:"));
        panelInferior.add(tipoEstanciaTextField);
        panelInferior.add(new JLabel("Precio por Día:"));
        panelInferior.add(precioXDiaTextField);
        panelInferior.add(new JLabel("Valoración:"));
        panelInferior.add(valoracionTextField);
        panelInferior.add(new JLabel("Ubicación:"));
        panelInferior.add(ubicacionTextField);
        panelInferior.add(new JLabel("Información:"));
        panelInferior.add(informacionTextField);
        panelInferior.add(new JLabel("Teléfono Estancia:"));
        panelInferior.add(telefonoEstTextField);
        panelInferior.add(new JLabel("Disponibilidad:"));
        panelInferior.add(disponibilidadTextField);
        panelInferior.add(new JLabel("Número de Personas:"));
        panelInferior.add(nPersonasTextField);
        panelInferior.add(new JLabel("Nombre:"));
        panelInferior.add(nombreTextField);
        panelInferior.add(new JLabel("Imagen:"));
        panelInferior.add(imagenTextField);
        panelInferior.add(new JLabel("Precio en Créditos:"));
        panelInferior.add(precioCreditosTextField);

        JButton crearEstanciaButton = new JButton("Crear Estancia");
        crearEstanciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearEstancia();
            }
        });

        panelInferior.add(crearEstanciaButton);

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private void crearEstancia() {
        try {
            String tipoEstancia = tipoEstanciaTextField.getText();
            String precioXDia = precioXDiaTextField.getText();
            int valoracion = Integer.parseInt(valoracionTextField.getText());
            String ubicacion = ubicacionTextField.getText();
            String informacion = informacionTextField.getText();
            int telefonoEst = Integer.parseInt(telefonoEstTextField.getText());
            String disponibilidad = disponibilidadTextField.getText();
            int nPersonas = Integer.parseInt(nPersonasTextField.getText());
            String nombre = nombreTextField.getText();
            String imagen = imagenTextField.getText();
            int precioCreditos = Integer.parseInt(precioCreditosTextField.getText());

            String consulta = "INSERT INTO estancia (id_estancia, tipo_estancia, precioxdia, valoracion, ubicacion, informacion, telefono_est, disponibilidad, npersonas, nombre, imagen, precio_creditos) VALUES (secuestancia.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
                sentencia.setString(1, tipoEstancia);
                sentencia.setString(2, precioXDia);
                sentencia.setInt(3, valoracion);
                sentencia.setString(4, ubicacion);
                sentencia.setString(5, informacion);
                sentencia.setInt(6, telefonoEst);
                sentencia.setString(7, disponibilidad);
                sentencia.setInt(8, nPersonas);
                sentencia.setString(9, nombre);
                sentencia.setString(10, imagen);
                sentencia.setInt(11, precioCreditos);

                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Estancia creada exitosamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al crear la estancia");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al crear la estancia");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos en campos adecuados");
        }
    }
}
