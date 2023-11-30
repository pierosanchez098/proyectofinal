package proyectofinal;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

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

        Font fuentePersonalizada = new Font("Arial", Font.BOLD, 30);

        // Panel inferior con paneles individuales para cada estancia
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        // Obtener datos de la tabla "estancia"
        try {
            String consulta = "SELECT id_estancia, nombre, tipo_estancia, precioxdia, valoracion, ubicacion, disponibilidad, imagen FROM estancia";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idEstancia = resultSet.getInt("id_estancia");
                String nombreEstancia = resultSet.getString("nombre");
                String tipoEstancia = resultSet.getString("tipo_estancia");
                int precioDia = resultSet.getInt("precioxdia");
                String valoracion = resultSet.getString("valoracion");
                String ubicacion = resultSet.getString("ubicacion");
                String disponibilidad = resultSet.getString("disponibilidad");
                String imagenPath = resultSet.getString("imagen");

                // Crear un panel para cada estancia
                JPanel estanciaPanel = new JPanel(new BorderLayout());

                // Crear un panel para la información de la estancia
                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

                JLabel nombreLabel = CrearLabel(nombreEstancia);
                nombreLabel.setFont(fuentePersonalizada);
                infoPanel.add(nombreLabel);
                infoPanel.add(CrearLabel("Tipo de Estancia: " + tipoEstancia));
                infoPanel.add(CrearLabel("Precio por Día: " + precioDia + "€"));
                infoPanel.add(CrearLabel("Valoración: " + valoracion + " estrellas"));
                infoPanel.add(CrearLabel("Ubicación: " + ubicacion));
                infoPanel.add(CrearLabel("Disponibilidad: " + disponibilidad));

                // Añadir la imagen al panel
                JLabel imagenLabel = new JLabel();
                try {
                    ImageIcon icono1 = new ImageIcon(imagenPath);
                    icono1 = new ImageIcon(icono1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    imagenLabel.setIcon(icono1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                infoPanel.add(imagenLabel);

                // Agregar el panel de información al panel de estancia
                estanciaPanel.add(infoPanel, BorderLayout.CENTER);

                // Añadir el botón "Realizar reserva" a la derecha
                JButton reservaBoton = new JButton("Realizar reserva");

                reservaBoton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDateChooser dateChooserInicio = new JDateChooser();

                        int resultInicio = JOptionPane.showConfirmDialog(null, dateChooserInicio, "Seleccione la fecha de inicio", JOptionPane.OK_CANCEL_OPTION);

                        if (resultInicio == JOptionPane.OK_OPTION) {
                            Date fechaInicio = new Date(dateChooserInicio.getDate().getTime());
                            System.out.println("Fecha de inicio seleccionada: " + fechaInicio);

                            JDateChooser dateChooserFin = new JDateChooser();
                            int resultFin = JOptionPane.showConfirmDialog(null, dateChooserFin, "Seleccione la fecha de fin", JOptionPane.OK_CANCEL_OPTION);

                            if (resultFin == JOptionPane.OK_OPTION) {
                                Date fechaFin = new Date(dateChooserFin.getDate().getTime());
                                System.out.println("Fecha de fin seleccionada: " + fechaFin);

                                String inputPersonas = JOptionPane.showInputDialog(null, "Ingrese el número de personas:");

                                try {
                                    // Convertir la entrada a un entero
                                    int numeroPersonas = Integer.parseInt(inputPersonas);

                                    // Guardar los valores necesarios antes de cerrar el resultSet
                                    int idCliente = obtenerIdClienteDesdeReserva(1);
                                    double precioEstancia = precioDia; // Aquí ajusta según tu lógica
                                    double precioTotal = precioEstancia * numeroPersonas;

                                    // Ahora, puedes usar los valores en tu consulta SQL
                                    String insertReserva = "INSERT INTO reserva (id_reserva, id_cliente, id_estancia, fechai, fechaf, pagado, preciototal, personas, direccion) VALUES (mi_secuencia.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
                                    PreparedStatement preparedStatement1 = conexion.prepareStatement(insertReserva);
                                    preparedStatement1.setInt(1, idCliente);
                                    preparedStatement1.setInt(2, idEstancia);
                                    preparedStatement1.setDate(3, fechaInicio);
                                    preparedStatement1.setDate(4, fechaFin);
                                    preparedStatement1.setString(5, "si"); // Valor predeterminado
                                    preparedStatement1.setDouble(6, precioTotal);
                                    preparedStatement1.setInt(7, numeroPersonas);
                                    preparedStatement1.setString(8, ubicacion); // Reemplaza con el valor real

                                    preparedStatement1.executeUpdate();

                                    // Cierra el PreparedStatement después de su uso
                                    preparedStatement1.close();
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                });

                estanciaPanel.add(reservaBoton, BorderLayout.EAST);

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

    private int obtenerIdClienteDesdeReserva(int idReserva) {
        try {
            String consulta = "SELECT id_cliente FROM reserva WHERE id_reserva = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setInt(1, idReserva);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Devolver el ID del cliente si se encontró la reserva en la base de datos
                return resultSet.getInt("id_cliente");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Devolver un valor predeterminado o manejar el caso de error según tus necesidades
        return -1; // Por ejemplo, devolver -1 si no se encuentra la reserva
    }

    private JLabel CrearLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }
}

