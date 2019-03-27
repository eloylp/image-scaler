package imagescaler.infrastructure.persistence.mongo;

import java.io.InputStream;

public class ImageStream {
    private final String contentType;
    private final int contentLength;
    private final InputStream data;

    ImageStream(String contentType, int contentLength, InputStream data) {
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getData() {
        return data;
    }

    public int getContentLength() {
        return contentLength;
    }
}
