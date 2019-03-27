package imagescaler.domain;

public class Image {

    private final Uuid uuid;
    private Uuid groupUuid;
    private final String name;
    private final ImageInfo imageInfo;
    private final byte[] data;
    private boolean original;

    public Image(String name, ImageInfo imageInfo, byte[] data) {
        this.uuid = new Uuid();
        this.groupUuid = new Uuid();
        this.name = name;
        this.imageInfo = imageInfo;
        this.data = data;
        this.original = false;
    }

    public Uuid getUuid() {
        return uuid;
    }

    public Uuid getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(Uuid groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return imageInfo.getContentType().toString();
    }

    public int getSize() {
        return imageInfo.getSize();
    }

    public int getHeight() {
        return imageInfo.getScale().getHeight();
    }

    public int getWidth() {
        return imageInfo.getScale().getWidth();
    }

    public byte[] getData() {
        return data;
    }

    public boolean isOriginal() {
        return original;
    }

    public void markAsOriginal() {
        this.original = true;
    }
}
