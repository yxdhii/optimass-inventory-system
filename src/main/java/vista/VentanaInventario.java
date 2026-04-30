package vista;
import modelo.Producto;
import java.util.List;
import dao.ProductoDAO;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class VentanaInventario extends VentanaBase1 {
    
    private String codigoUsuario;


    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotalProductos;
    private JLabel lblBajoStock;
    private JLabel lblStockOptimo;

    public VentanaInventario(String codigoUsuario,String nombreUsuario, String rolUsuario) {
        
        super(nombreUsuario, rolUsuario);
        this.codigoUsuario = codigoUsuario; 
        
        setTitle("Inventario");
        
        // Panel principal de contenido
        panelMedio.setLayout(new BorderLayout());

        // sub encabezado
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230)); 
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50)); 

        // BOTÓN DE REGRESAR A LA IZQUIERDA
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
                new Menu(codigoUsuario,nombreUsuario, rolUsuario).setVisible(true);//hice cambio
                dispose();
            }
        });
        
        // PANEL IZQUIERDO PARA EL BOTÓN
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10)); 
        panelIzquierdo.setOpaque(false); 
        panelIzquierdo.add(btnRegresar);

        //  PANEL CENTRAL CON ICONO + TEXTO
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Inventarios-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Inventario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);

        // Agregar al sub-encabezado
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER);
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);

        
        // CONTENIDO DEL INVENTARIO 
        
        // Panel principal para el contenido
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // 
        JPanel panelEstadisticas = new JPanel(new GridLayout(1, 3, 15, 0));
        panelEstadisticas.setBackground(Color.WHITE);
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Tarjetas de estadísticas
        lblTotalProductos = new JLabel();
        lblBajoStock = new JLabel();
        lblStockOptimo = new JLabel();
        JPanel cardTotal = crearTarjetaEstadistica("Total Productos", lblTotalProductos, new Color(70, 130, 180));
        JPanel cardBajoStock = crearTarjetaEstadistica("Bajo Stock", lblBajoStock, new Color(220, 80, 60));
        JPanel cardOptimo = crearTarjetaEstadistica("Stock Óptimo", lblStockOptimo, new Color(60, 170, 90));

        panelEstadisticas.add(cardTotal);
        panelEstadisticas.add(cardBajoStock);
        panelEstadisticas.add(cardOptimo);

        //Tabla de inventario con mejor diseño
        String[] columnas = {"Código", "Producto", "Stock Actual", "Stock Mínimo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        // Datos de ejemplo con estados
        modeloTabla.addRow(new Object[]{"P001", "Arroz Costeño 1kg", 20, 10, "Óptimo"});
        modeloTabla.addRow(new Object[]{"P002", "Aceite Primor 1L", 5, 8, "Bajo Stock"});
        modeloTabla.addRow(new Object[]{"P003", "Leche Gloria 400ml", 15, 5, "Óptimo"});
        modeloTabla.addRow(new Object[]{"P004", "Azúcar Rubia 1kg", 25, 10, "Óptimo"});
        modeloTabla.addRow(new Object[]{"P005", "Fideos Don Vittorio 400g", 7, 8, "Bajo Stock"});
        modeloTabla.addRow(new Object[]{"P006", "Atún Florida 180g", 12, 5, "Óptimo"});
        
        tablaInventario = new JTable(modeloTabla);
        cargarProductos();
        
        // Mejorar el diseño de la tabla
        tablaInventario.setRowHeight(35);
        tablaInventario.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaInventario.setSelectionBackground(new Color(220, 240, 255));
        tablaInventario.setSelectionForeground(Color.BLACK);
        tablaInventario.setGridColor(new Color(220, 220, 220));
        tablaInventario.setShowGrid(true);
        
        // Personalizar el header de la tabla
        JTableHeader header = tablaInventario.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(60, 90, 140));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        
        // Centrar el contenido de las columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaInventario.getColumnCount(); i++) {
            tablaInventario.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Personalizar columna de Estado
        tablaInventario.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRenderer());
        
        JScrollPane scrollTabla = new JScrollPane(tablaInventario);
        scrollTabla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        scrollTabla.getViewport().setBackground(Color.WHITE);

        // --- Panel de botones mejorado ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        JButton btnEntrada = crearBotonConIcono("Entrada", "/imagenes/entrada-icon.png", new Color(60, 140, 80));
        JButton btnSalida = crearBotonConIcono("Salida", "/imagenes/salida-icon.png", new Color(200, 80, 60));
        JButton btnActualizar = crearBotonConIcono("Actualizar", "/imagenes/actualizar-icon.png", new Color(70, 130, 180));
        JButton btnReporte = crearBotonConIcono("Reporte", "/imagenes/reporte-icon.png", new Color(150, 100, 200));

        btnEntrada.addActionListener(e -> {
    
            JOptionPane.showMessageDialog(
        
                    this,
        
                    "Se agregaron productos correctamente. El inventario ya muestra los datos actualizados.",
        
                    "Entrada registrada",
        
                    JOptionPane.INFORMATION_MESSAGE
    
            );

        });

        btnSalida.addActionListener(e -> {
    
            JOptionPane.showMessageDialog(
        
                    this,
        
                    "Se retiraron productos del inventario. Revise el stock actualizado.",
        
                    "Salida registrada",
        
                    JOptionPane.WARNING_MESSAGE
    
            );

        });
        
        btnActualizar.addActionListener(e -> {
    
            cargarProductos();
    
            JOptionPane.showMessageDialog(this, "Inventario actualizado correctamente.");

        });
        
        btnReporte.addActionListener(e -> {
    
            reporte();
    
            

        });
        
        panelBotones.add(btnEntrada);
        panelBotones.add(btnSalida);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnReporte);

        // Agregar componentes al panel de contenido
        panelContenido.add(panelEstadisticas, BorderLayout.NORTH);
        panelContenido.add(scrollTabla, BorderLayout.CENTER);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);

        // Agregar contenido al panel medio
        panelMedio.add(panelContenido, BorderLayout.CENTER);
    }
    
    private JPanel crearTarjetaEstadistica(String titulo, JLabel lblValor, Color color) {
    
        JPanel tarjeta = new JPanel();    
        tarjeta.setLayout(new BorderLayout());   
        tarjeta.setBackground(Color.WHITE);   
        tarjeta.setBorder(BorderFactory.createCompoundBorder(       
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),        
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ));
    
    JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
    lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
    lblTitulo.setForeground(new Color(100, 100, 100));
    
    lblValor.setFont(new Font("Arial", Font.BOLD, 24));
    lblValor.setForeground(color);
    lblValor.setHorizontalAlignment(SwingConstants.CENTER);
    lblValor.setText("0"); // Valor inicial
    
    tarjeta.add(lblTitulo, BorderLayout.NORTH);
    tarjeta.add(lblValor, BorderLayout.CENTER);
    
    return tarjeta;
    }

    // Método para crear botones con iconos
    private JButton crearBotonConIcono(String texto, String rutaIcono, Color color) {
        JButton boton = new JButton(texto);
        
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaIcono));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        } catch (Exception e) {
            // Si no encuentra el icono, solo muestra el texto
        }
        
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }

    // Renderer personalizado para la columna de Estado
    private class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (value != null) {
                String estado = value.toString();
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Arial", Font.BOLD, 12));
                
                if (estado.equals("Óptimo")) {
                    setBackground(new Color(200, 255, 200));
                    setForeground(new Color(0, 100, 0));
                } else if (estado.equals("Bajo Stock")) {
                    setBackground(new Color(255, 220, 220));
                    setForeground(new Color(180, 0, 0));
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
            }
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
            
            return c;
        }
    
        
        
    }
    
    
    // CARGAR PRODUCTOS EN EL INVENTARIO
   
    
    private void cargarProductos() {
        ProductoDAO dao = new ProductoDAO(); // usa el constructor que acabas de arreglar
        List<Producto> lista = dao.listarProductos(); // trae todos los productos de la BD
        modeloTabla.setRowCount(0); // limpia la tabla antes de agregar nuevos datos

        for (Producto p : lista) {
        
            Object[] fila = new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getCantidad(),
            10, // stock mínimo fijo (puedes cambiarlo)
            p.getCantidad() >= 10 ? "Óptimo" : "Bajo Stock"
        };
        modeloTabla.addRow(fila);
    }
        actualizarEstadisticas();

}
    
    private void actualizarEstadisticas() {
    
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista = dao.listarProductos();

        int total = lista.size();    
        int bajoStock = 0;   
        int stockOptimo = 0;
  
        for (Producto p : lista) {
            if (p.getCantidad() < 10) {
                bajoStock++;
            } else {
                stockOptimo++;
            }
        }

    lblTotalProductos.setText(String.valueOf(total));
    lblBajoStock.setText(String.valueOf(bajoStock));
    lblStockOptimo.setText(String.valueOf(stockOptimo));

    }
    
    public void reporte() {

    try {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        fileChooser.setSelectedFile(new java.io.File("inventario.pdf"));

        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String ruta = fileChooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".pdf")) ruta += ".pdf";

        com.itextpdf.text.Document documento = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(documento, new java.io.FileOutputStream(ruta));
        documento.open();

        // --- LOGO ---
        try {
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance("src/main/resources/imagenes/MassLogo.png");
            logo.scaleToFit(80, 80);
            logo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            documento.add(logo);
        } catch (Exception e) {
            System.out.println("No se pudo agregar logo: " + e.getMessage());
        }

        // --- TÍTULO ---
        com.itextpdf.text.Font fuenteTitulo = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA,
                16,
                com.itextpdf.text.Font.BOLD
        );

        com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph("Reporte de Inventario\n\n", fuenteTitulo);
        titulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        documento.add(titulo);

        // --- TABLA ---
        com.itextpdf.text.pdf.PdfPTable tablaPDF = new com.itextpdf.text.pdf.PdfPTable(modeloTabla.getColumnCount());
        tablaPDF.setWidthPercentage(100);

        // Encabezados
        com.itextpdf.text.BaseColor colorEncabezado = new com.itextpdf.text.BaseColor(0, 102, 204);
        com.itextpdf.text.Font fuenteEncabezado = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA,
                12,
                com.itextpdf.text.Font.BOLD,
                com.itextpdf.text.BaseColor.WHITE
        );

        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            com.itextpdf.text.pdf.PdfPCell celda = new com.itextpdf.text.pdf.PdfPCell(
                    new com.itextpdf.text.Phrase(modeloTabla.getColumnName(i), fuenteEncabezado)
            );
            celda.setBackgroundColor(colorEncabezado);
            celda.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            celda.setPadding(5);
            tablaPDF.addCell(celda);
        }

        // Datos con filas alternadas
        com.itextpdf.text.BaseColor colorFila = new com.itextpdf.text.BaseColor(230, 230, 230);

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            for (int j = 0; j < modeloTabla.getColumnCount(); j++) {

                Object valor = modeloTabla.getValueAt(i, j);
                String texto = (valor == null) ? "" : valor.toString();

                com.itextpdf.text.pdf.PdfPCell celda = new com.itextpdf.text.pdf.PdfPCell(
                        new com.itextpdf.text.Phrase(texto)
                );
                celda.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                celda.setPadding(5);

                if (i % 2 == 0) celda.setBackgroundColor(colorFila);

                tablaPDF.addCell(celda);
            }
        }

        documento.add(tablaPDF);
        com.itextpdf.text.Font fuenteInfo = new com.itextpdf.text.Font(
        com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.NORMAL);


        String fechaHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .format(new java.util.Date());

        com.itextpdf.text.Paragraph info = new com.itextpdf.text.Paragraph(
        
                "Exportado por: " + nombreUsuario + " (" + rolUsuario + ")\n" +
       
                        "Fecha y hora: " + fechaHora + "\n\n",
         
                fuenteInfo);
        info.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        documento.add(info);
        documento.close();

        JOptionPane.showMessageDialog(this, "PDF exportado correctamente.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error exportando PDF: " + e.getMessage());
        e.printStackTrace();
    }
}

    

    
}