package proyectofinal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class medplaya_hotel extends JFrame{
	
	public medplaya_hotel() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1080, 720); //El tamaño de que de la ventana de la App
		setTitle("Pantalla Princial");
		setLocationRelativeTo(null); //permite que la ventana siempre se inicilice en el centro de la pantalla
		setMinimumSize(new Dimension (200, 200));//el tamaño minimo
		
			iniciarComponentes();			
					
	}
private void iniciarComponentes() {
	
	JPanel panel1 = new JPanel();
	//ImageIcon icon = new ImageIcon("pictures/Logo_Book4U.jpg");
	panel1.setBounds(0, 0, 1080, 200);
	//panel1.add(new JLabel(icon));
	panel1.setLayout(null);
	panel1.setBackground(new Color(213,232,212,255));
	this.getContentPane().add(panel1);
	
	
	JLabel Texto1 = new JLabel();
	Texto1.setText("Medplaya Hotel");
	//Texto1.setForeground(Color.orange);//color teexto
	Texto1.setBounds(335, 30, 400, 50);
	Texto1.setFont(new Font("georgia",Font.BOLD,35));
	panel1.add(Texto1);
	
}

}
