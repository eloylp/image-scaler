package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import imagescaler.domain.ImageScalerException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageDataGatherer {

    private final GridFSBucket gridFSBuckets;

    @Autowired
    ImageDataGatherer(MongoDbFactory mongoDbFactory) {
        this.gridFSBuckets = GridFSBuckets.create(mongoDbFactory.getDb());
    }

    public byte[] perform(GridFSFile gridFSFile) throws ImageScalerException {
        try {
            byte[] bytes;
            InputStream scalerImageDataStream = this.gridFSBuckets.openDownloadStream(gridFSFile.getObjectId());
            bytes = IOUtils.toByteArray(scalerImageDataStream);
            return bytes;
        } catch (IOException e) {
            throw new ImageScalerException("Cannot retrieve file from storage driver: " + e.getMessage());
        }
    }
}
