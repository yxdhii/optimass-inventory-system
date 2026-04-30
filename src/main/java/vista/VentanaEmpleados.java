package vista;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.pdf.PdfWriter;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
import util.Correo;
import controlador.EmpleadoControlador;
import java.sql.*; //
import dao.RolDAO;  // importa el DAO de roles
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;



public class VentanaEmpleados extends VentanaBase1 {
    
    private String codigoUsuario;
    
    private JTextField txtCodigo;
    private JTextField txtNombre;
    
    private JTextField txtTelefono;
    private JTextField txtPuesto; // este es CORREO
    
    private JTextField txtSalario;
    private JTextField txtFecha;
    public JComboBox<String> cbCargo;
    private EmpleadoControlador empleadoControlador;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloEmpleados;
    private JScrollPane scrollTabla;

    public VentanaEmpleados(String codigoUsuario,String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario); // llama al constructor de VentanaBase1
        this.codigoUsuario = codigoUsuario; 
        setTitle("Empleados");
        Connection conexion = new conexion.ConexionBD().getConnection();
        empleadoControlador = new EmpleadoControlador(conexion);
        cbCargo = new JComboBox<>();
        
        // Cargar roles desde la base de datos

        dao.RolDAO rolDAO = new dao.RolDAO(); 
        cbCargo.removeAllItems();
        for (Object[] rol : rolDAO.listarRoles()) {    
            cbCargo.addItem((String) rol[1]);  
        }
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
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Empleado-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Empleados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);
        
        // Agregar al sub-encabezado
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);   // a la izquierda
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER); // en el centro
        // Agregar el sub-encabezado arriba
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH); 
        
        
        //contenido
        
        

        JPanel panelContenido = new JPanel(new BorderLayout(20, 0));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        // FORMULARIO IZQUIERDA

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(235, 245, 255));
        panelFormulario.setBorder(BorderFactory.createLineBorder(new Color(150, 170, 200), 2, true));
        panelFormulario.setPreferredSize(new Dimension(450, 0));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // TÍTULO FORMULARIO

        JLabel lblTituloFormulario = new JLabel("Registro de Empleados");
        lblTituloFormulario.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloFormulario.setHorizontalAlignment(JLabel.CENTER);
        lblTituloFormulario.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; gbc.insets = new Insets(15, 8, 15, 8);
        panelFormulario.add(lblTituloFormulario, gbc);
        gbc.gridwidth = 1; gbc.insets = new Insets(8, 8, 8, 8);

        // Campos de texto
        txtCodigo = new JTextField(12);
        txtCodigo.setEditable(false);
        txtNombre = new JTextField(12);
        

        
        //cbCargo.setEditable(true); // también modificable
        //cbCargo.setPreferredSize(new Dimension(150, 25));
        //cbCargo.setFont(new Font("Arial", Font.PLAIN, 13));
        
        txtPuesto = new JTextField(12);
           
        txtTelefono = new JTextField(12);
        txtSalario = new JTextField(12);

        // Bordes uniformes
        JTextField[] campos = {txtCodigo, txtNombre, txtPuesto, txtTelefono, txtSalario};       
        for (JTextField c : campos) {   
            c.setBorder(BorderFactory.createCompoundBorder(          
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),            
                    BorderFactory.createEmptyBorder(3, 5, 3, 5)    
            ));    
            c.setFont(new Font("Arial", Font.PLAIN, 13));
        }

        
        // Fila 1 para Código y Nombre
        gbc.gridy = 1;
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtCodigo, gbc);
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtNombre, gbc);



        // Fila 2 paraa Cargo y Puesto

        gbc.gridy = 2;
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(cbCargo, gbc);
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtPuesto, gbc);


       


        // Fila 3 para Teléfono y Salario base
        gbc.gridy = 3;
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtTelefono, gbc);
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Salario Base:"), gbc);
        gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtSalario, gbc);





        // Fila 4 para Fecha de registro
        gbc.gridy = 4;
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Fecha de registro:"), gbc);






        JPanel panelFecha = new JPanel(new BorderLayout(5, 0));
        panelFecha.setBackground(new Color(235, 245, 255));
        txtFecha = new JTextField("Seleccionar fecha");
        txtFecha.setEditable(false);
        txtFecha.setBorder(BorderFactory.createCompoundBorder(        
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),        
                BorderFactory.createEmptyBorder(3, 5, 3, 5)

        ));
        
        // Cargar la imagen 
        ImageIcon iconoCalendario = new ImageIcon(getClass().getResource("/imagenes/calendario.png"));
        // Escalar la imagen a 16x16 para q se vea bien:)
        Image img = iconoCalendario.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(img);

        // Crear el botón con el icono 
        JButton btnCalendario = new JButton(iconoEscalado);
        // quitar el fondo para que se vea "limpio"
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

        // Fila 5: Botones CRUD

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        panelBotones.setBackground(new Color(235, 245, 255));
        // Botón Agregar
        JButton btnAgregar = new JButton("Agregar");
        ImageIcon iconoAgregar = new ImageIcon(getClass().getResource("/imagenes/agregar.png"));
        Image imgAgregar = iconoAgregar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnAgregar.setIcon(new ImageIcon(imgAgregar));
        
 /*       
        btnAgregar.addActionListener(e -> {
    try {
        // Generar código automáticamente
        String codigo = empleadoControlador.generarCodigoEmpleado();
        txtCodigo.setText(codigo); // Mostrar en el campo

        // Obtener los demás datos del formulario
        String nombre = txtNombre.getText();
        String correo = txtPuesto.getText();
        String telefono = txtTelefono.getText();
        double salario = Double.parseDouble(txtSalario.getText());
        java.util.Date fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText());

        // Obtener el ID del rol seleccionado
        String rolSeleccionado = cbCargo.getSelectedItem().toString();
        int idRol = empleadoControlador.obtenerIdRolPorNombre(rolSeleccionado);

        // Crear objeto Empleado
        Empleado nuevo = new Empleado();
        nuevo.setCodigo(codigo);
        nuevo.setNombre(nombre);
        nuevo.setCorreo(correo);
        nuevo.setTelefono(telefono);
        nuevo.setSalarioBase(salario);
        nuevo.setFechaRegistro(fecha);
        nuevo.setIdRol(idRol);

        // Guardar en la base de datos
        if (empleadoControlador.agregarEmpleado(nuevo)) {
            JOptionPane.showMessageDialog(this, "Empleado agregado correctamente con código: " + codigo);
            listarEmpleadosEnTabla(); //  Recargar tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar empleado.");
        }

    
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        ex.printStackTrace();
    }
});
        
 */  
 
    btnAgregar.addActionListener(e -> {
        
    try {
        String codigo = empleadoControlador.generarCodigoEmpleado();
        txtCodigo.setText(codigo);

        String nombre = txtNombre.getText();
        String correo = txtPuesto.getText(); 
        String telefono = txtTelefono.getText();
        
        
        double salario = Double.parseDouble(txtSalario.getText());
        java.util.Date fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText());

        String rolSeleccionado = cbCargo.getSelectedItem().toString();
        int idRol = empleadoControlador.obtenerIdRolPorNombre(rolSeleccionado);

        // Crear empleado
        Empleado nuevo = new Empleado();
        nuevo.setCodigo(codigo);
        nuevo.setNombre(nombre);
        
        nuevo.setCorreo(correo);
        
        nuevo.setTelefono(telefono);
        nuevo.setSalarioBase(salario);
        nuevo.setFechaRegistro(fecha);
        nuevo.setIdRol(idRol);
        nuevo.setContrasena(codigo); // CONTRASEÑA = CÓDIGO

        if (empleadoControlador.agregarEmpleado(nuevo)) {

            // ENVIAR EMAIL AQUÍ
            Correo.enviar(
                nuevo.getCorreo(),
                "Bienvenido a Tiendas MASS",
                "Hola " + nuevo.getNombre()
                + "\n\nBienvenido(a) a Tiendas MASS.\n\n"
                + "Tus credenciales de acceso son:\n"
                + "✔ Código: " + nuevo.getCodigo() + "\n"
                + "✔ Contraseña: " + nuevo.getContrasena() + "\n\n"
                + "Por seguridad, cambie su contraseña al iniciar sesión."
            );

            JOptionPane.showMessageDialog(this, "Empleado agregado y correo enviado.");
            listarEmpleadosEnTabla();

        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar empleado.");
        }

    
     } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        ex.printStackTrace();
    
    }

    });
 

        // Botón Editar

        JButton btnEditar = new JButton("Editar");
        ImageIcon iconoEditar = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
        Image imgEditar = iconoEditar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnEditar.setIcon(new ImageIcon(imgEditar));
        btnEditar.addActionListener(e -> {
    
            try {
        
                if (txtCodigo.getText().isEmpty()) {
            
                    JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla.");
            
                    return;
        
                }

            // Obtener ID real desde el código
        
            int idEmpleado = empleadoControlador.obtenerIdEmpleadoPorCodigo(txtCodigo.getText());
        
            if (idEmpleado == 0) {
            
                JOptionPane.showMessageDialog(this, "No se pudo obtener el ID del empleado.");
            
                return;
        
            }

            // Obtener datos del formulario
        
            String nombre = txtNombre.getText();
            
            String correo = txtPuesto.getText();
            
            String telefono = txtTelefono.getText();
            
        
            double salario = Double.parseDouble(txtSalario.getText());
        
            java.util.Date fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText());

       
            String rolSeleccionado = cbCargo.getSelectedItem().toString();
        
            int idRol = empleadoControlador.obtenerIdRolPorNombre(rolSeleccionado);

            // Crear objeto empleado
        
            Empleado emp = new Empleado();
        
            emp.setId(idEmpleado);
            emp.setCodigo(txtCodigo.getText());
            emp.setNombre(nombre);
            
            emp.setCorreo(correo);
            emp.setTelefono(telefono);
        
            emp.setSalarioBase(salario);
        
            emp.setFechaRegistro(fecha);
       
            emp.setIdRol(idRol);

            // Editar en BD
        
            if (empleadoControlador.editarEmpleado(emp)) {
            
                JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
            
                listarEmpleadosEnTabla(); // refrescar tabla
        
            } else {
            
                JOptionPane.showMessageDialog(this, "Error al editar empleado.");
        
            }

    
            } catch (Exception ex) {
        
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    
            }

        });


        // Botón Eliminar

        JButton btnEliminar = new JButton("Eliminar");
        ImageIcon iconoEliminar = new ImageIcon(getClass().getResource("/imagenes/eliminar.png"));
        Image imgEliminar = iconoEliminar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnEliminar.setIcon(new ImageIcon(imgEliminar));
        panelBotones.add(btnAgregar); panelBotones.add(btnEditar); panelBotones.add(btnEliminar);
        btnEliminar.addActionListener(e -> {
    
            if (txtCodigo.getText().isEmpty()) {
        
                JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla.");
        
                return;
    
            }

    
            int confirm = JOptionPane.showConfirmDialog(
            
                    this,
            
                    "¿Seguro que quieres eliminar este empleado?",
            
                    "Confirmar eliminación",
           
                    JOptionPane.YES_NO_OPTION
    
            );

    
    
            if (confirm == JOptionPane.YES_OPTION) {
        
        
                int idEmpleado = empleadoControlador.obtenerIdEmpleadoPorCodigo(txtCodigo.getText());

        
                if (empleadoControlador.eliminarEmpleado(idEmpleado)) {
            
                    JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
            
                    listarEmpleadosEnTabla(); // refrescar tabla
        
                } else {
            
                    JOptionPane.showMessageDialog(this, "Error al eliminar empleado.");
        
                }
   
    
            }

       
        });

        // --- Espaciador arriba ---
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.weighty = 0.5;  // ocupa mitad del espacio sobrante arriba
        gbc.fill = GridBagConstraints.BOTH;
        panelFormulario.add(Box.createVerticalGlue(), gbc);

        // --- Botones CRUD en el centro ---

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.weighty = 0; 
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(panelBotones, gbc);

        // --- Espaciador abajo ---
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.weighty = 0.5;  // ocupa mitad del espacio sobrante abajo
        gbc.fill = GridBagConstraints.BOTH;
        panelFormulario.add(Box.createVerticalGlue(), gbc);

        // ****TABLA DERECHA ****

        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setBackground(Color.WHITE);
 
        JLabel lblTituloTabla = new JLabel("Listado de Empleados");
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
        panelCampoBusqueda.setPreferredSize(new Dimension(860, 28));
        panelCampoBusqueda.setMaximumSize(new Dimension(300, 28));
        panelCampoBusqueda.setBackground(Color.WHITE);
        
        JTextField txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        panelCampoBusqueda.add(iconoLupa, BorderLayout.WEST);
        panelCampoBusqueda.add(txtBuscar, BorderLayout.CENTER);
        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(Box.createHorizontalGlue());
        panelBusqueda.add(panelCampoBusqueda);
        panelBusqueda.add(Box.createHorizontalStrut(10));
        panelBusqueda.add(btnBuscar);
        
        
        // con esto puedo buscar y  que aparezca rapido
        txtBuscar.addKeyListener(new KeyAdapter() {
    
            @Override
    
            public void keyReleased(KeyEvent e) {
        
                String texto = txtBuscar.getText().trim();
        
                filtrarTabla(texto);
    
            }

        });


        JPanel panelSuperiorTabla = new JPanel(new BorderLayout());
        panelSuperiorTabla.add(lblTituloTabla, BorderLayout.NORTH);
        panelSuperiorTabla.add(panelBusqueda, BorderLayout.SOUTH);
        panelTabla.add(panelSuperiorTabla, BorderLayout.NORTH);

        // Tabla de empleados
        String[] columnas = {"Código", "Nombre", "Teléfono", "Cargo", "Fecha Registro"};
        Object[][] datos = {    
            {"EMP001", "Juan Pérez", "987654321", "Gerente", "01/09/2025"},    
            {"EMP002", "María López", "912345678", "Cajera", "15/09/2025"},    
            {"EMP003", "Carlos Ruiz", "987111222", "Vendedor", "20/09/2025"},
        };


        String[] columnasTabla = {"Código", "Nombre", "Teléfono", "Cargo", "Fecha Registro"};
        modeloEmpleados = new DefaultTableModel(columnasTabla, 0);
        tablaEmpleados = new JTable(modeloEmpleados);
        tablaEmpleados.setRowHeight(28);
        scrollTabla = new JScrollPane(tablaEmpleados);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        
        

        // listar
        listarEmpleadosEnTabla();
        // limpiar
        limpiarFormulario();



        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {   
            if (!e.getValueIsAdjusting()) {
                int fila = tablaEmpleados.getSelectedRow();
                if (fila != -1) {
             // Obtener el código desde la tabla
             String codigo = tablaEmpleados.getValueAt(fila, 0).toString();
             // Buscar empleado COMPLETO en la base de datos        
             Empleado emp = empleadoControlador.buscarEmpleadoPorCodigo(codigo);

                if (emp != null) {              
                 
                    txtCodigo.setText(emp.getCodigo());                            
                    txtNombre.setText(emp.getNombre());                            
                    txtTelefono.setText(emp.getTelefono());                              
                    txtPuesto.setText(emp.getCorreo());                                
                    txtSalario.setText(String.valueOf(emp.getSalarioBase()));
                    // Rol                 
                    String nombreRol = empleadoControlador.obtenerNombreRolPorId(emp.getIdRol());                                
                    cbCargo.setSelectedItem(nombreRol);
                    // Fecha                          
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    txtFecha.setText(sdf.format(emp.getFechaRegistro()));
                }

                }
            }
        });

        //este codigo limpiar los datos al hacer clic en la tabla

        tablaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int fila = tablaEmpleados.rowAtPoint(e.getPoint());       
                if (fila == -1) {          
                    limpiarFormulario();         
                    tablaEmpleados.clearSelection();
                }
            }
        });

        scrollTabla.getViewport().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaEmpleados.rowAtPoint(e.getPoint());
                if (fila == -1) {          
                    limpiarFormulario();           
                    tablaEmpleados.clearSelection();
                }
            }

        });

        panelTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override  
            public void mouseClicked(java.awt.event.MouseEvent e) {       
                limpiarFormulario();       
                tablaEmpleados.clearSelection();
            }
        });
        
        
        // Acciones

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Botón exportar pdf

        JButton btnExportarPDF = new JButton("Exportar PDF");

        try {   
            ImageIcon iconoPdf = new ImageIcon(getClass().getResource("/imagenes/pdf.png"));   
            Image imgPdf = iconoPdf.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);   
            btnExportarPDF.setIcon(new ImageIcon(imgPdf));
        } catch (Exception e) {}


        JButton btnExportarExcel = new JButton("Exportar Excel");

        try {
    
            ImageIcon iconoExcel = new ImageIcon(getClass().getResource("/imagenes/Excel.png"));
    
            Image imgExcel = iconoExcel.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    
            btnExportarExcel.setIcon(new ImageIcon(imgExcel));

        } catch (Exception e) {}
        
        btnExportarExcel.addActionListener(e -> {
    
            exportarExcel();

        });
        
        btnExportarPDF.addActionListener(e -> {
    
            exportarPDF();

        });


        panelAcciones.add(btnExportarPDF);
        panelAcciones.add(btnExportarExcel);
        panelTabla.add(panelAcciones, BorderLayout.SOUTH);

        // Agrego todo al contenido
        panelContenido.add(panelFormulario, BorderLayout.WEST);
        panelContenido.add(panelTabla, BorderLayout.CENTER);
        panelMedio.add(panelContenido, BorderLayout.CENTER);
        
        //txtCodigo.setText(empleadoControlador.generarCodigoEmpleado());

   
    

    }
 
        private void listarEmpleadosEnTabla() {
    
        
            List<Empleado> lista = empleadoControlador.listarEmpleados();
        
            modeloEmpleados.setRowCount(0); // limpio mi tabla
        
            for (Empleado e : lista) {
            
                String rolNombre = empleadoControlador.obtenerNombreRolPorId(e.getIdRol());
            
                modeloEmpleados.addRow(new Object[]{
                
                    e.getCodigo(),
                
                    e.getNombre(),
                
                    e.getTelefono(),
                
                    rolNombre,
                
                    e.getFechaRegistro()
            
                });
        
            }
   
        }


        //cargo los roles en mi combobox
    
        private void cargarRolesEnComboBox() {
    
            RolDAO rolDAO = new RolDAO();
    
            cbCargo.removeAllItems();
    
            List<Object[]> roles = rolDAO.listarRoles();
    
    
            if (roles.isEmpty()) {
        
                cbCargo.addItem("Sin roles disponibles");
    
            } else {
        
                for (Object[] rol : roles) {
           
                    cbCargo.addItem(rol[1].toString());
        
                }
    
            }


        }
    
  
    
        private void filtrarTabla(String nombre) {
    
        
            try {
             // usare el modelo correcto
          
             modeloEmpleados.setRowCount(0);

          
             List<Empleado> lista = empleadoControlador.buscarEmpleadosPorNombre(nombre);

          
             SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

          
             for (Empleado emp : lista) {
              
                 String rol = empleadoControlador.obtenerNombreRolPorId(emp.getIdRol());       
              
                 String fecha = formatoFecha.format(emp.getFechaRegistro());          
              
                 Object[] fila = {                
                  
                     emp.getCodigo(),                
                  
                     emp.getNombre(),               
                  
                     emp.getTelefono(),                
                  
                     rol,             
                  
                     fecha
              
                 };
              
              
                 modeloEmpleados.addRow(fila);  
          
             }

        
            } catch (Exception e) {
            
                System.out.println("Error al filtrar tabla: " + e.getMessage());
        
            }

        }
  

    
        private void limpiarFormulario() {
    
        
            txtCodigo.setText("");
        
            txtNombre.setText("");
        
            txtTelefono.setText("");
       
            txtPuesto.setText("");
        
            txtSalario.setText("");
        
            cbCargo.setSelectedIndex(0);
        
            txtFecha.setText("Seleccionar fecha");
       
            tablaEmpleados.clearSelection(); // con esto limpio la selección de la tabla
    
        }
        
        
        //esto me sirve para exportar excel, es un metodo:)

        public void exportarExcel() {
    
            try {
        
                JFileChooser fileChooser = new JFileChooser();
        
                fileChooser.setDialogTitle("Guardar Excel");
        
                fileChooser.setSelectedFile(new java.io.File("empleados.xlsx"));

        
                if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            
                    return;
        
                }

        
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
        
                if (!ruta.endsWith(".xlsx")) ruta += ".xlsx";

        
                org.apache.poi.xssf.usermodel.XSSFWorkbook libro = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
        
                org.apache.poi.ss.usermodel.Sheet hoja = libro.createSheet("Empleados");

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
                // Cargar imagen desde resources
            
                java.io.InputStream is = getClass().getResourceAsStream("/imagenes/MassLogo.png");
                
                if (is == null) {

                    System.out.println("No se pudo encontrar la imagen del logo.");
                } else {
        
                    byte[] bytes = is.readAllBytes();

                    int idx = libro.addPicture(bytes, org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG);

                    is.close();

                // Crear el dibujo en la hoja
        
        
                org.apache.poi.ss.usermodel.Drawing<?> drawing = hoja.createDrawingPatriarch();
                org.apache.poi.ss.usermodel.ClientAnchor anchor = libro.getCreationHelper().createClientAnchor();
        
                // Definir posición inicial y final del logo
        
                anchor.setCol1(0); // columna inicio      
                anchor.setRow1(0); // fila inicio
                anchor.setCol2(3); // columna final (ocupando 3 columnas) 
                anchor.setRow2(4); // fila final (ocupando 4 filas)

                // Insertar y escalar la imagen
        
                org.apache.poi.ss.usermodel.Picture pict = drawing.createPicture(anchor, idx);
        
                pict.resize(0.6); // escala la imagen al 60% de la zona definida

                // Ajustar altura de la primera fila para que se vea completa
        
                hoja.getRow(0).setHeightInPoints(50);
    
        
                }


                
        
                } catch (Exception ex) {
    
            
                    System.out.println("No se pudo cargar logo: " + ex.getMessage());

        
                }

                // --- Título centrado ---
        
                org.apache.poi.ss.usermodel.Row filaTitulo = hoja.createRow(3);
        
        
                org.apache.poi.ss.usermodel.Cell celdaTitulo = filaTitulo.createCell(0);
        
        
                celdaTitulo.setCellValue("Listado de Empleados");
        
                celdaTitulo.setCellStyle(estiloTitulo);
        
                hoja.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 0, modeloEmpleados.getColumnCount() - 1));

                // --- Encabezados de tabla ---
        
                org.apache.poi.ss.usermodel.Row filaEncabezado = hoja.createRow(5);
        
        
                for (int i = 0; i < modeloEmpleados.getColumnCount(); i++) {
            
            
                    org.apache.poi.ss.usermodel.Cell celda = filaEncabezado.createCell(i);
            
           
                    celda.setCellValue(modeloEmpleados.getColumnName(i));
           
            
                    celda.setCellStyle(estiloEncabezado);
        
                }

        
                // --- Datos ---
        
        
                for (int i = 0; i < modeloEmpleados.getRowCount(); i++) {
           
            
                    org.apache.poi.ss.usermodel.Row fila = hoja.createRow(i + 6);
            
                    for (int j = 0; j < modeloEmpleados.getColumnCount(); j++) {
                
                
                        org.apache.poi.ss.usermodel.Cell celda = fila.createCell(j);
                
                
                        Object valor = modeloEmpleados.getValueAt(i, j);
                
                        celda.setCellValue(valor == null ? "" : valor.toString());
                
                        if (i % 2 == 0) celda.setCellStyle(estiloFilaGris); // filas alternadas
           
                    }
        
                }

        // Pie de página con fecha
        
        
        //esto hace que aparezca el nombre del empleado que realiza la exportación:)
        String fechaHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        .format(new java.util.Date());
        // Fila después de la última fila de datos
        int filaInfoIndex = modeloEmpleados.getRowCount() + 7;
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
        0, modeloEmpleados.getColumnCount() - 1
        ));

                // Ajustar ancho de columnas   
                for (int i = 0; i < modeloEmpleados.getColumnCount(); i++) hoja.autoSizeColumn(i);
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

       
       //esto me sirve para exportar pdf, es un metodo:)

       public void exportarPDF() {
    
           try {
        
               JFileChooser fileChooser = new JFileChooser();       
               fileChooser.setDialogTitle("Guardar PDF");        
               fileChooser.setSelectedFile(new java.io.File("empleados.pdf"));        
        
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
                
                      com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
        
              com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph("Listado de Empleados\n\n", fuenteTitulo);
        
              titulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        
              documento.add(titulo);

             // --- TABLA ---
        
             com.itextpdf.text.pdf.PdfPTable tablaPDF = new com.itextpdf.text.pdf.PdfPTable(modeloEmpleados.getColumnCount());
        
             tablaPDF.setWidthPercentage(100);

             // Encabezados
        
             com.itextpdf.text.BaseColor colorEncabezado = new com.itextpdf.text.BaseColor(0, 102, 204); // azul
        
             com.itextpdf.text.Font fuenteEncabezado = new com.itextpdf.text.Font(
                
                     com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD, com.itextpdf.text.BaseColor.WHITE);

       
             for (int i = 0; i < modeloEmpleados.getColumnCount(); i++) {
            
                 com.itextpdf.text.pdf.PdfPCell celda = new com.itextpdf.text.pdf.PdfPCell(
                    
                         new com.itextpdf.text.Phrase(modeloEmpleados.getColumnName(i), fuenteEncabezado));
            
                 celda.setBackgroundColor(colorEncabezado);
            
                 celda.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            
                 celda.setPadding(5);
            
                 tablaPDF.addCell(celda);
        
             }

             // Datos fila por fila con color alterno
        
             com.itextpdf.text.BaseColor colorFila = new com.itextpdf.text.BaseColor(230, 230, 230); // gris claro
        
             
             for (int i = 0; i < modeloEmpleados.getRowCount(); i++) {
            
                 for (int j = 0; j < modeloEmpleados.getColumnCount(); j++) {
                
                     com.itextpdf.text.pdf.PdfPCell celda = new com.itextpdf.text.pdf.PdfPCell(
                        
                             new com.itextpdf.text.Phrase(modeloEmpleados.getValueAt(i, j).toString()));
                
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