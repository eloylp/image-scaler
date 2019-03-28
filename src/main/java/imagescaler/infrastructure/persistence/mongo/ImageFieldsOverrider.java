package imagescaler.infrastructure.persistence.mongo;

import imagescaler.domain.Image;
import imagescaler.domain.ImageScalerException;
import imagescaler.domain.Uuid;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class ImageFieldsOverrider {

    public void perform(Document metadata, Image image) throws ImageScalerException {
        try {

            Class scalerImageClazz = image.getClass();
            Field uuid = scalerImageClazz.getDeclaredField("uuid");
            uuid.setAccessible(true);
            uuid.set(image, new Uuid(metadata.getString("uuid")));

            Field original = scalerImageClazz.getDeclaredField("original");
            original.setAccessible(true);
            original.set(image, metadata.getBoolean("original"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ImageScalerException("Cannot perform input scalerImageData from database:" + e.getMessage());
        }
    }

}
