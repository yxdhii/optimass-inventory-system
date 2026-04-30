
package dao;

import conexion.ConexionBD;
import modelo.Categoria;
import java.sql.*;
import java.util.*;

public class CategoriaDAO {
     private Connection con;

    public CategoriaDAO() {
        con = ConexionBD.getConnection();
    }

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Categoria c = new Categoria(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean agregar(Categoria c) {
        String sql = "INSERT INTO categorias (codigo, nombre) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCodigo());
            ps.setString(2, c.getNombre());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Categoria c) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Categoria buscarCategoria(String codigo) {
    Categoria categoria = null;
    String sql = "SELECT * FROM categorias WHERE codigo = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            categoria = new Categoria(
                rs.getInt("id"),
                rs.getString("codigo"),
                rs.getString("nombre")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return categoria;
    
    
}
    
    public String obtenerUltimoCodigo() {
    String sql = "SELECT codigo FROM categorias ORDER BY id DESC LIMIT 1"; 
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
    String sql = "UPDATE categorias SET nombre = ? WHERE codigo = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nuevoNombre);
        ps.setString(2, codigo);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
}
