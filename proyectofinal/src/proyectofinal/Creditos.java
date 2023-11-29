package proyectofinal;

import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Creditos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField cant;
    //private Connection conexion;
    private JTextField titular;
    private JTextField numTarjeta;
    private JTextField fechaCad;
    private JTextField cvc;

    public Creditos(String nombreUsuario, Connection conexion) {
        // Assuming you have the 'conexion' instance available here.

        getContentPane().setBackground(new Color(213, 232, 212, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 689, 496);
        getContentPane().setLayout(null);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        JButton btnNewButton = new JButton("Volver a mi perfil");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pantallaUsuario pantallaUsuario = new pantallaUsuario(nombreUsuario, conexion);
                pantallaUsuario.setVisible(true);
                dispose(); // Cierra la ventana actual (Creditos)
            }
        });
        
        btnNewButton.setBounds(260, 26, 115, 21);
        getContentPane().add(btnNewButton);

        JLabel lblNewLabel = new JLabel("¿Cómo quieres pagar?");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        lblNewLabel.setBounds(268, 72, 135, 27);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("imagenes/62780 (1).png"));
        lblNewLabel_1.setBounds(290, 109, 85, 68);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_3 = new JLabel("Nombre del titular de la tarjeta");
        lblNewLabel_3.setBounds(241, 201, 142, 13);
        getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Número de la tarjeta");
        lblNewLabel_4.setBounds(241, 253, 95, 13);
        getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Fecha de caducidad");
        lblNewLabel_5.setBounds(241, 305, 103, 13);
        getContentPane().add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("CVC");
        lblNewLabel_6.setBounds(348, 305, 45, 13);
        getContentPane().add(lblNewLabel_6);

        cant = new JTextField();
        cant.setBounds(241, 376, 96, 19);
        getContentPane().add(cant);
        cant.setColumns(10);

        JLabel lblNewLabel_7 = new JLabel("Cantidad");
        lblNewLabel_7.setBounds(241, 357, 45, 13);
        getContentPane().add(lblNewLabel_7);

        JButton pagar = new JButton("Pagar");
        pagar.setBounds(348, 375, 73, 21);
        getContentPane().add(pagar);

        JLabel lblNewLabel_8 = new JLabel("Solo se aceptan cantidades que son múltiples de 10");
        lblNewLabel_8.setForeground(new Color(255, 0, 0));
        lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 8));
        lblNewLabel_8.setBounds(241, 412, 201, 13);
        getContentPane().add(lblNewLabel_8);

        titular = new JTextField();
        titular.setBounds(241, 224, 180, 19);
        getContentPane().add(titular);
        titular.setColumns(10);

        numTarjeta = new JTextField();
        numTarjeta.setBounds(240, 276, 181, 19);
        getContentPane().add(numTarjeta);
        numTarjeta.setColumns(10);

        fechaCad = new JTextField();
        fechaCad.setBounds(240, 328, 96, 19);
        getContentPane().add(fechaCad);
        fechaCad.setColumns(10);

        cvc = new JTextField();
        cvc.setBounds(348, 328, 73, 19);
        getContentPane().add(cvc);
        cvc.setColumns(10);
    }
}

