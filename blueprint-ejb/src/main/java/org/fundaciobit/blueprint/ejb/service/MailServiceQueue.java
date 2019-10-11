package org.fundaciobit.blueprint.ejb.service;


import org.fundaciobit.blueprint.ejb.mail.MailSender;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Coa per un servei de email.
 */
@JMSDestinationDefinition(
        name="java:app/jms/MailQueue",
        interfaceName = "javax.jms.Queue",
        destinationName = "MailQueue"
)
@MessageDriven(
        name = "MailServiceQueue",
        activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:app/jms/MailQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
})
public class MailServiceQueue implements MessageListener {

    @Inject
    private MailSender mailSender;

    @Resource
    private MessageDrivenContext context;

    private static final Logger LOG = Logger.getLogger(MailServiceQueue.class.getName());

    @PostConstruct
    public void init() {
        LOG.info("init");
    }

    @PreDestroy
    public void destroy() {
        LOG.info("destroy");
    }

    @Override
    public void onMessage(Message message) {
        try {
            String id = message.getJMSMessageID();
            int priority = message.getJMSPriority();
            Map map = message.getBody(Map.class);
            String subject = (String) map.get("subject");
            String destination = (String) map.get("destination");
            String content = (String) map.get("content");

            Object[] params = new Object[] {id, priority, subject, destination, content };
            LOG.log(Level.INFO, "Rebut id:{0}, priority:{1}, subject:{2}, destination:{3},\n" +
                    "content:{4}", params);

            mailSender.sendEmail(subject, destination, content);

        } catch (JMSException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            /* Si feim rollback en teoria s'ha de reintentar */
            context.setRollbackOnly();
        }
    }
}
