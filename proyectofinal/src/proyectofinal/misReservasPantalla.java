package proyectofinal;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class misReservasPantalla extends JFrame {


	private String nombreUsuario;
	private Connection conexion = book4u.obtenerConexion();

	public misReservasPantalla(String nombreUsuario, Connection conexion) {

	this.nombreUsuario = nombreUsuario;
	this.conexion = conexion;



	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1080, 720);
    setLocationRelativeTo(null);

    JPanel contentPane = new JPanel();
    contentPane.setBackground(new Color(15, 82, 15));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    contentPane.add(panel, BorderLayout.CENTER);

    JPanel panel_1 = new JPanel();
    panel_1.setBackground(new Color(214, 232, 212));
    panel_1.setLayout(new FlowLayout(FlowLayout.CENTER));

    ImageIcon icono = new ImageIcon("imagenes/casa.png");
    icono = new ImageIcon(icono.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH));
    JLabel iconoLabel = new JLabel(icono);
    panel_1.add(iconoLabel);

    JLabel lblNewLabel_1 = new JLabel("Mis reservas");
    lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 34));
    panel_1.add(lblNewLabel_1);

    iconoLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            MenuPrincipalFrame menuPrincipalFrame = new MenuPrincipalFrame(nombreUsuario, conexion);
            menuPrincipalFrame.setVisible(true);
            dispose();
        }
    });

    panel.add(panel_1, BorderLayout.NORTH);

    JLabel pie = new JLabel("Reservas en pie:");
    pie.setFont(new Font("Arial", Font.BOLD, 22));
    panel.add(pie, BorderLayout.WEST);

    int idCliente = obtenerIdCliente(nombreUsuario);

    mostrarReservas(idCliente, panel);
}

private int obtenerIdCliente(String nombreUsuario) {
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

private void mostrarReservas(int idCliente, JPanel panel) {
    try {
        String consultaReservas = "SELECT id_reserva, nombre, direccion, preciototal, personas, fechai, fechaf, imagen FROM reserva WHERE id_cliente = ? AND estado = 'reservado'";
        PreparedStatement preparedStatementReservas = conexion.prepareStatement(consultaReservas);
        preparedStatementReservas.setInt(1, idCliente);

        ResultSet resultSetReservas = preparedStatementReservas.executeQuery();

        JPanel reservasPanel = new JPanel();
        reservasPanel.setLayout(new BoxLayout(reservasPanel, BoxLayout.Y_AXIS));

        JLabel encabezadoReservas = new JLabel("Precio y disponibilidad:");
        encabezadoReservas.setFont(new Font("Arial", Font.BOLD, 22));
        reservasPanel.add(encabezadoReservas);

        while (resultSetReservas.next()) {
            int idReserva = resultSetReservas.getInt("id_reserva");
            String nombreReserva = resultSetReservas.getString("nombre");
            String direccion = resultSetReservas.getString("direccion");
            int precioTotal = resultSetReservas.getInt("preciototal");
            int personas = resultSetReservas.getInt("personas");
            Date fechaInicio = resultSetReservas.getDate("fechai");
            Date fechaFin = resultSetReservas.getDate("fechaf");
            String imagenPath = resultSetReservas.getString("imagen");

            boolean disponible = verificarDisponibilidad(idReserva);

            JPanel reservaPanel = new JPanel(new BorderLayout());

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            JLabel nombreEstanciaLabel = new JLabel("Nombre: " + nombreReserva);
            nombreEstanciaLabel.setFont(new Font("Arial", Font.BOLD, 18));
            infoPanel.add(nombreEstanciaLabel);

            infoPanel.add(crearLabel("Direccion: " + direccion));
            infoPanel.add(crearLabel("Precio Total: " + precioTotal));
            infoPanel.add(crearLabel("Personas: " + personas));
            infoPanel.add(crearLabel("Fecha Inicio: " + fechaInicio));
            infoPanel.add(crearLabel("Fecha Fin: " + fechaFin));

            JLabel imagenLabel = new JLabel();
            try {
                ImageIcon iconoReserva = new ImageIcon(imagenPath);
                iconoReserva = new ImageIcon(iconoReserva.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                imagenLabel.setIcon(iconoReserva);
            } catch (Exception e) {
                e.printStackTrace();
            }
            infoPanel.add(imagenLabel);

            reservaPanel.add(infoPanel, BorderLayout.CENTER);

            JPanel disponibilidadPanel = new JPanel();
            disponibilidadPanel.setLayout(new BoxLayout(disponibilidadPanel, BoxLayout.Y_AXIS));

            int precioCreditos = obtenerPrecioCreditos(idReserva);
            disponibilidadPanel.add(crearLabel("Precio de reserva: " + precioCreditos + " crÃ©ditos"));

            if (disponible) {
            	JButton btnDisponible = new JButton("Disponible (haz click para modificar)");
            	btnDisponible.addActionListener(e -> {
            	    Object[] opciones = {"Fecha de inicio", "Fecha de fin", "Numero de personas", "Cancelar reserva"};
            	    int opcionElegida = JOptionPane.showOptionDialog(null, "¿Que quieres modificar?", "Modificar Reserva", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            	    if (opcionElegida == 0 || opcionElegida == 1) {
            	        JDateChooser dateChooser = new JDateChooser();
            	        int result = JOptionPane.showConfirmDialog(null, dateChooser, "Seleccione una nueva fecha", JOptionPane.OK_CANCEL_OPTION);

            	        if (result == JOptionPane.OK_OPTION) {
            	            Date nuevaFecha = new java.sql.Date(dateChooser.getDate().getTime());
            	            
            	            
            	        
                    
                    switch (opcionElegida) {
                    case 0: 
                        modificarFechaInicio(idReserva, nuevaFecha);
                        break;
                    case 1: 
                        modificarFechaFin(idReserva, nuevaFecha);                        
                        break;
                    case 2: 
                        String nuevoNumeroPersonas = JOptionPane.showInputDialog(null, "Ingrese el nuevo numero de personas:");
                        modificarNumeroPersonas(idReserva, Integer.parseInt(nuevoNumeroPersonas));
                        break;
                    case 3:
                    	cancelarReserva(idReserva);
                    	cancelarReservaHistorico(idReserva);
                    	break;
                    default:
                        // El usuario cerrÃ³ la ventana sin seleccionar ninguna opciÃ³n
                        break;
                
                    }
                    }
            	    } else if (opcionElegida == 2) {
                        String nuevoNumeroPersonas = JOptionPane.showInputDialog(null, "Ingrese el nuevo numero de personas:");
                        modificarNumeroPersonas(idReserva, Integer.parseInt(nuevoNumeroPersonas));
                    } else if (opcionElegida == 3) {
                    	cancelarReserva(idReserva);
                    	cancelarReservaHistorico(idReserva);
                    }
                });
             
                
                disponibilidadPanel.add(btnDisponible);
            } else {
                JLabel lblNoDisponible = new JLabel("No disponible");
                disponibilidadPanel.add(lblNoDisponible);
            }

            reservaPanel.add(disponibilidadPanel, BorderLayout.EAST);

            reservasPanel.add(reservaPanel);
        }

        JScrollPane scrollPane = new JScrollPane(reservasPanel);

        panel.add(scrollPane, BorderLayout.CENTER);

        resultSetReservas.close();
        preparedStatementReservas.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    panel.revalidate();
    panel.repaint();
}

private boolean verificarDisponibilidad(int idReserva) {
    try {
        String consultaDisponibilidad = "SELECT e.disponibilidad FROM reserva r " +
                                        "JOIN estancia e ON r.id_estancia = e.id_estancia " +
                                        "WHERE r.id_reserva = ?";
        PreparedStatement preparedStatementDisponibilidad = conexion.prepareStatement(consultaDisponibilidad);
        preparedStatementDisponibilidad.setInt(1, idReserva);

        ResultSet resultSetDisponibilidad = preparedStatementDisponibilidad.executeQuery();

        if (resultSetDisponibilidad.next()) {
            String disponibilidad = resultSetDisponibilidad.getString("disponibilidad");
            return "Si".equalsIgnoreCase(disponibilidad);
        }

        resultSetDisponibilidad.close();
        preparedStatementDisponibilidad.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

private void cancelarReserva(int idReserva) {
    try {
        String updateQuery = "UPDATE reserva SET estado = 'cancelado' WHERE id_reserva = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery);
        preparedStatement.setInt(1, idReserva);

        int filasAfectadas = preparedStatement.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(
                null, "Reserva cancelada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE
            );

            cancelarReservaHistorico(idReserva);
        } else {
            JOptionPane.showMessageDialog(
                null, "No se pudo cancelar la reserva", "Error", JOptionPane.ERROR_MESSAGE
            );
        }

        preparedStatement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
            null, "Error al cancelar la reserva. Por favor, inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE
        );
    }
}

private void cancelarReservaHistorico(int idReserva) {
    try {
        String updateQuery = "UPDATE historico SET estado = 'cancelado' WHERE id_reserva = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery);
        preparedStatement.setInt(1, idReserva);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

private int obtenerPrecioCreditos(int idReserva) {
    try {
        String consultaPrecioCreditos = "SELECT precio_creditostotal FROM reserva WHERE id_reserva = ?";
        PreparedStatement preparedStatementPrecioCreditos = conexion.prepareStatement(consultaPrecioCreditos);
        preparedStatementPrecioCreditos.setInt(1, idReserva);

        ResultSet resultSetPrecioCreditos = preparedStatementPrecioCreditos.executeQuery();

        if (resultSetPrecioCreditos.next()) {
            return resultSetPrecioCreditos.getInt("precio_creditostotal");
        }

        resultSetPrecioCreditos.close();
        preparedStatementPrecioCreditos.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return -1;
}

private void modificarNumeroPersonas(int idReserva, int nuevoNumeroPersonas) {
    try {
        int creditosEstancia = obtenerCreditosEstancia(idReserva);

        if (creditosEstancia != -1) {
            int nuevoPrecioCreditos = nuevoNumeroPersonas * creditosEstancia;

            String updateQuery = "UPDATE reserva SET personas = ?, precio_creditostotal = ? WHERE id_reserva = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, nuevoNumeroPersonas);
                preparedStatement.setInt(2, nuevoPrecioCreditos);
                preparedStatement.setInt(3, idReserva);

                int filasAfectadas = preparedStatement.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(
                        null, "Numero de personas modificado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        null, "No se pudo modificar el numero de personas", "Error", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                null, "No se pudo obtener el valor de 'creditos_estancia'", "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
            null, "Error al modificar el numero de personas. Por favor, intentelo de nuevo.", "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


private int obtenerCreditosEstancia(int idReserva) {
    try {
        String consultaCreditosEstancia = "SELECT creditos_estancia FROM reserva WHERE id_reserva = ?";
        try (PreparedStatement preparedStatementCreditosEstancia = conexion.prepareStatement(consultaCreditosEstancia)) {
            preparedStatementCreditosEstancia.setInt(1, idReserva);

            try (ResultSet resultSetCreditosEstancia = preparedStatementCreditosEstancia.executeQuery()) {
                if (resultSetCreditosEstancia.next()) {
                    return resultSetCreditosEstancia.getInt("creditos_estancia");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return -1;
}

private void modificarFechaInicio(int idReserva, Date nuevaFecha) {
    try {
    	
    	java.sql.Date sqlNuevaFecha = new java.sql.Date(nuevaFecha.getTime());
        String updateQuery = "UPDATE reserva SET fechai = ? WHERE id_reserva = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery);
        preparedStatement.setDate(1, sqlNuevaFecha);
        preparedStatement.setInt(2, idReserva);
        
        int filasAfectadas = preparedStatement.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "Fecha modificada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo modificar la fecha", "Error", JOptionPane.ERROR_MESSAGE);
        }

        preparedStatement.close();
        
        String updatehistorico = "UPDATE historico SET fechai = ? WHERE id_reserva = ?";
        PreparedStatement preparedStatementHistorico = conexion.prepareStatement(updatehistorico);
        preparedStatementHistorico.setDate(1, sqlNuevaFecha);
        preparedStatementHistorico.setInt(2, idReserva);
        
        preparedStatementHistorico.executeUpdate();
        preparedStatementHistorico.close();
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al modificar la fecha. Por favor, intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void modificarFechaFin(int idReserva, Date nuevaFecha) {
    try {
    	java.sql.Date sqlNuevaFecha = new java.sql.Date(nuevaFecha.getTime());
        String updateQuery = "UPDATE reserva SET fechaf = ? WHERE id_reserva = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery);
        preparedStatement.setDate(1, sqlNuevaFecha);
        preparedStatement.setInt(2, idReserva);
        
        int filasAfectadas = preparedStatement.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "Fecha modificada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo modificar la fecha", "Error", JOptionPane.ERROR_MESSAGE);
        }

        preparedStatement.close();
        
        String updatehistorico = "UPDATE historico SET fechaf = ? WHERE id_reserva = ?";
        PreparedStatement preparedStatementHistorico = conexion.prepareStatement(updatehistorico);
        preparedStatementHistorico.setDate(1, sqlNuevaFecha);
        preparedStatementHistorico.setInt(2, idReserva);
        
        preparedStatementHistorico.executeUpdate();
        preparedStatementHistorico.close();
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al modificar la fecha. Por favor, intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}



private JLabel crearLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Arial", Font.PLAIN, 16));
    return label;
}
}



