package imagescaler.domain;

public class ImageBuilder {

    private final Name name;
    private final ImageInfo imageInfo;
    private final byte[] data;


    public ImageBuilder(Name name, ImageInfo imageInfo, byte[] data) {
        this.name = name;
        this.imageInfo = imageInfo;
        this.data = data;
    }

    public ImageBuilder(String name, ImageInfo imageInfo, byte[] data) throws ImageScalerException {
        this.name = new Name(name);
        this.imageInfo = imageInfo;
        this.data = data;
    }

    public Image build() {
        return new Image(name, imageInfo, data);
    }
}
