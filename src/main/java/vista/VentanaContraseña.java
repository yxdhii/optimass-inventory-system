package vista;

import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaContraseña extends VentanaBase1 {
    private String codigoUsuario;


    public VentanaContraseña(String codigoUsuario, String nombreUsuario, String rolUsuario) {
    super(nombreUsuario, rolUsuario);
    this.codigoUsuario = codigoUsuario;  // GUARDA EL CÓDIGO
    
      //  esto para que salga en la consola
    System.out.println("****************************************");
    System.out.println("VentanaContraseña - Constructor");
    System.out.println("Código recibido: [" + codigoUsuario + "]");
    System.out.println("Nombre: " + nombreUsuario);
    System.out.println("*****************************************");
    
    setTitle("Cambiar Contraseña");
    
        // Panel principal de contenido
        panelMedio.setLayout(new BorderLayout());

        // sub encabezado
        JPanel panelSubEncabezado = new JPanel(new BorderLayout());
        panelSubEncabezado.setBackground(new Color(230, 230, 230)); 
        panelSubEncabezado.setPreferredSize(new Dimension(0, 50)); 

        //BOTÓN DE REGRESAR A LA IZQUIERDA
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
                new VentanaConfiguracion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
                dispose();
            }
        });
        
        //PANEL IZQUIERDO PARA EL BOTÓN

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 10)); // 15px espacio a la derecha
        panelIzquierdo.setOpaque(false); // sin color de fondo
        panelIzquierdo.add(btnRegresar);
        //PANEL CENTRAL CON ICONO + TEXTO 
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(0, -100, 0, 0));
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/contraseña.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenEscalada));
        JLabel lblTitulo = new JLabel("Cambiar Contraseña");
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
        
        
        
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Panel del formulario 
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        panelFormulario.setMaximumSize(new Dimension(500, 400));

        // Título del formulario
        JLabel lblTituloForm = new JLabel("Cambiar Contraseña");
        lblTituloForm.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloForm.setForeground(new Color(0, 51, 102));
        lblTituloForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTituloForm.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Panel para campos del formulario
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(3, 2, 15, 20));
        panelCampos.setBackground(Color.WHITE);
        panelCampos.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Estilos para etiquetas y campos
        Font fontEtiqueta = new Font("Arial", Font.BOLD, 14);
        Font fontCampo = new Font("Arial", Font.PLAIN, 14);

        // Campo contraseña actual
        JLabel lblActual = new JLabel("Contraseña actual:");
        lblActual.setFont(fontEtiqueta);
        lblActual.setForeground(new Color(60, 60, 60));
        JPasswordField txtActual = new JPasswordField();
        txtActual.setFont(fontCampo);
        txtActual.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Campo nueva contraseña
        JLabel lblNueva = new JLabel("Nueva contraseña:");
        lblNueva.setFont(fontEtiqueta);
        lblNueva.setForeground(new Color(60, 60, 60));
        JPasswordField txtNueva = new JPasswordField();
        txtNueva.setFont(fontCampo);
        txtNueva.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Campo confirmar contraseña
        JLabel lblConfirmar = new JLabel("Confirmar contraseña:");
        lblConfirmar.setFont(fontEtiqueta);
        lblConfirmar.setForeground(new Color(60, 60, 60));
        JPasswordField txtConfirmar = new JPasswordField();
        txtConfirmar.setFont(fontCampo);
        txtConfirmar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Agregar campos al panel
        panelCampos.add(lblActual);
        panelCampos.add(txtActual);
        panelCampos.add(lblNueva);
        panelCampos.add(txtNueva);
        panelCampos.add(lblConfirmar);
        panelCampos.add(txtConfirmar);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Botón Guardar (mejorado)
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(70, 130, 180));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover para botón Guardar
        
        
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(90, 150, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(70, 130, 180));
            }
        });

        // Botón Cancelar 
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(220, 220, 220));
        btnCancelar.setForeground(new Color(80, 80, 80));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover para botón Cancelar
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(200, 200, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(220, 220, 220));
            }
        });

        // Agregar botones al panel
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Agregar todos los componentes al formulario
        panelFormulario.add(lblTituloForm);
        panelFormulario.add(panelCampos);
        panelFormulario.add(panelBotones);

        // Centrar el formulario en el contenido
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenido.add(panelFormulario, gbc);

        // Eventos de botones
        btnGuardar.addActionListener(new ActionListener() {
            @Override
    
            public void actionPerformed(ActionEvent e) {

        
                String actual = new String(txtActual.getPassword());        
                String nueva = new String(txtNueva.getPassword());       
                String confirmar = new String(txtConfirmar.getPassword());
        
        // esto sale en la consola
        System.out.println("****************************************");
        System.out.println("Botón Guardar Cambios presionado");
        System.out.println("Código a validar: [" + codigoUsuario + "]");
        System.out.println("Contraseña actual: [" + actual + "]");
        System.out.println("Nueva contraseña: [" + nueva + "]");
        System.out.println("*****************************************");

        // Validación de campos vacíos
        if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Por favor, completa todos los campos.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar longitud mínima de contraseña
        if (nueva.length() < 4) {
            JOptionPane.showMessageDialog(null, 
                "La nueva contraseña debe tener al menos 4 caracteres.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si las contraseñas nuevas coinciden
        if (!nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(null, 
                "Las nuevas contraseñas no coinciden.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar al DAO
        UsuarioDAO dao = new UsuarioDAO();

        // Validar contraseña actual
        if (!dao.validarPassword(codigoUsuario, actual)) {
            JOptionPane.showMessageDialog(null, 
                "La contraseña actual es incorrecta.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar contraseña
        if (dao.actualizarPassword(codigoUsuario, nueva)) {

            JOptionPane.showMessageDialog(null, 
                "Contraseña cambiada con éxito.\n\n" +
                "Por seguridad, debes iniciar sesión nuevamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // CERRAR TODAS LAS VENTANAS
            Window[] ventanas = Window.getWindows();
            for (Window ventana : ventanas) {
                ventana.dispose();
            }
            
            // Abrir el Login
            new Login().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al actualizar la contraseña.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        
        }
    
            }
        });
        
        
        btnCancelar.addActionListener(e -> {
            new VentanaConfiguracion(codigoUsuario, nombreUsuario, rolUsuario).setVisible(true);
            dispose();
        });

        // Agregar contenido al panel medio
        panelMedio.add(panelContenido, BorderLayout.CENTER);
        
    }
}