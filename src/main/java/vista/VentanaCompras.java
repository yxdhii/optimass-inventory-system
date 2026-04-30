package vista;

import dao.CompraDAO;
import dao.ProductoDAO;
import dao.ProveedorDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

public class VentanaCompras extends VentanaBase1 {
    private String codigoUsuario;

    public VentanaCompras(String codigoUsuario,String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario); // llama al constructor de VentanaBase1
        this.codigoUsuario = codigoUsuario; 
       
        //TITULO DE LA VENTANA
        setTitle("Compras");
        
        // PANEL PRINCIPAL DE CONTENIDO
        panelMedio.setLayout(new BorderLayout());

        //  SUBENCABEZADO DEL MÓDULO 
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
        
        // PANEL IZQUIERDO PARA EL BOTÓn
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10)); // 15px espacio a la derecha
        panelIzquierdo.setOpaque(false); // sin color de fondo
        panelIzquierdo.add(btnRegresar);
        
        // PANEL CENTRAL CON ICONO + TEXTO
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Compras-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Compras");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);
        
        // Agregar al subencabezad
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);   // a la izquierda
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER); // en el centro
        // Agregar el subencabezado arriba
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);    
        
        //contenido
        
           // mi interfaz principal
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Compra"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos del formulario
        JLabel lblProveedor = new JLabel("Proveedor:");
        JComboBox<String> comboProveedor = new JComboBox<>();
        comboProveedor.addItem("Seleccionar...");

        ProveedorDAO proveedorDAO = new ProveedorDAO();

        for (String nombre : proveedorDAO.obtenerProveedores()) {
    
            comboProveedor.addItem(nombre);

        }

        
        JLabel lblProducto = new JLabel("Producto:");
        JComboBox<String> comboProducto = new JComboBox<>();
        comboProducto.addItem("Seleccionar...");

        ProductoDAO productoDAO = new ProductoDAO();

        Map<String, Double> mapaPrecios = new HashMap<>();


        for (Producto p : productoDAO.obtenerProductos()) {
    
            
            comboProducto.addItem(p.getNombre());
    
            mapaPrecios.put(p.getNombre(), p.getPrecio());

        }



        JLabel lblCantidad = new JLabel("Cantidad:");
        JTextField txtCantidad = new JTextField(10);

        JLabel lblPrecio = new JLabel("Precio Unitario:");
        JTextField txtPrecio = new JTextField(10);

        JButton btnRegistrar = new JButton("Registrar Compra");
        btnRegistrar.setBackground(new Color(72, 149, 239));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        
      
        comboProducto.addActionListener(e -> {
    
          String productoSeleccionado = (String) comboProducto.getSelectedItem();

    
    
          if (productoSeleccionado == null || productoSeleccionado.equals("Seleccionar...")) {
              txtPrecio.setText("");
              return;
    
          }
          
          Double precio = mapaPrecios.get(productoSeleccionado);
    
          txtPrecio.setText(precio != null ? String.valueOf(precio) : "");

      });
        

        // Ubicar en la cuadrícula
        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(lblProveedor, gbc);
        
        gbc.gridx = 1; panelFormulario.add(comboProveedor, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(lblProducto, gbc);
        
        gbc.gridx = 1; panelFormulario.add(comboProducto, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(lblCantidad, gbc);
        
        gbc.gridx = 1; panelFormulario.add(txtCantidad, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(lblPrecio, gbc);
        
        gbc.gridx = 1; panelFormulario.add(txtPrecio, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        
        panelFormulario.add(btnRegistrar, gbc);

        // ---------- TABLA DE HISTORIAL ----------
        String[] columnas = {"Proveedor", "Producto", "Cantidad", "Precio Unitario", "Fecha"};
        
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        
        JTable tablaHistorial = new JTable(modeloTabla);
        
        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Historial de Compras"));
        
        CompraDAO compraDAO3 = new CompraDAO();


        for (Object[] fila : compraDAO3.obtenerHistorialCompras()) {
    
            modeloTabla.addRow(fila);

        }

        // prueba :  al dar clic agrega fila

        btnRegistrar.addActionListener(e -> {

    
            String proveedor = (String) comboProveedor.getSelectedItem();
    
            String producto = (String) comboProducto.getSelectedItem();
    
            String cantidadTxt = txtCantidad.getText();
    
            String precioTxt = txtPrecio.getText();
    
    
      
            if (proveedor.equals("Seleccionar...") ||
        
                    producto.equals("Seleccionar...") ||
        
                    cantidadTxt.isEmpty() || precioTxt.isEmpty()) {

        
                JOptionPane.showMessageDialog(null, "Complete todos los campos.");
        
                return;
    
            }

    
            int cantidad = Integer.parseInt(cantidadTxt);
    
            double precioUnit = Double.parseDouble(precioTxt);

    
            // Registrar compra
    
            CompraDAO compraDAO = new CompraDAO();
    
            boolean exito = compraDAO.registrarCompra(proveedor, producto, cantidad, precioUnit);

    
            if (!exito) {
        
                JOptionPane.showMessageDialog(null, "Error al registrar la compra.");
        
                return;
    
            }

            // Aumentar stock
    
            ProductoDAO productoDAO2 = new ProductoDAO();
    
            productoDAO2.aumentarStock(producto, cantidad);

            // Agregar a la tabla del historial
    
            String fecha = java.time.LocalDate.now().toString();
    
            modeloTabla.addRow(new Object[]{proveedor, producto, cantidad, precioUnit, fecha});

    
            JOptionPane.showMessageDialog(null, "Compra registrada correctamente.");

            // Limpiar campos
    
            txtCantidad.setText("");
    
            txtPrecio.setText("");

        });

        // Agregar partes al contenido
        panelContenido.add(panelFormulario, BorderLayout.NORTH);
        panelContenido.add(scrollTabla, BorderLayout.CENTER);

        // Añadir contenido al panel principal
        panelMedio.add(panelContenido, BorderLayout.CENTER);
        
 
        
    }
    
    
}