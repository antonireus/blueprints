package org.fundaciobit.blueprint.ejb.mail;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.mail.MailSessionDefinition;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

@MailSessionDefinition(
        name = "java:app/mail/MailSession",
        host = "localhost",
        from = "server@test.com",
        transportProtocol = "smtp"
)
@Named
public class MailSender {

    private static final Logger LOG = Logger.getLogger(MailSender.class.getName());

    @Resource(lookup="java:app/mail/MailSession")
    private Session session;

    public void sendEmail(String subject, String destination, String content) {
        LOG.info("sendEmail");
        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
