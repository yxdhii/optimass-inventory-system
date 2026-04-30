/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yadhira Saavedra
 */
public class CompraDAO {
    public boolean registrarCompra(String proveedor, String producto, int cantidad, double precioUnitario) {
        String sql = "INSERT INTO compras (proveedor, producto, cantidad, precio_unitario, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, proveedor);
            ps.setString(2, producto);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precioUnitario);
            ps.setDouble(5, cantidad * precioUnitario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Object[]> obtenerHistorialCompras() {
    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT proveedor, producto, cantidad, precio_unitario, fecha FROM compras";

    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[5];
            fila[0] = rs.getString("proveedor");
            fila[1] = rs.getString("producto");
            fila[2] = rs.getInt("cantidad");
            fila[3] = rs.getDouble("precio_unitario");
            fila[4] = rs.getString("fecha");

            lista.add(fila);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
}
