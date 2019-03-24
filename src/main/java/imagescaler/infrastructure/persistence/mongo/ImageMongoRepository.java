package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import imagescaler.domain.*;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// TODO. Must be refactored and tested with extracting map logic in mappers.
@Component
public class ImageMongoRepository implements ImageRepository {

    private GridFsOperations gridFsOperations;
    private GridFSBucket gridFSBuckets;

    @Autowired
    ImageMongoRepository(GridFsOperations gridFsOperations, MongoDbFactory mongoDbFactory) {
        this.gridFsOperations = gridFsOperations;
        this.gridFSBuckets = GridFSBuckets.create(mongoDbFactory.getDb());
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
        gridFsOperations.store(new ByteArrayInputStream(image.getData()), image.getName(), image.getImageInfo().getContentType(), metaData);
    }

    @Override
    public List<Image> findAll() throws ImageScalerException {

        GridFSFindIterable files = gridFsOperations.find(new Query());
        List<Image> result = new ArrayList<>();

        for (GridFSFile file : files) {
            Image scalerImage = mapImage(file, false);
            result.add(scalerImage);
        }
        return result;
    }

    private Image mapImage(GridFSFile gridFSFile, boolean mapData) throws ImageScalerException {
        Document metadata = gridFSFile.getMetadata();
        Scale scale = new Scale(metadata.getInteger("width"), metadata.getInteger("width"));
        ImageInfo imageInfo = new ImageInfo(metadata.getString("contentType"), scale, metadata.getInteger("size"));
        Image scalerImage;
        byte[] scalerImageData = null;
        if (mapData) {
            try {
                InputStream scalerImageDataStream = this.gridFSBuckets.openDownloadStream(gridFSFile.getObjectId());
                scalerImageData = IOUtils.toByteArray(scalerImageDataStream);
            } catch (IOException e) {
                throw new ImageScalerException("Cannot retrieve file from storage driver: " + e.getMessage());
            }
        }

        scalerImage = new Image(
                metadata.getString("name"),
                imageInfo,
                scalerImageData
        );
        scalerImage.setGroupUuid(metadata.getString("groupUuid"));
        hydrateHiddenFields(metadata, scalerImage);
        return scalerImage;
    }

    private void hydrateHiddenFields(Document metadata, Image scalerImage) throws ImageScalerException {
        try {

            Class scalerImageClazz = scalerImage.getClass();
            Field uuid = scalerImageClazz.getDeclaredField("uuid");
            uuid.setAccessible(true);
            uuid.set(scalerImage, metadata.getString("uuid"));

            Field original = scalerImageClazz.getDeclaredField("original");
            original.setAccessible(true);
            original.set(scalerImage, metadata.getBoolean("original"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ImageScalerException("Cannot map input scalerImageData from database:" + e.getMessage());
        }
    }

    @Override
    public Image findByUUID(String uuid) throws ImageScalerException {

        GridFSFile file = gridFsOperations.findOne(new Query().addCriteria(Criteria.where("metadata.uuid").is(uuid)));
        if (file == null) {
            throw new ImageNotFoundException("Cannot find image with uuid: " + uuid);
        }
        return mapImage(file, true);
    }

    public ImageStream findByUUIDForStream(String uuid) throws ImageScalerException {
        GridFSFile file = gridFsOperations.findOne(new Query().addCriteria(Criteria.where("metadata.uuid").is(uuid)));
        if (file == null) {
            throw new ImageNotFoundException("Cannot find image with uuid: " + uuid);
        }
        InputStream imageStream = this.gridFSBuckets.openDownloadStream(file.getObjectId());
        Document metadata = file.getMetadata();
        return new ImageStream(metadata.getString("contentType"), metadata.getInteger("size"), imageStream);
    }
}
