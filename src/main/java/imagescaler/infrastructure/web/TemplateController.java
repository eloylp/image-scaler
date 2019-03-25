package imagescaler.infrastructure.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class TemplateController {

    @Value("${scaler.app.name}")
    private String appName;

    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("appName", appName);
        return "upload";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", appName);
        return "index";
    }
}
