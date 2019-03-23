package imagescaler.domain;

import java.io.BufferedInputStream;

public interface ImageAnalyzer {
    ImageInfo perform(BufferedInputStream image) throws ImageScalerException;
}
