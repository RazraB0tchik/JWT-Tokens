package auth.auth_files.autentification_jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class AutentificationJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutentificationJwtApplication.class, args);
    }

}
