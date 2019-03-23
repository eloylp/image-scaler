package imagescaler.infrastructure.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@PropertySources({
        @PropertySource("classpath:/properties/application.properties"),
})
@SpringBootApplication
@ComponentScan(basePackages = {"imagescaler.application", "imagescaler.infrastructure"})
@EnableMongoRepositories(basePackages = {"imagescaler.infrastructure.persistence.mongo"})
public class ImageScaler {
    public static void main(String[] args) {
        SpringApplication.run(ImageScaler.class, args);
    }
}
