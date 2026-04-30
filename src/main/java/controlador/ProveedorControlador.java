/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ProveedorDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Proveedor;
import vista.VentanaProveedores;

/**
 *
 * @author Yadhira Saavedra
 */
public class ProveedorControlador {
     private VentanaProveedores vista;
     private ProveedorDAO dao;
     
     public ProveedorControlador(VentanaProveedores vista) {
        this.vista = vista;
        this.dao = new ProveedorDAO();
        inicializar();
    }

    private void inicializar() {
        cargarTabla();
        vista.btnAgregar.addActionListener(e -> agregarProveedor());
        vista.btnEditar.addActionListener(e -> editarProveedor());
        vista.btnEliminar.addActionListener(e -> eliminarProveedor());
        vista.btnBuscar.addActionListener(e -> buscarProveedor());
    }

    private void cargarTabla() {
        List<Proveedor> lista = dao.listar();
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);
        for (Proveedor p : lista) {
            modelo.addRow(new Object[]{ p.getCodigo(), p.getNombre()});
        }
    }

    private void agregarProveedor() {
        String nombre = vista.txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un nombre.");
            return;
        }
        // Generar el código automáticamente
        String codigo = generarCodigo();
        // Mostrar el código en el campo (solo visual, no editable)
    vista.txtCodigo.setText(codigo);

        Proveedor p = new Proveedor(0, codigo, nombre);
        if (dao.agregar(p)) {
            JOptionPane.showMessageDialog(vista, "Proveedor agregado con éxito.");
            cargarTabla();
        vista.txtNombre.setText("");
        vista.txtCodigo.setText("");
        
        }
    }

    //private void editarCategoria() {
        //int fila = vista.tabla.getSelectedRow();
        //if (fila == -1) {
            //JOptionPane.showMessageDialog(vista, "Seleccione una categoría.");
            //return;
        //}
        //int id = (int) vista.tabla.getValueAt(fila, 0);
        //String nombre = JOptionPane.showInputDialog("Nuevo nombre:");
        //Categoria c = new Categoria(id, "", nombre);
        //if (dao.editar(c)) {
            //JOptionPane.showMessageDialog(vista, "Categoría actualizada.");
            //cargarTabla();
        //}
    //}
    
        //editar categoriaaa el nombre nomas

         private void editarProveedor() {    
             int fila = vista.tabla.getSelectedRow();
             if (fila == -1) {        
                 JOptionPane.showMessageDialog(vista, "Seleccione un proveedor.");        
                 return;
             }

             String codigo = (String) vista.tabla.getValueAt(fila, 0);
             String nuevoNombre = (String) JOptionPane.showInputDialog("Nuevo nombre:");
             
             if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                 JOptionPane.showMessageDialog(vista, "El nombre no puede estar vacío.");
                 return;
                
             }

        // Actualizar en la base de datos usando DAO
             boolean actualizado = dao.actualizarNombre(codigo, nuevoNombre);
          
                if (actualizado) {
                    JOptionPane.showMessageDialog(vista, "Proveedor actualizado.");
                    cargarTabla(); // refresca la tabla con los datos actualizados              
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al actualizar proveedor.");
                }
         }
    
      // Buscar la categoría por su código
    
         private void buscarProveedor() {
             String codigo = vista.txtBuscar.getText().trim(); // ahora usa el campo de búsqueda

             
             if (codigo.isEmpty()) {
                 JOptionPane.showMessageDialog(vista, "Ingrese un código para buscar.");
                 return;
             }

             Proveedor proveedor = dao.buscarProveedor(codigo);
             if (proveedor != null) {
              // Limpiamos la tabla y mostramos solo el resultado encontrado
              DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
              modelo.setRowCount(0);        
              modelo.addRow(new Object[]{proveedor.getCodigo(), proveedor.getNombre()});  
              JOptionPane.showMessageDialog(vista, "Proveedor encontrada: " + proveedor.getNombre());
             } else {
                 JOptionPane.showMessageDialog(vista, "No se encontró un proveedor con el código: " + codigo);
             }
         }

    //private void eliminarProveedor() {
        //int fila = vista.tabla.getSelectedRow();
        //if (fila == -1) {
            //JOptionPane.showMessageDialog(vista, "Seleccione una proveedor.");
            //return;
        //}
        //int id = (int) vista.tabla.getValueAt(fila, 0);
        //if (dao.eliminar(id)) {
            //JOptionPane.showMessageDialog(vista, "Proveedor eliminado.");
            //cargarTabla();
            
        //}
    //}
  
         private void eliminarProveedor() {        
             int fila = vista.tabla.getSelectedRow();        
             if (fila == -1) {           
                 JOptionPane.showMessageDialog(vista, "Seleccione un proveedor.");            
                 return;        
             }
             
             String codigo = vista.tabla.getValueAt(fila, 0).toString();        
             Proveedor p = dao.buscarProveedor(codigo);
        
             if (p == null) {
                 JOptionPane.showMessageDialog(vista, "Error: no se encontró el proveedor.");
                 return;
             }

             if (dao.eliminar(p.getId())) {
                 JOptionPane.showMessageDialog(vista, "Proveedor eliminado.");
                 cargarTabla();        
             } else {            
                 JOptionPane.showMessageDialog(vista, "Error al eliminar el proveedor.");        
             }    
         }
  

    //private String generarCodigo() {
        //int num = dao.listar().size() + 1;
        //return String.format("PROV%03d", num);
    //}
         
         private String generarCodigo() {
             String codigoNuevo = "PROV001"; // valor por defecto si no hay registros   
             String ultimoCodigo = dao.obtenerUltimoCodigo(); // llamamos al método del DAO   
             if (ultimoCodigo != null) {       
                 int numero = Integer.parseInt(ultimoCodigo.substring(4)); // extrae "001" -> 1       
                 numero++; // siguiente consecutivo        
                 codigoNuevo = String.format("PROV%03d", numero); // PROV002, PROV003, etc.    
             }    
             return codigoNuevo;
         }    
}
