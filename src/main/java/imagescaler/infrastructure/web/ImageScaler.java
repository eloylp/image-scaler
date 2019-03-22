package imagescaler.infrastructure.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
        @PropertySource("classpath:/properties/application.properties"),
})
@SpringBootApplication
public class ImageScaler {
    public static void main(String[] args) {
        SpringApplication.run(ImageScaler.class, args);
    }
}
