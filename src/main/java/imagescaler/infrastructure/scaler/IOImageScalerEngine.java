package imagescaler.infrastructure.scaler;

import imagescaler.domain.*;
import imagescaler.domain.Image;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class IOImageScalerEngine implements ImageScalerEngine {

    @Override
    public Image scaleImageTo(Image original, Scale scale) throws ImageScalerException {
        try {

            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(original.getData()));
            java.awt.Image originalImageScaledInstance = originalImage.getScaledInstance(scale.getWidth(), scale.getHeight(), java.awt.Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(scale.getWidth(), scale.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(originalImageScaledInstance, 0, 0, null);
            graphics.dispose();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, original.getImageInfo().getContentType().split("/")[1], outputStream);
            return new Image(
                    original.getName(),
                    new ImageInfo(original.getImageInfo().getContentType(), scale, outputStream.size()),
                    outputStream.toByteArray()
            );
        } catch (IOException e) {
            throw new ImageScalerException("Cannot scale image: " + original.getUuid() + "due to error: " + e.getMessage());
        }
    }
}
