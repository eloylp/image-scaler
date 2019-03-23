package imagescaler.infrastructure.web;

import imagescaler.application.UploadImage;
import imagescaler.application.UploadImageRequest;
import imagescaler.application.UploadImageResponse;
import imagescaler.domain.ImageScalerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
class ImageController {

    private UploadImage uploadImage;

    @Autowired
    ImageController(UploadImage uploadImage) {
        this.uploadImage = uploadImage;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UploadImageResponse status(@RequestParam("image") MultipartFile file,
                                      @RequestParam("name") String name,
                                      HttpServletResponse response
    ) throws IOException, ImageScalerException {
        UploadImageResponse imageResponse = this.uploadImage.perform(new UploadImageRequest(name, file.getInputStream()));
        response.addCookie(new Cookie("Image-Scaler-Last-Upload", imageResponse.getUuid()));
        return imageResponse;
    }
}
