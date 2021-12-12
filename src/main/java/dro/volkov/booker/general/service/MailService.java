package dro.volkov.booker.general.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import static dro.volkov.booker.util.StrUtil.asUsername;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Value("${app.root-mail}")
    private String rootMail;

    @Value("${app.root-path}")
    private String rootPath;

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
                String.format(
                        """
                                 Successful account activation.
                                    username: %s
                                    password: %s
                                """
                        , asUsername(email)
                        , password),
                "Activation info");
    }

    public void sendActivationMessage(String email) {
        String activationUrl = rootPath
                .concat("/activation?email=")
                .concat(email);
        sendMessage(
                email,
                activationUrl,
                "Email confirmation");
    }
}
