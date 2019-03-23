package imagescaler.application;

import imagescaler.domain.Image;
import imagescaler.domain.ImageRepository;
import imagescaler.domain.ImageScalerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListImages {
    private ImageRepository imageRepository;

    @Autowired
    ListImages(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> perform() throws ImageScalerException {
        return this.imageRepository.findAll();
    }
}
