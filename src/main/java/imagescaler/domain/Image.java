package imagescaler.domain;

import java.io.InputStream;

public class Image {

    private String uuid;
    private String groupUuid;
    private String name;
    private ImageInfo imageInfo;
    private InputStream data;
    private boolean original;

    public Image(String name, ImageInfo imageInfo, InputStream data) {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.groupUuid = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.imageInfo = imageInfo;
        this.data = data;
        this.original = false;
    }

    public String getUuid() {
        return uuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getName() {
        return name;
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public InputStream getData() {
        return data;
    }

    public boolean isOriginal() {
        return original;
    }

    public void markAsOriginal() {
        this.original = true;
    }
}
