package imagescaler.application;

import java.io.BufferedInputStream;

public class UploadImageRequest {

    private String name;
    private BufferedInputStream data;

    public UploadImageRequest(String name, BufferedInputStream data) {
        this.name = name;
        this.data = data;
    }

    String getName() {
        return name;
    }

    BufferedInputStream getData() {
        return data;
    }
}
