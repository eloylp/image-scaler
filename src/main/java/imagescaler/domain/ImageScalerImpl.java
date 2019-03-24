package imagescaler.domain;


import java.util.ArrayList;
import java.util.List;

public class ImageScalerImpl implements ImageScaler {

    private ImageScalerEngine imageScalerEngine;

    ImageScalerImpl(ImageScalerEngine imageScalerEngine) {
        this.imageScalerEngine = imageScalerEngine;
    }

    @Override
    public List<Image> scaleImageTo(Image original, List<Scale> scales) throws ImageScalerException {

        List<Image> result = new ArrayList<>();

        for (Scale scale : scales) {

            Image scaledImage = this.imageScalerEngine.scaleImageTo(original, scale);
            scaledImage.setGroupUuid(original.getGroupUuid());
            result.add(scaledImage);
        }
        return result;
    }
}
