/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {
    // Agregar nuevo rol
    public boolean agregarRol(String nombre) {
        String sql = "INSERT INTO roles (nombre) VALUES (?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar rol: " + e.getMessage());
            return false;
        }
    }

    //  Editar rol existente
    public boolean editarRol(int id, String nuevoNombre) {
        String sql = "UPDATE roles SET nombre = ? WHERE id = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al editar rol: " + e.getMessage());
            return false;
        }
    }

    // Eliminar rol
    public boolean eliminarRol(int id) {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar rol: " + e.getMessage());
            return false;
        }
    }

    //  Listar todos los roles
    public List<Object[]> listarRoles() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM roles";
        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Object[]{rs.getInt("id"), rs.getString("nombre")});
            }
        } catch (SQLException e) {
            System.err.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }
    
    
    //COMBOBOX CARGO
    public java.util.List<String> obtenerNombresRoles() {
    java.util.List<String> lista = new java.util.ArrayList<>();
    String sql = "SELECT nombre FROM roles ORDER BY nombre ASC";
    try (java.sql.Connection con = ConexionBD.getConnection();
         java.sql.Statement st = con.createStatement();
         java.sql.ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(rs.getString("nombre"));
        }
    } catch (java.sql.SQLException e) {
        System.err.println("Error al obtener roles: " + e.getMessage());
    }
    return lista;
}
}
