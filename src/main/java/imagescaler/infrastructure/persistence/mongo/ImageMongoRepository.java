package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import imagescaler.domain.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageMongoRepository implements ImageRepository {

    private GridFsOperations gridFsOperations;

    @Autowired
    ImageMongoRepository(GridFsOperations gridFsOperations) {
        this.gridFsOperations = gridFsOperations;
    }

    @Override
    public void save(Image image) {
        BasicDBObject metaData = new BasicDBObject();
        metaData.put("name", image.getName());
        metaData.put("uuid", image.getUuid());
        metaData.put("groupUuid", image.getGroupUuid());
        metaData.put("width", image.getImageInfo().getScale().getWidth());
        metaData.put("height", image.getImageInfo().getScale().getHeight());
        metaData.put("contentType", image.getImageInfo().getContentType());
        metaData.put("size", image.getImageInfo().getSize());
        metaData.put("original", image.isOriginal());
        gridFsOperations.store(image.getData(), image.getName(), image.getImageInfo().getContentType(), metaData);
    }

    @Override
    public List<Image> findAll() throws ImageScalerException {

        GridFSFindIterable files = gridFsOperations.find(new Query());
        List<Image> result = new ArrayList<>();

        for (GridFSFile file : files) {

            Document metadata = file.getMetadata();
            Scale scale = new Scale(metadata.getInteger("width"), metadata.getInteger("width"));
            ImageInfo imageInfo = new ImageInfo(metadata.getString("contentType"), scale, metadata.getInteger("size"));

            Image scalerImage = new Image(
                    metadata.getString("name"),
                    imageInfo,
                    null
            );
            scalerImage.setGroupUuid(metadata.getString("groupUuid"));

            try {

                Class scalerImageClazz = scalerImage.getClass();
                Field uuid = scalerImageClazz.getDeclaredField("uuid");
                uuid.setAccessible(true);
                uuid.set(scalerImage, metadata.getString("uuid"));

                Field original = scalerImageClazz.getDeclaredField("original");
                original.setAccessible(true);
                original.set(scalerImage, metadata.getBoolean("original"));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ImageScalerException("Cannot map input data from database:" + e.getMessage());
            }
            result.add(scalerImage);
        }
        return result;
    }
}
