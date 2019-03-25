package imagescaler.infrastructure.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@PropertySources({
        @PropertySource("classpath:/properties/web.properties"),
})
@SpringBootApplication
@ComponentScan(basePackages = {
        "imagescaler.application.web",
        "imagescaler.infrastructure.analyzer",
        "imagescaler.infrastructure.persistence.mongo",
        "imagescaler.infrastructure.web",
})
@EnableMongoRepositories(basePackages = {"imagescaler.infrastructure.persistence.mongo"})
public class Web {
    public static void main(String[] args) {
        SpringApplication.run(Web.class, args);
    }
}
