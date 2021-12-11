package dro.volkov.booker.security.service;

import dro.volkov.booker.user.data.UserRepository;
import dro.volkov.booker.user.data.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (log.isDebugEnabled()) {
            log.debug(String.format("Security verification for user %s", username.toLowerCase()));
        }

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s does not exist", username.toLowerCase())));

        user.setLastAccess(LocalDateTime.now());
        userRepository.save(user);

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(), user.isEnabled(), true, true, true,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().role())));
    }
}
