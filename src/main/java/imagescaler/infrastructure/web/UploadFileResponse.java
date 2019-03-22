package imagescaler.infrastructure.web;

class UploadFileResponse {

    private String uuid;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    UploadFileResponse(String uuid, String fileName, String fileDownloadUri, String fileType, long size) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public long getSize() {
        return size;
    }
}
