
package modelo;

import java.util.Date;

public class Empleado {
         private int id;
    private String codigo;
    private String nombre;
    private String apellido;
    private String codigoMass;
    private int idRol;
    private String correo;
    private String telefono;
    private double salarioBase;
    private Date fechaRegistro;
    private String contrasena;
    

    // Constructor vacío
    public Empleado() {
    }

    // Constructor con parámetros
    public Empleado(int id, String codigo, String nombre, int idRol, String correo, 
                    String telefono, double salarioBase, Date fechaRegistro) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.idRol = idRol;
        this.correo = correo;
        this.telefono = telefono;
        this.salarioBase = salarioBase;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public String getCodigo() { 
        return codigo; 
    }
    
    public void setCodigo(String codigo) { 
        this.codigo = codigo; 
    }

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public int getIdRol() { 
        return idRol; 
    }
    
    public void setIdRol(int idRol) { 
        this.idRol = idRol; 
    }

    public String getCorreo() { 
        return correo; 
    }
    
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    public String getTelefono() { 
        return telefono; 
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public double getSalarioBase() { 
        return salarioBase; 
    }
    
    public void setSalarioBase(double salarioBase) { 
        this.salarioBase = salarioBase; 
    }

    public Date getFechaRegistro() { 
        return fechaRegistro; 
    }
    
    public void setFechaRegistro(Date fechaRegistro) { 
        this.fechaRegistro = fechaRegistro; 
    }
    
    

public String getContrasena() {
    return contrasena;
}

public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
}
}
