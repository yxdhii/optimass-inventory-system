/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import util.Correo;


public class TestCorreo {
     public static void main(String[] args) {
        Correo.enviar(
                "yadhisaavedra7@gmail.com",
                "Correo de prueba",
                "Hola, este es un correo de prueba enviado desde Java."
        );
    }

}
