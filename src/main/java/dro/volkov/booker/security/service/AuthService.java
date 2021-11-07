package dro.volkov.booker.security.service;

import com.vaadin.flow.spring.annotation.UIScope;
import dro.volkov.booker.MailService;
import dro.volkov.booker.user.data.entity.User;
import dro.volkov.booker.user.data.service.UserCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@UIScope
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserCrudService userService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public boolean activated(String email) {
        return userService.getByEmail(email).map(User::isActive).orElse(false);
    }

    @Transactional
    public void activate(String email, String password) {
        Optional<User> userContainer = userService.getByEmail(email);
        if (userContainer.isPresent()) {
            User user = userContainer.get();
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            mailService.sendSuccessActivationMessage(email, password);
        } else {
            throw new RuntimeException(String.format("User with email=%s doesn't exist", email));
        }
    }


//    public void authenticate(String userName, String password) {
//        User user = userService.getByUserName(userName);
//        if (user.getPasswordHash() == null) {
//            UI.getCurrent().navigate("registration",
//                    new QueryParameters(
//                            Collections.singletonMap("code", Collections.singletonList(user.getActivationCode()))));
//        } else if (user.isValidPassword(password) && user.getEnabled()) {
//            VaadinSession.getCurrent().setAttribute(User.class, user);
//            createRoutes(user.getRole());
//            user.setLastActivity(LocalDateTime.now());
//            userService.update(user);
//        } else {
//            throw new AuthException("Password incorrect");
//        }
//    }

//    public Optional<User> getAuthUser() {
//        return Optional.ofNullable(VaadinSession.getCurrent().getAttribute(User.class));
//    }

//    public void activate(String password, String activationCode) {
//        User user = userService.getByActivationCode(activationCode);
//        updatePassword(user, password);
//        mailService.sendMessage(
//                user.getEmail(),
//                String.format("Login: %s \nPassword: %s", user.getUserName(), password),
//                "Registration info");
//    }

//    public void refreshPassword(String password) {
//        getAuthUser().ifPresent(authUser -> updatePassword(authUser, password));
//    }

//    public boolean isActivated(String code) {
//        return userService.isActivated(code);
//    }


//    public void updatePassword(User user, String password) {
//        String passwordSalt = RandomStringUtils.random(32);
//        String passwordHash = DigestUtils.sha1Hex(password.concat(passwordSalt));
//        user.setPasswordSalt(passwordSalt);
//        user.setPasswordHash(passwordHash);
//        user.setEnabled(true);
//        userService.update(user);
//    }
}
