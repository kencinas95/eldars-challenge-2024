package ar.com.eldars.kencinas.challenge.configs;

import ar.com.eldars.kencinas.challenge.utils.DummyMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailSenderConfiguration {
    @Bean
    public JavaMailSender mailSender() {
        return new DummyMailSender();
    }
}
