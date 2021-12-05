package dro.volkov.booker.security.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import dro.volkov.booker.user.data.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityService {

    private static final String LOGOUT_SUCCESS_URL = "/login";

    public UserDetails getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        return null;
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

    public boolean hasRole(Role role) {
        UserDetails authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser == null) {
            return false;
        }
        return authenticatedUser.getAuthorities().stream()
                .peek(user-> System.out.println(user.getAuthority()))
                .anyMatch(user -> user.getAuthority().equals(role.role()));
    }
}