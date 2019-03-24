package imagescaler.infrastructure.backend;

import imagescaler.application.backend.ScaleImage;
import imagescaler.domain.ImageScalerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class MessageReceiver {
    private ScaleImage scaleImage;
    @Value("${scaler.target.scales}")
    private String scales;

    @Autowired
    MessageReceiver(ScaleImage scaleImage) {
        this.scaleImage = scaleImage;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    public void receiveMessage(String message) {
        LOGGER.info("Received image <" + message + ">");
        try {
            this.scaleImage.perform(message, scales);
        } catch (ImageScalerException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
