package imagescaler.infrastructure.analyzer;

import imagescaler.domain.ImageAnalyzer;
import imagescaler.domain.ImageInfo;
import imagescaler.domain.ImageScalerException;
import imagescaler.domain.Scale;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;

@Component
public class IOImageAnalyzer implements ImageAnalyzer {

    @Override
    public ImageInfo perform(byte[] image) throws ImageScalerException {
        try {
            String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
            BufferedImage ioImage = ImageIO.read(new ByteArrayInputStream(image));
            Scale scale = new Scale(ioImage.getWidth(), ioImage.getHeight());
            return new ImageInfo(mimeType, scale, image.length);
        } catch (IOException e) {
            throw new ImageScalerException("Cannot read input image in analyzer.");
        }
    }
}