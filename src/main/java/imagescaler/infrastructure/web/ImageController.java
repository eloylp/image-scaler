package imagescaler.infrastructure.web;

import imagescaler.domain.Image;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload() {

    }

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public Image images() {
        return new Image("image", new Byte[1]);
    }
}
