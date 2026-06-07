package styles_ai_task.product_management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import styles_ai_task.product_management.entity.User;
import styles_ai_task.product_management.repository.UserRepository;

/**
 * Seeds default application users during application startup.
 *
 * This component runs automatically when Spring Boot starts
 * and creates predefined users if they do not already exist
 * in the database.
 *
 * Default Users:
 * 1. admin  / admin123  -> EDIT role
 * 2. viewer / viewer123 -> VIEW role
 *
 * The passwords are stored in encrypted form using BCrypt.
 *
 * This ensures the application can be used immediately after
 * deployment without manually creating initial users.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    /**
     * Repository used for user database operations.
     */
    private final UserRepository userRepository;

    /**
     * Password encoder used to securely hash passwords
     * before storing them in the database.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes automatically when the application starts.
     *
     * Creates default users if they are not already present
     * in the database.
     *
     * @param args application startup arguments
     */
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

    /**
     * Creates a user if the username does not already exist.
     *
     * Steps:
     * 1. Check whether the user already exists.
     * 2. Encrypt the provided password.
     * 3. Create and save the user.
     * 4. Log the created user information.
     *
     * @param username unique username
     * @param password plain text password that will be encrypted
     * @param role application role (VIEW or EDIT)
     */
    private void seedUser(
            String username,
            String password,
            String role
    ) {

        // Skip creation if user already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return;
        }

        // Build user entity with encrypted password
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        // Save user in database
        userRepository.save(user);

        // Log created user
        System.out.println(
                "Default user created: "
                        + username
                        + " [" + role + "]"
        );
    }
}