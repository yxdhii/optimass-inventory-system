package vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;
import conexion.ConexionBD;
import controlador.ProductoControlador;
import modelo.Producto;

public class VentanaVerProductos extends VentanaBase1 {
    
    private ProductoControlador productoControlador;
    private Connection conexion;
    private String codigoUsuario;
    
    private JTextField txtBuscar;
    private JPanel panelGrid;
    private JScrollPane scrollPanel;

    public VentanaVerProductos(String codigoUsuario, String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario);
        this.codigoUsuario = codigoUsuario;
        
        // Inicializar conexión y controlador
        conexion = ConexionBD.getConnection();
        productoControlador = new ProductoControlador(conexion);
        
        setTitle("Catálogo de Productos");
        
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
                new VentanaProducto(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
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
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/catalogo.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Catálogo de Productos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);
        
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER);
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);    
        
        // ---- PANEL CONTENIDO ----
        JPanel panelCuerpo = new JPanel(new BorderLayout());
        panelCuerpo.setOpaque(false);
        panelCuerpo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ---------- PANEL DE BÚSQUEDA ----------
        JPanel panelBusquedaContenedor = new JPanel(new GridBagLayout());
        panelBusquedaContenedor.setOpaque(true);
        panelBusquedaContenedor.setBackground(Color.WHITE);
        panelBusquedaContenedor.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel iconoLupa;
        try {
            ImageIcon iconoBusqueda = new ImageIcon(getClass().getResource("/imagenes/Buscar-icon.png"));
            Image imagenLupa = iconoBusqueda.getImage().getScaledInstance(19, 19, Image.SCALE_SMOOTH);
            iconoLupa = new JLabel(new ImageIcon(imagenLupa));
        } catch (Exception e) {
            iconoLupa = new JLabel("🔍");
            iconoLupa.setFont(new Font("Arial", Font.PLAIN, 17));
        }
        gbc.gridx = 0;
        gbc.weightx = 0;
        panelBusquedaContenedor.add(iconoLupa, gbc);

        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 15));
        txtBuscar.setPreferredSize(new Dimension(300, 32));
        txtBuscar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        
        // BÚSQUEDA EN TIEMPO REAL
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = txtBuscar.getText().trim();
                if (texto.isEmpty()) {
                    cargarProductos(productoControlador.listarProductos());
                } else {
                    cargarProductos(productoControlador.buscarProductosPorNombre(texto));
                }
            }
        });
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        panelBusquedaContenedor.add(txtBuscar, gbc);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 15));
        btnBuscar.setBackground(new Color(38, 177, 163));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = txtBuscar.getText().trim();
                if (texto.isEmpty()) {
                    cargarProductos(productoControlador.listarProductos());
                } else {
                    cargarProductos(productoControlador.buscarProductosPorNombre(texto));
                }
            }
        });
        
        gbc.gridx = 2;
        gbc.weightx = 0;
        panelBusquedaContenedor.add(btnBuscar, gbc);

        panelCuerpo.add(panelBusquedaContenedor, BorderLayout.NORTH);

        // ---------- PANEL GRID DE PRODUCTOS ----------
        panelGrid = new JPanel(new GridLayout(0, 5, 20, 18)); // 0 filas = dinámico, 5 columnas
        panelGrid.setBackground(new Color(250, 250, 252));
        panelGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Scroll elegante
        scrollPanel = new JScrollPane(panelGrid);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.getViewport().setBackground(new Color(250, 250, 252));
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(38, 177, 163);
            }
        });

        panelCuerpo.add(scrollPanel, BorderLayout.CENTER);
        panelMedio.add(panelCuerpo, BorderLayout.CENTER);
        
        // ✅ CARGAR PRODUCTOS AL INICIAR
        cargarProductos(productoControlador.listarProductos());
    }
    
    // ========== MÉTODO PARA CARGAR PRODUCTOS DESDE LA BD ==========
    private void cargarProductos(List<Producto> listaProductos) {
        panelGrid.removeAll(); // Limpiar panel antes de cargar
        
        if (listaProductos.isEmpty()) {
            JLabel lblSinResultados = new JLabel("No se encontraron productos");
            lblSinResultados.setFont(new Font("Arial", Font.BOLD, 16));
            lblSinResultados.setForeground(Color.GRAY);
            lblSinResultados.setHorizontalAlignment(JLabel.CENTER);
            panelGrid.add(lblSinResultados);
        } else {
            for (Producto producto : listaProductos) {
                panelGrid.add(crearTarjetaProducto(producto));
            }
        }
        
        panelGrid.revalidate();
        panelGrid.repaint();
    }
    
    // ========== CREAR TARJETA DE PRODUCTO (VERSIÓN COMPACTA) ==========
private JPanel crearTarjetaProducto(Producto producto) {
    JPanel tarjeta = new JPanel();
    tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
    tarjeta.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(218, 218, 218), 2, true),
        BorderFactory.createEmptyBorder(10, 8, 10, 8)
    ));
    tarjeta.setBackground(Color.WHITE);
    tarjeta.setPreferredSize(new Dimension(180, 240)); // Tamaño más compacto
    tarjeta.setMaximumSize(new Dimension(180, 240));

    // ===== IMAGEN DEL PRODUCTO =====
    JLabel lblImg = new JLabel();
    lblImg.setPreferredSize(new Dimension(80, 80));
    lblImg.setMaximumSize(new Dimension(80, 80));
    lblImg.setMinimumSize(new Dimension(80, 80));
    
    if (producto.getImagen() != null && producto.getImagen().length > 0) {
        try {
            ImageIcon iconoOriginal = new ImageIcon(producto.getImagen());
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            lblImg.setIcon(new ImageIcon(imagenEscalada));
        } catch (Exception e) {
            lblImg.setText("Sin imagen");
            lblImg.setFont(new Font("Arial", Font.ITALIC, 10));
            lblImg.setForeground(Color.GRAY);
        }
    } else {
        lblImg.setText("Sin imagen");
        lblImg.setFont(new Font("Arial", Font.ITALIC, 10));
        lblImg.setForeground(Color.GRAY);
    }
    lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblImg.setHorizontalAlignment(JLabel.CENTER);

    // ===== NOMBRE DEL PRODUCTO (con truncado si es muy largo) =====
    String nombreProducto = producto.getNombre();
    if (nombreProducto.length() > 25) {
        nombreProducto = nombreProducto.substring(0, 22) + "...";
    }
    JLabel lblNombre = new JLabel("<html><center>" + nombreProducto + "</center></html>");
    lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
    lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblNombre.setHorizontalAlignment(JLabel.CENTER);

    lblNombre.setMaximumSize(new Dimension(160, 35));

    // ===== CÓDIGO =====
    JLabel lblCodigo = new JLabel("Código: " + producto.getCodigo());
    lblCodigo.setFont(new Font("Arial", Font.PLAIN, 10));
    lblCodigo.setForeground(new Color(65, 65, 65));
    lblCodigo.setAlignmentX(Component.CENTER_ALIGNMENT);

    // ===== PRECIO =====
    JLabel lblPrecio = new JLabel(String.format("Precio: S/ %.2f", producto.getPrecio()));
    lblPrecio.setFont(new Font("Arial", Font.BOLD, 12));
    lblPrecio.setForeground(new Color(34, 166, 60));
    lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

    // ===== STOCK =====
    JLabel lblStock = new JLabel("Stock: " + producto.getCantidad() + " unidades");
    lblStock.setFont(new Font("Arial", Font.PLAIN, 10));
    lblStock.setForeground(new Color(60, 93, 254));
    lblStock.setAlignmentX(Component.CENTER_ALIGNMENT);

    // ===== CÓDIGO DE BARRAS (DIBUJADO Y COMPACTO) =====
    JPanel panelCodigoBarras = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            
            int x = 5;
            for (int i = 0; i < 25; i++) {
                int altura = (i % 2 == 0) ? 18 : 13;
                g2d.fillRect(x, 2, 2, altura);
                x += 3;
            }
        }
    };
    panelCodigoBarras.setPreferredSize(new Dimension(80, 22));
    panelCodigoBarras.setMaximumSize(new Dimension(80, 22));
    panelCodigoBarras.setBackground(Color.WHITE);
    panelCodigoBarras.setAlignmentX(Component.CENTER_ALIGNMENT);

    // ===== AGREGAR COMPONENTES A LA TARJETA CON ESPACIADO OPTIMIZADO =====
    tarjeta.add(Box.createVerticalStrut(3));
    tarjeta.add(lblImg);
    tarjeta.add(Box.createVerticalStrut(5));
    tarjeta.add(lblNombre);
    tarjeta.add(Box.createVerticalStrut(2));
    tarjeta.add(lblCodigo);
    tarjeta.add(Box.createVerticalStrut(3));
    tarjeta.add(lblPrecio);
    tarjeta.add(Box.createVerticalStrut(2));
    tarjeta.add(lblStock);
    tarjeta.add(Box.createVerticalStrut(4));
    tarjeta.add(panelCodigoBarras);
    tarjeta.add(Box.createVerticalGlue()); // Espacio flexible al final

    // ===== EFECTO HOVER =====
    tarjeta.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            tarjeta.setBackground(new Color(240, 248, 255));
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(38, 177, 163), 2, true),
                BorderFactory.createEmptyBorder(10, 8, 10, 8)
            ));
            tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            tarjeta.setBackground(Color.WHITE);
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 218, 218), 2, true),
                BorderFactory.createEmptyBorder(10, 8, 10, 8)
            ));
            tarjeta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    });

    return tarjeta;
}
}