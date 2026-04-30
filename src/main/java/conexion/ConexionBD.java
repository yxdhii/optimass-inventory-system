
package conexion;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_gestion_db";
    private static final String USUARIO = "root"; 
    private static final String CONTRASENA = ""; 
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión exitosa a la base de datos.");
            
            
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return con;
    }
    
}
