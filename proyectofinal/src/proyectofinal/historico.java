
package proyectofinal;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class historico extends JFrame {
	
	private String nombreUsuario;
	private Connection conexion = book4u.obtenerConexion();


	
	public historico(String nombreUsuario, Connection conexion) {
		
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
	    panel.setBackground(new Color(214, 232, 212));
	    contentPane.add(panel, BorderLayout.CENTER);

	    JPanel panel_1 = new JPanel();
	    panel_1.setBackground(new Color(214, 232, 212));
	    panel_1.setLayout(new FlowLayout(FlowLayout.CENTER));

	    ImageIcon icono = new ImageIcon("imagenes/casa.png");
	    icono = new ImageIcon(icono.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH));
	    JLabel iconoLabel = new JLabel(icono);
	    panel_1.add(iconoLabel);

	    JLabel lblNewLabel_1 = new JLabel("Historico");
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

	    JLabel pie = new JLabel("Reservas realizadas:");
	    pie.setFont(new Font("Arial", Font.BOLD, 22));
	    panel.add(pie, BorderLayout.WEST);

	    int idCliente = obtenerIdCliente(nombreUsuario);

	    mostrarReservas(idCliente, panel);
	}

	private int obtenerIdCliente(String nombreUsuario) {
	    try {
	        String consulta = "SELECT id_cliente FROM cliente WHERE nombre = ? OR correo = ?";
	        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
	        preparedStatement.setString(1, nombreUsuario);
	        preparedStatement.setString(2, nombreUsuario);

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
	        String consultaReservas = "SELECT nombre, direccion, preciototal, personas, fechai, fechaf, precio_creditostotal, estado, imagen FROM historico WHERE id_cliente = ?";
	        PreparedStatement preparedStatementReservas = conexion.prepareStatement(consultaReservas);
	        preparedStatementReservas.setInt(1, idCliente);

	        ResultSet resultSetReservas = preparedStatementReservas.executeQuery();

	        JPanel reservasPanel = new JPanel();
	        reservasPanel.setLayout(new BoxLayout(reservasPanel, BoxLayout.Y_AXIS));
	        reservasPanel.setBackground(new Color(247, 220, 111));

	        JLabel encabezadoReservas = new JLabel("BookCoins gastados y equivalencia:");
	        encabezadoReservas.setFont(new Font("Arial", Font.BOLD, 22));
	        encabezadoReservas.setForeground(new Color (0,0,0));
	        reservasPanel.add(encabezadoReservas);

	        while (resultSetReservas.next()) {
	            String nombreReserva = resultSetReservas.getString("nombre");
	            String direccion = resultSetReservas.getString("direccion");
	            int precioTotal = resultSetReservas.getInt("preciototal");
	            int personas = resultSetReservas.getInt("personas");
	            Date fechaInicio = resultSetReservas.getDate("fechai");
	            Date fechaFin = resultSetReservas.getDate("fechaf");
	            int preciocreditosTotal = resultSetReservas.getInt("precio_creditostotal");
	            String estadoReserva = resultSetReservas.getString("estado");
	            String imagenPath = resultSetReservas.getString("imagen");

	            JPanel reservaPanel = new JPanel(new BorderLayout());

	            JPanel infoPanel = new JPanel();
	            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	            infoPanel.setBackground(new Color(255,255,255,255));

	            JLabel nombreEstanciaLabel = new JLabel("Nombre: " + nombreReserva);
	            nombreEstanciaLabel.setFont(new Font("Arial", Font.BOLD, 18));
	            infoPanel.add(nombreEstanciaLabel);

	            infoPanel.add(crearLabel("Direccion: " + direccion));
	            infoPanel.add(crearLabel("Precio Total: " + precioTotal + " euros"));
	            infoPanel.add(crearLabel("Personas: " + personas));
	            infoPanel.add(crearLabel("Fecha Inicio: " + fechaInicio));
	            infoPanel.add(crearLabel("Fecha Fin: " + fechaFin));
	            infoPanel.add(crearLabel("Estado: " + estadoReserva));

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
	            JPanel borde = new JPanel();
	            borde.setBackground(new Color (247, 220, 111));
	            reservaPanel.add(borde, BorderLayout.SOUTH);

	            JPanel disponibilidadPanel = new JPanel();
	            disponibilidadPanel.setLayout(new BoxLayout(disponibilidadPanel, BoxLayout.Y_AXIS));
	            disponibilidadPanel.setBackground(new Color(255,255,255,255));

	            int precioCreditos = preciocreditosTotal;
	            disponibilidadPanel.add(crearLabel("Precio de reserva: " + precioCreditos + " BookCoins"));
	            
	            int precioEquivalente = precioCreditos * 10;
	            disponibilidadPanel.add(crearLabel("Precio equivalente (dinero real): " + precioEquivalente + " euros"));

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





	private JLabel crearLabel(String text) {
	    JLabel label = new JLabel(text);
	    label.setFont(new Font("Arial", Font.PLAIN, 16));
	    return label;
	}
	}
