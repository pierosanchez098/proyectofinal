package proyectofinal;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class book4u extends JFrame {
    private static final String USER = "DW2_2324_BOOK4U_ESECODI";
    private static final String PWD = "AESECODI";
    private static final String[] HOSTS = {"192.168.3.26", "oracle.ilerna.com"};
    private static final String PORT = "1521";
    private static final String SID = "xe";

    private static String hostFuncional = null;

    public static Connection obtenerConexion() {
        if (hostFuncional == null) {
            hostFuncional = conectarBaseDatos();
        }
        return hostFuncional != null ? establecerConexion(hostFuncional) : null;
    }

    private static Connection establecerConexion(String host) {
        Connection con = null;
        String url = "jdbc:oracle:thin:@" + host + ":" + PORT + ":" + SID;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, USER, PWD);
            System.out.println("Conectado a la base de datos con host: " + host);
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha encontrado el driver " + e);
        } catch (SQLException e) {
            System.out.println("Error en las credenciales o en la URL " + e);
        }

        return con;
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

    private static String conectarBaseDatos() {
        System.out.println("Intentando conectarse a la base de datos");

        for (String host : HOSTS) {
            Connection con = establecerConexion(host);
            
         // Si la conexión es exitosa, almacena el host funcional y retorna dicho host
            if (con != null) {
                hostFuncional = host;
                return host;
            } else {
                System.out.println("Intentando con otro host...");
            }
        }

        System.out.println("No se pudo conectar a la base de datos con ninguno de los hosts.");
        return null;
    }
}