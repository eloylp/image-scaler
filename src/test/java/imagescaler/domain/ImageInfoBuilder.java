package imagescaler.domain;

public class ImageInfoBuilder {

    private String contentType;
    private Scale scale;
    private int size;

    public ImageInfoBuilder(String contentType, Scale scale, int size) {
        this.contentType = contentType;
        this.scale = scale;
        this.size = size;
    }

    public ImageInfo build() {
        return new ImageInfo(contentType, scale, size);
    }
}
