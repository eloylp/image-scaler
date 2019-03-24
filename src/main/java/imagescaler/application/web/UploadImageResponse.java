package imagescaler.application.web;

public class UploadImageResponse {

    private String uuid;

    UploadImageResponse(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }
}
