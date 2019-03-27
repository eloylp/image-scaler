package imagescaler.domain;

import java.util.Arrays;

public class ContentType {

    private String value;
    private final String[] allowedContentTypes = {"image/png", "image/jpeg", "image/jpg"};

    public ContentType(String value) throws ImageScalerException {
        checkContentType(value);
        this.value = value;
    }

    private void checkContentType(String contentType) throws ImageScalerException {

        for (String allowedContentType : allowedContentTypes) {
            if (allowedContentType.equals(contentType)) {
                return;
            }
        }
        throw new ImageScalerException("This content type is not allowed. Allowed types are: " + Arrays.toString(allowedContentTypes));
    }

    @Override
    public String toString() {
        return value;
    }
}
