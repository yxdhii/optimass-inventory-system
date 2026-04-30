package vista;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

public class VentanaBase1 extends JFrame {
    
    // Variables para el menú de usuario
    private boolean menuVisible = false;
    private JButton btnCerrarSesion;
    private JLabel lblFlecha; // mover la flecha a nivel de clase
    private ImageIcon flechaAbajo;
    private ImageIcon flechaArriba;

    
    // Paneles que se usarán en todas las ventanas
    protected JPanel panelEncabezado;
    protected JPanel panelPie;
    protected JPanel panelMedio;
    protected JLabel lblFecha;
    protected JLabel lblHora;
    protected String nombreUsuario;
    protected String rolUsuario;
    
    

    public VentanaBase1(String nombreUsuario, String rolUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.rolUsuario = rolUsuario;
        
        // Configuración  de la ventana
        this.setSize(1500, 860);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        // Llamaremos a métodos para crear cada sección
        crearEncabezado();
        crearPanelMedio();
        crearPie();
        
        establecerIcono();
        
    
    
        
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
    

    // Método para crear el encabezado amarillo
    private void crearEncabezado() {
        panelEncabezado = new JPanel();
        panelEncabezado.setBackground(new Color(255, 204, 0));
        panelEncabezado.setLayout(null);
        panelEncabezado.setPreferredSize(new Dimension(this.getWidth(), 120));
        this.add(panelEncabezado, BorderLayout.NORTH);
        
        // Logo en la esquina izquierda
        ImageIcon logoMassIcon = new ImageIcon(getClass().getResource("/imagenes/MassLogo.png"));
        Image imgLogo = logoMassIcon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imgLogo));
        lblLogo.setBounds(50, 10, 300, 100);
        panelEncabezado.add(lblLogo);

        agregarPanelUsuario();
    }

    // Método para crear el panel central blanco
    private void crearPanelMedio() {
        panelMedio = new JPanel();
        panelMedio.setBackground(new Color(255,255,255));
        this.add(panelMedio, BorderLayout.CENTER);
    }

    // Método para crear el pie azul
    private void crearPie() {
     panelPie = new JPanel();
    panelPie.setBackground(new Color(0, 51, 204));
    panelPie.setPreferredSize(new Dimension(this.getWidth(), 120));

    // Layout horizontal, alineado a la izquierda, con padding desde arriba
    panelPie.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 50)); // 50 px para bajarlo verticalmente
    // Mover todo hacia la derecha
    panelPie.add(Box.createHorizontalStrut(40)); // <-- ajusta este valor

    // Icono de calendario
    ImageIcon iconCalendario = new ImageIcon(getClass().getResource("/imagenes/Calendario-icon.png"));
    JLabel lblIconCalendario = new JLabel(new ImageIcon(iconCalendario.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    panelPie.add(lblIconCalendario);

    // Label para la fecha
    lblFecha = new JLabel();
    lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 16));
    lblFecha.setForeground(Color.WHITE);
    panelPie.add(lblFecha);

    // Espacio entre fecha y hora
    panelPie.add(Box.createHorizontalStrut(20));

    // Icono de reloj
    ImageIcon iconReloj = new ImageIcon(getClass().getResource("/imagenes/Reloj-icon.png"));
    JLabel lblIconReloj = new JLabel(new ImageIcon(iconReloj.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    panelPie.add(lblIconReloj);

    // Label para la hora
    lblHora = new JLabel();
    lblHora.setFont(new Font("Segoe UI", Font.BOLD, 16));
    lblHora.setForeground(Color.WHITE);
    panelPie.add(lblHora);
    
    // ESPACIO GRANDE PARA EMPUJAR EL COPYRIGHT HACIA LA DERECHA
    panelPie.add(Box.createHorizontalStrut(600)); // Ajusta este valor
    
    // COPYRIGHT
    JLabel lblCopyright = new JLabel("© 2025 Optimass. Todos los derechos reservados.");
    lblCopyright.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    lblCopyright.setForeground(Color.WHITE);
    panelPie.add(lblCopyright); // Agregar al FlowLayout, NO usar setBounds
    
    this.add(panelPie, BorderLayout.SOUTH);

    // Actualizar fecha y hora
    mostrarFechaHora();
        
    }

    // Método para agregar el panel de usuario en el encabezado
    
    private void agregarPanelUsuario() {

        JPanel panelUsuario = new JPanel();
    
        panelUsuario.setOpaque(false);

    int anchoPanel = 450;
    int posicionX = Math.max(20, getWidth() - anchoPanel - 30);
    int gapVertical = getHeight() <= 600 ? 15 : 35;

    panelUsuario.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, gapVertical));
    panelUsuario.setBounds(posicionX, 0, anchoPanel, 120);

    // Panel de texto con nombre y rol
    JPanel panelTexto = new JPanel(new GridLayout(2, 1, 0, 2));
    panelTexto.setOpaque(false);

    JLabel lblSaludo = new JLabel("Hola, ");
    lblSaludo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
    lblSaludo.setForeground(Color.BLACK);

    JLabel lblNombre = new JLabel("<html><b>" + nombreUsuario + "</b></html>");
    lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 22));
    lblNombre.setForeground(Color.BLACK);

    JPanel panelHorizontalNombre = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    panelHorizontalNombre.setOpaque(false);
    panelHorizontalNombre.add(lblSaludo);
    panelHorizontalNombre.add(lblNombre);

    /*
    JLabel lblRol = new JLabel(rolUsuario);
*/
    
    JLabel lblRol = new JLabel(capitalizar(rolUsuario));

    lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 20));
    lblRol.setForeground(new Color(102, 102, 102));

    JPanel panelRol = new JPanel(new FlowLayout(FlowLayout.LEFT, 56, 0));
    panelRol.setOpaque(false);
    panelRol.add(lblRol);

    panelTexto.add(panelHorizontalNombre);
    panelTexto.add(panelRol);

    // Icono de usuario
    ImageIcon iconUsuario = new ImageIcon(getClass().getResource("/imagenes/Usuario2-icon.png"));
    JLabel lblUsuario = new JLabel(new ImageIcon(iconUsuario.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH)));

    // Flecha con evento
    flechaAbajo = new ImageIcon(getClass().getResource("/imagenes/Flecha-icon.png"));
    flechaArriba = new ImageIcon(getClass().getResource("/imagenes/flechaarriba-icon.png"));
    lblFlecha = new JLabel(new ImageIcon(flechaAbajo.getImage().getScaledInstance(34, 34, Image.SCALE_SMOOTH)));
    lblFlecha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Evento de la flecha
    lblFlecha.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            menuVisible = !menuVisible;
            // Cambiar imagen flecha
            lblFlecha.setIcon(new ImageIcon(
                (menuVisible ? flechaArriba : flechaAbajo).getImage().getScaledInstance(34, 34, Image.SCALE_SMOOTH)
            ));
         // Si la flecha está "arriba" mostramos opción de cerrar sesión
        if (menuVisible) {
            int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Quieres cerrar sesión?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

 
            if (opcion == JOptionPane.YES_OPTION) {
                //  Cierra la ventana actual y abre Login
                dispose();
                new Login().setVisible(true);
            } else {
                // Si eligió NO la flecha vuelve hacia abajo
                menuVisible = false;
                lblFlecha.setIcon(new ImageIcon(
                    flechaAbajo.getImage().getScaledInstance(34, 34, Image.SCALE_SMOOTH)
                ));
            }
        
        }
            
        }
    });

    // Agregar al panel
    panelUsuario.add(panelTexto);
    panelUsuario.add(lblUsuario);
    panelUsuario.add(lblFlecha);
    panelEncabezado.add(panelUsuario);

    }
    

    private void mostrarFechaHora() {
    // Crear ambos formatos
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
    
    // Obtener la fecha/hora actual una sola vez
    Date fechaActual = new Date();
    
    // Establecer ambos textos al mismo tiempo
    lblFecha.setText(formatoFecha.format(fechaActual));
    lblHora.setText(formatoHora.format(fechaActual));

    // Timer para actualizar la hora cada segundo
    Timer timer = new Timer(1000, e -> {
        SimpleDateFormat formatoHoraTimer = new SimpleDateFormat("HH:mm:ss");
        lblHora.setText(formatoHoraTimer.format(new Date()));
    });
    timer.start();

    }
    
    
    //esto puse para poner el rol mayuscula
    private String capitalizar(String texto) {
    if (texto == null || texto.isEmpty()) return texto;
    texto = texto.toLowerCase(); 
    return texto.substring(0, 1).toUpperCase() + texto.substring(1);

    }

    
}