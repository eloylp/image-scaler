package imagescaler.domain;

public class ImageInfoBuilder {

    private final String contentType;
    private final Scale scale;
    private final int size;

    public ImageInfoBuilder(String contentType, Scale scale, int size) {
        this.contentType = contentType;
        this.scale = scale;
        this.size = size;
    }

    public ImageInfo build() throws ImageScalerException {
        return new ImageInfo(new ContentType(contentType), scale, size);
    }
}
