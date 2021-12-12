package dro.volkov.booker.security.service;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class UserDetailsImpl extends User {

    @Getter
    private final Integer userId;

    public UserDetailsImpl(dro.volkov.booker.user.data.entity.User appUser) {
        super(appUser.getUsername(), appUser.getPassword(), appUser.isEnabled(),
                true, true, appUser.isActive(),
                Collections.singleton(new SimpleGrantedAuthority(appUser.getRole().role())));
        this.userId = appUser.getId();
    }
}
