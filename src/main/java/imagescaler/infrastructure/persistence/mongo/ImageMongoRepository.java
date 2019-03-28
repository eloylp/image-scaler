package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Indexes;
import imagescaler.domain.Image;
import imagescaler.domain.ImageNotFoundException;
import imagescaler.domain.ImageRepository;
import imagescaler.domain.ImageScalerException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/// TODO. Increase test coverage on this dependencies.
@Component
public class ImageMongoRepository implements ImageRepository {

    private final GridFsOperations gridFsOperations;
    private final GridFSBucket gridFSBuckets;
    private MetadataMapper metadataMapper;
    private ImageMapper imageMapper;

    @Autowired
    ImageMongoRepository(
            GridFsOperations gridFsOperations,
            MongoDbFactory mongoDbFactory,
            MetadataMapper metadataMapper,
            ImageMapper imageMapper
    ) {
        this.gridFsOperations = gridFsOperations;
        this.gridFSBuckets = GridFSBuckets.create(mongoDbFactory.getDb());
        this.metadataMapper = metadataMapper;
        this.imageMapper = imageMapper;
    }

    @Override
    public void save(Image image) {
        BasicDBObject metaData = this.metadataMapper.perform(image);
        gridFsOperations.store(new ByteArrayInputStream(image.getData()), image.getName().toString(), image.getContentType(), metaData);
    }

    @Override
    public List<Image> findAll() throws ImageScalerException {

        GridFSFindIterable files = gridFsOperations.find(new Query()).sort(Indexes.descending("uploadDate"));
        List<Image> result = new ArrayList<>();

        for (GridFSFile file : files) {
            Image image = this.imageMapper.perform(file, false);
            result.add(image);
        }
        return result;
    }

    @Override
    public Image findByUUID(String uuid) throws ImageScalerException {

        GridFSFile file = gridFsOperations.findOne(new Query().addCriteria(Criteria.where("metadata.uuid").is(uuid)));
        throwImageNotFoundException(uuid, file);
        return this.imageMapper.perform(file, true);
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
