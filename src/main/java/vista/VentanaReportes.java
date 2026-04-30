package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controlador.ProductoControlador;
import util.GeneradorGraficas;
import org.jfree.chart.ChartPanel;
import conexion.ConexionBD;
import java.util.Map;

public class VentanaReportes extends VentanaBase1 {
    private String codigoUsuario;
    private JPanel panelResultados; //Variable de instancia
    private ProductoControlador productoControlador; // Controlador

    public VentanaReportes(String codigoUsuario, String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario);
        this.codigoUsuario = codigoUsuario;
        
        // ✅ Inicializar controlador
        this.productoControlador = new ProductoControlador(ConexionBD.getConnection());
        
        setTitle("Reportes");
        panelMedio.setLayout(new BorderLayout());

        // ----- SUB-ENCABEZADO DEL MÓDULO -----
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230));
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50));

        // === BOTÓN DE REGRESAR A LA IZQUIERDA ===
        ImageIcon iconoRegresarOriginal = new ImageIcon(getClass().getResource("/imagenes/regresar-icon.png"));
        Image imgRegresar = iconoRegresarOriginal.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        JButton btnRegresar = new JButton(new ImageIcon(imgRegresar));
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setToolTipText("Regresar");
        btnRegresar.setBorder(BorderFactory.createEmptyBorder(1, 0, 5, 0));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
                dispose();
            }
        });

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.add(btnRegresar);

        // === PANEL CENTRAL CON ICONO + TEXTO ===
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Reportes-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Reportes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);

        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER);
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);

        // ----- CONTENIDO -----
        JPanel panelContenido = new JPanel(new BorderLayout());

        // Panel lateral con SOLO 2 BOTONES
        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new GridLayout(2, 1, 10, 10)); // ✅ Cambiado a 2
        panelLateral.setPreferredSize(new Dimension(200, 0));
        panelLateral.setBackground(new Color(245, 245, 245));

        JButton btnInventario = new JButton("Inventario actual");
        JButton btnBajoStock = new JButton("Productos con bajo stock");

        // Formato uniforme
        JButton[] botones = {btnInventario, btnBajoStock}; // ✅ Solo 2 botones
        for (JButton b : botones) {
            b.setFocusPainted(false);
            b.setBackground(new Color(200, 200, 255));
            b.setFont(new Font("Arial", Font.PLAIN, 14));
            panelLateral.add(b);
        }

        // Panel central para mostrar resultados
        panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBorder(BorderFactory.createTitledBorder("Vista del Reporte"));

        JLabel lblPlaceholder = new JLabel("Selecciona un reporte a la izquierda", SwingConstants.CENTER);
        lblPlaceholder.setFont(new Font("Arial", Font.ITALIC, 16));
        panelResultados.add(lblPlaceholder, BorderLayout.CENTER);

        // Acciones de los botones
        btnInventario.addActionListener(e -> mostrarGraficaInventario());
        btnBajoStock.addActionListener(e -> mostrarGraficaBajoStock());

        // Agregar al contenido
        panelContenido.add(panelLateral, BorderLayout.WEST);
        panelContenido.add(panelResultados, BorderLayout.CENTER);

        panelMedio.add(panelContenido, BorderLayout.CENTER);
    }

    // MÉTODO 1: Mostrar inventario actual
    private void mostrarGraficaInventario() {
        try {
            Map<String, Integer> inventario = productoControlador.obtenerInventarioParaReporte();

            if (inventario.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay productos registrados",
                        "Sin datos",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            ChartPanel chartPanel = GeneradorGraficas.crearGraficaBarrasInventario(inventario);

            panelResultados.removeAll();
            panelResultados.setLayout(new BorderLayout());
            panelResultados.add(chartPanel, BorderLayout.CENTER);
            panelResultados.revalidate();
            panelResultados.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // MÉTODO 2: Mostrar productos con bajo stock
    private void mostrarGraficaBajoStock() {
        try {
            Map<String, Integer> productos = productoControlador.obtenerProductosBajoStock(10); // menos de 10 unidades

            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay productos con bajo stock (menos de 10 unidades)",
                        "Sin datos",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            ChartPanel chartPanel = GeneradorGraficas.crearGraficaBajoStock(productos);

            panelResultados.removeAll();
            panelResultados.setLayout(new BorderLayout());
            panelResultados.add(chartPanel, BorderLayout.CENTER);
            panelResultados.revalidate();
            panelResultados.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}