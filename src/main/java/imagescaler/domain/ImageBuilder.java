package imagescaler.domain;


public class ImageBuilder {

    private final Name name;
    private Uuid groupUuid;
    private final ImageInfo imageInfo;
    private final byte[] data;


    public ImageBuilder(String name, String contentType, int width, int height, int size, byte[] data) throws ImageScalerException {
        this.name = new Name(name);
        this.imageInfo = new ImageInfo(
                new ContentType(contentType),
                new Scale(width, height), size
        );
        this.data = data;
    }

    public ImageBuilder(Name name, String contentType, int width, int height, int size, byte[] data) throws ImageScalerException {
        this.name = name;
        this.imageInfo = new ImageInfo(
                new ContentType(contentType),
                new Scale(width, height), size
        );
        this.data = data;
    }

    public ImageBuilder(Name name, ImageInfo imageInfo, byte[] data) {
        this.name = name;
        this.imageInfo = imageInfo;
        this.data = data;
    }

    public ImageBuilder withGroup(String groupUuid) {
        this.groupUuid = new Uuid(groupUuid);
        return this;
    }

    public Image build() {
        Image image = new Image(name, imageInfo, data);
        if (groupUuid != null) {
            image.setGroupUuid(groupUuid);
        }
        return image;
    }
}
