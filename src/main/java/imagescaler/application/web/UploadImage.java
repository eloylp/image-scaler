package imagescaler.application.web;

import imagescaler.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.IOException;

@Component
public class UploadImage {

    private final ImageAnalyzer imageAnalyzer;
    private final ImageRepository imageRepository;
    private final ImageEnqueuer imageEnqueuer;

    @Autowired
    UploadImage(ImageAnalyzer imageAnalyzer, ImageRepository imageRepository, ImageEnqueuer imageEnqueuer) {
        this.imageAnalyzer = imageAnalyzer;
        this.imageRepository = imageRepository;
        this.imageEnqueuer = imageEnqueuer;
    }

    public UploadImageResponse perform(UploadImageRequest uploadImageRequest) throws ImageScalerException {

        try {

            byte[] imageData = new byte[uploadImageRequest.getData().available()];
            DataInputStream dataInputStream = new DataInputStream(uploadImageRequest.getData());
            dataInputStream.readFully(imageData);
            ImageInfo imageInfo = this.imageAnalyzer.perform(imageData);
            Image image = new Image(
                    uploadImageRequest.getName(),
                    imageInfo,
                    imageData
            );
            image.markAsOriginal();
            this.imageRepository.save(image);
            this.imageEnqueuer.perform(image);
            return new UploadImageResponse(image.getUuid().toString(), image.getGroupUuid().toString());
        } catch (IOException e) {
            throw new ImageScalerException("Cannot read image stream in upload.");
        }
    }
}
