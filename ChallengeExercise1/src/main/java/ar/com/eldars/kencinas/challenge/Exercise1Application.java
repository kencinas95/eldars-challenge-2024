package ar.com.eldars.kencinas.challenge;

import ar.com.eldars.kencinas.challenge.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Exercise1Application implements CommandLineRunner {
    private final ApplicationService app;

    @Override
    public void run(String... args) {
        app.mainloop();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Exercise1Application.class, args);
    }
}
