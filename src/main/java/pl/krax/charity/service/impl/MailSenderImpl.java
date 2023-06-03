package pl.krax.charity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.krax.charity.service.MailSenderService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSenderService {
    private static final String MAIL_SENDING_MESSAGES = "kraxmailsender@gmail.com";
    private static final String ACCOUNT_ACTIVATION_MAIL_SUBJECT = "CharityApp account activation";
    private final JavaMailSender mailSender;

    @Override
    public void simpleMailSender(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(MAIL_SENDING_MESSAGES);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

    @Override
    public void confirmAccountCreationMail(String accountEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MAIL_SENDING_MESSAGES);
        message.setTo(accountEmail);
        message.setSubject(ACCOUNT_ACTIVATION_MAIL_SUBJECT);
        message.setText("Hello!\n\n"
                + "This is a message just for you. "
                + "localhost:8080/activateUser?token=" + token + "&email=" + accountEmail
                + " Please click on this link to activate your account!\n\n"
                + "Best regards,\n"
                + "The CharityApp Team");
        mailSender.send(message);
    }

    @Override
    public void passwordChangeMail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MAIL_SENDING_MESSAGES);
        message.setTo(email);
        message.setSubject("CharityApp - forgotten password");
        message.setText("Hello!\n\n"
                + "This is a message just for you. "
                + "localhost:8080/password?token=" + token + "&email=" + email
                + " Please click on this link to change your password account!\n\n"
                + "Best regards,\n"
                + "The CharityApp Team");
        mailSender.send(message);
    }
}
