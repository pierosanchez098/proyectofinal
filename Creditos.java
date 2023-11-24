package proyectofinal;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

    }

    /**
     * Create the frame.
     * @param conexion2 
     * @param nombreUsuario 
     */
    public Creditos(String nombreUsuario, Connection conexion2) {
        // Assuming you have the 'conexion' instance available here.

        getContentPane().setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 689, 496);
        getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("Volver a mi perfil");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pantallaUsuario pantallaUsuario = new pantallaUsuario(nombreUsuario, conexion2);
                pantallaUsuario.setVisible(true);
                dispose(); // Cierra la ventana actual (Creditos)
            }
        });
        
        btnNewButton.setBounds(260, 26, 115, 21);
        getContentPane().add(btnNewButton);

        JLabel lblNewLabel = new JLabel("¿Cómo quieres pagar?");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        lblNewLabel.setBounds(240, 72, 135, 27);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\34667\\Downloads\\proyectofinal-main (2)\\proyectofinal-main\\proyectofinal\\imagenes\\62780 (1).png"));
        lblNewLabel_1.setBounds(149, 116, 85, 68);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\34667\\Downloads\\proyectofinal-main (2)\\proyectofinal-main\\proyectofinal\\imagenes\\Bizum.svg(1).png"));
        lblNewLabel_2.setBounds(340, 94, 135, 99);
        getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Nombre del titular de la tarjeta");
        lblNewLabel_3.setBounds(78, 194, 142, 13);
        getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Número de la tarjeta");
        lblNewLabel_4.setBounds(78, 246, 95, 13);
        getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Fecha de caducidad");
        lblNewLabel_5.setBounds(78, 298, 103, 13);
        getContentPane().add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("CVC");
        lblNewLabel_6.setBounds(185, 298, 45, 13);
        getContentPane().add(lblNewLabel_6);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(303, 183, 27, 247);
        getContentPane().add(separator);

        JButton bizum = new JButton("Bizum");
        bizum.setBounds(420, 280, 85, 21);
        getContentPane().add(bizum);

        cant = new JTextField();
        cant.setBounds(78, 369, 96, 19);
        getContentPane().add(cant);
        cant.setColumns(10);

        JLabel lblNewLabel_7 = new JLabel("Cantidad");
        lblNewLabel_7.setBounds(78, 350, 45, 13);
        getContentPane().add(lblNewLabel_7);

        JButton pagar = new JButton("Pagar");
        pagar.setBounds(185, 368, 73, 21);
        getContentPane().add(pagar);

        JLabel lblNewLabel_8 = new JLabel("Solo se aceptan cantidades que son múltiples de 10");
        lblNewLabel_8.setForeground(new Color(255, 0, 0));
        lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 8));
        lblNewLabel_8.setBounds(78, 405, 201, 13);
        getContentPane().add(lblNewLabel_8);

        titular = new JTextField();
        titular.setBounds(78, 217, 180, 19);
        getContentPane().add(titular);
        titular.setColumns(10);

        numTarjeta = new JTextField();
        numTarjeta.setBounds(77, 269, 181, 19);
        getContentPane().add(numTarjeta);
        numTarjeta.setColumns(10);

        fechaCad = new JTextField();
        fechaCad.setBounds(77, 321, 96, 19);
        getContentPane().add(fechaCad);
        fechaCad.setColumns(10);

        cvc = new JTextField();
        cvc.setBounds(185, 321, 73, 19);
        getContentPane().add(cvc);
        cvc.setColumns(10);
    }
}

