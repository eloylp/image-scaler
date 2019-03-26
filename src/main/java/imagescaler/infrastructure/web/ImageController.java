package imagescaler.infrastructure.web;

import imagescaler.application.web.ListImages;
import imagescaler.application.web.UploadImage;
import imagescaler.application.web.UploadImageRequest;
import imagescaler.application.web.UploadImageResponse;
import imagescaler.domain.Image;
import imagescaler.domain.ImageNotFoundException;
import imagescaler.domain.ImageScalerException;
import imagescaler.infrastructure.persistence.mongo.ImageMongoRepository;
import imagescaler.infrastructure.persistence.mongo.ImageStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

@RestController
class ImageController {

    private UploadImage uploadImage;
    private ListImages listImages;
    private ImageMongoRepository imageMongoRepository;

    @Autowired
    ImageController(UploadImage uploadImage, ListImages listImages, ImageMongoRepository imageMongoRepository) {
        this.uploadImage = uploadImage;
        this.listImages = listImages;
        this.imageMongoRepository = imageMongoRepository;
    }

    @PostMapping("/images")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UploadImageResponse status(@RequestParam("image") MultipartFile image,
                                      @RequestParam("name") String name,
                                      HttpServletResponse response
    ) throws IOException, ImageScalerException {
        BufferedInputStream imageData = new BufferedInputStream(image.getInputStream());
        UploadImageResponse imageResponse = this.uploadImage.perform(new UploadImageRequest(name, imageData));
        response.addCookie(new Cookie("Image-Scaler-Last-Uploaded-Group", imageResponse.getGroupUuid()));
        return imageResponse;
    }

    @GetMapping("/images")
    @ResponseStatus(HttpStatus.OK)
    public List<Image> images() throws ImageScalerException {
        return this.listImages.perform();
    }

    @GetMapping("/images/{uuid}")
    public void image(@PathVariable("uuid") String uuid, HttpServletResponse response) throws ImageScalerException, IOException {
        try {
            ImageStream image = this.imageMongoRepository.findByUUIDForStream(uuid);
            response.addHeader("Content-Type", image.getContentType());
            response.addHeader("Content-Length", Integer.toString(image.getContentLength()));
            IOUtils.copy(image.getData(), response.getOutputStream());
            response.flushBuffer();
        } catch (ImageNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
