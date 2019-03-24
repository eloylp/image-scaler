package imagescaler.application.web;

import java.io.InputStream;

public class UploadImageRequest {

    private String name;
    private InputStream data;

    public UploadImageRequest(String name, InputStream data) {
        this.name = name;
        this.data = data;
    }

    String getName() {
        return name;
    }

    InputStream getData() {
        return data;
    }
}
