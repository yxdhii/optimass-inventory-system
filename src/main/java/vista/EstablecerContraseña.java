
package vista;
import dao.UsuarioDAO;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class EstablecerContraseña extends JFrame {
    private String codigoUsuario;

public EstablecerContraseña(String codigoUsuario) {
    this.codigoUsuario = codigoUsuario; // esto es importante:)
    
        establecerIcono();

        
        setTitle("Establecer Nueva Contraseña");
        setSize(527, 729);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 204, 0)); //amarillo para el MASS:)
        header.setBounds(0, 0, 527, 130);
        header.setLayout(null);
        add(header);

        // Aqui pongo el logo del mass
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/MassRestablecer.png"));
        Image img = icon.getImage().getScaledInstance(198, 68, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(img));
        lblLogo.setBounds((527 - 198) / 2, 35, 198, 68);
        header.add(lblLogo);

       
        JPanel franja = new JPanel();
        franja.setBackground(new Color(230, 230, 230)); 
        franja.setBounds(0, 130, 527, 60);
        franja.setLayout(null);
        add(franja);

        JLabel lblTitulo = new JLabel("Establecer Nueva Contraseña", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(0, 10, 527, 40);
        franja.add(lblTitulo);

        // textito 
        JLabel lblInfo = new JLabel(
                "Ingresa y confirma tu nueva contraseña segura",
                SwingConstants.CENTER
        );
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 15));
        lblInfo.setForeground(new Color(90, 90, 90));
        lblInfo.setBounds(0, 200, 527, 25);
        add(lblInfo);

        // Color del fondo
        Color campoFondo = Color.WHITE;
        Color bordeColor = new Color(210, 210, 210);

        // nueva contraseña
        JLabel lblNueva = new JLabel("Nueva Contraseña");
        lblNueva.setFont(new Font("Arial", Font.BOLD, 16));
        lblNueva.setBounds(80, 250, 300, 20);
        add(lblNueva);

        JPanel panelNueva = new JPanel(new BorderLayout());
        panelNueva.setBounds(80, 280, 360, 45);
        panelNueva.setBackground(campoFondo);
        panelNueva.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bordeColor, 1),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10) // mi pa
                )
        );
        add(panelNueva);

        JPasswordField txtNueva = new JPasswordField();
        txtNueva.setBorder(null);
        txtNueva.setOpaque(false);
        txtNueva.setMargin(new Insets(0, 5, 0, 5)); 
        txtNueva.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNueva.setEchoChar('•');
        panelNueva.add(txtNueva, BorderLayout.CENTER);

        JLabel ojoNueva = new JLabel("🙈");
        ojoNueva.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        ojoNueva.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelNueva.add(ojoNueva, BorderLayout.EAST);

        final boolean[] mostrarNueva = {false};
        ojoNueva.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarNueva[0] = !mostrarNueva[0];
                txtNueva.setEchoChar(mostrarNueva[0] ? (char) 0 : '•');
                ojoNueva.setText(mostrarNueva[0] ? "👁" : "🙈");
            }
        });

        //confirmar contraseña
        JLabel lblConfirmar = new JLabel("Confirmar Contraseña");
        lblConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
        lblConfirmar.setBounds(80, 340, 300, 20);
        add(lblConfirmar);

        JPanel panelConfirmar = new JPanel(new BorderLayout());
        panelConfirmar.setBounds(80, 370, 360, 45);
        panelConfirmar.setBackground(campoFondo);
        panelConfirmar.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bordeColor, 1),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );
        add(panelConfirmar);

        JPasswordField txtConfirmar = new JPasswordField();
        txtConfirmar.setOpaque(false);
        txtConfirmar.setBorder(null);
        txtConfirmar.setMargin(new Insets(0, 5, 0, 5)); 
        txtConfirmar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtConfirmar.setEchoChar('•');
        panelConfirmar.add(txtConfirmar, BorderLayout.CENTER);

        JLabel ojoConfirmar = new JLabel("🙈");
        ojoConfirmar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        ojoConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelConfirmar.add(ojoConfirmar, BorderLayout.EAST);

        final boolean[] mostrarConfirmar = {false};
        ojoConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarConfirmar[0] = !mostrarConfirmar[0];
                txtConfirmar.setEchoChar(mostrarConfirmar[0] ? (char) 0 : '•');
                ojoConfirmar.setText(mostrarConfirmar[0] ? "👁" : "🙈");
            }
        });

        // boton guardar amarillo
        JButton btnGuardar = new JButton("Guardar Contraseña") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 204, 0));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };

        btnGuardar.setBounds(150, 450, 230, 45);
        btnGuardar.setOpaque(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder());
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        add(btnGuardar);
        
        
        btnGuardar.addActionListener(e -> {
    
            String nueva = new String(txtNueva.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());
            
        // para las validaciones
    
        
        if (nueva.isEmpty() || confirmar.isEmpty()) {      
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);       
            return;
    
        }

    
        if (!nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);  
            return;
    
        }

        // Llamar al DAO usuario
    
        UsuarioDAO dao = new UsuarioDAO();
    
        boolean actualizado = dao.actualizarPasswordPorCodigo(codigoUsuario, nueva);  
          
        if (actualizado) {       
            JOptionPane.showMessageDialog(this,                
                    "Contraseña actualizada correctamente.",                
                    "Éxito",             
                    JOptionPane.INFORMATION_MESSAGE);
        // Abrir login      
        new Login().setVisible(true); 
        dispose(); // Cerrar ventana actual

    
        } else {
        
            JOptionPane.showMessageDialog(this, 
                
                    "No se pudo actualizar la contraseña.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        });

        // volver al inicio de sesion

        JLabel lblVolver = new JLabel("Volver al inicio de Sesión", SwingConstants.CENTER);
        lblVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        lblVolver.setForeground(new Color(0, 102, 200));
        lblVolver.setBounds(0, 510, 527, 25);
        lblVolver.setCursor(new Cursor(Cursor.HAND_CURSOR)); // hacer clickeable
        add(lblVolver);

        // Hover + acción clic

        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
    
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblVolver.setForeground(new Color(70, 140, 230)); // más claro al pasar
            }

    
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {      
                lblVolver.setForeground(new Color(0, 102, 200)); // color original  
            }

    
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            //  ABRIR LOGIN     
            Login login = new Login();
            login.setVisible(true);

        //  Cerrar ventana actual
        SwingUtilities.getWindowAncestor(lblVolver).dispose();
            }
        });
        

        // pie azul
        
        JPanel fondoAzul = new JPanel();
        fondoAzul.setBackground(new Color(30, 0, 255));
        fondoAzul.setBounds(0, 600, 527, 200);
        add(fondoAzul);
    
}

        public static void main(String[] args) {
        
            new EstablecerContraseña("1234").setVisible(true);
        
       
    
        }
    
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
}