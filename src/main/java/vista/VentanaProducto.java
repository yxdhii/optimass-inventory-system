package vista;
import javax.swing.JFileChooser;
import javax.swing.JTable;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Cell;
import java.sql.Connection;
import conexion.ConexionBD;
import controlador.ProductoControlador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import javax.swing.JSpinner;
//import javax.swing.SpinnerDateModel;
import java.util.Date;
//import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import java.util.List;

public class VentanaProducto extends VentanaBase1 {
    
    private ProductoControlador productoControlador;
    private Connection conexion;
    
    // Componentes GUI
    private String codigoUsuario;
    public JTextField txtNombre, txtPrecio, txtCantidad, txtStockMinimo, txtCodigo, txtFecha;
    public JComboBox<String> comboCategoria, comboProveedor;
    public JSpinner spinnerFecha;
    public JLabel lblImagenProducto;
    public JButton btnCargarImagen, btnAgregar, btnEditar, btnEliminar, btnVerCatalogo;
    public JTable tabla;
    public DefaultTableModel modeloTabla;
    
    public byte[] imagenSeleccionada;
    

    public VentanaProducto(String codigoUsuario,String nombreUsuario, String rolUsuario) {
        
        
        super(nombreUsuario, rolUsuario);
        this.codigoUsuario = codigoUsuario; 
        conexion = ConexionBD.getConnection();
        productoControlador = new ProductoControlador(conexion);
        
        setTitle("Productos");
        
        
        panelMedio.setLayout(new BorderLayout());

        // ----- SUB-ENCABEZADO DEL MÓDULO -----
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230)); 
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50)); 

        // === BOTÓN DE REGRESAR ===
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
        
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Producto-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Productos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);
        
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER);
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);   
        
        //contenido medio
        JPanel panelContenido = new JPanel(new BorderLayout(20, 0));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ---------- FORMULARIO IZQUIERDA (MEJORADO Y COMPLETO) ----------
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(235, 245, 255));
        panelFormulario.setBorder(BorderFactory.createLineBorder(new Color(150, 170, 200), 2, true));
        panelFormulario.setPreferredSize(new Dimension(450, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;// TÍTULO DEL FORMULARIO
    JLabel lblTituloFormulario = new JLabel("Registro de Productos");
    lblTituloFormulario.setFont(new Font("Arial", Font.BOLD, 16));
    lblTituloFormulario.setHorizontalAlignment(JLabel.CENTER);
    lblTituloFormulario.setForeground(new Color(40, 40, 40));
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; gbc.insets = new Insets(15, 8, 15, 8);
    panelFormulario.add(lblTituloFormulario, gbc);

gbc.gridwidth = 1; gbc.insets = new Insets(8, 8, 8, 8);

// Campos de texto
 txtCodigo = new JTextField(12);
 txtCodigo.setEditable(false); //  NO PERMITIR EDICIÓN MANUAL
 txtNombre = new JTextField(12);
 txtPrecio = new JTextField(12);
 txtCantidad = new JTextField(12);

// COMBOBOX para Categoría
//String[] categoriasIniciales = {"Lácteos", "Abarrotes", "Aseo", "Bebidas"};
//JComboBox<String> 

comboCategoria = new JComboBox<>();//(categoriasIniciales);
comboCategoria.setEditable(true); // permite que escribas nuevas categorías
comboCategoria.setPreferredSize(new Dimension(150, 25));
comboCategoria.setFont(new Font("Arial", Font.PLAIN, 13));

// COMBOBOX para Proveedor
//String[] proveedoresIniciales = {"Nestlé", "Gloria", "Costeño", "Otros"};
//JComboBox<String> 
comboProveedor = new JComboBox<>(); //(proveedoresIniciales);
comboProveedor.setEditable(true); //  también modificable
comboProveedor.setPreferredSize(new Dimension(150, 25));
comboProveedor.setFont(new Font("Arial", Font.PLAIN, 13));

// Bordes uniformes y modernos
JTextField[] campos = {txtCodigo, txtNombre, txtPrecio, txtCantidad};
for (JTextField c : campos) {
    c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
            BorderFactory.createEmptyBorder(3, 5, 3, 5)
    ));
    c.setFont(new Font("Arial", Font.PLAIN, 13));
}

// Fila 1: Código y Nombre
gbc.gridy = 1;
gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Código:"), gbc);
gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtCodigo, gbc);
gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Nombre:"), gbc);
gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtNombre, gbc);

// Fila 2: Categoría y Precio
gbc.gridy = 2;
gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Categoría:"), gbc);
gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(comboCategoria, gbc); // 👈 antes era txtCategoria
gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Precio:"), gbc);
gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtPrecio, gbc);

// Fila 3: Cantidad y Proveedor
gbc.gridy = 3;
gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Cantidad:"), gbc);
gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtCantidad, gbc);
gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Proveedor:"), gbc);
gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(comboProveedor, gbc); // 👈 antes era txtProveedor

// Fila 4: Fecha de caducidad (centrada)
gbc.gridy = 4;
gbc.gridx = 1; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Fecha de caducidad:"), gbc);

JPanel panelFecha = new JPanel(new BorderLayout(5, 0));
panelFecha.setBackground(new Color(235, 245, 255));
txtFecha = new JTextField("Seleccionar fecha");
txtFecha.setEditable(false);
txtFecha.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
        BorderFactory.createEmptyBorder(3, 5, 3, 5)
));
// Cargar la imagen original desde la carpeta del proyecto (ej: /imagenes/calendario.png)
ImageIcon iconoCalendario = new ImageIcon(getClass().getResource("/imagenes/calendario.png"));

// Escalar la imagen a 16x16
Image img = iconoCalendario.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
ImageIcon iconoEscalado = new ImageIcon(img);

// Crear el botón con el icono (sin texto)
JButton btnCalendario = new JButton(iconoEscalado);

// Opcional: quitar el fondo para que se vea "limpio"
btnCalendario.setBorderPainted(false);
btnCalendario.setContentAreaFilled(false);
btnCalendario.setFocusPainted(false);
//  Cursor de mano al pasar sobre el botón
btnCalendario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));


btnCalendario.setPreferredSize(new Dimension(35, txtFecha.getPreferredSize().height));
btnCalendario.addActionListener(e -> {
    JSpinner spinner = new JSpinner(new javax.swing.SpinnerDateModel());
    spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
    int opcion = JOptionPane.showConfirmDialog(panelFormulario, spinner, "Seleccionar fecha", JOptionPane.OK_CANCEL_OPTION);
    if (opcion == JOptionPane.OK_OPTION) {
        txtFecha.setText(new java.text.SimpleDateFormat("dd/MM/yyyy").format((Date) spinner.getValue()));
    }
});
panelFecha.add(txtFecha, BorderLayout.CENTER);
panelFecha.add(btnCalendario, BorderLayout.EAST);

gbc.gridx = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
panelFormulario.add(panelFecha, gbc);
gbc.gridwidth = 1;


// ---------- PANEL DE IMAGEN MEJORADO CON FUNCIONALIDAD ----------
JPanel panelImagen = new JPanel(new BorderLayout(10, 10));
panelImagen.setBackground(new Color(245, 250, 255));
panelImagen.setBorder(null);


lblImagenProducto = new JLabel();
lblImagenProducto.setPreferredSize(new Dimension(180, 120));
lblImagenProducto.setHorizontalAlignment(JLabel.CENTER);
lblImagenProducto.setVerticalAlignment(JLabel.CENTER);
lblImagenProducto.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
lblImagenProducto.setOpaque(true);
lblImagenProducto.setBackground(Color.WHITE);
lblImagenProducto.setText("Sin imagen");

// Panel de botones más estilizado
JPanel panelBotonesImagen = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
panelBotonesImagen.setBackground(new Color(245, 250, 255));

JButton btnSeleccionar = new JButton("Seleccionar");
btnSeleccionar.setBackground(new Color(100, 170, 220));
btnSeleccionar.setForeground(Color.WHITE);
btnSeleccionar.setFocusPainted(false);
btnSeleccionar.setFont(new Font("Arial", Font.BOLD, 12));
btnSeleccionar.setCursor(new Cursor(Cursor.HAND_CURSOR));

JButton btnQuitar = new JButton("Quitar");
btnQuitar.setBackground(new Color(220, 100, 100));
btnQuitar.setForeground(Color.WHITE);
btnQuitar.setFocusPainted(false);
btnQuitar.setFont(new Font("Arial", Font.BOLD, 12));
btnQuitar.setCursor(new Cursor(Cursor.HAND_CURSOR));



// FUNCIONALIDAD PARA SELECCIONAR IMAGEN
btnSeleccionar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen del producto");
        
        // Filtrar solo archivos de imagen
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Archivos de imagen (*.jpg, *.png, *.jpeg, *.gif)", 
            "jpg", "png", "jpeg", "gif"
        );
        fileChooser.setFileFilter(filter);
        
        // Establecer directorio inicial (opcional)
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        int resultado = fileChooser.showOpenDialog(panelFormulario);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            
            try {
                // Cargar la imagen
                ImageIcon imagenOriginal = new ImageIcon(archivoSeleccionado.getAbsolutePath());
                
               // Redimensionar la imagen a un tamaño específico que tú definas
                int anchoDeseado = 80;;  // Cambia este valor al ancho que quieras
                int altoDeseado = 100;;   // Cambia este valor al alto que quieras
                
                Image imagenRedimensionada = imagenOriginal.getImage().getScaledInstance(
                    anchoDeseado, 
                    altoDeseado, 
                    Image.SCALE_SMOOTH
                );
                
                // Establecer la imagen en el label
                lblImagenProducto.setIcon(new ImageIcon(imagenRedimensionada));
                lblImagenProducto.setText(""); // Quitar el texto "Sin imagen"
                
                // Opcional: Guardar la ruta del archivo para uso posterior
                lblImagenProducto.setToolTipText(archivoSeleccionado.getAbsolutePath());
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    panelFormulario,
                    "Error al cargar la imagen: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
});

// FUNCIONALIDAD PARA QUITAR IMAGEN
btnQuitar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        lblImagenProducto.setIcon(null);
        lblImagenProducto.setText("Sin imagen");
        lblImagenProducto.setToolTipText(null);
    }
});

panelBotonesImagen.add(btnSeleccionar);
panelBotonesImagen.add(btnQuitar);

panelImagen.add(lblImagenProducto, BorderLayout.CENTER);
panelImagen.add(panelBotonesImagen, BorderLayout.SOUTH);

// Agregamos al formulario en la fila 5
gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
gbc.fill = GridBagConstraints.BOTH; 
gbc.weightx = 1.0; 
gbc.weighty = 1.0; // dale más peso para que no se achique
panelFormulario.add(panelImagen, gbc);
gbc.gridwidth = 1;


// Fila 6: Botones del formulario
JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
panelBotones.setBackground(new Color(235, 245, 255));
// Botón Agregar
btnAgregar = new JButton("Agregar");
ImageIcon iconoAgregar = new ImageIcon(getClass().getResource("/imagenes/agregar.png"));
Image imgAgregar = iconoAgregar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
btnAgregar.setIcon(new ImageIcon(imgAgregar));



// Botón Editar
btnEditar = new JButton("Editar");
ImageIcon iconoEditar = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
Image imgEditar = iconoEditar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
btnEditar.setIcon(new ImageIcon(imgEditar));

// Botón Eliminar
btnEliminar = new JButton("Eliminar");
ImageIcon iconoEliminar = new ImageIcon(getClass().getResource("/imagenes/eliminar.png"));
Image imgEliminar = iconoEliminar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
btnEliminar.setIcon(new ImageIcon(imgEliminar));
panelBotones.add(btnAgregar); panelBotones.add(btnEditar); panelBotones.add(btnEliminar);

gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
panelFormulario.add(panelBotones, gbc);

// Componente invisible para absorber espacio extra
gbc.gridy = 7; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
panelFormulario.add(new JLabel(""), gbc);



// ---------- TABLA DERECHA ----------
JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
panelTabla.setBackground(Color.WHITE);
JLabel lblTituloTabla = new JLabel("Listado de Productos");
lblTituloTabla.setFont(new Font("Arial", Font.BOLD, 16));
lblTituloTabla.setHorizontalAlignment(JLabel.CENTER);
lblTituloTabla.setForeground(new Color(0, 0, 0));
lblTituloTabla.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

// Panel de búsqueda con layout que no deja espacios al maximizar
JPanel panelBusqueda = new JPanel();
panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.LINE_AXIS));
panelBusqueda.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

// Crear icono de lupa usando imagen
JLabel iconoLupa;
try {
    ImageIcon iconoBusqueda = new ImageIcon(getClass().getResource("/imagenes/Buscar-icon.png"));
    Image imagenLupa = iconoBusqueda.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    ImageIcon iconoLupaImagen = new ImageIcon(imagenLupa);
    iconoLupa = new JLabel(iconoLupaImagen);
} catch (Exception e) {
    iconoLupa = new JLabel("🔍");
    iconoLupa.setFont(new Font("Arial", Font.PLAIN, 14));
}
iconoLupa.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

// Crear panel contenedor para el campo de búsqueda con ícono
JPanel panelCampoBusqueda = new JPanel(new BorderLayout());
panelCampoBusqueda.setPreferredSize(new Dimension(860, 28));
panelCampoBusqueda.setMaximumSize(new Dimension(300, 28));
panelCampoBusqueda.setBackground(Color.WHITE);

JTextField txtBuscar = new JTextField();
txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));

//funcionar el buscar
txtBuscar.addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        String texto = txtBuscar.getText().trim();
        List<Producto> lista;

        if (texto.isEmpty()) {
            lista = productoControlador.listarProductos(); // si está vacío, muestra todos
        } else {
            lista = productoControlador.buscarProductosPorNombre(texto); // busca por nombre
        }

        // actualizar la tabla
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getCantidad(),
                productoControlador.obtenerNombreCategoriaPorId(p.getIdCategoria()),
                p.getFechaCaducidad()
            });
        }
    }
});

// Agregar componentes al panel de búsqueda
panelCampoBusqueda.add(iconoLupa, BorderLayout.WEST);
panelCampoBusqueda.add(txtBuscar, BorderLayout.CENTER);

JButton btnBuscar = new JButton("Buscar");



// Agregar espaciador que empuja los componentes hacia la derecha
panelBusqueda.add(Box.createHorizontalGlue());
panelBusqueda.add(panelCampoBusqueda);
panelBusqueda.add(Box.createHorizontalStrut(10));
panelBusqueda.add(btnBuscar);

JPanel panelSuperiorTabla = new JPanel(new BorderLayout());
panelSuperiorTabla.add(lblTituloTabla, BorderLayout.NORTH);
panelSuperiorTabla.add(panelBusqueda, BorderLayout.SOUTH);
panelTabla.add(panelSuperiorTabla, BorderLayout.NORTH);

String[] columnas = {"Código", "Nombre", "Cantidad", "Categoria", "Fecha de Caducidad"};
// CREAR MODELO PRIMERO
modeloTabla = new DefaultTableModel(columnas, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Hace que las celdas NO sean editables
    }
};

//  ASIGNAR MODELO A LA TABLA
tabla = new JTable(modeloTabla);
tabla.setRowHeight(28);
tabla.getTableHeader().setBackground(new Color(230, 230, 230));
tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
JScrollPane scrollTabla = new JScrollPane(tabla);
panelTabla.add(scrollTabla, BorderLayout.CENTER);

JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

// Botón Ver todos los productos con ícono
JButton btnVerTodos = new JButton("Catálogo de Productos");
try {
    ImageIcon iconoVer = new ImageIcon(getClass().getResource("/imagenes/Verproductos.png"));
    Image imgVer = iconoVer.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    btnVerTodos.setIcon(new ImageIcon(imgVer));
} catch (Exception e) {
    // Si no encuentra la imagen, continúa sin ícono
}

        // Eventos
        
        //  EVENTO: Al hacer clic en una fila, cargar datos en el formulario
tabla.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int fila = tabla.getSelectedRow();
        
        if (fila != -1) {
            try {
                // Obtener el código de la fila seleccionada
                String codigo = (String) tabla.getValueAt(fila, 0);
                System.out.println("Producto seleccionado - Código: " + codigo);
                
                // Buscar producto completo en la base de datos
                int id = productoControlador.obtenerIdProductoPorCodigo(codigo);
                Producto p = productoControlador.obtenerProductoPorId(id);
                
                if (p != null) {
                    // Cargar todos los datos en el formulario
                    txtCodigo.setText(p.getCodigo());
                    txtNombre.setText(p.getNombre());
                    txtPrecio.setText(String.valueOf(p.getPrecio()));
                    txtCantidad.setText(String.valueOf(p.getCantidad()));
                    
                    // Cargar categoría
                    String nombreCategoria = productoControlador.obtenerNombreCategoriaPorId(p.getIdCategoria());
                    comboCategoria.setSelectedItem(nombreCategoria);
                    
                    // Cargar proveedor
                    String nombreProveedor = productoControlador.obtenerNombreProveedorPorId(p.getIdProveedor());
                    comboProveedor.setSelectedItem(nombreProveedor);
                    
                    // Cargar fecha
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    txtFecha.setText(formato.format(p.getFechaCaducidad()));
                    
                    // Cargar imagen si existe
                    if (p.getImagen() != null && p.getImagen().length > 0) {
                        ImageIcon iconoImagen = new ImageIcon(p.getImagen());
                        Image imagenEscalada = iconoImagen.getImage().getScaledInstance(80, 100, Image.SCALE_SMOOTH);
                        lblImagenProducto.setIcon(new ImageIcon(imagenEscalada));
                        lblImagenProducto.setText("");
                    } else {
                        lblImagenProducto.setIcon(null);
                        lblImagenProducto.setText("Sin imagen");
                    }
                    
                    System.out.println("Datos cargados correctamente: " + p.getNombre());
                }
            } catch (Exception e) {
                System.out.println("Error al cargar producto: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
});

//  Limpiar al hacer clic en el viewport del JScrollPane
scrollTabla.getViewport().addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int fila = tabla.rowAtPoint(e.getPoint());
        if (fila == -1) {
            limpiarCampos();
            tabla.clearSelection();
        }
    }
});

//  Limpiar al hacer clic en el panel de la tabla (áreas externas)
panelTabla.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        limpiarCampos();
        tabla.clearSelection();
    }
});

//  Limpiar al hacer clic en el formulario
panelFormulario.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        limpiarCampos();
        tabla.clearSelection();
    }
});
        

        btnAgregar.addActionListener(e -> {
    
            try {
        //  VALIDAR QUE LOS CAMPOS NO ESTÉN VACÍOS
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, " Ingrese el nombre del producto");
            return;
        }
        if (comboCategoria.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría");
            return;
        }
        if (comboProveedor.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            return;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el precio");
            return;
        }
        if (txtCantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cantidad");
            return;
        }
        if (txtFecha.getText().equals("Seleccionar fecha")) {
            JOptionPane.showMessageDialog(this, "Seleccione la fecha de caducidad");
            return;
        }


        // GENERAR CÓDIGO AUTOMÁTICAMENTE
        String codigoGenerado = productoControlador.generarCodigoProducto();
        txtCodigo.setText(codigoGenerado);

        //  CREAR OBJETO PRODUCTO
        Producto nuevo = new Producto();
        nuevo.setCodigo(codigoGenerado);
        nuevo.setNombre(txtNombre.getText().trim());
        nuevo.setIdCategoria(productoControlador.obtenerIdCategoriaPorNombre((String) comboCategoria.getSelectedItem()));
        nuevo.setIdProveedor(productoControlador.obtenerIdProveedorPorNombre((String) comboProveedor.getSelectedItem()));
        nuevo.setPrecio(Double.parseDouble(txtPrecio.getText()));
        nuevo.setCantidad(Integer.parseInt(txtCantidad.getText()));

        //  CONVERTIR FECHA
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = formato.parse(txtFecha.getText());
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        nuevo.setFechaCaducidad(fechaSQL);

        // PROCESAR IMAGEN (SI EXISTE)
        byte[] imagenBytes = null;
        if (lblImagenProducto.getToolTipText() != null) {
            File archivo = new File(lblImagenProducto.getToolTipText());
            FileInputStream fis = new FileInputStream(archivo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            imagenBytes = baos.toByteArray();
            fis.close();
            baos.close();
        }
        nuevo.setImagen(imagenBytes);

        //  GUARDAR EN BASE DE DATOS
        boolean exito = productoControlador.agregarProducto(nuevo);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
            
            //  AGREGAR A LA TABLA
            agregarFilaATabla(nuevo);
            
            //  LIMPIAR CAMPOS
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar producto");
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Precio y cantidad deben ser números válidos");
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
});
        
                
// BOTÓN ELIMINAR 
btnEliminar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int fila = tabla.getSelectedRow();
        System.out.println("Fila seleccionada: " + fila);
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(VentanaProducto.this, "Seleccione un producto");
            return;
        }
        
        String codigo = (String) tabla.getValueAt(fila, 0);
        System.out.println("Código obtenido: " + codigo);
        
        int id = productoControlador.obtenerIdProductoPorCodigo(codigo);
        System.out.println("ID obtenido: " + id);
        
        if (id > 0) {
            boolean resultado = productoControlador.eliminarProducto(id);
            System.out.println("Resultado eliminación: " + resultado);
            
            if (resultado) {
                JOptionPane.showMessageDialog(VentanaProducto.this, "Eliminado");
                cargarTablaProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(VentanaProducto.this, "No se pudo eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(VentanaProducto.this, "No se encontró el ID del producto");
        }
    }
});

// BOTÓN EDITAR 
btnEditar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int fila = tabla.getSelectedRow();
            System.out.println("Fila seleccionada para editar: " + fila);
            
            if (fila == -1) {
                JOptionPane.showMessageDialog(VentanaProducto.this, "Seleccione un producto");
                return;
            }
            
            String codigo = txtCodigo.getText();
            System.out.println("Código del producto: " + codigo);
            
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(VentanaProducto.this, "No hay código en el campo. Haga clic en una fila de la tabla primero.");
                return;
            }
            
            int id = productoControlador.obtenerIdProductoPorCodigo(codigo);
            System.out.println("ID obtenido: " + id);
            
            if (id == 0) {
                JOptionPane.showMessageDialog(VentanaProducto.this, "No se encontró el producto");
                return;
            }
            
            Producto p = productoControlador.obtenerProductoPorId(id);
            System.out.println("Producto obtenido: " + (p != null ? p.getNombre() : "null"));
            
            if (p == null) {
                JOptionPane.showMessageDialog(VentanaProducto.this, "No se pudo cargar el producto");
                return;
            }
            
            p.setNombre(txtNombre.getText());
            p.setIdCategoria(productoControlador.obtenerIdCategoriaPorNombre((String) comboCategoria.getSelectedItem()));
            p.setIdProveedor(productoControlador.obtenerIdProveedorPorNombre((String) comboProveedor.getSelectedItem()));
            p.setPrecio(Double.parseDouble(txtPrecio.getText()));
            p.setCantidad(Integer.parseInt(txtCantidad.getText()));
            
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            p.setFechaCaducidad(new java.sql.Date(formato.parse(txtFecha.getText()).getTime()));
            
            //  ACTUALIZAR IMAGEN SI SE SELECCIONÓ UNA NUEVA
if (lblImagenProducto.getToolTipText() != null && !lblImagenProducto.getToolTipText().isEmpty()) {
    try {
        File archivo = new File(lblImagenProducto.getToolTipText());
        if (archivo.exists()) {
            FileInputStream fis = new FileInputStream(archivo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] imagenBytes = baos.toByteArray();
            p.setImagen(imagenBytes);
            fis.close();
            baos.close();
            System.out.println("Nueva imagen cargada para edición");
        }
    } catch (Exception ex) {
        System.out.println("Error al cargar nueva imagen: " + ex.getMessage());
    }
}
            
            System.out.println("Intentando actualizar producto ID: " + p.getId());
            boolean resultado = productoControlador.editarProducto(p);
            System.out.println("Resultado edición: " + resultado);
            
            if (resultado) {
                JOptionPane.showMessageDialog(VentanaProducto.this, "Actualizado");
                cargarTablaProductos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(VentanaProducto.this, "No se pudo actualizar");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(VentanaProducto.this, "Error: " + ex.getMessage());
        }
    }
});

        btnVerTodos.addActionListener(e -> {
    new VentanaVerProductos(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);  // ✅ CORRECTO
    dispose();
});
// Botón Exportar excel con ícono
JButton btnExportar = new JButton("Exportar Excel");
try {
    ImageIcon iconoexc = new ImageIcon(getClass().getResource("/imagenes/Excel.png"));
    Image imgEXC = iconoexc.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    btnExportar.setIcon(new ImageIcon(imgEXC));
} catch (Exception e) {
    // Si no encuentra la imagen, continúa sin ícono
}

btnExportar.addActionListener(e -> {
    exportarExcel();
});

panelAcciones.add(btnVerTodos);
panelAcciones.add(btnExportar);
panelTabla.add(panelAcciones, BorderLayout.SOUTH);
panelContenido.add(panelFormulario, BorderLayout.WEST);
panelContenido.add(panelTabla, BorderLayout.CENTER);
panelMedio.add(panelContenido, BorderLayout.CENTER);

 // LLAMAR MÉTODO PARA CARGAR COMBOS DESDE BD ----
        //  AGREGADO: cargar tabla inicial
        
        cargarCombos();
        cargarTablaProductos();
        
        
        
    }
    
    
    
    
    //  MÉTODO PARA AGREGAR FILA A LA TABLA

    private void agregarFilaATabla(Producto p) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    
    // Obtener nombre de categoría (en lugar del ID)
    String nombreCategoria = productoControlador.obtenerNombreCategoriaPorId(p.getIdCategoria());
    
    // Formatear fecha
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    String fechaFormateada = formatoFecha.format(p.getFechaCaducidad());
    
    // Agregar fila
    Object[] fila = {
        p.getCodigo(),
        p.getNombre(),
        p.getCantidad(),
        nombreCategoria,
        fechaFormateada
    };
    
    modelo.addRow(fila);
    }
    
    //  MÉTODO PARA CARGAR TODOS LOS PRODUCTOS EN LA TABLA
private void cargarTablaProductos() {
    try {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar
        
        List<Producto> listaProductos = productoControlador.listarProductos();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Producto p : listaProductos) {
            String nombreCategoria = productoControlador.obtenerNombreCategoriaPorId(p.getIdCategoria());
            String fechaFormateada = formatoFecha.format(p.getFechaCaducidad());
            
            Object[] fila = {
                p.getCodigo(),
                p.getNombre(),
                p.getCantidad(),
                nombreCategoria,
                fechaFormateada
            };
            
            modelo.addRow(fila);
        }
        
        System.out.println(" Se cargaron " + listaProductos.size() + " productos en la tabla");
        
    } catch (Exception e) {
        System.out.println(" Error al cargar tabla: " + e.getMessage());
        e.printStackTrace();
    }
}
    
  /*
    private void limpiarCampos() {
    txtCodigo.setText("");
    txtNombre.setText("");
    txtPrecio.setText("");
    txtCantidad.setText("");
    comboCategoria.setSelectedIndex(0);
    comboProveedor.setSelectedIndex(0);
    lblImagenProducto.setIcon(null);
    lblImagenProducto.setText("Sin imagen");
    lblImagenProducto.setToolTipText(null);
    }
    
    
*/

private void limpiarCampos() {
    txtCodigo.setText("");
    txtNombre.setText("");
    txtPrecio.setText("");
    txtCantidad.setText("");
    
    // Resetear combos al valor inicial
    comboCategoria.setSelectedIndex(0);
    comboProveedor.setSelectedIndex(0);
    
    // Resetear fecha
    txtFecha.setText("Seleccionar fecha");
    
    // Limpiar imagen
    lblImagenProducto.setIcon(null);
    lblImagenProducto.setText("Sin imagen");
    lblImagenProducto.setToolTipText(null);
    
    // LIMPIAR SELECCIÓN DE LA TABLA
    tabla.clearSelection();
}

    
   
    
    
    
    
     // ----------------- MÉTODO PARA CARGAR COMBOS -----------------
    public void cargarCombos() {
        try {
            Connection conexion = ConexionBD.getConnection(); // tu método para obtener la conexión
            ProductoControlador pc = new ProductoControlador(conexion);

            comboCategoria.removeAllItems();
            comboProveedor.removeAllItems();
            // Agregar texto guía al inicio
            comboCategoria.addItem("Seleccione...");
            comboProveedor.addItem("Seleccione...");
            
            //Llenar  con los datos reales

            for (String categoria : pc.obtenerCategorias()) {
                comboCategoria.addItem(categoria);
            }

            for (String proveedor : pc.obtenerProveedores()) {
                comboProveedor.addItem(proveedor);
            }
            
            // Seleccionamos la primera opción, que es el texto guía
        
            comboCategoria.setSelectedIndex(0);
        
            comboProveedor.setSelectedIndex(0);
            
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar combos: " + e.getMessage());
        }
    }
    
    
    //METODO PARA EXPORTAR EXCEL OK
public void exportarExcel() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Excel");
        fileChooser.setSelectedFile(new java.io.File("productos.xlsx"));

        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String ruta = fileChooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".xlsx")) ruta += ".xlsx";

        org.apache.poi.xssf.usermodel.XSSFWorkbook libro = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet hoja = libro.createSheet("Productos");

        
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

        
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/imagenes/MassLogo.png");
            if (is != null) {
                byte[] bytes = is.readAllBytes();
                int idx = libro.addPicture(bytes, org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG);
                is.close();

                org.apache.poi.ss.usermodel.Drawing<?> drawing = hoja.createDrawingPatriarch();
                org.apache.poi.ss.usermodel.ClientAnchor anchor = libro.getCreationHelper().createClientAnchor();
                anchor.setCol1(0);
                anchor.setRow1(0);
                anchor.setCol2(3);
                anchor.setRow2(4);

                org.apache.poi.ss.usermodel.Picture pict = drawing.createPicture(anchor, idx);
                pict.resize(0.6);

                hoja.createRow(0).setHeightInPoints(50);
            }
        } catch (Exception ex) {
            System.out.println("No se pudo cargar logo: " + ex.getMessage());
        }

        
        org.apache.poi.ss.usermodel.Row filaTitulo = hoja.createRow(3);
        org.apache.poi.ss.usermodel.Cell celdaTitulo = filaTitulo.createCell(0);
            celdaTitulo.setCellValue("Listado de Productos");
        celdaTitulo.setCellStyle(estiloTitulo);

        hoja.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(
            3, 3, 0, tabla.getColumnCount() - 1
        ));

        
        org.apache.poi.ss.usermodel.Row filaEncabezado = hoja.createRow(5);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            org.apache.poi.ss.usermodel.Cell celda = filaEncabezado.createCell(i);
            celda.setCellValue(tabla.getColumnName(i));
            celda.setCellStyle(estiloEncabezado);
        }

        
        for (int i = 0; i < tabla.getRowCount(); i++) {
            org.apache.poi.ss.usermodel.Row fila = hoja.createRow(i + 6);
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                org.apache.poi.ss.usermodel.Cell celda = fila.createCell(j);
                Object valor = tabla.getValueAt(i, j);
                celda.setCellValue(valor == null ? "" : valor.toString());
                if (i % 2 == 0) celda.setCellStyle(estiloFilaGris);
            }
        }

        

        String fechaHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new java.util.Date());

        int filaInfoIndex = tabla.getRowCount() + 7;

        org.apache.poi.ss.usermodel.Row filaInfo = hoja.createRow(filaInfoIndex);
        org.apache.poi.ss.usermodel.Cell celdaInfo = filaInfo.createCell(0);

        celdaInfo.setCellValue(
            "Exportado por: " + nombreUsuario + " (" + rolUsuario + ")\n" +
            "Fecha y hora de exportación: " + fechaHora
        );

        org.apache.poi.ss.usermodel.CellStyle estiloInfo = libro.createCellStyle();
        estiloInfo.setWrapText(true);
        celdaInfo.setCellStyle(estiloInfo);

        hoja.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(
            filaInfoIndex, filaInfoIndex + 1,
            0, tabla.getColumnCount() - 1
        ));

        
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            hoja.setColumnWidth(i, 8000);
        }

        
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

    
    
    
}