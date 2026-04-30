/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Empleado;
import java.sql.*;
import java.util.*;
import conexion.ConexionBD;
import java.sql.Date;
public class EmpleadoDAO {
        private Connection conexion;

    public EmpleadoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public EmpleadoDAO() {
        this.conexion = new conexion.ConexionBD().getConnection();
    }

public boolean agregarEmpleado(Empleado e) {
    try {
        // Crear primero el usuario
        if (!crearUsuarioParaEmpleado(e.getCodigo(), e.getNombre(), e.getIdRol())) {
            System.out.println("Error: no se pudo crear el usuario.");
            return false;
        }

        // Luego insertar el empleado
        String sql = "INSERT INTO empleados (codigo, nombre, id_roles, correo, telefono, salario_base, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, e.getCodigo());
            ps.setString(2, e.getNombre());
            ps.setInt(3, e.getIdRol());
            ps.setString(4, e.getCorreo());
            ps.setString(5, e.getTelefono());
            ps.setDouble(6, e.getSalarioBase());
            ps.setDate(7, new java.sql.Date(e.getFechaRegistro().getTime()));
            
            return ps.executeUpdate() > 0;
        }

    } catch (SQLException ex) {
        System.out.println("Error al agregar empleado: " + ex.getMessage());
        ex.printStackTrace();
        return false;
    }
}

    // CREAR USUARIO AUTOMÁTICAMENTE PARA EL EMPLEADO
    private boolean crearUsuarioParaEmpleado(String codigo, String nombre, int idRol) {
        String sqlUsuario = "INSERT INTO usuario (codigo, nombre, contrasena, id_roles) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sqlUsuario)) {
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, codigo); // La contraseña es igual al código
            ps.setInt(4, idRol);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            System.out.println("Error al crear usuario: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // EDITAR EMPLEADO
    public boolean editarEmpleado(Empleado e) {
        String sql = "UPDATE empleados SET nombre=?, id_roles=?, correo=?, telefono=?, salario_base=?, fecha_registro=? WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setInt(2, e.getIdRol());
            ps.setString(3, e.getCorreo());
            ps.setString(4, e.getTelefono());
            ps.setDouble(5, e.getSalarioBase());
            java.util.Date fechaUtil = e.getFechaRegistro();

            java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());

            ps.setDate(6, fechaSQL);
            ps.setInt(7, e.getId());
            
            int filasAfectadas = ps.executeUpdate();
            
            // Actualizar también en la tabla usuario
            if (filasAfectadas > 0) {
                actualizarUsuario(e.getCodigo(), e.getNombre(), e.getIdRol());
            }
            
            return filasAfectadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Error al editar empleado: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR DATOS EN TABLA USUARIO
    private void actualizarUsuario(String codigo, String nombre, int idRol) {
        String sql = "UPDATE usuario SET nombre=?, id_roles=? WHERE codigo=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idRol);
            ps.setString(3, codigo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar usuario: " + ex.getMessage());
        }
    }

    // ELIMINAR EMPLEADO
    public boolean eliminarEmpleado(int id) {
        // Primero obtener el código para eliminar el usuario
        String sqlObtenerCodigo = "SELECT codigo FROM empleados WHERE id=?";
        String codigo = null;
        
        try (PreparedStatement ps = conexion.prepareStatement(sqlObtenerCodigo)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                codigo = rs.getString("codigo");
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener código: " + ex.getMessage());
        }
        
        // Eliminar de empleados
        String sql = "DELETE FROM empleados WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            
            // Eliminar también de usuario
            if (filasAfectadas > 0 && codigo != null) {
                eliminarUsuario(codigo);
            }
            
            return filasAfectadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Error al eliminar empleado: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // ELIMINAR USUARIO ASOCIADO
    private void eliminarUsuario(String codigo) {
        String sql = "DELETE FROM usuario WHERE codigo=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }
    }

    // LISTAR TODOS LOS EMPLEADOS
    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("id"));
                e.setCodigo(rs.getString("codigo"));
                e.setNombre(rs.getString("nombre"));
                e.setIdRol(rs.getInt("id_roles"));
                e.setCorreo(rs.getString("correo"));
                e.setTelefono(rs.getString("telefono"));
                e.setSalarioBase(rs.getDouble("salario_base"));
                e.setFechaRegistro(rs.getDate("fecha_registro"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar empleados: " + ex.getMessage());
        }
        return lista;
    }

    // OBTENER EMPLEADO POR ID
    public Empleado obtenerEmpleadoPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        Empleado e = null;
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                e = new Empleado();
                e.setId(rs.getInt("id"));
                e.setCodigo(rs.getString("codigo"));
                e.setNombre(rs.getString("nombre"));
                e.setIdRol(rs.getInt("id_roles"));
                e.setCorreo(rs.getString("correo"));
                e.setTelefono(rs.getString("telefono"));
                e.setSalarioBase(rs.getDouble("salario_base"));
                e.setFechaRegistro(rs.getDate("fecha_registro"));
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al buscar empleado por ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return e;
    }

    // BUSCAR EMPLEADOS POR NOMBRE
    public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados WHERE nombre LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("id"));
                e.setCodigo(rs.getString("codigo"));
                e.setNombre(rs.getString("nombre"));
                e.setIdRol(rs.getInt("id_roles"));
                e.setCorreo(rs.getString("correo"));
                e.setTelefono(rs.getString("telefono"));
                e.setSalarioBase(rs.getDouble("salario_base"));
                e.setFechaRegistro(rs.getDate("fecha_registro"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar empleados: " + ex.getMessage());
        }
        return lista;
    }

    // VERIFICAR SI EXISTE UN CÓDIGO
    public boolean existeCodigo(String codigo) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE codigo = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al verificar código: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return false;
    }
    
    public Empleado buscarPorCodigo(String codigo) {
    String sql = "SELECT * FROM empleados WHERE codigo = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Empleado e = new Empleado();
            e.setCodigo(rs.getString("codigo"));
            e.setNombre(rs.getString("nombre"));
            e.setCorreo(rs.getString("correo"));
            e.setTelefono(rs.getString("telefono"));
            e.setSalarioBase(rs.getDouble("salario_base"));
            e.setFechaRegistro(rs.getDate("fecha_registro"));
            e.setIdRol(rs.getInt("id_roles"));
            return e;
        }
    } catch (Exception ex) {
        System.out.println("Error al buscar empleado: " + ex.getMessage());
    }
    return null;
}
    
    
}
