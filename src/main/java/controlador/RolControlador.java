/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import dao.RolDAO;
import vista.VentanaRoles;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class RolControlador {
     private VentanaRoles vista;
    private RolDAO dao;

    public RolControlador(VentanaRoles vista) {
        this.vista = vista;
        this.dao = new RolDAO();
        cargarTabla();

        // Eventos de botones
        vista.btnAgregar.addActionListener(e -> agregarRol());
        vista.btnEditar.addActionListener(e -> editarRol());
        vista.btnEliminar.addActionListener(e -> eliminarRol());
    }

    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        for (Object[] fila : dao.listarRoles()) {
            modelo.addRow(new Object[]{fila[1]}); // Solo el nombre
        }
    }

    private void agregarRol() {
        String nombre = vista.txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un nombre para el rol.");
            return;
        }

        if (dao.agregarRol(nombre)) {
            JOptionPane.showMessageDialog(vista, "Rol agregado correctamente.");
            vista.txtNombre.setText("");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar el rol. Verifique que no exista.");
        }
    }

    private void editarRol() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un rol para editar.");
            return;
        }

        String nombreActual = (String) vista.tabla.getValueAt(fila, 0);
        String nuevoNombre = JOptionPane.showInputDialog(vista, "Nuevo nombre:", nombreActual);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            int id = obtenerIdPorNombre(nombreActual);
            if (dao.editarRol(id, nuevoNombre)) {
                JOptionPane.showMessageDialog(vista, "Rol editado correctamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al editar el rol.");
            }
        }
    }

    private void eliminarRol() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un rol para eliminar.");
            return;
        }

        String nombre = (String) vista.tabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Eliminar el rol '" + nombre + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = obtenerIdPorNombre(nombre);
            if (dao.eliminarRol(id)) {
                JOptionPane.showMessageDialog(vista, "Rol eliminado correctamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el rol.");
            }
        }
    }

    private int obtenerIdPorNombre(String nombre) {
        for (Object[] fila : dao.listarRoles()) {
            if (fila[1].equals(nombre)) {
                return (int) fila[0];
            }
        }
        return -1;
    }
}
