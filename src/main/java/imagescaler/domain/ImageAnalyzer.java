package imagescaler.domain;

import java.io.InputStream;

public interface ImageAnalyzer {
    ImageInfo perform(InputStream image) throws ImageScalerException;
}
