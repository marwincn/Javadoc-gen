package cn.marwin.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    Configuration configuration;

    @RequestMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("projects", configuration.getProjects());
        return "index";
    }
}
