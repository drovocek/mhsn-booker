package dro.volkov.booker.general.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:mail-config.properties")
@Configuration
public class MailConfig {
}
