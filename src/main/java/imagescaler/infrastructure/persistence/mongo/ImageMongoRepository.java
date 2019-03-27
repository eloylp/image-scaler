package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Indexes;
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

    private final GridFsOperations gridFsOperations;
    private final GridFSBucket gridFSBuckets;

    @Autowired
    ImageMongoRepository(GridFsOperations gridFsOperations, MongoDbFactory mongoDbFactory) {
        this.gridFsOperations = gridFsOperations;
        this.gridFSBuckets = GridFSBuckets.create(mongoDbFactory.getDb());
    }

    @Override
    public void save(Image image) {
        BasicDBObject metaData = new BasicDBObject();
        metaData.put("name", image.getName().toString());
        metaData.put("uuid", image.getUuid().toString());
        metaData.put("groupUuid", image.getGroupUuid().toString());
        metaData.put("width", image.getWidth());
        metaData.put("height", image.getHeight());
        metaData.put("contentType", image.getContentType());
        metaData.put("size", image.getSize());
        metaData.put("original", image.isOriginal());
        gridFsOperations.store(new ByteArrayInputStream(image.getData()), image.getName().toString(), image.getContentType(), metaData);
    }

    @Override
    public List<Image> findAll() throws ImageScalerException {

        GridFSFindIterable files = gridFsOperations.find(new Query()).sort(Indexes.descending("uploadDate"));
        List<Image> result = new ArrayList<>();

        for (GridFSFile file : files) {
            Image scalerImage = mapImage(file, false);
            result.add(scalerImage);
        }
        return result;
    }

    private Image mapImage(GridFSFile gridFSFile, boolean mapData) throws ImageScalerException {
        Document metadata = gridFSFile.getMetadata();
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
        scalerImage = new ImageBuilder(
                metadata.getString("name"),
                metadata.getString("contentType"),
                metadata.getInteger("width"),
                metadata.getInteger("height"),
                metadata.getInteger("size"),
                scalerImageData)
                .withGroup(metadata.getString("groupUuid"))
                .build();
        hydrateHiddenFields(metadata, scalerImage);
        return scalerImage;
    }

    private void hydrateHiddenFields(Document metadata, Image scalerImage) throws ImageScalerException {
        try {

            Class scalerImageClazz = scalerImage.getClass();
            Field uuid = scalerImageClazz.getDeclaredField("uuid");
            uuid.setAccessible(true);
            uuid.set(scalerImage, new Uuid(metadata.getString("uuid")));

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
        throwImageNotFoundException(uuid, file);
        return mapImage(file, true);
    }

    private void throwImageNotFoundException(String uuid, GridFSFile file) throws ImageNotFoundException {
        if (file == null) {
            throw new ImageNotFoundException("Cannot find image with uuid: " + uuid);
        }
    }

    public ImageStream findByUUIDForStream(String uuid) throws ImageScalerException {
        GridFSFile file = gridFsOperations.findOne(new Query().addCriteria(Criteria.where("metadata.uuid").is(uuid)));
        throwImageNotFoundException(uuid, file);
        InputStream imageStream = this.gridFSBuckets.openDownloadStream(file.getObjectId());
        Document metadata = file.getMetadata();
        return new ImageStream(metadata.getString("contentType"), metadata.getInteger("size"), imageStream);
    }
}
