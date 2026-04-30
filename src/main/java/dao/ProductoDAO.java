
package dao;
import conexion.ConexionBD;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import modelo.Producto;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductoDAO {
   private Connection conexion;

    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ProductoDAO() {
    this.conexion = new conexion.ConexionBD().getConnection(); // ajusta el paquete si es que la clase está en otro
}

        //  AGREGAR PRODUCTO
    public boolean agregarProducto(Producto p) {
        String sql = "INSERT INTO productos (codigo, nombre, id_categoria, id_proveedor, precio, cantidad, fecha_caducidad, imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getIdCategoria());
            ps.setInt(4, p.getIdProveedor());
            ps.setDouble(5, p.getPrecio());
            ps.setInt(6, p.getCantidad());
            ps.setDate(7, (Date) p.getFechaCaducidad());
            ps.setBytes(8, p.getImagen());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //  EDITAR PRODUCTO
    public boolean editarProducto(Producto p) {
        String sql = "UPDATE productos SET codigo=?, nombre=?, id_categoria=?, id_proveedor=?, precio=?, cantidad=?, fecha_caducidad=?, imagen=? WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getIdCategoria());
            ps.setInt(4, p.getIdProveedor());
            ps.setDouble(5, p.getPrecio());
            ps.setInt(6, p.getCantidad());
            ps.setDate(7, (Date) p.getFechaCaducidad());
            ps.setBytes(8, p.getImagen());
            ps.setInt(9, p.getId());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al editar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //  ELIMINAR PRODUCTO
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setFechaCaducidad(rs.getDate("fecha_caducidad")); // Tipo DATE
                p.setImagen(rs.getBytes("imagen"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }
    
    // BUSCAR PRODUCTO POR ID
    public Producto obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        Producto p = null;
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setFechaCaducidad(rs.getDate("fecha_caducidad"));
                p.setImagen(rs.getBytes("imagen"));
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar producto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return p;
    }
    
    /*
    // BUSCAR PRODUCTOS POR NOMBRE (PARA LA BÚSQUEDA)
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE nombre LIKE ? ORDER BY nombre ASC";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setFechaCaducidad(rs.getDate("fecha_caducidad"));
                p.setImagen(rs.getBytes("imagen"));
                lista.add(p);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar productos por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
*/
    
    /*
    public List<Producto> buscarProductosPorNombre(String nombre) {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT * FROM productos WHERE nombre LIKE ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, "%" + nombre + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setCodigo(rs.getString("codigo"));
            p.setNombre(rs.getString("nombre"));
            p.setIdCategoria(rs.getInt("id_categoria"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setFechaCaducidad(rs.getDate("fecha_caducidad"));
            lista.add(p);
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar productos: " + e.getMessage());
    }
    return lista;
    }
    
    */
    
    // BUSCAR PRODUCTOS POR NOMBRE O CATEGORÍA
public List<Producto> buscarProductosPorNombre(String texto) {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT p.* FROM productos p " +
                 "LEFT JOIN categorias c ON p.id_categoria = c.id " +
                 "WHERE p.nombre LIKE ? OR c.nombre LIKE ?";
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        String textoBusqueda = "%" + texto + "%";
        ps.setString(1, textoBusqueda);
        ps.setString(2, textoBusqueda);
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setCodigo(rs.getString("codigo"));
            p.setNombre(rs.getString("nombre"));
            p.setIdCategoria(rs.getInt("id_categoria"));
            p.setIdProveedor(rs.getInt("id_proveedor"));
            p.setPrecio(rs.getDouble("precio"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setFechaCaducidad(rs.getDate("fecha_caducidad"));
            p.setImagen(rs.getBytes("imagen"));
            lista.add(p);
        }
        
    } catch (SQLException e) {
        System.out.println("Error al buscar productos: " + e.getMessage());
        e.printStackTrace();
    }
    
    return lista;
}

        //  VERIFICAR SI EXISTE UN CÓDIGO
    public boolean existeCodigo(String codigo) {
        String sql = "SELECT COUNT(*) FROM productos WHERE codigo = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al verificar código: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
public List<Producto> obtenerProductos() {
    List<Producto> lista = new ArrayList<>();
    String sql = "SELECT id, nombre, precio FROM productos";
    try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            Producto p = new Producto();
            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));
            p.setPrecio(rs.getDouble("precio")); //trae también el precio
            lista.add(p);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener productos: " + e.getMessage());
    }
    return lista;
}


public boolean actualizarStock(String nombreProducto, int cantidadVendida) {
    String sql = "UPDATE productos SET cantidad = cantidad - ? WHERE nombre = ?";
    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, cantidadVendida);
        ps.setString(2, nombreProducto);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


//para el modulo de compras
public boolean aumentarStock(String nombreProducto, int cantidadComprada) {
    String sql = "UPDATE productos SET cantidad = cantidad + ? WHERE nombre = ?";
    try (Connection con = ConexionBD.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, cantidadComprada);
        ps.setString(2, nombreProducto);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}



//  Inventario completo la grafica
public Map<String, Integer> obtenerInventarioParaReporte() {
    Map<String, Integer> inventario = new LinkedHashMap<>();
    String sql = "SELECT nombre, cantidad FROM productos ORDER BY cantidad DESC";
    
    try (Statement st = conexion.createStatement(); 
         ResultSet rs = st.executeQuery(sql)) {
        
        while (rs.next()) {
            inventario.put(rs.getString("nombre"), rs.getInt("cantidad"));
        }
        
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
    
    return inventario;
}

// Productos con bajo stock la grafica
public Map<String, Integer> obtenerProductosBajoStock(int umbral) {
    Map<String, Integer> productos = new LinkedHashMap<>();
    String sql = "SELECT nombre, cantidad FROM productos WHERE cantidad <= ? ORDER BY cantidad ASC";
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, umbral);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            productos.put(rs.getString("nombre"), rs.getInt("cantidad"));
        }
        
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
    
    return productos;
}

}
