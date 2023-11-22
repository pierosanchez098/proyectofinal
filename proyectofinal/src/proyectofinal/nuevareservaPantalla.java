package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;

public class nuevareservaPantalla extends JFrame {

    private String nombreUsuario;
    private Connection conexion = book4u.obtenerConexion();

    public nuevareservaPantalla(String nombreUsuario, Connection conexion) {
        this.nombreUsuario = nombreUsuario;
        this.conexion = conexion;


        setSize(1080, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Color de fondo
        Color colorDeFondo = new Color(15, 82, 15, 255);
        getContentPane().setBackground(colorDeFondo);

        // Panel superior
        JPanel barraMenu = new JPanel(new BorderLayout());
        barraMenu.setBackground(new Color(213, 232, 212, 255));
        barraMenu.setPreferredSize(new Dimension(1050, 80));

        // Icono
        ImageIcon icono = new ImageIcon("imagenes/casa.png");
        icono = new ImageIcon(icono.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH));
        JLabel iconoLabel = new JLabel(icono);
        barraMenu.add(iconoLabel, BorderLayout.WEST);

        iconoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuPrincipalFrame menuPrincipalFrame = new MenuPrincipalFrame(nombreUsuario, conexion);
                menuPrincipalFrame.setVisible(true);
                dispose();
            }
        });

        JLabel labelTexto = new JLabel("Nueva reserva");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.PLAIN, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);
        
        
        Font fuentePersonalizada = new Font("Arial", Font.BOLD, 30); // Puedes ajustar el tamaño y el estilo aquí

        // Panel inferior con paneles individuales para cada estancia
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        // Obtener datos de la tabla "estancia"
        try {
            String consulta = "SELECT nombre, tipo_estancia, precioxdia, valoracion, ubicacion, disponibilidad, imagen FROM estancia";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nombreEstancia = resultSet.getString("nombre");
                String tipoEstancia = resultSet.getString("tipo_estancia");
                String precioDia = resultSet.getString("precioxdia");
                String valoracion = resultSet.getString("valoracion");
                String ubicacion = resultSet.getString("ubicacion");
                String disponibilidad = resultSet.getString("disponibilidad");
                String imagenPath = resultSet.getString("imagen");
                

                // Crear un panel para cada estancia
                JPanel estanciaPanel = new JPanel();
                estanciaPanel.setLayout(new BoxLayout(estanciaPanel, BoxLayout.Y_AXIS));
                
                JLabel nombreLabel = createLabel(nombreEstancia);
                nombreLabel.setFont(fuentePersonalizada); // Aplicar la fuente personalizada al nombre
                estanciaPanel.add(nombreLabel);

                // Añadir etiquetas con la información de la estancia
                estanciaPanel.add(nombreLabel);
                estanciaPanel.add(createLabel("Tipo de Estancia: " + tipoEstancia));
                estanciaPanel.add(createLabel("Precio por Día: " + precioDia + "€"));
                estanciaPanel.add(createLabel("Valoración: " + valoracion + " estrellas"));
                estanciaPanel.add(createLabel("Ubicación: " + ubicacion));
                estanciaPanel.add(createLabel("Disponibilidad: " + disponibilidad));

                // Añadir la imagen al panel
                JLabel imagenLabel = new JLabel();
                try {
                    ImageIcon icono1 = new ImageIcon(imagenPath);
                    icono1 = new ImageIcon(icono1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    imagenLabel.setIcon(icono1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                estanciaPanel.add(imagenLabel);

                // Añadir el panel de estancia al panel inferior
                panelInferior.add(estanciaPanel);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }
}
