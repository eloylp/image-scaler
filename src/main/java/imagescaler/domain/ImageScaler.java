package imagescaler.domain;

import java.util.List;

public interface ImageScaler {

    List<Image> scaleImageTo(Image original, List<Scale> scales) throws ImageScalerException;
}
