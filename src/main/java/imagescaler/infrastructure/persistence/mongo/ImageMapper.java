package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.client.gridfs.model.GridFSFile;
import imagescaler.domain.Image;
import imagescaler.domain.ImageBuilder;
import imagescaler.domain.ImageScalerException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    private final ImageDataGatherer imageDataGatherer;
    private final ImageFieldsOverrider imageFieldsOverrider;

    @Autowired
    ImageMapper(ImageDataGatherer imageDataGatherer, ImageFieldsOverrider imageFieldsOverrider) {
        this.imageDataGatherer = imageDataGatherer;
        this.imageFieldsOverrider = imageFieldsOverrider;
    }

    Image perform(GridFSFile gridFSFile, boolean mapData) throws ImageScalerException {

        Document metadata = gridFSFile.getMetadata();
        Image image;
        byte[] scalerImageData = null;
        if (mapData) {
            scalerImageData = this.imageDataGatherer.perform(gridFSFile);
        }

        image = new ImageBuilder(
                metadata.getString("name"),
                metadata.getString("contentType"),
                metadata.getInteger("width"),
                metadata.getInteger("height"),
                metadata.getInteger("size"),
                scalerImageData)
                .withGroup(metadata.getString("groupUuid"))
                .build();

        this.imageFieldsOverrider.perform(metadata, image);
        return image;
    }
}
