package imagescaler.application.web;

import imagescaler.domain.Image;

public class ImageResponse {

    private Image image;

    ImageResponse(Image image) {
        this.image = image;
    }

    public String getUuid() {
        return image.getUuid().toString();
    }

    public String getGroupUuid() {
        return image.getGroupUuid().toString();
    }

    public String getName() {
        return image.getName().toString();
    }

    public String getContentType() {
        return image.getContentType();
    }

    public int getSize() {
        return image.getSize();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public byte[] getData() {
        return image.getData();
    }

    public boolean isOriginal() {
        return image.isOriginal();
    }
}
