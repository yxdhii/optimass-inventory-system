
package dao;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    /*public static String[] validarUsuario(String usuario, String contrasena) {
        String[] datos = null;
        String sql = "SELECT nombre, rol FROM usuario WHERE codigo = ? AND contrasena = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");
                datos = new String[]{nombre, rol}; // ✅ devolvemos nombre y rol
            }

        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
        }
        return datos;
    }

*/
    
      public static String[] validarUsuario(String usuario, String contrasena) {
        String[] datos = null;

        String sql = """
            SELECT e.nombre AS nombreEmpleado, r.nombre AS nombreRol
            FROM usuario u
            INNER JOIN empleados e ON u.codigo = e.codigo
            INNER JOIN roles r ON e.id_roles = r.id
            WHERE u.codigo = ? AND u.contrasena = ?
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                String nombre = rs.getString("nombreEmpleado");
                String rol = rs.getString("nombreRol");
                datos = new String[]{nombre, rol};
            }

        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
        }

        return datos;
    }
public boolean validarPassword(String codigo, String passwordActual) {
    String sql = "SELECT contrasena FROM usuario WHERE codigo = ? AND contrasena = ?";
    
    // 🔹 DEBUG TEMPORAL
    System.out.println("********************************************");
    System.out.println("UsuarioDAO.validarPassword");
    System.out.println("SQL: " + sql);
    System.out.println("Código recibido: [" + codigo + "]");
    System.out.println("Password recibido: [" + passwordActual + "]");
    
    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, codigo);
        ps.setString(2, passwordActual);
        
        System.out.println("Query preparado: " + ps.toString());
        
        ResultSet rs = ps.executeQuery();
        boolean resultado = rs.next();
        
        if (resultado) {
            System.out.println("CONTRASEÑA VÁLIDA - Usuario encontrado");
        } else {
            System.out.println("CONTRASEÑA INVÁLIDA - Usuario NO encontrado");
        }
        System.out.println("*************************************");
        
        return resultado;
    } catch (SQLException e) {
        System.err.println("Error validando contraseña actual: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

//actualizar contraseña
public boolean actualizarPassword(String codigo, String nuevaPassword) {

    String sql = "UPDATE usuario SET contrasena = ? WHERE codigo = ?";

    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nuevaPassword);
        ps.setString(2, codigo);

        return ps.executeUpdate() > 0; // éxito si afectó 1 fila

    } catch (SQLException e) {
        System.err.println("Error actualizando contraseña: " + e.getMessage());
        return false;
    }
}




 //Obtiene el código de usuario por su correo
 
public static String obtenerCodigoPorCorreo(String correo) {
    String sql = "SELECT codigo FROM empleados WHERE correo = ?";
    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, correo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("codigo");
        }
    } catch (SQLException e) {
        System.err.println("Error: " + e.getMessage());
    }
    return null;
}


 // Actualiza contraseña por código
 
public static boolean actualizarPasswordPorCodigo(String codigo, String nuevaPassword) {
    String sql = "UPDATE usuario SET contrasena = ? WHERE codigo = ?";
    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nuevaPassword);
        ps.setString(2, codigo);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error: " + e.getMessage());
        return false;
    }
}

public void enviarCorreoCodigo(String correoDestino, String codigo) throws Exception {
    util.Correo.enviarCodigo(correoDestino, codigo); // enviar correo verdadero
}

public String obtenerCorreoPorCodigoUsuario(String codigoUsuario) {
    String sql = "SELECT correo FROM empleados WHERE codigo = ?";

    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, codigoUsuario);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("correo");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}
