package imagescaler.infrastructure.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@PropertySources({
        @PropertySource("classpath:/properties/backend.properties"),
})
@ComponentScan(basePackages = {
        "imagescaler.application.scaler",
        "imagescaler.domain",
        "imagescaler.infrastructure.backend",
        "imagescaler.infrastructure.scaler",
        "imagescaler.infrastructure.persistence.mongo",
})
@EnableMongoRepositories(basePackages = {"imagescaler.infrastructure.persistence.mongo"})
@SpringBootApplication
public class Scaler {

    public static void main(String[] args) {
        SpringApplication.run(Scaler.class, args);
    }

}
