/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import vista.Login;

/**
 *
 * @author Yadhira Saavedra
 */
public class Principal {
    public static void main(String[] args) {
         // Para que las interfaces gráficas se carguen correctamente en Swing:
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Crear una instancia del login
            Login login = new Login();
            // Hacer visible la ventana
            login.setVisible(true);
        });
    }
}
