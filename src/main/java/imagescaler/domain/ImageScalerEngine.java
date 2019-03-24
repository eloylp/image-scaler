package imagescaler.domain;

public interface ImageScalerEngine {

    Image scaleImageTo(Image original, Scale scale) throws ImageScalerException;
}
