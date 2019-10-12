package org.fundaciobit.blueprint.ejb.test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.fundaciobit.blueprint.ejb.mail.MailSender;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMailSender {

    private static MailSender mailSender;

    private static GreenMail greenMail;

    @BeforeClass
    public static void init() {
        greenMail = new GreenMail(ServerSetupTest.ALL);
        greenMail.start();

        mailSender = new MailSender();
        mailSender.setSession(GreenMailUtil.getSession(ServerSetupTest.SMTP));
    }

    @Test
    public void testSendEmail() throws MessagingException {

        mailSender.sendEmail("Assumpte", "test@test.com", "Contingut");

        Assert.assertTrue((greenMail.waitForIncomingEmail(1000, 1)));

        final MimeMessage msg = greenMail.getReceivedMessages()[0];
        Assert.assertEquals("Assumpte", msg.getSubject());

        final Address recipient = msg.getRecipients(Message.RecipientType.TO)[0];
        Assert.assertEquals("test@test.com", recipient.toString());
    }

    @AfterClass
    public static void tearDown(){
        greenMail.stop();
    }
}
