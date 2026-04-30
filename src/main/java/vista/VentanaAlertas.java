package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.ProductoDAO;
import modelo.Producto;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import javax.swing.Timer; 

public class VentanaAlertas extends VentanaBase1 {
    public String codigoUsuario;
    
    // Componentes de configuración
    private JCheckBox chkNotificaciones;
    private JSpinner spnUmbral;
    private JComboBox<String> cmbMetodo;
    private JCheckBox chkStockBajo;
    private JCheckBox chkVencimiento;
    
    // Tablas para mostrar alertas
    private JTable tablaStockBajo;
    private JTable tablaProximosVencer;
    private DefaultTableModel modeloStockBajo;
    private DefaultTableModel modeloProximosVencer;

    public VentanaAlertas(String codigoUsuario, String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario);
        this.codigoUsuario = codigoUsuario;
        setTitle("Alertas");
        
        // Panel principal de contenido
        panelMedio.setLayout(new BorderLayout());

        // ----- SUB-ENCABEZADO DEL MÓDULO -----
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230));
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50));

        // BOTÓN DE REGRESAR
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
                new VentanaConfiguracion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
                dispose();
            }
        });

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.add(btnRegresar);

        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/alerta.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Alertas y Notificaciones");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);

        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER);
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);

        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        
        JPanel panelConfiguracion = crearPanelConfiguracion();
        tabbedPane.addTab("Configuración", panelConfiguracion);
        
        
        JPanel panelAlertasActivas = crearPanelAlertasActivas();
        tabbedPane.addTab("Alertas Activas", panelAlertasActivas);

        panelMedio.add(tabbedPane, BorderLayout.CENTER);
        
        
        actualizarAlertas();
        
            // AGREGAR ESTO: Mostrar alerta automática después de 2 segundos
    
            Timer timer = new Timer(2000, e -> mostrarNotificacionAutomatica());
    
            timer.setRepeats(false); // Solo una vez
    
            timer.start();
            
    }
    
    

    // panel configuracion
    private JPanel crearPanelConfiguracion() {
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(new Color(245, 245, 245));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        int fila = 0;

        // 
        JPanel panelGeneral = crearPanelSeccion("Configuración General");
        panelGeneral.setLayout(new GridBagLayout());
        GridBagConstraints gbcGeneral = new GridBagConstraints();
        gbcGeneral.insets = new Insets(10, 10, 10, 10);
        gbcGeneral.fill = GridBagConstraints.HORIZONTAL;
        gbcGeneral.anchor = GridBagConstraints.WEST;

        chkNotificaciones = new JCheckBox("Activar notificaciones en pantalla");
        chkNotificaciones.setFont(new Font("Arial", Font.PLAIN, 14));
        chkNotificaciones.setBackground(Color.WHITE);
        chkNotificaciones.setSelected(true);
        gbcGeneral.gridx = 0; gbcGeneral.gridy = 0; gbcGeneral.gridwidth = 2;
        panelGeneral.add(chkNotificaciones, gbcGeneral);

        gbcGeneral.gridwidth = 1;
        JLabel lblUmbral = new JLabel("Stock mínimo global:");
        lblUmbral.setFont(new Font("Arial", Font.BOLD, 14));
        lblUmbral.setForeground(new Color(60, 60, 60));
        gbcGeneral.gridx = 0; gbcGeneral.gridy = 1;
        panelGeneral.add(lblUmbral, gbcGeneral);

        spnUmbral = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        spnUmbral.setFont(new Font("Arial", Font.PLAIN, 14));
        gbcGeneral.gridx = 1; gbcGeneral.gridy = 1;
        panelGeneral.add(spnUmbral, gbcGeneral);

        JLabel lblMetodo = new JLabel("Método de notificación:");
        lblMetodo.setFont(new Font("Arial", Font.BOLD, 14));
        lblMetodo.setForeground(new Color(60, 60, 60));
        gbcGeneral.gridx = 0; gbcGeneral.gridy = 2;
        panelGeneral.add(lblMetodo, gbcGeneral);

        cmbMetodo = new JComboBox<>(new String[]{"Ventanas emergentes", "Mensajes en interfaz", "Ambos métodos"});
        cmbMetodo.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbMetodo.setBackground(Color.WHITE);
        gbcGeneral.gridx = 1; gbcGeneral.gridy = 2;
        panelGeneral.add(cmbMetodo, gbcGeneral);

        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        panelContenido.add(panelGeneral, gbc);

        // SECCIÓN 2: Tipos de Alertas
        JPanel panelAlertas = crearPanelSeccion("Tipos de Alertas");
        panelAlertas.setLayout(new BoxLayout(panelAlertas, BoxLayout.Y_AXIS));

        chkStockBajo = crearCheckboxConIcono("Stock bajo", new Color(220, 80, 60));
        chkVencimiento = crearCheckboxConIcono("Productos próximos a vencer (30 días)", new Color(255, 140, 0));

        chkStockBajo.setSelected(true);
        chkVencimiento.setSelected(true);

        panelAlertas.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAlertas.add(chkStockBajo);
        panelAlertas.add(Box.createRigidArea(new Dimension(0, 12)));
        panelAlertas.add(chkVencimiento);
        panelAlertas.add(Box.createRigidArea(new Dimension(0, 10)));

        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        panelContenido.add(panelAlertas, gbc);

        // 
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panelBotones.setBackground(new Color(245, 245, 245));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton btnGuardar = new JButton("Guardar y Actualizar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 15));
        btnGuardar.setBackground(new Color(70, 130, 180));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnRestablecer = new JButton("Restablecer");
        btnRestablecer.setFont(new Font("Arial", Font.BOLD, 15));
        btnRestablecer.setBackground(new Color(220, 220, 220));
        btnRestablecer.setForeground(new Color(80, 80, 80));
        btnRestablecer.setFocusPainted(false);
        btnRestablecer.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnRestablecer.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnRestablecer);

        gbc.gridx = 0; gbc.gridy = fila++; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(panelBotones, gbc);

        
        btnGuardar.addActionListener(e -> {
            actualizarAlertas();
            JOptionPane.showMessageDialog(this,
                "Configuración guardada y alertas actualizadas",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnRestablecer.addActionListener(e -> {
            chkNotificaciones.setSelected(true);
            spnUmbral.setValue(10);
            cmbMetodo.setSelectedIndex(0);
            chkStockBajo.setSelected(true);
            chkVencimiento.setSelected(true);
            actualizarAlertas();
            
            JOptionPane.showMessageDialog(this,
                "Configuración restablecida a valores predeterminados.",
                "Configuración Restablecida",
                JOptionPane.INFORMATION_MESSAGE);
        });

        return panelContenido;
    }

    // panel alertas activas
    private JPanel crearPanelAlertasActivas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con resumen
        JPanel panelResumen = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelResumen.setBackground(new Color(255, 245, 230));
        panelResumen.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 100), 2));

        JLabel lblResumen = new JLabel("Alertas detectadas automáticamente desde la base de datos");
        lblResumen.setFont(new Font("Arial", Font.BOLD, 14));
        lblResumen.setForeground(new Color(150, 80, 0));
        panelResumen.add(lblResumen);

        panel.add(panelResumen, BorderLayout.NORTH);

        // Panel central con tablas
        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCentral.setBackground(Color.WHITE);

        // Productos con Stock Bajo
        JPanel panelStockBajo = new JPanel(new BorderLayout());
        panelStockBajo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 80, 60), 2),
            "Productos con Stock Bajo",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(180, 0, 0)
        ));
        
        String[] columnasStock = {"Código", "Producto", "Stock Actual", "Stock Mínimo"};
        modeloStockBajo = new DefaultTableModel(columnasStock, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaStockBajo = new JTable(modeloStockBajo);
        tablaStockBajo.setRowHeight(30);
        tablaStockBajo.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaStockBajo.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaStockBajo.getTableHeader().setBackground(new Color(255, 220, 220));
        JScrollPane scrollStock = new JScrollPane(tablaStockBajo);
        panelStockBajo.add(scrollStock, BorderLayout.CENTER);

        // Productos Próximos a Vencer
        JPanel panelVencer = new JPanel(new BorderLayout());
        panelVencer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
            "Productos Próximos a Vencer (30 días)",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(200, 100, 0)
        ));
        
        String[] columnasVencer = {"Código", "Producto", "Fecha Caducidad", "Días Restantes"};
        modeloProximosVencer = new DefaultTableModel(columnasVencer, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProximosVencer = new JTable(modeloProximosVencer);
        tablaProximosVencer.setRowHeight(30);
        tablaProximosVencer.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaProximosVencer.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaProximosVencer.getTableHeader().setBackground(new Color(255, 240, 220));
        JScrollPane scrollVencer = new JScrollPane(tablaProximosVencer);
        panelVencer.add(scrollVencer, BorderLayout.CENTER);

        panelCentral.add(panelStockBajo);
        panelCentral.add(panelVencer);

        panel.add(panelCentral, BorderLayout.CENTER);

        // Botón actualizar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        
        JButton btnActualizar = new JButton("Actualizar Alertas");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(70, 130, 180));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnActualizar.addActionListener(e -> actualizarAlertas());
        
        panelBoton.add(btnActualizar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    // metodo de actualizar alertas
    private void actualizarAlertas() {
        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.listarProductos();
        
        int stockMinimo = (int) spnUmbral.getValue();
        
        // Limpiar tablas
        modeloStockBajo.setRowCount(0);
        modeloProximosVencer.setRowCount(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date hoy = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoy);
        cal.add(Calendar.DAY_OF_MONTH, 30);
        Date dentroDeUnMes = cal.getTime();
        
        // Revisar cada producto
        for (Producto p : productos) {
            // ALERTA 1: STOCK BAJO
            if (chkStockBajo.isSelected() && p.getCantidad() < stockMinimo) {
                modeloStockBajo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCantidad(),
                    stockMinimo
                });
            }
            
            //PRODUCTOS PRÓXIMOS A VENCER
            if (chkVencimiento.isSelected() && p.getFechaCaducidad() != null) {
                Date fechaCad = p.getFechaCaducidad();
                
                if (fechaCad.before(dentroDeUnMes) && fechaCad.after(hoy)) {
                    long diff = fechaCad.getTime() - hoy.getTime();
                    int diasRestantes = (int) (diff / (1000 * 60 * 60 * 24));
                    
                    modeloProximosVencer.addRow(new Object[]{
                        p.getCodigo(),
                        p.getNombre(),
                        sdf.format(fechaCad),
                        diasRestantes + " días"
                    });
                }
            }
        }
        
        // Mostrar notificación si está activado
        if (chkNotificaciones.isSelected()) {
            int totalAlertas = modeloStockBajo.getRowCount() + modeloProximosVencer.getRowCount();
            
            if (totalAlertas > 0) {
                String mensaje = String.format(
                    "Se detectaron %d alertas:\n\n" +
                    "%d productos con stock bajo\n" +
                    "%d productos próximos a vencer",
                    totalAlertas,
                    modeloStockBajo.getRowCount(),
                    modeloProximosVencer.getRowCount()
                );
                
                if (cmbMetodo.getSelectedIndex() == 0 || cmbMetodo.getSelectedIndex() == 2) {
                    JOptionPane.showMessageDialog(this, mensaje, "Alertas Detectadas", 
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    // metodos
    private JPanel crearPanelSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                titulo,
                0, 0,
                new Font("Arial", Font.BOLD, 16),
                new Color(0, 51, 102)
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return panel;
    }

    private JCheckBox crearCheckboxConIcono(String texto, Color color) {
        JCheckBox checkbox = new JCheckBox(texto);
        checkbox.setFont(new Font("Arial", Font.PLAIN, 14));
        checkbox.setBackground(Color.WHITE);
        checkbox.setFocusPainted(false);
        return checkbox;
    }
    
    // AGREGAR ESTE MÉTODO al final de tu clase
private void mostrarNotificacionAutomatica() {
    if (chkNotificaciones.isSelected()) {
        int totalAlertas = modeloStockBajo.getRowCount() + modeloProximosVencer.getRowCount();
        
        if (totalAlertas > 0) {
            String mensaje = String.format(
                "ALERTAS DETECTADAS ️\n\n" +
                "Se encontraron %d alertas:\n\n" +
                "%d productos con STOCK BAJO\n" +
                "%d productos próximos a VENCER\n\n" +
                "Revisa los detalles en la pestaña 'Alertas Activas'",
                totalAlertas,
                modeloStockBajo.getRowCount(),
                modeloProximosVencer.getRowCount()
            );
            
            JOptionPane.showMessageDialog(this, 
                mensaje, 
                "Alertas del Sistema", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
}