package org.fundaciobit.blueprint.ejb.service;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Stateless
public class MailServiceBean implements MailService {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:app/jms/MailQueue")
    private Queue queue;

    @Override
    public void sendEmail(@NotNull String subject, @NotNull @Email String destination, @NotNull String content) {
        try {
            MapMessage mapMessage = jmsContext.createMapMessage();
            mapMessage.setString("subject", subject);
            mapMessage.setString("destination", destination);
            mapMessage.setString("content", content);
            jmsContext.createProducer().send(queue, mapMessage);
        } catch (JMSException e) {
            throw new EJBException(e);
        }
    }
}
