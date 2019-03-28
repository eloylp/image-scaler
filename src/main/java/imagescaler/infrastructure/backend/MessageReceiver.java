package imagescaler.infrastructure.backend;

import imagescaler.application.scaler.ScaleImage;
import imagescaler.domain.ImageScalerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    private final ScaleImage scaleImage;
    private final StringRedisTemplate redisTemplate;

    @Value("${scaler.target.scales}")
    private String scales;
    @Value("${scaler.topic}")
    private String topic;

    @Autowired
    MessageReceiver(ScaleImage scaleImage, StringRedisTemplate redisTemplate) {
        this.scaleImage = scaleImage;
        this.redisTemplate = redisTemplate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    public void receiveMessage(String message) {
        LOGGER.info("Processing image " + message);
        try {
            this.scaleImage.perform(message, scales);
            LOGGER.info("Image " + message + " processed.");
        } catch (ImageScalerException e) {
            this.redisTemplate.convertAndSend(this.topic, message);
            LOGGER.error("Returning image to queue due to error : " + e.getMessage());
        }
    }
}
