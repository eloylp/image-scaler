package imagescaler.infrastructure.domain;

import imagescaler.domain.ImageAnalyzer;
import imagescaler.domain.ImageInfo;
import imagescaler.domain.ImageScalerException;
import imagescaler.domain.Scale;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@Component
public class IOImageAnalyzer implements ImageAnalyzer {

    @Override
    public ImageInfo perform(InputStream image) throws ImageScalerException {
        try {
            BufferedImage ioImage = ImageIO.read(image);
            String mimeType = URLConnection.guessContentTypeFromStream(image);
            Scale scale = new Scale(ioImage.getWidth(), ioImage.getHeight());
            return new ImageInfo(mimeType, scale, image.available());
        } catch (IOException e) {
            throw new ImageScalerException("Cannot read input image.");
        }
    }
}
