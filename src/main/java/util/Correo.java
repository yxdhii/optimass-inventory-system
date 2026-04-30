
package util;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
public class Correo {

    public static void enviar(String destinatario, String asunto, String mensajeTexto) {

        final String remitente = "yadhirasaavedra069@gmail.com";
        final String clave = "obqkbcaptcfgfzcl"; 

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(mensajeTexto);

            Transport.send(message);

            System.out.println("Correo enviado correctamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
     // AGREGA ESTOS 2 MÉTODOS:
    
    /**
     * Genera un código de 6 dígitos
     */
    public static String generarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
    
    /**
     * Envía código de verificación
     */
    public static boolean enviarCodigo(String destinatario, String codigo) {
        try {
            String asunto = "Código de Verificación - OptiMass";
            String mensaje = "Tu código de verificación es: " + codigo + "\n\n"
                           + "Este código expira en 10 minutos.\n\n"
                           + "Si no solicitaste este código, ignora este mensaje.";
            
            enviar(destinatario, asunto, mensaje);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
