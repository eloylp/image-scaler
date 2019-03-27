package imagescaler.infrastructure.scaler;

import org.springframework.stereotype.Component;

@Component
class ImageFormatterCalculator {

    public String perform(String contentType) {
        return contentType.split("/")[1].toLowerCase();
    }
}
