package imagescaler.domain;

public class ImageInfo {

    private final ContentType contentType;
    private final Scale scale;
    private final int size;


    public ImageInfo(ContentType contentType, Scale scale, int size) {
        this.contentType = contentType;
        this.scale = scale;
        this.size = size;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public Scale getScale() {
        return scale;
    }

    public int getSize() {
        return size;
    }
}
