package styles_ai_task.product_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of the Product Management Application.
 *
 * The @SpringBootApplication annotation enables:
 *
 * 1. @Configuration
 *    - Allows this class to define Spring beans.
 *
 * 2. @EnableAutoConfiguration
 *    - Automatically configures Spring Boot components
 *      based on project dependencies.
 *
 * 3. @ComponentScan
 *    - Scans the current package and all sub-packages
 *      for Spring-managed components such as:
 *          - Controllers
 *          - Services
 *          - Repositories
 *          - Configurations
 *          - Filters
 *
 * Package Structure Scanned:
 *
 * styles_ai_task.product_management
 * ├── controller
 * ├── service
 * ├── repository
 * ├── security
 * ├── exception
 * ├── config
 * └── entity
 *
 * This class bootstraps the entire Spring Boot application.
 */
@SpringBootApplication
public class ProductManagementApplication {

    /**
     * Application startup method.
     *
     * Spring Boot execution starts here.
     *
     * Steps performed:
     * 1. Creates Spring Application Context
     * 2. Loads all configurations
     * 3. Registers Beans
     * 4. Configures Embedded Tomcat Server
     * 5. Initializes Security Configuration
     * 6. Connects to Database
     * 7. Exposes REST APIs
     *
     * @param args command-line arguments passed during startup
     */
    public static void main(String[] args) {

        SpringApplication.run(
                ProductManagementApplication.class,
                args
        );
    }
}