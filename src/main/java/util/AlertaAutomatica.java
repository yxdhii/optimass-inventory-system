package util;

import dao.ProductoDAO;
import modelo.Producto;
import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlertaAutomatica {
    
    /**
     * Verifica alertas y muestra notificación automática
     */
    public static void verificarYMostrarAlertas(JFrame parent, int stockMinimo) {
        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.listarProductos();
        
        int productosStockBajo = 0;
        int productosProximosVencer = 0;
        
        Date hoy = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoy);
        cal.add(Calendar.DAY_OF_MONTH, 30);
        Date dentroDeUnMes = cal.getTime();
        
        // Contar alertas
        for (Producto p : productos) {
            // Stock bajo
            if (p.getCantidad() < stockMinimo) {
                productosStockBajo++;
            }
            
            // Próximos a vencer
            if (p.getFechaCaducidad() != null) {
                Date fechaCad = p.getFechaCaducidad();
                if (fechaCad.before(dentroDeUnMes) && fechaCad.after(hoy)) {
                    productosProximosVencer++;
                }
            }
        }
        
        int totalAlertas = productosStockBajo + productosProximosVencer;
        
        // Mostrar notificación si hay alertas
        if (totalAlertas > 0) {
            String mensaje = String.format(
                "ALERTAS DEL SISTEMA ️\n\n" +
                "Se detectaron %d alertas:\n\n" +
                "%d producto(s) con STOCK BAJO (menos de %d unidades)\n" +
                "%d producto(s) próximos a VENCER (30 días)\n\n" +
                "Ve a 'Configuración → Alertas' para ver los detalles.",
                totalAlertas,
                productosStockBajo,
                stockMinimo,
                productosProximosVencer
            );
            
            JOptionPane.showMessageDialog(parent, mensaje, 
                "Alertas Importantes", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
}