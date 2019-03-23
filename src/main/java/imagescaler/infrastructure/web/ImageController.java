package imagescaler.infrastructure.web;

import imagescaler.application.ListImages;
import imagescaler.application.UploadImage;
import imagescaler.application.UploadImageRequest;
import imagescaler.application.UploadImageResponse;
import imagescaler.domain.Image;
import imagescaler.domain.ImageScalerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

@RestController
class ImageController {

    private UploadImage uploadImage;
    private ListImages listImages;

    @Autowired
    ImageController(UploadImage uploadImage, ListImages listImages) {
        this.uploadImage = uploadImage;
        this.listImages = listImages;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UploadImageResponse status(@RequestParam("image") MultipartFile image,
                                      @RequestParam("name") String name,
                                      HttpServletResponse response
    ) throws IOException, ImageScalerException {
        BufferedInputStream imageData = new BufferedInputStream(image.getInputStream());
        UploadImageResponse imageResponse = this.uploadImage.perform(new UploadImageRequest(name, imageData));
        response.addCookie(new Cookie("Image-Scaler-Last-Upload", imageResponse.getUuid()));
        return imageResponse;
    }

    @GetMapping("/images")
    @ResponseStatus(HttpStatus.OK)
    public List<Image> images() throws ImageScalerException {
        return this.listImages.perform();
    }
}
