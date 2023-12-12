package proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class pantallaUsuario extends JFrame {

	  private String nombreUsuario;

	  private Connection conexion = book4u.obtenerConexion();


	  public pantallaUsuario(String nombreUsuario, Connection conexion) {

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


        JLabel labelTexto = new JLabel("Mi perfil");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.PLAIN, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JButton botonCerrarSesion = new JButton("Cerrar sesi�n");
        barraMenu.add(botonCerrarSesion, BorderLayout.EAST);
        
        botonCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Pantalla_login Pantalla_login = new Pantalla_login(conexion);
                Pantalla_login.setVisible(true);
            }
        });

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(255, 255, 255, 255));

        ImageIcon usuarioIcono = new ImageIcon("imagenes/usuario.png");
        usuarioIcono = new ImageIcon(usuarioIcono.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
        JLabel usuarioLabel = new JLabel(usuarioIcono);
        panelInferior.add(usuarioLabel, BorderLayout.WEST);

        JPanel panelCampos = new JPanel(new GridLayout(5, 1)); 
        panelCampos.setBackground(new Color(255, 255, 255)); 

        Dimension campoDimension = new Dimension(200, 20); 

        Font fuenteEtiqueta = new Font("Arial", Font.PLAIN, 16); 

        // Agregar campos de entrada, botones "Editar" y l�gica de edici�n
        JTextField[] campos = new JTextField[5];
        JToggleButton[] botonesEditar = new JToggleButton[5];
        String[] valoresOriginales = new String[5];

        try {
            String consulta = "SELECT nombre, apellidos, telefono, dni, contrasenya FROM cliente WHERE nombre = ?";
            
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, nombreUsuario); // Usar el nombre de usuario actual
            
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si se encuentra un registro, mostrar los datos en los campos de texto
            if (resultSet.next()) {
                for (int i = 0; i < 5; i++) {
                    campos[i] = new JTextField(resultSet.getString(i + 1)); //Indices de resulset comienzan en 1
                    campos[i].setPreferredSize(campoDimension);
                    campos[i].setEditable(false);

                    JLabel etiqueta = new JLabel();
                    switch (i) {
                        case 0:
                            etiqueta.setText("Nombre:");
                            break;
                        case 1:
                            etiqueta.setText("Apellidos:");
                            break;
                        case 2:
                            etiqueta.setText("Teléfono:");
                            break;
                        case 3:
                            etiqueta.setText("DNI:");
                            break;
                        case 4:
                            etiqueta.setText("Contraseña:");
                            break;
                    }
                    etiqueta.setFont(fuenteEtiqueta);

                    botonesEditar[i] = new JToggleButton("Editar");
                    botonesEditar[i].setFont(fuenteEtiqueta);
                    final int index = i;
                    botonesEditar[i].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (botonesEditar[index].isSelected()) {
                                botonesEditar[index].setText("Cancelar");
                                valoresOriginales[index] = campos[index].getText(); // Guardar el valor original
                                campos[index].setEditable(true);
                            } else {
                                botonesEditar[index].setText("Editar");
                                campos[index].setText(valoresOriginales[index]); // Restaurar el valor original
                                campos[index].setEditable(false);
                            }
                        }
                    });

                    JPanel filaPanel = new JPanel(new BorderLayout());
                    filaPanel.setBackground(new Color(255, 255, 255));

                    filaPanel.add(etiqueta, BorderLayout.WEST);
                    filaPanel.add(campos[index], BorderLayout.CENTER);
                    filaPanel.add(botonesEditar[index], BorderLayout.EAST);
                    panelCampos.add(filaPanel);
                }
            }

            resultSet.close();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
            
        
    
        panelInferior.add(panelCampos, BorderLayout.CENTER);

        JPanel panelGuardarCambios = new JPanel(new BorderLayout());
        panelGuardarCambios.setBackground(new Color(255, 255, 255));

        JTextField numCreditos = new JTextField();
        numCreditos.setEditable(false);
        numCreditos.setPreferredSize(campoDimension);
        JLabel numCreditosLabel = new JLabel("Número de créditos actuales:");
        
        int creditosActuales = obtenerCreditosActuales();
        
        numCreditos.setText(String.valueOf(creditosActuales));

        JTextField comprarCreditos = new JTextField();
        comprarCreditos.setPreferredSize(campoDimension);
        JLabel comprarCreditosLabel = new JLabel("�Quieres comprar m�s cr�dito?");

        JButton botonComprarCreditos = new JButton("Comprar cr�ditos");

        botonComprarCreditos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Creditos creditos = new Creditos(nombreUsuario, conexion);
                creditos.setVisible(true);
                dispose();
            }
        });

        // Boton de gaurdarcambios
        JButton botonGuardarCambios = new JButton("Guardar cambios");
        botonGuardarCambios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear una nueva conexi�n para esta operaci�n
                	Connection conexion = book4u.obtenerConexion();
                    
                    for (int i = 0; i < 5; i++) {
                        if (campos[i].isEditable()) {
                            String nuevoValor = campos[i].getText();
                            String consulta = "UPDATE cliente SET ";
                            switch (i) {
                                case 0:
                                    consulta += "nombre = ?";
                                    break;
                                case 1:
                                    consulta += "apellidos = ?";
                                    break;
                                case 2:
                                    consulta += "telefono = ?";
                                    break;
                                case 3:
                                    consulta += "dni = ?";
                                    break;
                                case 4:
                                    consulta += "contrasenya = ?";
                                    break;
                            }
                            consulta += " WHERE nombre = ?"; 
                            
                            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
                            preparedStatement.setString(1, nuevoValor);
                            preparedStatement.setString(2, nombreUsuario);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();

                            campos[i].setText(nuevoValor);
                            campos[i].setEditable(false);
                            botonesEditar[i].setText("Editar");
                        }
                    }
                    
                    conexion.close(); 
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIzquierdo.add(numCreditosLabel);
        panelIzquierdo.add(numCreditos);
        panelIzquierdo.add(comprarCreditosLabel);
        panelIzquierdo.add(botonComprarCreditos);

        panelGuardarCambios.add(panelIzquierdo, BorderLayout.WEST);

        JPanel panelBotonGuardarCambios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonGuardarCambios.add(botonGuardarCambios);
        panelGuardarCambios.add(panelBotonGuardarCambios, BorderLayout.SOUTH);

        panelInferior.add(panelGuardarCambios, BorderLayout.SOUTH);

        add(barraMenu, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.CENTER);
        setVisible(true);
    }
	  
	  private int obtenerCreditosActuales() {
		    try {
		        String consulta = "SELECT creditos FROM cliente WHERE nombre = ?";
		        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
		        preparedStatement.setString(1, nombreUsuario);

		        ResultSet resultSet = preparedStatement.executeQuery();

		        if (resultSet.next()) {
		            return resultSet.getInt("creditos");
		        }

		        resultSet.close();
		        preparedStatement.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return 0;
		}
}
        
    	
    	
    