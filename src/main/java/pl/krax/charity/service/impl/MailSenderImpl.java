package pl.krax.charity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.krax.charity.service.MailSenderService;
@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSenderService {
    private final JavaMailSender mailSender;

    @Override
    public void simpleMailSender(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("kraxmailsender@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }
}
