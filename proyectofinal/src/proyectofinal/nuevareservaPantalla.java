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
                MenuPrincipalFrame menuPrincipalFrame = new MenuPrincipalFrame(nombreUsuario, conexion);
                menuPrincipalFrame.setVisible(true);
                dispose();
            }
        });

        JLabel labelTexto = new JLabel("Nueva reserva");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.BOLD, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        Font fuentePersonalizada = new Font("Arial", Font.BOLD, 30);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        try {
            String consulta = "SELECT id_estancia, nombre, tipo_estancia, precioxdia, valoracion, ubicacion, disponibilidad, precio_creditos, imagen FROM estancia";
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
                int precioCreditos = resultSet.getInt("precio_creditos");

                JPanel estanciaPanel = new JPanel(new BorderLayout());

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

                JLabel nombreLabel = CrearLabel(nombreEstancia);
                nombreLabel.setFont(fuentePersonalizada);
                infoPanel.add(nombreLabel);
                infoPanel.add(CrearLabel("Tipo de Estancia: " + tipoEstancia));
                infoPanel.add(CrearLabel("Precio por Dia: " + precioDia + "euros"));
                infoPanel.add(CrearLabel("Valoracion: " + valoracion + " estrellas"));
                infoPanel.add(CrearLabel("Ubicacion: " + ubicacion));
                infoPanel.add(CrearLabel("Disponibilidad: " + disponibilidad));
                infoPanel.add(CrearLabel("Créditos para reservar (1 persona): " + precioCreditos + " créditos"));

                // A�adir la imagen al panel
                JLabel imagenLabel = new JLabel();
                try {
                    ImageIcon icono1 = new ImageIcon(imagenPath);
                    icono1 = new ImageIcon(icono1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    imagenLabel.setIcon(icono1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                infoPanel.add(imagenLabel);

                estanciaPanel.add(infoPanel, BorderLayout.CENTER);

                JButton reservaBoton = new JButton("Realizar reserva");

                int idEstanciaActual = idEstancia;

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

                                String inputPersonas = JOptionPane.showInputDialog(null, "Ingrese el numero de personas:");

                                try {
                                    int numeroPersonas = Integer.parseInt(inputPersonas);
                                    int idCliente = obtenerIdClienteDesdeUsuario(nombreUsuario);

                                    if (!existeCliente(idCliente)) {
                                        JOptionPane.showMessageDialog(null, "El cliente no existe. Por favor, seleccione un cliente valido.", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        double precioEstancia = precioDia; 
                                        double precioTotal = precioEstancia * numeroPersonas;
                                        int creditospreciototal = precioCreditos * numeroPersonas;
                                        
                                        int creditosDisponibles = obtenerCreditosActuales(idCliente);

                                        if (creditosDisponibles >= creditospreciototal) {

                                        String insertReserva = "INSERT INTO reserva (id_reserva, id_cliente, id_estancia, fechai, fechaf, pagado, preciototal, personas, direccion, nombre, precio_creditostotal, creditos_estancia, estado, imagen) VALUES (secureserva.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                        PreparedStatement preparedStatement1 = conexion.prepareStatement(insertReserva);
                                        preparedStatement1.setInt(1, idCliente);
                                        preparedStatement1.setInt(2, idEstanciaActual); 
                                        preparedStatement1.setDate(3, fechaInicio);
                                        preparedStatement1.setDate(4, fechaFin);
                                        preparedStatement1.setString(5, "no"); 
                                        preparedStatement1.setDouble(6, precioTotal);
                                        preparedStatement1.setInt(7, numeroPersonas);
                                        preparedStatement1.setString(8, ubicacion); 
                                        preparedStatement1.setString(9, nombreEstancia);
                                        preparedStatement1.setInt(10, creditospreciototal);
                                        preparedStatement1.setInt(11, precioCreditos);
                                        preparedStatement1.setString(12, "reservado");
                                        preparedStatement1.setString(13, imagenPath);

                                        preparedStatement1.executeUpdate();
                                        
                                        preparedStatement1.close();

                                        String insertHistorico = "INSERT INTO historico (id_reserva, id_cliente, id_estancia, fechai, fechaf, pagado, preciototal, personas, direccion, nombre, precio_creditostotal, creditos_estancia, estado, imagen) VALUES (secuhistorico.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                        PreparedStatement preparedStatementHistorico = conexion.prepareStatement(insertHistorico);
                                        preparedStatementHistorico.setInt(1, idCliente);
                                        preparedStatementHistorico.setInt(2, idEstanciaActual);
                                        preparedStatementHistorico.setDate(3, fechaInicio);
                                        preparedStatementHistorico.setDate(4, fechaFin);
                                        preparedStatementHistorico.setString(5, "no"); 
                                        preparedStatementHistorico.setDouble(6, precioTotal);
                                        preparedStatementHistorico.setInt(7, numeroPersonas);
                                        preparedStatementHistorico.setString(8, ubicacion);
                                        preparedStatementHistorico.setString(9, nombreEstancia);
                                        preparedStatementHistorico.setInt(10, creditospreciototal);
                                        preparedStatementHistorico.setInt(11, precioCreditos);
                                        preparedStatementHistorico.setString(12, "reservado");
                                        preparedStatementHistorico.setString(13, imagenPath);

                                        preparedStatementHistorico.executeUpdate();
                                        preparedStatementHistorico.close();

                                     String updateCreditos = "UPDATE cliente SET creditos = creditos - ? WHERE id_cliente = ?";
                                     PreparedStatement preparedStatement2 = conexion.prepareStatement(updateCreditos);
                                     preparedStatement2.setInt(1, creditospreciototal);
                                     preparedStatement2.setInt(2, idCliente);
                                     preparedStatement2.executeUpdate();
                                     preparedStatement2.close();

                                        
                                        JOptionPane.showMessageDialog(null, "Reserva realizada", "�xito", JOptionPane.INFORMATION_MESSAGE); 
                                        
                                     } else {
                                        // No hay suficientes créditos
                                        JOptionPane.showMessageDialog(null, "No posees los créditos necesarios para hacer la reserva", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    }
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    
                                    JOptionPane.showMessageDialog(null, "Error al realizar la reserva. Por favor, intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                                
                                }
                                
                            }
                        }
                    }
                });

                estanciaPanel.add(reservaBoton, BorderLayout.EAST);

                panelInferior.add(estanciaPanel);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    
        
        add(barraMenu, BorderLayout.NORTH);
        add(new JScrollPane(panelInferior), BorderLayout.CENTER);
        setVisible(true);
    }
    
    

    private int obtenerIdClienteDesdeUsuario(String nombreUsuario) {
        try {
            String consulta = "SELECT id_cliente FROM cliente WHERE nombre = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, nombreUsuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_cliente");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; 
    }
    
    private int obtenerCreditosActuales(int idCliente) {
        try {
            String consulta = "SELECT creditos FROM cliente WHERE id_cliente = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setInt(1, idCliente);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("creditos");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    
   
    
    private boolean existeCliente(int idCliente) {
        try {
            String consulta = "SELECT COUNT(*) FROM cliente WHERE id_cliente = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setInt(1, idCliente);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int count = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar la existencia del cliente. Por favor, int�ntelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    private JLabel CrearLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }
}