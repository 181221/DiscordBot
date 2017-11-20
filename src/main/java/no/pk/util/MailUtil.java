package no.pk.util;

import no.pk.model.Mail;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static no.pk.model.Mail.FRAMAIL;
import static no.pk.model.Mail.PASSORD;

public class MailUtil {
    public static void setUpMail(String til, String msg, String subject) {
        Mail mail = opprettMail(til, msg, subject);
        Properties props = setUpProps();
        System.out.println(sendMail(mail, props));
    }

    private static Mail opprettMail(String til, String msg, String subject) {
        return new Mail(til, msg, subject);
    }

    private static Properties setUpProps() {
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.user", FRAMAIL);
        props.put("mail.password", PASSORD);
        props.put("mail.port", "465");
        return props;
    }

    private static String sendMail(Mail mail, Properties props) {
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Mail.getFRAMAIL(), Mail.getPASSORD());
            }
        });
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(Mail.getFRAMAIL()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTil()));
            message.setSubject(mail.getSubject());
            message.setText(mail.getMsg());
            javax.mail.Transport.send(message);
            mail.setResult("Your mail sent successfully....");
        } catch (AddressException e) {
            mail.setResult(e.getMessage());
        } catch (javax.mail.MessagingException e) {
            mail.setResult(e.getMessage());
        }
        return mail.getResult();
    }
}
