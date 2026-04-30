
package controlador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dao.EmpleadoDAO;
import modelo.Empleado;
import conexion.ConexionBD;
public class EmpleadoControlador {
     private Connection conexion;
    private EmpleadoDAO empleadoDAO;

    public EmpleadoControlador(Connection conexion) {
        this.conexion = conexion;
        this.empleadoDAO = new EmpleadoDAO(conexion);
    }

    // AGREGAR EMPLEADO
    public boolean agregarEmpleado(Empleado empleado) {
        return empleadoDAO.agregarEmpleado(empleado);
    }
    
    // EDITAR EMPLEADO
    public boolean editarEmpleado(Empleado empleado) {
        return empleadoDAO.editarEmpleado(empleado);
    }
    
    // ELIMINAR EMPLEADO
    public boolean eliminarEmpleado(int id) {
        return empleadoDAO.eliminarEmpleado(id);
    }
    
    // LISTAR TODOS LOS EMPLEADOS
    public List<Empleado> listarEmpleados() {
        return empleadoDAO.listarEmpleados();
    }

    // OBTENER EMPLEADO POR ID
    public Empleado obtenerEmpleadoPorId(int id) {
        return empleadoDAO.obtenerEmpleadoPorId(id);
    }

    // BUSCAR EMPLEADOS POR NOMBRE
    public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
        return empleadoDAO.buscarEmpleadosPorNombre(nombre);
    }

    // OBTENER ROLES DESDE LA BD
    public ArrayList<String> obtenerRoles() {
        ArrayList<String> lista = new ArrayList<>();
        try {
            String sql = "SELECT nombre FROM roles ORDER BY id ASC";
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

    // OBTENER NOMBRE DE ROL POR ID
    public String obtenerNombreRolPorId(int id) {
        if (id <= 0) return "";
        try {
            String sql = "SELECT nombre FROM roles WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getString("nombre");
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return "";
    }

    // OBTENER ID DE ROL POR NOMBRE
    public int obtenerIdRolPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return 0;
        try {
            String sql = "SELECT id FROM roles WHERE nombre = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return 0;
    }
    
    // OBTENER ID DE EMPLEADO POR CÓDIGO
    public int obtenerIdEmpleadoPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) return 0;
        try {
            String sql = "SELECT id FROM empleados WHERE codigo = ?";
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

    // GENERAR CÓDIGO DE EMPLEADO AUTOMÁTICAMENTE (M0001, M0002, etc.)
    public String generarCodigoEmpleado() {
        String nuevoCodigo = "M0001";
        String sql = "SELECT codigo FROM empleados ORDER BY id DESC LIMIT 1";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String ultimoCodigo = rs.getString("codigo");
                if (ultimoCodigo != null && ultimoCodigo.startsWith("M")) {
                    try {
                        int numero = Integer.parseInt(ultimoCodigo.substring(1));
                        numero++;
                        nuevoCodigo = String.format("M%04d", numero);
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

    // VERIFICAR SI EXISTE UN CÓDIGO
    public boolean existeCodigo(String codigo) {
        return empleadoDAO.existeCodigo(codigo);
    }
    
    
    //esto es parte de mostrar en la tabla los datos
    public Empleado buscarEmpleadoPorCodigo(String codigo) {
    return empleadoDAO.buscarPorCodigo(codigo);
}
    
    
    
}
