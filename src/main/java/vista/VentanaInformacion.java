package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInformacion extends VentanaBase1 {
    private String codigoUsuario;

    public VentanaInformacion(String codigoUsuario,String nombreUsuario, String rolUsuario) {
        super(nombreUsuario, rolUsuario); // llama al constructor de VentanaBase1
        this.codigoUsuario = codigoUsuario; 
        setTitle("Información");
        
        panelMedio.setLayout(new BorderLayout());

        
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230)); 
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50)); 

        
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
        
        

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10)); // 15px espacio a la derecha
        panelIzquierdo.setOpaque(false); 
        panelIzquierdo.add(btnRegresar);
        
        
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/Informacion-icon.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Información");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        panelCentro.add(lblIcono);
        panelCentro.add(lblTitulo);
        
        
        
        panelSubEncabezado.add(panelIzquierdo, BorderLayout.WEST);  
        panelSubEncabezado.add(panelCentro, BorderLayout.CENTER); 
        
        panelMedio.add(panelSubEncabezado, BorderLayout.NORTH);   
        
        
        
        //contenido
        
        
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);

        JPanel panelCentrado = new JPanel();
        panelCentrado.setLayout(new BoxLayout(panelCentrado, BoxLayout.Y_AXIS));
        panelCentrado.setBackground(Color.WHITE);
        panelCentrado.setMaximumSize(new Dimension(600, 700));

        
        JLabel lblTituloPrincipal = new JLabel("OPTIMASS");
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 32));
        lblTituloPrincipal.setForeground(new Color(0,51,204));
        lblTituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTituloPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        
        JLabel lblSubtitulo = new JLabel("Todo Bajo control.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(255,204,0));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        
        JLabel lblDescripcion = new JLabel(
            "<html><div style='text-align: center; width: 500px; padding: 10px;'>" +
            "Somos una empresa comprometida con brindar soluciones tecnológicas innovadoras. " +
            "Nuestro equipo está dedicado a proporcionar productos y servicios de alta calidad " +
            "que satisfagan las necesidades de nuestros clientes. ¡Gracias por elegirnos!" +
            "</div></html>"
        );
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescripcion.setForeground(new Color(60, 60, 60));
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        
        Font fontInfo = new Font("Arial", Font.PLAIN, 14);

        
        JPanel panelInfoProyecto = new JPanel();
        panelInfoProyecto.setLayout(new BoxLayout(panelInfoProyecto, BoxLayout.Y_AXIS));
        panelInfoProyecto.setBackground(Color.WHITE);
        panelInfoProyecto.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfoProyecto.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelInfoProyecto.setMaximumSize(new Dimension(500, 150));

        
        JLabel lblDocente = new JLabel("<html><div style='text-align: center; width: 400px;'><b>Proyecto:</b> Sistema de Control de Inventario</div></html>");
        JLabel lblGrupo = new JLabel("<html><div style='text-align: center; width: 425px;'><b>Grupo:</b> 04</div></html>");
        JLabel lblActualizacion = new JLabel("<html><div style='text-align: center; width: 410px;'><b>Inicio del proyecto:</b> 01/09/2025</div></html>");

        lblDocente.setFont(fontInfo);
        lblGrupo.setFont(fontInfo);
        lblActualizacion.setFont(fontInfo);

        lblDocente.setForeground(new Color(60, 60, 60));
        lblGrupo.setForeground(new Color(60, 60, 60));
        lblActualizacion.setForeground(new Color(60, 60, 60));

        lblDocente.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblGrupo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblActualizacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelInfoProyecto.add(lblDocente);
        panelInfoProyecto.add(Box.createRigidArea(new Dimension(0, 8)));
        panelInfoProyecto.add(lblGrupo);
        panelInfoProyecto.add(Box.createRigidArea(new Dimension(0, 8)));
        panelInfoProyecto.add(lblActualizacion);

        
        JPanel panelContacto = new JPanel();
        panelContacto.setLayout(new BoxLayout(panelContacto, BoxLayout.Y_AXIS));
        panelContacto.setBackground(Color.WHITE);
        panelContacto.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContacto.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelContacto.setMaximumSize(new Dimension(500, 150));

        JLabel lblSoporte = new JLabel("<html><div style='text-align: center; width: 415px;'><b>Docente:</b> Milton Freddy Amache Sanchez</div></html>");
        JLabel lblEstudiantes = new JLabel("<html><div style='text-align: center; width: 400px;'><b>Última actualización:</b> 26/11/2025 </div></html>");

        lblSoporte.setFont(fontInfo);
        lblEstudiantes.setFont(fontInfo);
        lblSoporte.setForeground(new Color(60, 60, 60));
        lblEstudiantes.setForeground(new Color(60, 60, 60));
        lblSoporte.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstudiantes.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContacto.add(lblSoporte);
        panelContacto.add(Box.createRigidArea(new Dimension(0, 8)));
        panelContacto.add(lblEstudiantes);

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(400, 1));
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);
        separador.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel lblEmpresa = new JLabel("Universidad Tecnológica del Perú");
        lblEmpresa.setFont(new Font("Arial", Font.BOLD, 18));
        lblEmpresa.setForeground(new Color(0, 51, 102));
        lblEmpresa.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEmpresa.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        panelCentrado.add(lblTituloPrincipal);
        panelCentrado.add(lblSubtitulo);
        panelCentrado.add(lblDescripcion);
        panelCentrado.add(panelInfoProyecto);
        panelCentrado.add(panelContacto);
        panelCentrado.add(separador);
        panelCentrado.add(lblEmpresa);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(panelCentrado, gbc);

        panelMedio.add(panelContenido, BorderLayout.CENTER);
        
    }
}