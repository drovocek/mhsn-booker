package dro.volkov.booker.security.service;

import dro.volkov.booker.general.service.MailService;
import dro.volkov.booker.user.data.UserCrudService;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserCrudService userCrudService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public boolean activated(String email) {
        return userCrudService.getByEmail(email)
                .map(User::isActive)
                .orElse(false);
    }

    @Transactional
    public void activate(String email, String password) {
        Optional<User> userContainer = userCrudService.getByEmail(email);
        if (userContainer.isPresent()) {
            User user = userContainer.get();
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Role.USER);
            userCrudService.save(user);
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
