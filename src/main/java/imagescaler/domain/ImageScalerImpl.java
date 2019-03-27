package imagescaler.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageScalerImpl implements ImageScaler {

    private final ImageScalerEngine imageScalerEngine;
    @Autowired
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
