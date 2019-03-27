package imagescaler.infrastructure.scaler;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
class BufferedImageTypeCalculator {

    public int perform(String contentType) {
        if (contentType.equals("image/jpeg") || contentType.equals("image/jpg")) {
            return BufferedImage.TYPE_3BYTE_BGR;
        }
        return BufferedImage.TYPE_INT_ARGB;
    }
}
