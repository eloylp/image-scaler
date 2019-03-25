package imagescaler.infrastructure.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class TemplateController {

    @Value("${scaler.app.name}")
    private String appName;

    @GetMapping("/upload")
    public String upload(Model model) {
        ModelAndView mav = new ModelAndView("upload");
        model.addAttribute("appName", appName);
        return "upload";
    }

    @GetMapping("/")
    public String index(Model model) {
        ModelAndView mav = new ModelAndView("index");
        model.addAttribute("appName", appName);
        return "index";
    }
}
