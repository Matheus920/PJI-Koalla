package app.control;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public abstract class EmailSender {
    
    public static void sendEmail(String messagem, String destinatario, String assunto){
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("pjikoalla@gmail.com", "pjikoalla123"));
        email.setSSLOnConnect(true);
        email.setSubject(assunto);
        try {
            email.setFrom("pjikoalla@gmail.com");
            email.setMsg(messagem);
            email.addTo(destinatario);
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
