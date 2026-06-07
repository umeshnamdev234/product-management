package styles_ai_task.product_management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import styles_ai_task.product_management.entity.User;
import styles_ai_task.product_management.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        seedUser(
                "admin",
                "admin123",
                "EDIT"
        );

        seedUser(
                "viewer",
                "viewer123",
                "VIEW"
        );
    }

    private void seedUser(
            String username,
            String password,
            String role
    ) {

        if (userRepository.findByUsername(username).isPresent()) {
            return;
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        userRepository.save(user);

        System.out.println(
                "Default user created: "
                        + username
                        + " [" + role + "]"
        );
    }
}