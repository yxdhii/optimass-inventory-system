package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaConfiguracion extends VentanaBase1 {
    private String codigoUsuario;
    public VentanaConfiguracion(String codigoUsuario, String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario);
        this.codigoUsuario = codigoUsuario; // AHORA SÍ LO GUARDAS

        setTitle("Configuración");
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
        
        // PANEL IZQUIERDO PARA EL BOTÓN (con espacio extra)

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10)); // 15px espacio a la derecha
        panelIzquierdo.setOpaque(false); // sin color de fondo
        panelIzquierdo.add(btnRegresar);
        // === PANEL CENTRAL CON ICONO + TEXTO ===
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/configuracion.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Configuración");
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
        
        // CONTENIDO DEL MÓDULO
        JPanel panelContenido = new JPanel(new GridLayout(3, 1, 15, 15));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        panelContenido.setBackground(Color.WHITE);

        // Botón 1: Cambiar contraseña
        JButton btnCambiarContrasena = new JButton(" Cambiar Contraseña");
        btnCambiarContrasena.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCambiarContrasena.setBackground(new Color(200, 230, 250));
        btnCambiarContrasena.setFocusPainted(false);
        btnCambiarContrasena.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botón 2: Configuración de alertas
        JButton btnConfigAlertas = new JButton(" Configuración de Alertas");
        btnConfigAlertas.setFont(new Font("Arial", Font.PLAIN, 18));
        btnConfigAlertas.setBackground(new Color(220, 250, 220));
        btnConfigAlertas.setFocusPainted(false);
        btnConfigAlertas.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botón 3: Gestión de roles y permisos
        JButton btnRoles = new JButton("Roles");
        btnRoles.setFont(new Font("Arial", Font.PLAIN, 18));
        btnRoles.setBackground(new Color(250, 230, 200));
        btnRoles.setFocusPainted(false);
        btnRoles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Eventos

       btnCambiarContrasena.addActionListener(e -> {
    
           new VentanaContraseña(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
    
           dispose();

       });
        
        btnConfigAlertas.addActionListener(e -> {
            new VentanaAlertas(codigoUsuario,nombreUsuario, rolUsuario).setVisible(true);
            dispose();
        });
        
        /*
         btnRoles.addActionListener(e -> {
            new VentanaRoles(nombreUsuario, rolUsuario).setVisible(true);
            dispose();
        });
        */

        // Agregar botones al panel
        panelContenido.add(btnCambiarContrasena);
        panelContenido.add(btnConfigAlertas);
        panelContenido.add(btnRoles);

        // Agregar al centro de la ventana
        panelMedio.add(panelContenido, BorderLayout.CENTER);
        
       if (rolUsuario.equalsIgnoreCase("vendedor")) {

    // Dejar el botón NORMAL (sin desactivar)
    btnRoles.setEnabled(true);
    btnRoles.setBackground(new Color(250, 230, 200));

    // Bloquear el acceso SOLO en el evento
    btnRoles.addActionListener(e ->
        JOptionPane.showMessageDialog(this,
            "Solo personal autorizado",
            "Acceso Denegado",
            JOptionPane.WARNING_MESSAGE
        )
    );

} else {
    // SI NO ES EMPLEADO (Administrador o Supervisor) → abrir la ventana normal
    btnRoles.addActionListener(e -> {
        new VentanaRoles(nombreUsuario, rolUsuario).setVisible(true);
        dispose();
    });
}
    }
    
    
}