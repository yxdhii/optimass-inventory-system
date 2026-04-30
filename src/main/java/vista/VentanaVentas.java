package vista;
import java.util.List;
import dao.ProductoDAO;
import modelo.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class VentanaVentas extends VentanaBase1 {
    
    // === CAMPOS DEL FORMULARIO ===
    private String codigoUsuario;

    private JComboBox<String> cbProducto;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JTextField txtTotal;
    private java.util.Map<String, Double> mapaPrecios = new java.util.HashMap<>();
    private JTable tablaVentas;
    private DefaultTableModel modeloVentas;


    public VentanaVentas(String codigoUsuario,String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario); // llama al constructor de VentanaBase1
        setTitle("Ventas");
        // Panel principal de contenido
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
                new Menu(codigoUsuario,nombreUsuario, rolUsuario).setVisible(true);
                dispose();
            }
        });
        
        // === PANEL IZQUIERDO PARA EL BOTÓN (con espacio extra) ===

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10)); // 15px espacio a la derecha
        panelIzquierdo.setOpaque(false); // sin color de fondo
        panelIzquierdo.add(btnRegresar);
        // === PANEL CENTRAL CON ICONO + TEXTO ===
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Ventas-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Ventas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);
        // === Agregar al sub-encabezado ===
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);   // a la izquierda
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER); // en el centro
        // Agregar el sub-encabezado arriba
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);    
        //contenido
        
        
            // ----- TABS -----
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Registrar Venta", crearPanelRegistro());
        pestañas.addTab("Consultar por Producto", crearPanelConsulta());

        panelMedio.add(pestañas, BorderLayout.CENTER);
    }

    // Panel de registro de ventas
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 247));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Nueva Venta"),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formulario.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> cbCliente = new JComboBox<>(new String[]{"Seleccione...", "Cliente A", "Cliente B"});
        formulario.add(cbCliente, gbc);
        gbc.gridx = 0; gbc.gridy = 1;

        formulario.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        cbProducto = new JComboBox<>();
        formulario.add(cbProducto, gbc);
        // Llenar el combo con los nombres de productos desde la base de datos
        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.obtenerProductos();
        cbProducto.addItem("Seleccione...");
        for (Producto p : productos) {   
            cbProducto.addItem(p.getNombre());   
            mapaPrecios.put(p.getNombre(), p.getPrecio());
}
        
      


        
        /*

        gbc.gridx = 0; gbc.gridy = 1;
        formulario.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> cbProducto = new JComboBox<>(new String[]{"Seleccione...", "Producto X", "Producto Y"});
        formulario.add(cbProducto, gbc);
         */


        gbc.gridx = 0; gbc.gridy = 2;
        formulario.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        txtCantidad = new JTextField();
        // Calcular total automáticamente al escribir la cantidad
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                calcularTotal();
            }});
        formulario.add(txtCantidad, gbc);
        


        gbc.gridx = 0; gbc.gridy = 3;
        formulario.add(new JLabel("Precio Unitario:"), gbc);
        gbc.gridx = 1;
        txtPrecio = new JTextField();
        
        formulario.add(txtPrecio, gbc);
        

        cbProducto.addActionListener(e -> {    
            String seleccionado = (String) cbProducto.getSelectedItem();    
            if (mapaPrecios.containsKey(seleccionado)) {        
                txtPrecio.setText(String.valueOf(mapaPrecios.get(seleccionado)));        
                calcularTotal();    
            } else {        
                txtPrecio.setText("");        
                txtTotal.setText("");    
            }
        });

        gbc.gridx = 0; gbc.gridy = 4;
        formulario.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        formulario.add(txtTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnRegistrar = new JButton("Registrar Venta");
        
        btnRegistrar.addActionListener(e -> {
    String cliente = cbCliente.getSelectedItem().toString();
    String producto = cbProducto.getSelectedItem().toString();
    String cantidadStr = txtCantidad.getText();
    String precioStr = txtPrecio.getText();
    String totalStr = txtTotal.getText();

    if (cliente.equals("Seleccione...") || producto.equals("Seleccione...") ||
        cantidadStr.isEmpty() || precioStr.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int cantidad = Integer.parseInt(cantidadStr);
    double precio = Double.parseDouble(precioStr);
    double total = Double.parseDouble(totalStr);

    dao.ProductoDAO productoDAO = new dao.ProductoDAO();
    dao.VentaDAO ventaDAO = new dao.VentaDAO();

    // Registrar venta
    if (ventaDAO.registrarVenta(cliente, producto, cantidad, precio, total)) {
        // Actualizar stock
        if (productoDAO.actualizarStock(producto, cantidad)) {
            JOptionPane.showMessageDialog(null, " Venta registrada y stock actualizado correctamente.");
            
            // Agregar a la tabla visual
            DefaultTableModel modelo = (DefaultTableModel) ((JTable)((JScrollPane) panel.getComponent(1)).getViewport().getView()).getModel();
            modelo.addRow(new Object[]{modelo.getRowCount() + 1, cliente, producto, cantidad, precio, total});

            // Limpiar campos
            cbCliente.setSelectedIndex(0);
            cbProducto.setSelectedIndex(0);
            txtCantidad.setText("");
            txtPrecio.setText("");
            txtTotal.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
    }
});
        btnRegistrar.setBackground(new Color(0, 153, 76));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formulario.add(btnRegistrar, gbc);

        panel.add(formulario, BorderLayout.NORTH);

        // Tabla de ventas
        String[] columnas = {"ID", "Cliente", "Producto", "Cantidad", "Precio", "Total"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        // === Cargar las ventas guardadas en la base de datos ===

        dao.VentaDAO ventaDAO = new dao.VentaDAO();
        java.util.List<Object[]> ventasPrevias = ventaDAO.obtenerVentas();
        for (Object[] fila : ventasPrevias) {    
            modeloTabla.addRow(fila);
        }
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Ventas del día"));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(38, 177, 163);
            }
        });

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // Panel de consulta de ventas
// Panel de consulta de ventas
private JPanel crearPanelConsulta() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(245, 245, 247));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
    filtro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Buscar ventas por producto"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    filtro.setBackground(Color.WHITE);

    filtro.add(new JLabel("Producto:"));

    // ComboBox con productos
    JComboBox<String> cbProductoConsulta = new JComboBox<>();
    cbProductoConsulta.addItem("Seleccione...");
    ProductoDAO daoConsulta = new ProductoDAO();
    List<Producto> productos = daoConsulta.obtenerProductos();
    for (Producto p : productos) {
        cbProductoConsulta.addItem(p.getNombre());
    }
    filtro.add(cbProductoConsulta);

    // === Crear tabla y modelo ANTES del botón ===
    String[] columnas = {"Fecha", "Cliente", "Producto", "Cantidad", "Total"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
    JTable tabla = new JTable(modelo);
    tabla.setFillsViewportHeight(true);
    tabla.setRowHeight(25);

    JScrollPane scroll = new JScrollPane(tabla);
    scroll.getVerticalScrollBar().setUnitIncrement(16);
    scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(38, 177, 163);
        }
    });

    // === Botón de búsqueda ===
    JButton btnBuscar = new JButton("Buscar");
    btnBuscar.setBackground(new Color(38, 177, 163));
    btnBuscar.setForeground(Color.WHITE);
    btnBuscar.setFocusPainted(false);
    btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    filtro.add(btnBuscar);

    // === Acción del botón ===
    btnBuscar.addActionListener(e -> {
        String productoSeleccionado = cbProductoConsulta.getSelectedItem().toString();

        if (productoSeleccionado.equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dao.VentaDAO ventaDAO = new dao.VentaDAO();
        java.util.List<Object[]> ventas = ventaDAO.buscarVentasPorProducto(productoSeleccionado);

        // Limpiar tabla
        modelo.setRowCount(0);

        // Llenar con resultados
        for (Object[] fila : ventas) {
            modelo.addRow(fila);
        }

        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas registradas para este producto.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    });

    // === Añadir todo al panel ===
    panel.add(filtro, BorderLayout.NORTH);
    panel.add(scroll, BorderLayout.CENTER);

    return panel;
}
    
    private void calcularTotal() {
    try {
        double precio = Double.parseDouble(txtPrecio.getText());
        int cantidad = Integer.parseInt(txtCantidad.getText());
        double total = precio * cantidad;
        txtTotal.setText(String.valueOf(total));
    } catch (NumberFormatException ex) {
        txtTotal.setText("");
    }
}
    
    
    
    
    
}