/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class VentaDAO {
    public boolean registrarVenta(String cliente, String producto, int cantidad, double precio, double total) {
        String sql = "INSERT INTO ventas (cliente, producto, cantidad, precio, total) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente);
            ps.setString(2, producto);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precio);
            ps.setDouble(5, total);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     // === NUEVO MÉTODO: OBTENER TODAS LAS VENTAS GUARDADAS ===
    public List<Object[]> obtenerVentas() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, cliente, producto, cantidad, precio, total FROM ventas";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio"),
                    rs.getDouble("total")
                };
                lista.add(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // === BUSCAR VENTAS POR PRODUCTO ===
public List<Object[]> buscarVentasPorProducto(String nombreProducto) {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT fecha, cliente, producto, cantidad, total FROM ventas WHERE producto = ?";
    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nombreProducto);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] fila = {
                    rs.getDate("fecha"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("total")
                };
                lista.add(fila);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
    
    
}
