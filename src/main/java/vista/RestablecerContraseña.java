package vista;

import dao.UsuarioDAO;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import util.Correo;

public class RestablecerContraseña extends JFrame {
    
        private JTextField txtCorreo;


    public RestablecerContraseña() {

        establecerIcono();

        // 
        setTitle("Restablecer Contraseña");
        setSize(527, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 204, 0));//amarillo
        header.setBounds(0, 0, 527, 120);
        header.setLayout(null);
        add(header);

        // mi logo del mass centrado
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/MassRestablecer.png"));
        Image img = icon.getImage().getScaledInstance(198, 68, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(img));
        lblLogo.setBounds((527 - 198) / 2, 27, 198, 68);
        header.add(lblLogo);

        // para el titulo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(230, 230, 230));
        panelTitulo.setBounds(0, 120, 527, 60);
        panelTitulo.setLayout(null);
        add(panelTitulo);

        JLabel lblTitulo = new JLabel("Restablecer Contraseña", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(0, 10, 527, 40);
        panelTitulo.add(lblTitulo);

        
        JLabel lblInfo = new JLabel(
                "<html><center>Ingresa tu correo electrónico asociado a tu cuenta<br>"
                + "para que puedan enviarte un código de verificación.</center></html>",
                SwingConstants.CENTER
        );
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 15));
        lblInfo.setForeground(new Color(80, 80, 80));
        lblInfo.setBounds(0, 190, 527, 40);
        add(lblInfo);

        
        JLabel lblCorreo = new JLabel("Correo Electrónico");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 16));
        lblCorreo.setBounds(80, 250, 250, 20);
        add(lblCorreo);

        
        JPanel panelCorreo = new JPanel(new BorderLayout());
        panelCorreo.setBounds(80, 280, 360, 45);
        panelCorreo.setBackground(Color.WHITE);
        panelCorreo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(panelCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 15));
        txtCorreo.setBorder(null);
        txtCorreo.setOpaque(false);
        txtCorreo.setMargin(new Insets(0, 5, 0, 5));
        panelCorreo.add(txtCorreo, BorderLayout.CENTER);

        
        JButton btnEnviar = new JButton("Enviar Código") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(25, 0, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        
        

        btnEnviar.setBounds(150, 380, 230, 48);
        btnEnviar.setOpaque(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setBorder(BorderFactory.createEmptyBorder());
        btnEnviar.setFocusPainted(false);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 17));
        add(btnEnviar);

        
        JLabel lblVolver = new JLabel("Volver al inicio de Sesión", SwingConstants.CENTER);
        lblVolver.setFont(new Font("Arial", Font.PLAIN, 15));
        lblVolver.setForeground(new Color(50, 50, 200));
        lblVolver.setBounds(0, 440, 527, 25);
        add(lblVolver);
        
        

        
        // eventos 
        
        btnEnviar.addActionListener(e -> enviarCodigo());
        
        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new Login().setVisible(true);
                dispose();
            }
        });
        
        
        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblVolver.setForeground(new Color(100, 100, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblVolver.setForeground(new Color(50, 50, 200));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Login login = new Login();
                login.setVisible(true);
                SwingUtilities.getWindowAncestor(lblVolver).dispose();
            }
        });
    }

    public static void main(String[] args) {
        new RestablecerContraseña().setVisible(true);
    }

    private void establecerIcono() {
        try {
            Image icon = ImageIO.read(getClass().getResource("/imagenes/logoescritorio.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.err.println("No se pudo cargar el icono: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar el icono: " + e.getMessage());
        }
    }
    
     private void enviarCodigo() {
        String correo = txtCorreo.getText().trim();

        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingresa tu correo.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si existe el correo
        String codigoUsuario = UsuarioDAO.obtenerCodigoPorCorreo(correo);
        
        if (codigoUsuario == null) {
            JOptionPane.showMessageDialog(this, 
                "No existe una cuenta con este correo.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generar código
        String codigo = Correo.generarCodigo();
        
        // Enviar correo
        if (Correo.enviarCodigo(correo, codigo)) {
            JOptionPane.showMessageDialog(this, 
                "Código enviado a tu correo.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir ventana de verificación
            new VerificarCodigo(codigo, codigoUsuario).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al enviar el correo.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}