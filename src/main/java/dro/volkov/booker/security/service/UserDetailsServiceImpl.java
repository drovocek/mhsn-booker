package dro.volkov.booker.security.service;

import dro.volkov.booker.user.data.entity.Role;
import dro.volkov.booker.user.data.entity.User;
import dro.volkov.booker.user.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        if (log.isDebugEnabled()) {
            log.debug(String.format("Security verification for user %s", username.toLowerCase()));
        }

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s does not exist", username.toLowerCase())));

        user.setLastAccessDate(LocalDateTime.now());

        Role role = user.getRole();

        System.out.println(user);
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(), user.isEnabled(), true, true, true,
                Collections.singleton(new SimpleGrantedAuthority(role.getRoleName())));
    }
}
