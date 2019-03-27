package imagescaler.application.web;

public class UploadImageResponse {

    private final String uuid;
    private final String groupUuid;

    UploadImageResponse(String uuid, String groupUuid) {
        this.uuid = uuid;
        this.groupUuid = groupUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }
}
