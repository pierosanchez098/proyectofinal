package proyectofinal;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.sql.*;

public class MenuPrincipalFrame extends JFrame {

private String nombreUsuario;
private Connection conexion = book4u.obtenerConexion();

public MenuPrincipalFrame(String nombreUsuario, Connection conexion) {

this.nombreUsuario = nombreUsuario;
this.conexion = conexion;

setDefaultCloseOperation(EXIT_ON_CLOSE);
setSize(1080, 720);
setTitle("Pantalla Principal");
setLocationRelativeTo(null);
setMinimumSize(new Dimension (200, 200));

	iniciarComponentes();			
			
}
private void iniciarComponentes() {

JPanel panel1 = new JPanel();
ImageIcon icon = new ImageIcon("imagenes/Logo_Book4U.jpg");
panel1.setBounds(0, 0, 1080, 200);
panel1.add(new JLabel(icon));
panel1.setLayout(null);
panel1.setBackground(new Color(213,232,212,255));
this.getContentPane().add(panel1);

JLabel etiqueta2 = new JLabel();
ImageIcon imagen  = new ImageIcon("imagenes/Logo_Book4U.jpg"); 
etiqueta2.setBounds(05, 0, 170, 150);
etiqueta2.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH)));
panel1.add(etiqueta2);





JLabel Texto1 = new JLabel();
Texto1.setText("Pantalla Principal");
Texto1.setBounds(335, 30, 400, 50);
Texto1.setFont(new Font(Texto1.getFont().getName(), Font.BOLD, 30));
panel1.add(Texto1, BorderLayout.CENTER);


JLabel Texto2 = new JLabel();
Texto2.setText("Nro de creditos:");
Texto2.setBounds(670, 45, 110, 20);
Texto2.setForeground(Color.white);
Texto2.setOpaque(true);
Texto2.setBackground(new Color(15,82,15,255));
Texto2.setFont(new Font("arial",Font.LAYOUT_LEFT_TO_RIGHT,15));
panel1.add(Texto2);

JButton Nuevas=new JButton("Nuevas Reservas");

Nuevas.setBounds(0, 150, 360, 50);
Nuevas.setBackground(new Color(213,232,212,255));
//ImageIcon botonimagen = new ImageIcon("Pictures/back5.png");
//atras.setIcon(new ImageIcon(botonimagen.getImage().getScaledInstance(atras.getWidth(),atras.getHeight(),Image.SCALE_SMOOTH )));
panel1.add(Nuevas);
Nuevas.setVisible(true);

Nuevas.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        nuevareservaPantalla nuevareservaPantalla = new nuevareservaPantalla(nombreUsuario,conexion);
        nuevareservaPantalla.setVisible(true);
        dispose();
    }
});

JButton Reservas=new JButton("Mis reservas");
Reservas.setBounds(360, 150, 360, 50);
Reservas.setBackground(new Color(213,232,212,255));
//ImageIcon botonimagen = new ImageIcon("Pictures/back5.png");
//atras.setIcon(new ImageIcon(botonimagen.getImage().getScaledInstance(atras.getWidth(),atras.getHeight(),Image.SCALE_SMOOTH )));
panel1.add(Reservas);
Reservas.setVisible(true);

Reservas.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        misReservasPantalla misReservasPantalla = new misReservasPantalla(nombreUsuario,conexion);
        misReservasPantalla.setVisible(true);
        dispose();
    }
});



JButton History=new JButton("Historial");
History.setBounds(720, 150, 360, 50);
History.setBackground(new Color(213,232,212,255));
//ImageIcon botonimagen = new ImageIcon("Pictures/back5.png");
//atras.setIcon(new ImageIcon(botonimagen.getImage().getScaledInstance(atras.getWidth(),atras.getHeight(),Image.SCALE_SMOOTH )));
panel1.add(History);
History.setVisible(true);

History.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent arg0) {
	 dispose();
	 historico history = new historico(nombreUsuario, conexion);
	 history.setVisible(true);
}

});



JButton nomUsusario=new JButton(obtenerNombreUsuario());
nomUsusario.setBounds(670, 5, 280, 30);
nomUsusario.setForeground(new Color(255, 255, 255));
nomUsusario.setBackground(new Color(47, 79, 79));
panel1.add(nomUsusario);
panel1.add(nomUsusario);

 nomUsusario.addActionListener(new ActionListener() {
	 public void actionPerformed(ActionEvent e) {
         pantallaUsuario pantallaUsuario = new pantallaUsuario(nombreUsuario, conexion);
         pantallaUsuario.setVisible(true);
         dispose();
 }
 
});

 JTextField numCredit = new JTextField();
 numCredit.setBounds(775, 45, 175, 21);
 panel1.add(numCredit);
 numCredit.setVisible(true);
 numCredit.setEditable(false);

int creditosActuales = obtenerCreditosActuales();

//Mostrar el número actual de créditos en el JTextField
numCredit.setText(" " + String.valueOf(creditosActuales));



JPanel panel2 = new JPanel();
panel2.setBackground(Color.white);
panel2.setLayout(null);
this.getContentPane().add(panel2);


//Anuncio1
JLabel anuncio1 = new JLabel();
ImageIcon foto  = new ImageIcon("imagenes/medplaya-hotel-regente.jpg"); 
anuncio1.setBounds(5, 300, 170, 150);
anuncio1.setIcon(new ImageIcon(foto.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH)));
panel2.add(anuncio1);
anuncio1.setVisible(true);

JLabel Textoanuncio1 = new JLabel();
Textoanuncio1.setText("Medplaya Hotel Regente 74 euros");
//Texto1.setForeground(Color.orange);//color teexto
Textoanuncio1.setBounds(205, 230, 275, 150);
Textoanuncio1.setFont(new Font("arial",Font.BOLD,20));
panel2.add(Textoanuncio1);

JLabel ddescripcnanuncio0 = new JLabel();
ddescripcnanuncio0.setText("Habitacion Doble con balcon 2 camas");
//Texto1.setForeground(Color.orange);//color teexto
ddescripcnanuncio0.setBounds(205, 260, 270, 150);
ddescripcnanuncio0.setFont(new Font("arial",Font.BOLD,12));
panel2.add(ddescripcnanuncio0);

JLabel descripcnanuncio00 = new JLabel();
descripcnanuncio00.setText("2 Camas Individuales");
//Texto1.setForeground(Color.orange);//color teexto
descripcnanuncio00.setBounds(205, 274, 270, 150);
descripcnanuncio00.setFont(new Font("arial",Font.BOLD,12));
panel2.add(descripcnanuncio00);

JLabel descripcnanuncio000 = new JLabel();
descripcnanuncio000.setText("Desayuno incluido");
//Texto1.setForeground(Color.orange);//color teexto
descripcnanuncio000.setBounds(205, 289, 270, 150);
descripcnanuncio000.setFont(new Font("arial",Font.BOLD,12));
panel2.add(descripcnanuncio000);

JLabel descripcnanuncio0000 = new JLabel();
descripcnanuncio0000.setText("a 2,4 km del centro cerca de la playa");
//Texto1.setForeground(Color.orange);//color teexto
descripcnanuncio0000.setBounds(205, 304, 270, 150);
descripcnanuncio0000.setFont(new Font("arial",Font.BOLD,12));
panel2.add(descripcnanuncio0000);






/////////////////////////////
//Anuncio2
JLabel anuncio2 = new JLabel();
ImageIcon foto2  = new ImageIcon("imagenes/port-benidorm-hotel-spa.jpg"); 
anuncio2.setBounds(5, 480, 170, 150);
anuncio2.setIcon(new ImageIcon(foto2.getImage().getScaledInstance(170, 150, Image.SCALE_SMOOTH)));
panel2.add(anuncio2);
anuncio2.setVisible(true);

JLabel Textoanuncio2 = new JLabel();
Textoanuncio2.setText("Port Benidorm hotel Spa 73 euros");
//Texto1.setForeground(Color.orange);//color teexto
Textoanuncio2.setBounds(205, 415, 275, 150);
Textoanuncio2.setFont(new Font("arial",Font.BOLD,20));
panel2.add(Textoanuncio2);

JLabel ddescripcnanuncio1 = new JLabel();
ddescripcnanuncio1.setText("Habitacion Doble (2 adultos)");
//Texto1.setForeground(Color.orange);//color teexto
ddescripcnanuncio1.setBounds(205, 440, 270, 150);
ddescripcnanuncio1.setFont(new Font("arial",Font.BOLD,12));
panel2.add(ddescripcnanuncio1);

JLabel descripcnanuncio2 = new JLabel();
descripcnanuncio2.setText("2 Camas Individuales");
//Texto1.setForeground(Color.orange);//color teexto
descripcnanuncio2.setBounds(205, 454, 270, 150);
descripcnanuncio2.setFont(new Font("arial",Font.BOLD,12));
panel2.add(descripcnanuncio2);

JLabel descripcnanuncio3 = new JLabel();
descripcnanuncio3.setText("Desayuno incluido");
//Texto1.setForeground(Color.orange);//color teexto
descripcnanuncio3.setBounds(205, 467, 270, 150);
descripcnanuncio3.setFont(new Font("arial",Font.BOLD,12));
panel2.add(descripcnanuncio3);
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


public String obtenerNombreUsuario() {

    try {
        String consulta = "SELECT nombre FROM cliente WHERE nombre = ? OR correo = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
        preparedStatement.setString(1, nombreUsuario);
        preparedStatement.setString(2, nombreUsuario);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            nombreUsuario = resultSet.getString("nombre");
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return nombreUsuario;
}
}