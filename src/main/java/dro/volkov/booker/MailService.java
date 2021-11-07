package dro.volkov.booker;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Value("${app.root.mail}")
    private String rootMail;

    @Value("${app.root.url}")
    private String rootUrl;

    public void sendMessage(String targetMail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(rootMail);
        message.setTo(targetMail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendSuccessActivationMessage(String email, String password) {
        sendMessage(
                email,
                String.format("Successful account activation. Your password is %s", password),
                "Activation info");
    }

    public void sendActivationMessage(String email) {
        String activationUrl = rootUrl
                .concat("activation?email=")
                .concat(email);
        sendMessage(
                email,
                activationUrl,
                "Email confirmation");
    }
}
