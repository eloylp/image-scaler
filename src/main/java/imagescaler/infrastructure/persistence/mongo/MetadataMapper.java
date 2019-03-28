package imagescaler.infrastructure.persistence.mongo;

import com.mongodb.BasicDBObject;
import imagescaler.domain.Image;
import org.springframework.stereotype.Component;

@Component
public class MetadataMapper {

    public BasicDBObject perform(Image image) {

        BasicDBObject metaData = new BasicDBObject();
        metaData.put("name", image.getName().toString());
        metaData.put("uuid", image.getUuid().toString());
        metaData.put("groupUuid", image.getGroupUuid().toString());
        metaData.put("width", image.getWidth());
        metaData.put("height", image.getHeight());
        metaData.put("contentType", image.getContentType());
        metaData.put("size", image.getSize());
        metaData.put("original", image.isOriginal());

        return metaData;
    }
}
