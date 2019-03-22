package imagescaler.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
class ImageController {
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UploadFileResponse status(@RequestParam("file") MultipartFile file, @RequestHeader("Image-Scaler-Image-Name") String name, @RequestHeader("Image-Scaler-Image-Uuid") String uuid) {
        return new UploadFileResponse(uuid, name, "/list", file.getContentType(), file.getSize());
    }
}
