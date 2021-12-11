package dro.volkov.booker.user.data;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.data.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static dro.volkov.booker.util.StrUtil.asUsername;

@SpringComponent
public class UserDataGenerator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadUserData(UserRepository userRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("User generating demo data");
            ExampleDataGenerator<User> userGenerator = new ExampleDataGenerator<>(User.class, LocalDateTime.now());
            userGenerator.setData(User::setEmail, DataType.EMAIL);
            userGenerator.setData(User::setLastAccess, DataType.DATETIME_LAST_30_DAYS);
            userGenerator.setData(User::setRegistration, DataType.DATETIME_LAST_1_YEAR);

            List<User> users = userGenerator.create(20, seed).stream()
                    .peek(user -> user.setRole(Role.USER))
                    .peek(user -> user.setEnabled(true))
                    .peek(user -> user.setActive(true))
                    .peek(user -> user.setUsername(asUsername(user.getEmail())))
                    .peek(user -> user.setPassword(passwordEncoder.encode("user")))
                    .collect(Collectors.toList());

            User admin = new User("admin@admin.com",
                    passwordEncoder.encode("admin"),
                    Role.ADMIN);
            admin.setActive(true);
            admin.setEnabled(true);
            admin.setUsername(asUsername(admin.getEmail()));

            User user = new User("user@user.com",
                    passwordEncoder.encode("user"),
                    Role.USER);
            user.setActive(true);
            user.setEnabled(true);
            user.setUsername(asUsername(user.getEmail()));

            users.add(admin);
            users.add(user);

            userRepository.saveAll(users);

            logger.info("User generated demo data");
        };
    }
}
