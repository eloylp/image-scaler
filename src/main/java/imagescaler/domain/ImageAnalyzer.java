package imagescaler.domain;

public interface ImageAnalyzer {
    ImageInfo perform(byte[] image) throws ImageScalerException;
}
