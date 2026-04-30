package vista;

import controlador.ProveedorControlador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class VentanaProveedores extends VentanaBase1 {
    private String codigoUsuario;
        
    // Declaraciones públicas para que el controlador las use
    public JButton btnAgregar, btnEditar, btnEliminar;
    public JTextField txtNombre;
    public JTextField txtCodigo;
    public JTable tabla;
    public JButton btnBuscar;
    public JTextField txtBuscar;

    public VentanaProveedores(String codigoUsuario,String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario); // llama al constructor de VentanaBase1
        this.codigoUsuario = codigoUsuario; 
        setTitle("Proveedores");
        // Panel principal de contenido
        panelMedio.setLayout(new BorderLayout());

        // SUB-ENCABEZADO DEL MÓDULO
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
                new Menu(codigoUsuario,nombreUsuario, rolUsuario).setVisible(true);
                dispose();
            }
        });
        
        // PANEL IZQUIERDO PARA EL BOTÓN

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10)); // 15px espacio a la derecha
        panelIzquierdo.setOpaque(false); // sin color de fondo
        panelIzquierdo.add(btnRegresar);
        // PANEL CENTRAL CON ICONO + TEXTO
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Proveedores-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Proveedores");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);
        //  Agregar al sub-encabezado 
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);   // a la izquierda
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER); // en el centro
        // Agregar el sub-encabezado arriba
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);    
        //contenido
        
        
           // ----- CONTENIDO CENTRAL -----
        JPanel panelContenido = new JPanel(new BorderLayout(20, 0));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ---------- FORMULARIO IZQUIERDA ----------
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(235, 245, 255));
        panelFormulario.setBorder(BorderFactory.createLineBorder(new Color(150, 170, 200), 2, true));
        panelFormulario.setPreferredSize(new Dimension(400, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTituloFormulario = new JLabel("Registro de Proveedores");
        lblTituloFormulario.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloFormulario.setHorizontalAlignment(JLabel.CENTER);
        lblTituloFormulario.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(15, 8, 15, 8);
        panelFormulario.add(lblTituloFormulario, gbc);
        gbc.gridwidth = 1; gbc.insets = new Insets(8, 8, 8, 8);

        // Campos de texto
         txtCodigo = new JTextField(12);
         txtCodigo.setEditable(false); // Bloquea el campo 
         txtNombre = new JTextField(12);

        JTextField[] campos = {txtCodigo, txtNombre};
        for (JTextField c : campos) {
            c.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                    BorderFactory.createEmptyBorder(3, 5, 3, 5)
            ));
            c.setFont(new Font("Arial", Font.PLAIN, 13));
        }

        // Fila 1: Código
        gbc.gridy = 1;
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtCodigo, gbc);

        // Fila 2: Nombre
        gbc.gridy = 2;
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtNombre, gbc);

        // Fila 3: Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(new Color(235, 245, 255));

        btnAgregar = new JButton("Agregar");
        ImageIcon iconoAgregar = new ImageIcon(getClass().getResource("/imagenes/agregar.png"));
        Image imgAgregar = iconoAgregar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnAgregar.setIcon(new ImageIcon(imgAgregar));
        btnEditar = new JButton("Editar");
        ImageIcon iconoEditar = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
        Image imgEditar = iconoEditar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnEditar.setIcon(new ImageIcon(imgEditar));
        btnEliminar = new JButton("Eliminar");
        ImageIcon iconoEliminar = new ImageIcon(getClass().getResource("/imagenes/eliminar.png"));
        Image imgEliminar = iconoEliminar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnEliminar.setIcon(new ImageIcon(imgEliminar));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        gbc.gridy = 3;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(panelBotones, gbc);

        // ---------- TABLA DERECHA ----------
        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setBackground(Color.WHITE);

        JLabel lblTituloTabla = new JLabel("Listado de Proveedores");
        lblTituloTabla.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloTabla.setHorizontalAlignment(JLabel.CENTER);
        lblTituloTabla.setForeground(new Color(0, 0, 0));
        lblTituloTabla.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        // Panel búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.LINE_AXIS));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel iconoLupa;
        try {
            ImageIcon iconoBusqueda = new ImageIcon(getClass().getResource("/imagenes/Buscar-icon.png"));
            Image imagenLupa = iconoBusqueda.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            iconoLupa = new JLabel(new ImageIcon(imagenLupa));
        } catch (Exception e) {
            iconoLupa = new JLabel("🔍");
            iconoLupa.setFont(new Font("Arial", Font.PLAIN, 14));
        }
        iconoLupa.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        JPanel panelCampoBusqueda = new JPanel(new BorderLayout());
        panelCampoBusqueda.setPreferredSize(new Dimension(200, 28));
        panelCampoBusqueda.setBackground(Color.WHITE);
        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        panelCampoBusqueda.add(iconoLupa, BorderLayout.WEST);
        panelCampoBusqueda.add(txtBuscar, BorderLayout.CENTER);

        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(Box.createHorizontalGlue());
        panelBusqueda.add(panelCampoBusqueda);
        panelBusqueda.add(Box.createHorizontalStrut(10));
        panelBusqueda.add(btnBuscar);

        JPanel panelSuperiorTabla = new JPanel(new BorderLayout());
        panelSuperiorTabla.add(lblTituloTabla, BorderLayout.NORTH);
        panelSuperiorTabla.add(panelBusqueda, BorderLayout.SOUTH);
        panelTabla.add(panelSuperiorTabla, BorderLayout.NORTH);

        // Tabla categorías
        String[] columnas = {"Código", "Nombre"};
        Object[][] datos = {
                {"PRO001", "GLORIA SAC"},
                {"PRO002", "ARCOR SAC"},
                {"PRO003", "LAIVE"},
        };
        
        DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setBackground(new Color(230, 230, 230));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        JScrollPane scrollTabla = new JScrollPane(tabla);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        // Acciones inferiores (exportar PDF/Excel)
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnExportarPDF = new JButton("Exportar PDF");
        ImageIcon iconoPdf = new ImageIcon(getClass().getResource("/imagenes/pdf.png"));    
        Image imgPdf = iconoPdf.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);    
        btnExportarPDF.setIcon(new ImageIcon(imgPdf));
        JButton btnExportarExcel = new JButton("Exportar Excel");
        ImageIcon iconoExcel = new ImageIcon(getClass().getResource("/imagenes/Excel.png"));    
        Image imgExcel = iconoExcel.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);   
        btnExportarExcel.setIcon(new ImageIcon(imgExcel));
        
                btnExportarExcel.addActionListener(e -> {
    
            exportarExcel();

        });
        
        btnExportarPDF.addActionListener(e -> {
    
            exportarPdf();

        });
        panelAcciones.add(btnExportarPDF);
        panelAcciones.add(btnExportarExcel);
        panelTabla.add(panelAcciones, BorderLayout.SOUTH);

        // Agregar todo al contenido
        panelContenido.add(panelFormulario, BorderLayout.WEST);
        panelContenido.add(panelTabla, BorderLayout.CENTER);
        panelMedio.add(panelContenido, BorderLayout.CENTER);
        
        
        
        
        new ProveedorControlador(this);
    }
    
    // --- MÉTODO EXPORTAR EXCEL PARA PROVEEDORES ---
public void exportarExcel() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Excel");
        fileChooser.setSelectedFile(new java.io.File("proveedores.xlsx"));

        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String ruta = fileChooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".xlsx")) ruta += ".xlsx";

        org.apache.poi.xssf.usermodel.XSSFWorkbook libro = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet hoja = libro.createSheet("Proveedores");

        // --- Estilos ---
        org.apache.poi.ss.usermodel.CellStyle estiloTitulo = libro.createCellStyle();
        org.apache.poi.xssf.usermodel.XSSFFont fuenteTitulo = libro.createFont();
        fuenteTitulo.setBold(true);
        fuenteTitulo.setFontHeightInPoints((short) 16);
        estiloTitulo.setFont(fuenteTitulo);
        estiloTitulo.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);

        org.apache.poi.ss.usermodel.CellStyle estiloEncabezado = libro.createCellStyle();
        org.apache.poi.xssf.usermodel.XSSFFont fuenteEncabezado = libro.createFont();
        fuenteEncabezado.setBold(true);
        fuenteEncabezado.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
        estiloEncabezado.setFont(fuenteEncabezado);
        estiloEncabezado.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.BLUE.getIndex());
        estiloEncabezado.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        estiloEncabezado.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);

        org.apache.poi.ss.usermodel.CellStyle estiloFilaGris = libro.createCellStyle();
        estiloFilaGris.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.getIndex());
        estiloFilaGris.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

        // --- Logo ---
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/imagenes/MassLogo.png");
            if (is != null) {
                byte[] bytes = is.readAllBytes();
                int idx = libro.addPicture(bytes, org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG);
                is.close();

                org.apache.poi.ss.usermodel.Drawing<?> drawing = hoja.createDrawingPatriarch();
                org.apache.poi.ss.usermodel.ClientAnchor anchor = libro.getCreationHelper().createClientAnchor();
                anchor.setCol1(0); anchor.setRow1(0);
                anchor.setCol2(3); anchor.setRow2(4);
                org.apache.poi.ss.usermodel.Picture pict = drawing.createPicture(anchor, idx);
                pict.resize(0.6);

                hoja.createRow(0).setHeightInPoints(50);
            }
        } catch (Exception ex) {
            System.out.println("No se pudo cargar logo: " + ex.getMessage());
        }

        // --- Título centrado ---
        org.apache.poi.ss.usermodel.Row filaTitulo = hoja.createRow(3);
        org.apache.poi.ss.usermodel.Cell celdaTitulo = filaTitulo.createCell(0);
        celdaTitulo.setCellValue("Listado de Proveedores");
        celdaTitulo.setCellStyle(estiloTitulo);
        hoja.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 0, tabla.getColumnCount() - 1));

        // --- Encabezados de tabla ---
        org.apache.poi.ss.usermodel.Row filaEncabezado = hoja.createRow(5);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            org.apache.poi.ss.usermodel.Cell celda = filaEncabezado.createCell(i);
            celda.setCellValue(tabla.getColumnName(i));
            celda.setCellStyle(estiloEncabezado);
        }

        // --- Datos ---
        for (int i = 0; i < tabla.getRowCount(); i++) {
            org.apache.poi.ss.usermodel.Row fila = hoja.createRow(i + 6);
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                org.apache.poi.ss.usermodel.Cell celda = fila.createCell(j);
                Object valor = tabla.getValueAt(i, j);
                celda.setCellValue(valor == null ? "" : valor.toString());
                if (i % 2 == 0) celda.setCellStyle(estiloFilaGris);
            }
        }

        // --- Pie de página con fecha ---
                //esto hace que aparezca el nombre del empleado que realiza la exportación:)
        String fechaHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .format(new java.util.Date());
        // Fila después de la última fila de datos
        int filaInfoIndex = tabla.getRowCount() + 7;
        org.apache.poi.ss.usermodel.Row filaInfo = hoja.createRow(filaInfoIndex);
        org.apache.poi.ss.usermodel.Cell celdaInfo = filaInfo.createCell(0);
        celdaInfo.setCellValue(
        "Exportado por: " + nombreUsuario + " (" + rolUsuario + ")\n" +
        "Fecha y hora: " + fechaHora

        );
        // Estilo para multilínea
        org.apache.poi.ss.usermodel.CellStyle estiloInfo = libro.createCellStyle();
        estiloInfo.setWrapText(true);
        celdaInfo.setCellStyle(estiloInfo);
        // Combinar columnas
        hoja.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(
        filaInfoIndex, filaInfoIndex + 1,
        0, tabla.getColumnCount() - 1
        ));

        // --- Ajustar ancho y alto de celdas para que se vea bien el texto ---

        for (int i = 0; i < tabla.getColumnCount(); i++) {
    
            hoja.setColumnWidth(i, 8000); // fija el ancho de cada columna (8000 ≈ 20 caracteres)

        }

        // Guardar archivo
        java.io.FileOutputStream archivo = new java.io.FileOutputStream(ruta);
        libro.write(archivo);
        archivo.close();
        libro.close();

        JOptionPane.showMessageDialog(this, "Excel exportado correctamente.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error exportando Excel: " + e.getMessage());
        e.printStackTrace();
    }
}

// --- MÉTODO EXPORTAR PDF PARA PROVEEDORES ---
public void exportarPdf() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        fileChooser.setSelectedFile(new java.io.File("proveedores.pdf"));
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String ruta = fileChooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".pdf")) ruta += ".pdf";

        com.itextpdf.text.Document documento = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(documento, new java.io.FileOutputStream(ruta));
        documento.open();

        // --- LOGO ---
        try {
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(getClass().getResource("/imagenes/MassLogo.png"));
            logo.scaleToFit(80, 80);
            logo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            documento.add(logo);
        } catch (Exception e) {
            System.out.println("No se pudo agregar logo: " + e.getMessage());
        }

        // --- TÍTULO ---
        com.itextpdf.text.Font fuenteTitulo = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph("Listado de Proveedores\n\n", fuenteTitulo);
        titulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        documento.add(titulo);

        // --- TABLA ---
        com.itextpdf.text.pdf.PdfPTable tablaPDF = new com.itextpdf.text.pdf.PdfPTable(tabla.getColumnCount());
        tablaPDF.setWidthPercentage(100);

        com.itextpdf.text.BaseColor colorEncabezado = new com.itextpdf.text.BaseColor(0, 102, 204); // azul
        com.itextpdf.text.Font fuenteEncabezado = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD, com.itextpdf.text.BaseColor.WHITE);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            com.itextpdf.text.pdf.PdfPCell celda = new com.itextpdf.text.pdf.PdfPCell(
                    new com.itextpdf.text.Phrase(tabla.getColumnName(i), fuenteEncabezado));
            celda.setBackgroundColor(colorEncabezado);
            celda.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            celda.setPadding(5);
            tablaPDF.addCell(celda);
        }

        com.itextpdf.text.BaseColor colorFila = new com.itextpdf.text.BaseColor(230, 230, 230); // gris claro
        for (int i = 0; i < tabla.getRowCount(); i++) {
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                com.itextpdf.text.pdf.PdfPCell celda = new com.itextpdf.text.pdf.PdfPCell(
                        new com.itextpdf.text.Phrase(tabla.getValueAt(i, j).toString()));
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