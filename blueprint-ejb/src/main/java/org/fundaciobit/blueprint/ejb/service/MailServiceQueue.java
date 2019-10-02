package org.fundaciobit.blueprint.ejb.service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
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
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:app/jms/MailQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        // Propietat necessaria per bug a Wildfly.
        @ActivationConfigProperty(propertyName = "useJNDI",
                propertyValue = "true")
})
public class MailServiceQueue implements MessageListener {

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
            String content = message.getBody(String.class);
            Object[] params = new Object[] {id, priority, content };

            LOG.log(Level.INFO, "Rebut id:{0}, priority:{1}\ncontent:{2}", params);

        } catch (JMSException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            /* Si feim rollback en teoria s'ha de reintentar */
            context.setRollbackOnly();
        }
    }
}
