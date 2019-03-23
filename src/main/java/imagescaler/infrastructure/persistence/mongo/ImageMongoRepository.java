package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.BasicDBObject;
import imagescaler.domain.Image;
import imagescaler.domain.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;

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
        metaData.put("uuid", image.getUuid());
        metaData.put("groupUuid", image.getGroupUuid());
        metaData.put("width", image.getImageInfo().getScale().getWidth());
        metaData.put("height", image.getImageInfo().getScale().getHeight());
        metaData.put("size", image.getImageInfo().getSize());
        metaData.put("original", image.isOriginal());
        gridFsOperations.store(image.getData(), image.getName(), image.getImageInfo().getMimeType(), metaData);
    }
}
