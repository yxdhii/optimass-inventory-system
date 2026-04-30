package vista;
import javax.swing.Timer; 
import util.AlertaAutomatica; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends VentanaBase1 {
    private String codigoUsuario;
    //declarar variables
    private JButton btnProductos;
    private JButton btnCategorias;
    private JButton btnProveedores;
    private JButton btnInventario;
    private JButton btnVentas;
    private JButton btnCompras;
    private JButton btnEmpleados;
    private JButton btnReportes;
    private JButton btnConfiguracion;
    private JButton btnInformacion;
    private Timer timerAlertas; //alerta es la variable


    //esto cambié
    public Menu(String codigoUsuario, String nombreUsuario, String rolUsuario) {
    super(nombreUsuario, rolUsuario);
    this.codigoUsuario = codigoUsuario;  //esto guarda el codigo
    
    //  ALERTAS AUTOMÁTICAS: Una al inicio + cada 5 minutos
        iniciarAlertasAutomaticas();
    
    // DEBUG TEMPORAL
    System.out.println("****************************************");
    System.out.println("MENU - Constructor");
    System.out.println("Código: [" + codigoUsuario + "]");
    System.out.println("Nombre: " + nombreUsuario);
    System.out.println("Rol: " + rolUsuario);
    System.out.println("*****************************************");
        
       
         
        setTitle("Menú Principal");

        // sub encabezados
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230));
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50));

      

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10));
        panelIzquierdo.setOpaque(false);
        

        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/menu.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Bienvenido al menú principal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);

        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER);

        panelMedio.setLayout(new BorderLayout());
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);

        //  Panel de botones
        JPanel contenedorPrincipal = new JPanel(new GridBagLayout());
        contenedorPrincipal.setBackground(Color.WHITE);

        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Espacio entre botones
/*
        JButton[] botones = new JButton[]{
                crearBotonModulo("src/main/resources/imagenes/Producto-icon.png", "Productos"),
                crearBotonModulo("src/main/resources/imagenes/categorias.png", "Categorías"),
                crearBotonModulo("src/main/resources/imagenes/Proveedores-icon.png", "Proveedores"),
                crearBotonModulo("src/main/resources/imagenes/Inventarios-icon.png", "Inventario"),
                crearBotonModulo("src/main/resources/imagenes/Ventas-icon.png", "Ventas"),
                crearBotonModulo("src/main/resources/imagenes/Compras-icon.png", "Compras"),
                crearBotonModulo("src/main/resources/imagenes/Empleado-icon.png", "Empleados"),
                crearBotonModulo("src/main/resources/imagenes/Reportes-icon.png", "Reportes"),
                crearBotonModulo("src/main/resources/imagenes/configuracion.png", "Configuración"),
                crearBotonModulo("src/main/resources/imagenes/Informacion-icon.png", "Información")
        };

*/

btnProductos      = crearBotonModulo("/imagenes/Producto-icon.png", "Productos");
btnCategorias     = crearBotonModulo("/imagenes/categorias.png", "Categorías");
btnProveedores    = crearBotonModulo("/imagenes/Proveedores-icon.png", "Proveedores");
btnInventario     = crearBotonModulo("/imagenes/Inventarios-icon.png", "Inventario");
btnVentas         = crearBotonModulo("/imagenes/Ventas-icon.png", "Ventas");
btnCompras        = crearBotonModulo("/imagenes/Compras-icon.png", "Compras");
btnEmpleados      = crearBotonModulo("/imagenes/Empleado-icon.png", "Empleados");
btnReportes       = crearBotonModulo("/imagenes/Reportes-icon.png", "Reportes");
btnConfiguracion  = crearBotonModulo("/imagenes/configuracion.png", "Configuración");
btnInformacion    = crearBotonModulo("/imagenes/Informacion-icon.png", "Información");

aplicarPermisos(rolUsuario);

JButton[] botones = new JButton[]{
        btnProductos, btnCategorias, btnProveedores, btnInventario, btnVentas,
        btnCompras, btnEmpleados, btnReportes, btnConfiguracion, btnInformacion
};

        String[] modulos = new String[]{
                "Productos", "Categorías", "Proveedores", "Inventario", "Ventas",
                "Compras", "Empleados", "Reportes", "Configuración", "Información"
        };

        // Configurar eventos y agregar botones
        for (int i = 0; i < botones.length; i++) {
            configurarEventos(botones[i], modulos[i]);
            gbc.gridx = i % 5; // columna
            gbc.gridy = i / 5; // fila
            panelBotones.add(botones[i], gbc);
        }

        contenedorPrincipal.add(panelBotones);
        panelMedio.add(contenedorPrincipal, BorderLayout.CENTER);
    }

    private JButton crearBotonModulo(String rutaImagen, String texto) {
        JButton boton = new JButton();
        boton.setLayout(new BoxLayout(boton, BoxLayout.Y_AXIS));
        boton.setPreferredSize(new Dimension(257, 214)); // tamaño fijo
        boton.setBackground(new Color(204, 204, 204));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblImagen;
        try {
            //ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblImagen = new JLabel(new ImageIcon(imagenEscalada));
        } catch (Exception e) {
            lblImagen = new JLabel("📁");
            lblImagen.setFont(new Font("Arial", Font.PLAIN, 32));
        }
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTexto.setForeground(new Color(50, 50, 50));
        lblTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTexto.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        boton.add(Box.createVerticalGlue());
        boton.add(lblImagen);
        boton.add(lblTexto);
        boton.add(Box.createVerticalGlue());

        return boton;
    }
/*esto valee sirve
    private void configurarEventos(JButton boton, String modulo) {
        boton.addActionListener(e -> {
            System.out.println("Abriendo módulo: " + modulo);
            switch (modulo) {
            case "Productos" -> new VentanaProducto(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Categorías" -> new VentanaCategorias(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Proveedores" -> new VentanaProveedores(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Inventario" -> new VentanaInventario(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Ventas" -> new VentanaVentas(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Compras" -> new VentanaCompras(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Empleados" -> new VentanaEmpleados(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Reportes" -> new VentanaReportes(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Configuración" -> new VentanaConfiguracion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Información" -> new VentanaInformacion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            default -> JOptionPane.showMessageDialog(Menu.this, "Módulo: " + modulo + " - En desarrollo");
            }
            dispose();
        });
    }
*/
    //esto es testeo
    private void configurarEventos(JButton boton, String modulo) {
    boton.addActionListener(e -> {

        // Verificar permisos antes de abrir
        if (!rolTienePermiso(modulo, rolUsuario)) {
            mostrarAccesoDenegado();
            return;
        }

        // Abrir módulos
        System.out.println("Abriendo módulo: " + modulo);

        switch (modulo) {
            case "Productos" -> new VentanaProducto(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Categorías" -> new VentanaCategorias(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Proveedores" -> new VentanaProveedores(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Inventario" -> new VentanaInventario(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Ventas" -> new VentanaVentas(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Compras" -> new VentanaCompras(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Empleados" -> new VentanaEmpleados(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Reportes" -> new VentanaReportes(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Configuración" -> new VentanaConfiguracion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            case "Información" -> new VentanaInformacion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
        }

        dispose();
    });
}
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Menu ventana = new Menu("u2221","Yadhira Saavedra", "Administrador");
            ventana.setVisible(true);
            ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
    
    
    //metodo aplicar permisos
private void aplicarPermisos(String rol) {
    // No desactivamos botones, solo guardamos el rol
    this.rolUsuario = rol.toLowerCase();
}
    
    public void mostrarAccesoDenegado() {
    JOptionPane.showMessageDialog(this,
        "Solo personal autorizado",
        "Acceso Denegado",
        JOptionPane.WARNING_MESSAGE
    );
}
    
    
private boolean rolTienePermiso(String modulo, String rol) {

    rol = rol.toLowerCase();

    // Acceso total
    if (rol.equals("administrador") || rol.equals("supervisor")) {
        return true;
    }

    // Permisos para empleado
    if (rol.equals("vendedor")) {

        switch (modulo) {

            case "Productos":
            //case "Categorías":
            //case "Proveedores":
            case "Ventas":
            case "Información":
            case "Configuración":
                return true;

            default:
                return false; // bloqueado
        }
    }

    return false;
    
    
}
    

/*
private boolean permisoSubmoduloConfiguracion(String submodulo, String rol) {

    rol = rol.toLowerCase();

    // administrador y supervisor = acceso total
    if (rol.equals("administrador") || rol.equals("supervisor")) {
        return true;
    }

    // restricciones para vendedor
    if (rol.equals("vendedor")) {

        switch (submodulo) {

            case "Cambiar Contraseña":
            case "Configurar Alertas":
                return true;

            case "Registrar Roles":
                return false; // ❌ acceso denegado
        }
    }

    return false;
}

*/
    
    private void iniciarAlertasAutomaticas() {
        // Alerta inicial después de 3 segundos
        Timer timerInicial = new Timer(3000, e -> {
            AlertaAutomatica.verificarYMostrarAlertas(this, 10);
        });
        timerInicial.setRepeats(false);
        timerInicial.start();
        
        // Verificar cada 5 minutos (300000 ms)
        timerAlertas = new Timer(300000, e -> {
            AlertaAutomatica.verificarYMostrarAlertas(this, 10);
        });
        timerAlertas.start();
    }
    
    
    @Override
    public void dispose() {
        if (timerAlertas != null) {
            timerAlertas.stop();
        }
        super.dispose();
    }
   
}
