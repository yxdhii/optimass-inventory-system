
package vista;
import dao.UsuarioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
//import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    public Login() {
        initComponents();
                setTitle("OptiMass - Tiendas Mass");
        this.setSize(1500, 860);
        this.setLocationRelativeTo(null); // Centrar
        this.setLayout(new java.awt.GridLayout(1, 2));
        establecerIcono();
        
        // Panel amarillo (izquierda)
        javax.swing.JPanel panelIzq = new javax.swing.JPanel();
        panelIzq.setBackground(new java.awt.Color(255,204,0));
        
        // Panel blanco (derecha)
        javax.swing.JPanel panelDer = new javax.swing.JPanel();
        panelDer.setBackground(new java.awt.Color(255,255,255));
        
        // Agregarlos a la ventana
        this.add(panelIzq);
        this.add(panelDer);
        
        panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
        
        // Imagen del logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/imagenes/MassOpti.png"));
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Imagen de la foto circular
        ImageIcon fotoIcon = new ImageIcon(getClass().getResource("/imagenes/EquipoMass.png"));
        JLabel lblFoto = new JLabel();
        lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Espaciadores y agregar al panel
        panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
        panelIzq.add(Box.createVerticalStrut(30));
        panelIzq.add(lblLogo);
        panelIzq.add(Box.createVerticalStrut(50));
        panelIzq.add(lblFoto);
        
        panelIzq.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int anchoPanel = panelIzq.getWidth();
                int altoPanel = panelIzq.getHeight();
                int anchoLogo = (int) (anchoPanel * 0.7);
                int altoLogo = (int) (altoPanel * 0.3);
                Image imgLogo = logoIcon.getImage().getScaledInstance(anchoLogo, altoLogo, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imgLogo));
                int anchoFoto = (int) (anchoPanel * 0.6);
                int altoFoto = (int) (anchoPanel * 0.6);
                Image imgFoto = fotoIcon.getImage().getScaledInstance(anchoFoto, altoFoto, Image.SCALE_SMOOTH);
                lblFoto.setIcon(new ImageIcon(imgFoto));
            }
        });
        
        
        panelDer.setLayout(null); // ESTA LÍNEA ES importante:)

        // Logo del mass
        ImageIcon logoDerechoIcon = new ImageIcon(getClass().getResource("/imagenes/MassLogo.png"));
        JLabel lblLogoDerecho = new JLabel();

        // Defino el tamaño que quiero para la imagen
        int anchoLogo = 350; // más ancho que antes
        int altoLogo = 100;  // igual que antes
        Image imgDerecho = logoDerechoIcon.getImage().getScaledInstance(anchoLogo, altoLogo, Image.SCALE_SMOOTH);
        lblLogoDerecho.setIcon(new ImageIcon(imgDerecho));

        // Ajustar el JLabel exactamente al tamaño de la imagen
        lblLogoDerecho.setBounds(130, 50, anchoLogo, altoLogo);

        // Agregar al panel derecho
        panelDer.add(lblLogoDerecho);
        // Título principal
        JLabel lblTitulo = new JLabel("La nueva experiencia digital de gestión");
        lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(60,63,65)); 
        lblTitulo.setBounds(130, 200, 400, 35);
        panelDer.add(lblTitulo);
        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Optimiza, controla y crece");
        lblSubtitulo.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setBounds(130, 240, 410, 26);
        panelDer.add(lblSubtitulo);
        // Texto de ingreso
        JLabel lblIngreso = new JLabel("<html>Ingresa tus datos para <b>iniciar sesión</b>.</html>");
        lblIngreso.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 16)); // Fuente general
        lblIngreso.setForeground(Color.BLACK);
        lblIngreso.setBounds(130, 300, 410, 26);
        panelDer.add(lblIngreso);
        
        // Label Código Mass
        JLabel lblCodigoMass = new JLabel("Código Mass");
        lblCodigoMass.setFont(new Font("MS PGothic", Font.BOLD, 16));
        lblCodigoMass.setForeground(new Color(60,63,65)); 
        lblCodigoMass.setBounds(130, 350, 100, 20);
        panelDer.add(lblCodigoMass);
        
        // Panel para campo de usuario con icono
        JPanel panelUsuario = new JPanel();
        panelUsuario.setLayout(new BorderLayout());
        panelUsuario.setBounds(130, 380, 494, 35);
        panelUsuario.setBackground(Color.WHITE);
        panelUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        // Icono de usuario
        JLabel iconoUsuario = new JLabel("👤");

        // Campo de texto usuario
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(null);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        panelUsuario.add(iconoUsuario, BorderLayout.EAST);
        panelUsuario.add(txtUsuario, BorderLayout.CENTER);
        panelDer.add(panelUsuario);
        
        // Label Contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(new Font("MS PGothic", Font.BOLD, 16));
        lblContrasena.setForeground(new Color(60,63,65)); 
        lblContrasena.setBounds(130, 440, 100, 20);
        panelDer.add(lblContrasena);
        // Panel para campo de contraseña con icono
        JPanel panelContrasena = new JPanel();
        panelContrasena.setLayout(new BorderLayout());
        panelContrasena.setBounds(130, 470, 494, 35);
        panelContrasena.setBackground(Color.WHITE);panelContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Campo de contraseña
        JPasswordField txtContrasena = new JPasswordField();
        txtContrasena.setBorder(null);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        // Icono de ojo para mostrar/ocultar contraseña
        JLabel iconoOjo = new JLabel("🙈"); // agregué emoji inicial 🙈 (contraseña oculta)
        iconoOjo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ---------- LO QUE SE AGREGA AQUÍ ----------
        final boolean[] mostrar = {false}; // variable para controlar estado
        iconoOjo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!mostrar[0]) {
                    txtContrasena.setEchoChar((char) 0); // mostrar contraseña
                    iconoOjo.setText("👁");               // cambiar emoji a ojo abierto
                    mostrar[0] = true;
                } else {
                    txtContrasena.setEchoChar('•');       // ocultar contraseña
                    iconoOjo.setText("🙈");               // cambiar emoji a ojo cerrado
                    mostrar[0] = false;
                }
            }
        });
        
        // ---------- FIN DE LA ADICIÓN ----------
        panelContrasena.add(txtContrasena, BorderLayout.CENTER);
        panelContrasena.add(iconoOjo, BorderLayout.EAST);
        panelDer.add(panelContrasena);
        
        // Link "Restablecer Contraseña"
        JLabel lblRestablecer = new JLabel("Restablecer Contraseña");
        lblRestablecer.setFont(new Font("MS PGothic", Font.BOLD, 16));
        lblRestablecer.setForeground(new Color(0,102,255)); // Azul
        lblRestablecer.setBounds(280, 570, 200, 20);
        lblRestablecer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelDer.add(lblRestablecer);
        // Cambiar color al pasar el mouse
        lblRestablecer.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRestablecer.setForeground(new Color(102, 153, 255)); // color más claro al pasar
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRestablecer.setForeground(new Color(51, 102, 204)); // color original al salir
            }
            
              @Override
              public void mouseClicked(java.awt.event.MouseEvent evt) {
            //  ABRIR LA VENTANA DE RESTABLECER CONTRASEÑA
            RestablecerContraseña rc = new RestablecerContraseña();
            rc.setVisible(true);
            //  CERRAR LA VENTANA ACTUAL
            SwingUtilities.getWindowAncestor(lblRestablecer).dispose();
    
              }
        });
        
        
        // Botón Iniciar Sesión
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBounds(220, 630, 300, 40);
        btnIniciarSesion.setBackground(new Color(51, 102, 204)); // Azul
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFont(new Font("MS PGothic", Font.BOLD, 14));
        btnIniciarSesion.setBorder(BorderFactory.createEmptyBorder());
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIniciarSesion.addActionListener(e -> {
    
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        
        // Validación de campos vacíos
    
        if (usuario.isEmpty() && contrasena.isEmpty()) {
        
            JOptionPane.showMessageDialog(Login.this,
            
                    "Por favor ingrese usuario y contraseña.",
            
                    "Campos vacíos",
            
                    JOptionPane.WARNING_MESSAGE);
        
            txtUsuario.requestFocus();
        
            return;
    
        }
    
        if (usuario.isEmpty()) {
        
            JOptionPane.showMessageDialog(Login.this,
            
                    "Por favor ingrese su Código Mass.",
            
                    "Campo vacío",
            
                    JOptionPane.WARNING_MESSAGE);
        
            txtUsuario.requestFocus();
        
            return;
    
        }
    
        if (contrasena.isEmpty()) {
        
            JOptionPane.showMessageDialog(Login.this,
            
                    "Por favor ingrese su contraseña.",
            
                    "Campo vacío",
            
                    JOptionPane.WARNING_MESSAGE);
        
            txtContrasena.requestFocus();
        
            return;
    
        }

        // Aquí deberías validar contra la base de datos
        // Ejemplo: si existe el usuario y coincide la contraseña
      
        String[] datos = UsuarioDAO.validarUsuario(usuario, contrasena);
        if (datos != null) {
            String nombre = datos[0];
            String rol = datos[1];
    
        // AHORA SÍ FUNCIONA PORQUE YA ACTUALIce EL CONSTRUCTOR DE MENU
    
        Menu menu = new Menu(usuario, nombre, rol);
        menu.setVisible(true);
        dispose();
        } else {
            JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos");
        }
        
        });

        // Efecto hover para el botón
        btnIniciarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnIniciarSesion.setBackground(new Color(41, 82, 164));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnIniciarSesion.setBackground(new Color(51, 102, 204));
            } 
        });
        
        panelDer.add(btnIniciarSesion);
        

        panelDer.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int anchoDisponible = panelDer.getWidth() - 150; // margen de 150 px
                lblTitulo.setBounds(lblTitulo.getX(), lblTitulo.getY(), anchoDisponible, lblTitulo.getHeight());
                lblSubtitulo.setBounds(lblSubtitulo.getX(), lblSubtitulo.getY(), anchoDisponible, lblSubtitulo.getHeight());
            }
        }); 
        
        
       
    }
    
    
    //icono en vez de java
    
    private void establecerIcono() {
        try {
            // Cargar la imagen del icono desde recursos
            Image icon = ImageIO.read(getClass().getResource("/imagenes/logoescritorio.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.err.println("No se pudo cargar el icono: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar el icono: " + e.getMessage());
        }
        
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
