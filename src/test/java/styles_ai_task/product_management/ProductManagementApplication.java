package styles_ai_task.product_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of the Product Management application.
 *
 * <p>
 * The @SpringBootApplication annotation enables:
 * </p>
 * <ul>
 *     <li>Auto Configuration</li>
 *     <li>Component Scanning</li>
 *     <li>Spring Boot Configuration</li>
 * </ul>
 *
 * <p>
 * When the application starts, Spring Boot initializes
 * all configured beans, controllers, services,
 * repositories, security components, and other
 * application resources.
 * </p>
 */
@SpringBootApplication
public class ProductManagementApplication {

    /**
     * Application startup method.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {

        SpringApplication.run(
                ProductManagementApplication.class,
                args
        );
    }
}