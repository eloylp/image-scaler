package imagescaler.application.web;

import imagescaler.domain.Image;
import imagescaler.domain.ImageRepository;
import imagescaler.domain.ImageScalerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListImages {
    private final ImageRepository imageRepository;

    @Autowired
    ListImages(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<ImageResponse> perform() throws ImageScalerException {

        List<Image> images = this.imageRepository.findAll();
        List<ImageResponse> imageResponses = new ArrayList<>();
        for (Image image : images) {
            imageResponses.add(new ImageResponse(image));
        }
        return imageResponses;
    }
}
