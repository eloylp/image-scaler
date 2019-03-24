package imagescaler.infrastructure.persistence.mongo;

import java.io.InputStream;

public class ImageStream {
    private String contentType;
    private int contentLength;
    private InputStream data;

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
