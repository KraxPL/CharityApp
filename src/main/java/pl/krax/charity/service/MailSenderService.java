package pl.krax.charity.service;

public interface MailSenderService {
    void simpleMailSender(String to, String subject, String content);
}
