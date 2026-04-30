
package vista;

import dao.UsuarioDAO;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class VerificarCodigo extends JFrame {
    
    private JTextField[] txtCodigo; // ← ahora es GLOBAL
    private String codigoCorrect;
    private String codigoUsuario;

    public VerificarCodigo(String codigoEnviado, String codigoUsuario) {
        this.codigoCorrect = codigoEnviado;
        this.codigoUsuario = codigoUsuario;
        establecerIcono();

        // ----------------------------- VENTANA -----------------------------
        setTitle("Verificar Código");
        setSize(527, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // ----------------------------- HEADER AMARILLO -----------------------------
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 204, 0));
        header.setBounds(0, 0, 527, 120);
        header.setLayout(null);
        add(header);

        // Logo centrado
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/MassRestablecer.png"));
        Image img = icon.getImage().getScaledInstance(198, 68, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(img));
        lblLogo.setBounds((527 - 198) / 2, 27, 198, 68);
        header.add(lblLogo);

        // ----------------------------- PANEL ROSADO (TÍTULO) -----------------------------
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(230, 230, 230));
        panelTitulo.setBounds(0, 120, 527, 60);
        panelTitulo.setLayout(null);
        add(panelTitulo);

        JLabel lblTitulo = new JLabel("Verificar Código", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(0, 10, 527, 40);
        panelTitulo.add(lblTitulo);

        // --------------------------- TEXTO SECUNDARIO ---------------------------
        JLabel lblInfo = new JLabel(
                "<html><center>Hemos enviado el código de 6 dígitos a su correo<br> electrónico. Ingrese a continuación.</center></html>",
                SwingConstants.CENTER
        );
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 15));
        lblInfo.setForeground(new Color(80, 80, 80));
        lblInfo.setBounds(0, 190, 527, 40);
        add(lblInfo);

        // ----------------------------- LABEL CORREO -----------------------------
        JLabel lblCorreo = new JLabel("Nuevo Código");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 16));
        lblCorreo.setBounds(80, 250, 250, 20);
        add(lblCorreo);

// ----------------------------- CÓDIGOS EN CUADRITOS -----------------------------
int cantidad = 6; // 6 dígitos
txtCodigo = new JTextField[cantidad];

// NUEVAS COORDENADAS — centrado y alineado con la interfaz
int totalAncho = (55 * cantidad) + (10 * (cantidad - 1)); 
int inicioX = (527 - totalAncho) / 2; // centrado dinámicamente
int ancho = 55;        
int espacio = 10;      
int posY = 285;        // SUBIDO para estar alineado con el texto

for (int i = 0; i < cantidad; i++) {

    JTextField cuadro = new JTextField();

    cuadro.setFont(new Font("Arial", Font.BOLD, 22));
    cuadro.setHorizontalAlignment(JTextField.CENTER);
    cuadro.setBounds(inicioX + (i * (ancho + espacio)), posY, ancho, 55);

    // borde moderno
    cuadro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
    ));
    cuadro.setBackground(Color.WHITE);

    // permitir solo un número
    cuadro.addKeyListener(new java.awt.event.KeyAdapter() {

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar()) || cuadro.getText().length() == 1) {
                e.consume();
            }
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {

            // avanzar automáticamente
            if (cuadro.getText().length() == 1) {
                for (int j = 0; j < cantidad - 1; j++) {
                    if (txtCodigo[j] == cuadro) {
                        txtCodigo[j + 1].requestFocus();
                        break;
                    }
                }
            }

            // retroceder con backspace
            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE &&
                cuadro.getText().isEmpty()) {

                for (int j = 1; j < cantidad; j++) {
                    if (txtCodigo[j] == cuadro) {
                        txtCodigo[j - 1].requestFocus();
                        break;
                    }
                }
            }
        }
    });

    txtCodigo[i] = cuadro;
    add(cuadro);
}

        // ----------------------------- BOTÓN REDONDEADO -----------------------------
        JButton btnEnviar = new JButton("Verificar Código") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(25, 0, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };

        btnEnviar.setBounds(150, 370, 230, 48);
        btnEnviar.setOpaque(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setBorder(BorderFactory.createEmptyBorder());
        btnEnviar.setFocusPainted(false);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 17));
        add(btnEnviar);

        // --------------------------- VOLVER AL LOGIN ---------------------------
        JLabel lblVolver = new JLabel("¿No recibió el código? Reenviar", SwingConstants.CENTER);
        lblVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        lblVolver.setForeground(new Color(50, 50, 200));
        lblVolver.setBounds(0, 430, 527, 25);
        add(lblVolver);
        
        // Eventos
        btnEnviar.addActionListener(e -> verificarCodigo());
        
        // Efecto hover + acción al hacer clic
        
        

        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
    
            @Override
    
            public void mouseEntered(java.awt.event.MouseEvent e) {
        
                lblVolver.setForeground(new Color(100, 100, 255)); // más claro al pasar
    
            }

    
            @Override
    
            public void mouseExited(java.awt.event.MouseEvent e) {
        
                lblVolver.setForeground(new Color(50, 50, 200)); // color original
    
            }

    
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

    
                try {
                 //  Generar nuevo código
                 String nuevoCodigo = String.valueOf(100000 + (int)(Math.random() * 900000));

                 //  Guardarlo
        
                 codigoCorrect = nuevoCodigo;

                 //  Obtener correo REAL del usuario
        
                 UsuarioDAO dao = new UsuarioDAO();
        
                 String correoReal = dao.obtenerCorreoPorCodigoUsuario(codigoUsuario);

       
                 if (correoReal == null) {
            
                     JOptionPane.showMessageDialog(null,
                
                             "No se encontró un correo asociado al usuario.",
                
                             "Error", JOptionPane.ERROR_MESSAGE);
            
                     return;
        
                 }

                 //  Enviar correo REAL
        
                 dao.enviarCorreoCodigo(correoReal, nuevoCodigo);

                 //  Avisar
        
                 JOptionPane.showMessageDialog(null,
                
                         "Se ha enviado un nuevo código a: " + correoReal,
                
                         "Código reenviado",
                
                         JOptionPane.INFORMATION_MESSAGE);

    
                } catch (Exception ex) {
        
                    JOptionPane.showMessageDialog(null,
                
                            "No se pudo reenviar el código. Intenta nuevamente.",
                
                            "Error", JOptionPane.ERROR_MESSAGE);
    
                }

            }

        
        });
    
    }

           public static void main(String[] args) {
        
               new VerificarCodigo("123456", "usuarioDemo").setVisible(true);
    
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
            
   
            private void verificarCodigo() {

    
                String codigo = "";

                // unir los 6 dígitos
    
                for (JTextField cuadro : txtCodigo) {
        
                    if (cuadro.getText().isEmpty()) {
            
                        JOptionPane.showMessageDialog(this,
               
                                "Debe ingresar los 6 dígitos.",
                
                                "Error", JOptionPane.ERROR_MESSAGE);
            
                        return;
        
                    }
        
                    codigo += cuadro.getText();  // concatenar
    
                }

    
                if (codigo.equals(codigoCorrect)) {
        
                    JOptionPane.showMessageDialog(this,
            
                            "Código correcto.",
           
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);

        
                    new EstablecerContraseña(codigoUsuario).setVisible(true);
        
                    dispose();

    
                } else {
        
                    JOptionPane.showMessageDialog(this,
            
                            "Código incorrecto. Intenta nuevamente.",
            
                            "Error", JOptionPane.ERROR_MESSAGE);
    
                }

            }
            
            

}
