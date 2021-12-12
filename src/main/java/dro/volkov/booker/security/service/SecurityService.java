package dro.volkov.booker.security.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import dro.volkov.booker.user.data.dict.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SecurityService {

    private static final String LOGOUT_SUCCESS_URL = "/login";

    public Optional<UserDetailsImpl> getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            return Optional.of((UserDetailsImpl) principal);
        }
        return Optional.empty();
    }

    public Optional<Integer> getAuthenticatedUserId() {
        if (getAuthenticatedUser().isPresent()) {
            return Optional.ofNullable(getAuthenticatedUser().get().getUserId());
        }
        return Optional.empty();
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

    public boolean hasRole(Role role) {
        return getAuthenticatedUser()
                .map(authenticatedUser ->
                        authenticatedUser
                                .getAuthorities().stream()
                                .anyMatch(user -> user.getAuthority().equals(role.role())))
                .orElse(false);
    }
}