package org.fundaciobit.blueprint.ejb.service;

import javax.ejb.Local;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * EJB per enviar correus.
 */
@Local
public interface MailService {

    void sendEmail(@NotNull String subject, @Email String destination, @NotNull String content);
}
