package com.daniela.expensemanagement.services.interfaces;

import org.eclipse.angus.mail.util.MailConnectException;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text) throws MailConnectException;
}
