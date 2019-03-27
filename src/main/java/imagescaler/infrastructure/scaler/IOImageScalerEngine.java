package imagescaler.infrastructure.scaler;

import imagescaler.domain.Image;
import imagescaler.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class IOImageScalerEngine implements ImageScalerEngine {


    private final BufferedImageTypeCalculator bufferedImageTypeCalculator;
    private final ImageFormatterCalculator imageFormatterCalculator;

    @Autowired
    IOImageScalerEngine(BufferedImageTypeCalculator bufferedImageTypeCalculator, ImageFormatterCalculator imageFormatterCalculator) {

        this.bufferedImageTypeCalculator = bufferedImageTypeCalculator;
        this.imageFormatterCalculator = imageFormatterCalculator;
    }

    @Override
    public Image scaleImageTo(Image original, Scale scale) throws ImageScalerException {
        try {

            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(original.getData()));
            java.awt.Image originalImageScaledInstance = originalImage.getScaledInstance(scale.getWidth(), scale.getHeight(), java.awt.Image.SCALE_SMOOTH);
            int bufferedImageType = bufferedImageTypeCalculator.perform(original.getContentType());
            BufferedImage resizedImage = new BufferedImage(scale.getWidth(), scale.getHeight(), bufferedImageType);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(originalImageScaledInstance, 0, 0, null);
            graphics.dispose();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String imageFormatter = this.imageFormatterCalculator.perform(original.getContentType());
            ImageIO.write(resizedImage, imageFormatter, outputStream);
            return new ImageBuilder(
                    original.getName(),
                    new ImageInfo(new ContentType(original.getContentType()), scale, outputStream.size()),
                    outputStream.toByteArray()
            ).build();
        } catch (IOException e) {
            throw new ImageScalerException("Cannot scale image: " + original.getUuid() + "due to error: " + e.getMessage());
        }
    }
}
