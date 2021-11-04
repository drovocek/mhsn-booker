package dro.volkov.booker.security.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import dro.volkov.booker.user.entity.Role;
import dro.volkov.booker.user.entity.User;
import dro.volkov.booker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String LOGOUT_SUCCESS_URL = "/login";

    public UserDetails getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        // Anonymous or no authentication.
        return null;
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }

    public void register(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println("!!!");
        System.out.println(hashedPassword);
        userRepository.save(new User(username, hashedPassword, Role.USER));
    }

    public boolean userDoesNotExist(String email) {
        return userRepository.getUserByEmail(email).isEmpty();
    }
}