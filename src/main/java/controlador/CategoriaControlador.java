/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import dao.CategoriaDAO;
import modelo.Categoria;
import vista.VentanaCategorias;
import javax.swing.*;
//import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Yadhira Saavedra
 */
public class CategoriaControlador {
    
    private VentanaCategorias vista;
    private CategoriaDAO dao;

    public CategoriaControlador(VentanaCategorias vista) {
        this.vista = vista;
        this.dao = new CategoriaDAO();
        inicializar();
    }

    private void inicializar() {
        cargarTabla();
        vista.btnAgregar.addActionListener(e -> agregarCategoria());
        vista.btnEditar.addActionListener(e -> editarCategoria());
        vista.btnEliminar.addActionListener(e -> eliminarCategoria());
        vista.btnBuscar.addActionListener(e -> buscarCategoria());
    }

    private void cargarTabla() {
        List<Categoria> lista = dao.listar();
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);
        for (Categoria c : lista) {
            modelo.addRow(new Object[]{ c.getCodigo(), c.getNombre()});
        }
    }
   
    
    

    private void agregarCategoria() {
        String nombre = vista.txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un nombre.");
            return;
        }
        // Generar el código automáticamente
        String codigo = generarCodigo();
        // Mostrar el código en el campo (solo visual, no editable)
    vista.txtCodigo.setText(codigo);

        Categoria c = new Categoria(0, codigo, nombre);
        if (dao.agregar(c)) {
            JOptionPane.showMessageDialog(vista, "Categoría agregada con éxito.");
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
private void editarCategoria() {
    int fila = vista.tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Seleccione una categoría.");
        return;
    }

    String codigo = (String) vista.tabla.getValueAt(fila, 0);
    String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:");

    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
        JOptionPane.showMessageDialog(vista, "El nombre no puede estar vacío.");
        return;
    }

    // Actualizar en la base de datos usando DAO
    boolean actualizado = dao.actualizarNombre(codigo, nuevoNombre);
    
    if (actualizado) {
        JOptionPane.showMessageDialog(vista, "Categoría actualizada.");
        cargarTabla(); // refresca la tabla con los datos actualizados
    } else {
        JOptionPane.showMessageDialog(vista, "Error al actualizar la categoría.");
    }
}
    
      // Buscar la categoría por su código
    

  private void buscarCategoria() {
    String codigo = vista.txtBuscar.getText().trim(); // ahora usa el campo de búsqueda

    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Ingrese un código para buscar.");
        return;
    }

    Categoria categoria = dao.buscarCategoria(codigo);

    if (categoria != null) {
        // Limpiamos la tabla y mostramos solo el resultado encontrado
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{categoria.getCodigo(), categoria.getNombre()});

        JOptionPane.showMessageDialog(vista, "Categoría encontrada: " + categoria.getNombre());
    } else {
        JOptionPane.showMessageDialog(vista, "No se encontró una categoría con el código: " + codigo);
    }
}

    //private void eliminarCategoria() {
        //int fila = vista.tabla.getSelectedRow();
        //if (fila == -1) {
            //JOptionPane.showMessageDialog(vista, "Seleccione una categoría.");
            //return;
        //}
        //int id = (int) vista.tabla.getValueAt(fila, 0);
        //if (dao.eliminar(id)) {
          //  JOptionPane.showMessageDialog(vista, "Categoría eliminada.");
            //cargarTabla();
            
        //}
    //}
  
private void eliminarCategoria() {
    int fila = vista.tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Seleccione una categoría.");
        return;
    }

    // Obtenemos el código visible de la categoría seleccionada
    String codigo = vista.tabla.getValueAt(fila, 0).toString(); // columna Código

    // Buscamos el ID en la base de datos usando el código
    Categoria c = dao.buscarCategoria(codigo);
    if (c == null) {
        JOptionPane.showMessageDialog(vista, "Error: no se encontró la categoría.");
        return;
    }

    // Eliminamos usando el ID real
    if (dao.eliminar(c.getId())) {
        JOptionPane.showMessageDialog(vista, "Categoría eliminada.");
        cargarTabla(); // actualizamos tabla
    } else {
        JOptionPane.showMessageDialog(vista, "Error al eliminar la categoría.");
    }
}

    //private String generarCodigo() {
        //int num = dao.listar().size() + 1;
        //return String.format("CAT%03d", num);
    //}

    private String generarCodigo() {
    String codigoNuevo = "CAT001"; // valor por defecto si no hay registros
    String ultimoCodigo = dao.obtenerUltimoCodigo(); // llamamos al método del DAO
    if (ultimoCodigo != null) {
        int numero = Integer.parseInt(ultimoCodigo.substring(4)); // extrae "001" -> 1
        numero++; // siguiente consecutivo
        codigoNuevo = String.format("CAT%03d", numero); // PROV002, PROV003, etc.
    }
    return codigoNuevo;
}

    
}
