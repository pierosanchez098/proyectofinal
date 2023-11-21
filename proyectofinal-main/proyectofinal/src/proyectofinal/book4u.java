package proyectofinal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class book4u extends JFrame {
private static final String USER = "DW2_2324_BOOK4U_ESECODI";
private static final String PWD = "AESECODI";
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521:xe";
    


public static Connection obtenerConexion() {
    return conectarBaseDatos();
}

public static void main(String[] args) {
   Connection conexion = book4u.obtenerConexion();
   if (conexion != null) {
       SwingUtilities.invokeLater(() -> {
           Pantalla_login Pantalla_login = new Pantalla_login(conexion);
           Pantalla_login.setVisible(true);
       });
   } else {
       System.out.println("No se pudo conectar a la base de datos. Verifica tus credenciales y URL.");
   }
}

private static Connection conectarBaseDatos() {
   Connection con = null;
   System.out.println("Intentando conectarse a la base de datos");
   try {
       Class.forName("oracle.jdbc.driver.OracleDriver");
       con = DriverManager.getConnection(URL, USER, PWD);
   } catch (ClassNotFoundException e) {
       System.out.println("No se ha encontrado el driver " + e);
   } catch (SQLException e) {
       System.out.println("Error en las credenciales o en la URL " + e);
   }
   System.out.println("Conectado a la base de datos");
   return con;
}
}
