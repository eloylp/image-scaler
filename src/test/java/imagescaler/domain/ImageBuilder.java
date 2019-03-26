package imagescaler.domain;

public class ImageBuilder {

    private String name;
    private ImageInfo imageInfo;
    private byte[] data;

    public ImageBuilder(String name, ImageInfo imageInfo, byte[] data) {
        this.name = name;
        this.imageInfo = imageInfo;
        this.data = data;
    }

    public Image build() {
        return new Image(name, imageInfo, data);
    }
}
