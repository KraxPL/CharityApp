package pl.krax.charity.service;

public interface MailSenderService {
    void simpleMailSender(String to, String subject, String content);
    void confirmAccountCreationMail(String accountEmail, String token);
    void passwordChangeMail(String email, String token);
}
