package AzatechStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "AzatechStore.repository")
public class AzatechApplication {
    public static void main(String[] args) {
        SpringApplication.run(AzatechApplication.class, args);
    }
}