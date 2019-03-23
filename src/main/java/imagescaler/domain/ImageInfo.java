package imagescaler.domain;

public class ImageInfo {

    private String contentType;
    private Scale scale;
    private int size;

    public ImageInfo(String contentType, Scale scale, int size) {
        this.contentType = contentType;
        this.scale = scale;
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public Scale getScale() {
        return scale;
    }

    public int getSize() {
        return size;
    }
}
