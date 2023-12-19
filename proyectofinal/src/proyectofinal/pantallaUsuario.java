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
        
        Font fuenteEtiqueta = new Font("Arial", Font.PLAIN, 16); 

        JLabel labelTexto = new JLabel("Mi perfil");
        labelTexto.setFont(new Font(labelTexto.getFont().getName(), Font.PLAIN, 30));
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        barraMenu.add(labelTexto, BorderLayout.CENTER);

        JButton botonCerrarSesion = new JButton("Cerrar sesion");
        botonCerrarSesion.setFont(fuenteEtiqueta);
        botonCerrarSesion.setForeground(new Color(255, 255, 255));
        botonCerrarSesion.setBackground(colorDeFondo);
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

        Dimension campoDimension = new Dimension(99, 20); 

        

        // Agregar campos de entrada, botones "Editar" y l�gica de edici�n
        JTextField[] campos = new JTextField[6];
        JToggleButton[] botonesEditar = new JToggleButton[6];
        String[] valoresOriginales = new String[6];

        try {
            String consulta = "SELECT nombre, apellidos, telefono, dni, contrasenya, correo FROM cliente WHERE nombre = ? OR correo = ?";
            
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, nombreUsuario); 
            preparedStatement.setString(2, nombreUsuario);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si se encuentra un registro, mostrar los datos en los campos de texto
            if (resultSet.next()) {
                for (int i = 0; i < 6; i++) {
                    campos[i] = new JTextField(resultSet.getString(i + 1)); //Indices de resulset comienzan en 1
                    campos[i].setPreferredSize(new Dimension(200, 30));
                    campos[i].setEditable(false);
                    campos[i].setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(41, 128, 185)),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));

                    JLabel etiqueta = new JLabel();
                    switch (i) {
                        case 0:
                            etiqueta.setText("Nombre:");
                            break;
                        case 1:
                            etiqueta.setText("Apellidos:");
                            break;
                        case 2:
                            etiqueta.setText("Telefono:");
                            break;
                        case 3:
                            etiqueta.setText("DNI:");
                            break;
                        case 4:
                            etiqueta.setText("Contrasena:");
                            break;
                        case 5:
                            etiqueta.setText("Correo:");
                            break;
                    }
                    etiqueta.setFont(fuenteEtiqueta);

                    botonesEditar[i] = new JToggleButton("Editar", new ImageIcon("iconos/edit.png"));
                    botonesEditar[i].setFont(fuenteEtiqueta);
                    botonesEditar[i].setForeground(new Color(255, 255, 255));
                    botonesEditar[i].setBackground(new Color(41, 128, 185));
                    botonesEditar[i].setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); 
                    botonesEditar[i].setPreferredSize(new Dimension(80, 30));
                    final int index = i;
                    botonesEditar[i].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (botonesEditar[index].isSelected()) {
                                botonesEditar[index].setText("Cancelar");
                                valoresOriginales[index] = campos[index].getText(); 
                                campos[index].setEditable(true);
                            } else {
                                botonesEditar[index].setText("Editar");
                                campos[index].setText(valoresOriginales[index]);
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
        JLabel numCreditosLabel = new JLabel("Numero de creditos actuales:");
        
        int creditosActuales = obtenerCreditosActuales();
        
        numCreditos.setText(String.valueOf(creditosActuales));

        JTextField comprarCreditos = new JTextField();
        comprarCreditos.setPreferredSize(campoDimension);
        JLabel comprarCreditosLabel = new JLabel("Quieres comprar mas creditos?");

        JButton botonComprarCreditos = new JButton("Comprar creditos");
        botonComprarCreditos.setForeground(new Color(255, 255, 255));
        botonComprarCreditos.setBackground(new Color(100, 149, 237));

        botonComprarCreditos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Creditos creditos = new Creditos(nombreUsuario, conexion);
                creditos.setVisible(true);
                dispose();
            }
        });

        // Boton de gaurdarcambios
        JButton botonGuardarCambios = new JButton("Guardar cambios");
        botonGuardarCambios.setFont(fuenteEtiqueta);
        botonGuardarCambios.setForeground(new Color(255, 255, 255));
        botonGuardarCambios.setBackground(new Color(41, 128, 185));
        
        botonGuardarCambios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                	Connection conexion = book4u.obtenerConexion();
                    
                    for (int i = 0; i < 6; i++) {
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
                                case 5:
                                    consulta += "correo = ?";
                                    break;
                            }
                            consulta += " WHERE nombre = ? OR correo = ?"; 
                            
                            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
                            preparedStatement.setString(1, nuevoValor);
                            preparedStatement.setString(2, nombreUsuario);
                            preparedStatement.setString(3, nombreUsuario);
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
        panelIzquierdo.setBackground(new Color(162, 217, 206));
        panelIzquierdo.add(numCreditosLabel);
        panelIzquierdo.add(numCreditos);
        panelIzquierdo.add(comprarCreditosLabel);
        panelIzquierdo.add(botonComprarCreditos);

        panelGuardarCambios.add(panelIzquierdo, BorderLayout.WEST);
        
        Dimension Panel = new Dimension(100, 80); 

        JPanel panelBotonGuardarCambios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonGuardarCambios.setPreferredSize(Panel);
        panelBotonGuardarCambios.setBackground(new Color(255, 255, 255));
        panelBotonGuardarCambios.add(botonGuardarCambios);
        panelGuardarCambios.add(panelBotonGuardarCambios, BorderLayout.SOUTH);

        panelInferior.add(panelGuardarCambios, BorderLayout.SOUTH);

        add(barraMenu, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.CENTER);
        setVisible(true);
    }
	  
	  private int obtenerCreditosActuales() {
		    try {
		        String consulta = "SELECT creditos FROM cliente WHERE nombre = ? OR correo = ?";
		        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
		        preparedStatement.setString(1, nombreUsuario);
		        preparedStatement.setString(2, nombreUsuario);

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
        
    	
    	
    