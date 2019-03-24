package imagescaler.application;

import imagescaler.domain.*;

import java.util.List;

public class ScaleImage {

    private ScalesMapper scalesMapper;
    private ImageScaler imageScaler;
    private ImageRepository imageRepository;

    ScaleImage(ScalesMapper scalesMapper, ImageScaler imageScaler, ImageRepository imageRepository) {
        this.scalesMapper = scalesMapper;
        this.imageScaler = imageScaler;
        this.imageRepository = imageRepository;
    }

    void perform(String uuid, String scales) throws ImageScalerException {

        List<Scale> desiredScales = this.scalesMapper.map(scales);
        Image originalImage = this.imageRepository.findByUUID(uuid);
        List<Image> scaledImages = this.imageScaler.scaleImageTo(originalImage, desiredScales);

        for (Image scaledImage : scaledImages) {
            this.imageRepository.save(scaledImage);
        }
    }
}
