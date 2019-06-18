package com.ayman.MessagesSystem.Code.Type;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.controlsfx.control.Notifications;

public class sendMail {

    private final static String username = "work000test@gmail.com"; // enter your mail id
    private final static String password = "10508836+++";// enter ur password

    static Properties props = new Properties();

    public static void sendMessage(String pass, String name, String email) {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("work000test@gmail.com")); // same email id
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));// whome u have to send mails that person id
            message.setSubject("Password recovery");
            message.setText("Dear " + name 
                    + "\nYou have requested a password recovery\n"
                    + "Your password is: \""+ pass + "\"\n "
                    + "\nRegards!\nIf you didn't request this. Please igonre and delete this message!");

            Transport.send(message);

        } catch (AddressException ex) {
            Logger.getLogger(sendMail.class.getName()).log(Level.SEVERE, null, ex);
            Notifications note = Notifications.create()
                    .title("Registeration note")
                    .text("Add valid email!")
                    .graphic(new Rectangle(20, 20))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_LEFT);
            note.showConfirm();
        } catch (MessagingException ex) {
            Logger.getLogger(sendMail.class.getName()).log(Level.SEVERE, null, ex);
                        Notifications note = Notifications.create()
                    .title("Registeration note")
                    .text("Add valid email!")
                    .graphic(new Rectangle(20, 20))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_LEFT);
            note.showConfirm();
        }   
    }

}
