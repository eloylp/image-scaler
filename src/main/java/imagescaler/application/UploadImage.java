package imagescaler.application;

import imagescaler.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadImage {

    private final ImageAnalyzer imageAnalyzer;
    private final ImageRepository imageRepository;

    @Autowired
    UploadImage(ImageAnalyzer imageAnalyzer, ImageRepository imageRepository) {
        this.imageAnalyzer = imageAnalyzer;
        this.imageRepository = imageRepository;
    }

    public UploadImageResponse perform(UploadImageRequest uploadImageRequest) throws ImageScalerException {

        ImageInfo imageInfo = this.imageAnalyzer.perform(uploadImageRequest.getData());
        Image image = new Image(
                uploadImageRequest.getName(),
                imageInfo,
                uploadImageRequest.getData()
        );
        image.markAsOriginal();
        this.imageRepository.save(image);
        return new UploadImageResponse(image.getUuid());
    }
}
