package imagescaler.domain;

public class ImageInfo {

    private String mimeType;
    private Scale scale;
    private int size;

    public ImageInfo(String mimeType, Scale scale, int size) {
        this.mimeType = mimeType;
        this.scale = scale;
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Scale getScale() {
        return scale;
    }

    public int getSize() {
        return size;
    }
}
