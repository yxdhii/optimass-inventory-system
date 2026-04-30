
package controlador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dao.ProductoDAO;
import modelo.Producto;
import conexion.ConexionBD;
import java.util.Map;


public class ProductoControlador {
    private Connection conexion;
    private ProductoDAO productoDAO;

    public ProductoControlador(Connection conexion) {
        this.conexion = conexion;
        this.productoDAO = new ProductoDAO(conexion);
    }
     //  AGREGAR PRODUCTO
    public boolean agregarProducto(Producto producto) {
        return productoDAO.agregarProducto(producto);
    }
    
    // EDITAR PRODUCTO
    public boolean editarProducto(Producto producto) {
        return productoDAO.editarProducto(producto);
    }
    
    // ELIMINAR PRODUCTO
    public boolean eliminarProducto(int id) {
        return productoDAO.eliminarProducto(id);
    }
    
    //  LISTAR TODOS LOS PRODUCTOS
    public List<Producto> listarProductos() {
        return productoDAO.listarProductos();
    }

    //  OBTENER PRODUCTO POR ID
    public Producto obtenerProductoPorId(int id) {
        return productoDAO.obtenerProductoPorId(id);
    }

    // BUSCAR PRODUCTOS POR NOMBRE
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoDAO.buscarProductosPorNombre(nombre);
    }


    public ArrayList<String> obtenerCategorias() {
        ArrayList<String> lista = new ArrayList<>();
        try {
            String sql = "SELECT nombre FROM categorias ORDER BY id ASC";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<String> obtenerProveedores() {
        ArrayList<String> lista = new ArrayList<>();
        try {
            String sql = "SELECT nombre FROM proveedores ORDER BY id ASC";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    // Métodos auxiliares para convertir id <-> nombre (útil para combos)
    public String obtenerNombreCategoriaPorId(int id) {
        if (id <= 0) return "";
        try {
            String sql = "SELECT nombre FROM categorias WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getString("nombre");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "";
    }

    public String obtenerNombreProveedorPorId(int id) {
        if (id <= 0) return "";
        try {
            String sql = "SELECT nombre FROM proveedores WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getString("nombre");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "";
    }

    public int obtenerIdCategoriaPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return 0;
        try {
            String sql = "SELECT id FROM categorias WHERE nombre = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int obtenerIdProveedorPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return 0;
        try {
            String sql = "SELECT id FROM proveedores WHERE nombre = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    
    //  OBTENER ID DE PRODUCTO POR CÓDIGO
public int obtenerIdProductoPorCodigo(String codigo) {
    if (codigo == null || codigo.trim().isEmpty()) return 0;
    try {
        String sql = "SELECT id FROM productos WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener ID por código: " + e.getMessage());
        e.printStackTrace();
    }
    return 0;
}
    
    
    // GENERAR CÓDIGO DE PRODUCTO AUTOMÁTICAMENTE
    public String generarCodigoProducto() {
        String nuevoCodigo = "PROD-0001";
        String sql = "SELECT codigo FROM productos ORDER BY id DESC LIMIT 1";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String ultimoCodigo = rs.getString("codigo");
                if (ultimoCodigo != null && ultimoCodigo.startsWith("PROD-")) {
                    try {
                        int numero = Integer.parseInt(ultimoCodigo.substring(5));
                        numero++;
                        nuevoCodigo = String.format("PROD-%04d", numero);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al parsear número del código: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al generar código: " + e.getMessage());
            e.printStackTrace();
        }
        
        return nuevoCodigo;
    }

    //VERIFICAR SI EXISTE UN CÓDIGO
    public boolean existeCodigo(String codigo) {
        return productoDAO.existeCodigo(codigo);
    }
    
    
    //esto es para la grafica
    public Map<String, Integer> obtenerInventarioParaReporte() {
    
        
        return productoDAO.obtenerInventarioParaReporte();

    }


    public Map<String, Integer> obtenerProductosBajoStock(int umbral) {
    
        return productoDAO.obtenerProductosBajoStock(umbral);

    }

}
