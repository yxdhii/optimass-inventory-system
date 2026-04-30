/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelo.Proveedor;
import java.sql.*;
import java.util.*;



/**
 *
 * @author Yadhira Saavedra
 */
public class ProveedorDAO {
    
        private Connection con;

    public ProveedorDAO() {
        con = ConexionBD.getConnection();
    }

    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Proveedor p = new Proveedor(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean agregar(Proveedor p) {
        String sql = "INSERT INTO proveedores (codigo, nombre) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Proveedor p) {
        String sql = "UPDATE proveedores SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM proveedores WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Proveedor buscarProveedor(String codigo) {
        Proveedor proveedor = null;
        String sql = "SELECT * FROM proveedores WHERE codigo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                proveedor = new Proveedor(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }
    
        public String obtenerUltimoCodigo() {
    String sql = "SELECT codigo FROM proveedores ORDER BY id DESC LIMIT 1"; 
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getString("codigo");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; // si no hay registros aún
}
        
         public boolean actualizarNombre(String codigo, String nuevoNombre) {
    String sql = "UPDATE proveedores SET nombre = ? WHERE codigo = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nuevoNombre);
        ps.setString(2, codigo);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
         //agregue esto para el modulo de compras
         public List<String> obtenerProveedores() {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT nombre FROM proveedores";

    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            lista.add(rs.getString("nombre"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
    
}
