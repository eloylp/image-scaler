package imagescaler.infrastructure.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class TemplateController {
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }
}
