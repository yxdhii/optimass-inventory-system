package vista;

import controlador.RolControlador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class VentanaRoles extends VentanaBase1 {
    
    private String codigoUsuario;

    
 // 🔹 Declaraciones públicas para el controlador
    public JButton btnAgregar, btnEditar, btnEliminar;
    public JTextField txtNombre;
    public JTable tabla;
    public JButton btnBuscar;
    public JTextField txtBuscar;

    public VentanaRoles(String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario);
        setTitle("Roles y Permisos");

        // Panel principal
        panelMedio.setLayout(new BorderLayout());

        // ===== SUB-ENCABEZADO =====
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230));
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50));

        // Botón regresar
        ImageIcon iconoRegresarOriginal = new ImageIcon(getClass().getResource("/imagenes/regresar-icon.png"));
        Image imgRegresar = iconoRegresarOriginal.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        JButton btnRegresar = new JButton(new ImageIcon(imgRegresar));
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setToolTipText("Regresar");
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegresar.addActionListener(e -> {
            new VentanaConfiguracion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
dispose();
        });

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.add(btnRegresar);

        // Icono + título
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/roles.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Roles y Permisos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);

        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER);
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);

        // ===== CONTENIDO CENTRAL =====
        JPanel panelContenido = new JPanel(new BorderLayout(20, 0));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // --- PANEL IZQUIERDO (Formulario) ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(235, 245, 255));
        panelFormulario.setBorder(BorderFactory.createLineBorder(new Color(150, 170, 200), 2, true));
        panelFormulario.setPreferredSize(new Dimension(400, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTituloFormulario = new JLabel("Registro de Roles");
        lblTituloFormulario.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloFormulario.setHorizontalAlignment(JLabel.CENTER);
        lblTituloFormulario.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 15, 8);
        panelFormulario.add(lblTituloFormulario, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Campo Nombre
        txtNombre = new JTextField(15);
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 13));

        gbc.gridy = 1;
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(new JLabel("Nombre del Rol:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(txtNombre, gbc);

        // Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(new Color(235, 245, 255));

        // Botón Agregar
        btnAgregar = new JButton("Agregar");
        ImageIcon iconoAgregar = new ImageIcon(getClass().getResource("/imagenes/agregar.png"));
        btnAgregar.setIcon(new ImageIcon(iconoAgregar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));

        // Botón Editar
        btnEditar = new JButton("Editar");
        ImageIcon iconoEditar = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
        btnEditar.setIcon(new ImageIcon(iconoEditar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar");
        ImageIcon iconoEliminar = new ImageIcon(getClass().getResource("/imagenes/eliminar.png"));
        btnEliminar.setIcon(new ImageIcon(iconoEliminar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        gbc.gridy = 2;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(panelBotones, gbc);

        // --- PANEL DERECHO (Tabla) ---
        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setBackground(Color.WHITE);

        JLabel lblTituloTabla = new JLabel("Listado de Roles");
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

        // Tabla
        String[] columnas = {"Nombre del Rol"};
        Object[][] datos = {
                {"Administrador"},
                {"Vendedor"},
                {"Supervisor"},
        };
        DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setBackground(new Color(230, 230, 230));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        // Acciones inferiores
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnExportarPDF = new JButton("Exportar PDF");
        ImageIcon iconoPdf = new ImageIcon(getClass().getResource("/imagenes/pdf.png"));
        btnExportarPDF.setIcon(new ImageIcon(iconoPdf.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        JButton btnExportarExcel = new JButton("Exportar Excel");
        ImageIcon iconoExcel = new ImageIcon(getClass().getResource("/imagenes/Excel.png"));
        btnExportarExcel.setIcon(new ImageIcon(iconoExcel.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        panelAcciones.add(btnExportarPDF);
        panelAcciones.add(btnExportarExcel);
        panelTabla.add(panelAcciones, BorderLayout.SOUTH);

        // Agregar paneles
        panelContenido.add(panelFormulario, BorderLayout.WEST);
        panelContenido.add(panelTabla, BorderLayout.CENTER);
        panelMedio.add(panelContenido, BorderLayout.CENTER);
        
        new RolControlador(this);
    }
}