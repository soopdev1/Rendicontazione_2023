package com.seta.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.seta.util.ConstantsSingleton;

@Stateless
public class EmailManagerEJB {

    private static final Logger LOGGER = Logger.getLogger(EmailManagerEJB.class.getName());

    @Resource(mappedName = "java:jboss/mail/Default")
    private Session session;

    public boolean sendEmail(String[] to, String subject, String msgText, String fileDaAllegare) {// buono
        try {
            // String from = "rendicontazione@calabrialavoro.eu";
            String from = ConstantsSingleton.getInstance().getProperty("mail.from");
            // String host = "smtp.calabrialavoro.eu";
            // String user = "rendicontazione@calabrialavoro.eu";
            // String password = "u@2{R&%ddw!X";
            // String port = "465";
            // Properties props = new Properties();
            // props.put("mail.smtp.host", host);
            // props.put("mail.smtp.socketFactory.port", port);
            //
            // props.put("mail.smtp.ssl.enable", "true");
            // props.put("mail.smtp.auth", "true");
            // props.put("mail.smtp.port", port);
            // props.put("mail.smtp.starttls.enable", "true");
            // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // Session session = Session.getInstance(props,
            // new javax.mail.Authenticator() {
            // protected PasswordAuthentication getPasswordAuthentication() {
            // return new PasswordAuthentication(user, password);
            // }
            // });
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            InternetAddress[] addressTo = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                addressTo[i] = new InternetAddress(to[i]);
            }

            message.setRecipients(Message.RecipientType.TO, addressTo);

            // Set Subject
            message.setSubject(subject);
            // Put the content of your message
            Multipart multi_part = new MimeMultipart();
            MimeBodyPart mime_body = new MimeBodyPart();
            mime_body.setContent(msgText, "text/html");
            multi_part.addBodyPart(mime_body);
            message.setContent(multi_part);
            // Attach File
            if (fileDaAllegare != null) {
                MimeBodyPart mime_body_2 = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(fileDaAllegare);
                mime_body_2.setDataHandler(new DataHandler(fds));
                mime_body_2.setFileName(fds.getName());
                multi_part.addBodyPart(mime_body_2);
            }

            StringBuffer str = new StringBuffer();
            str.append(" al server SMTP {from: ").append(from);
            str.append(", to: ").append(to[0]);
            str.append(", oggetto: '").append(subject).append("'}");

            LOGGER.log(Level.INFO, "INIT mail da consegnare" + str.toString());
            // Send message
            Transport.send(message);
            LOGGER.log(Level.INFO, "COMPLETED mail consegnata" + str.toString());

            return true;
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public boolean sendEmail(String[] to, String subject, String msgText) {
        return sendEmail(to, subject, msgText, null);
    }

}
