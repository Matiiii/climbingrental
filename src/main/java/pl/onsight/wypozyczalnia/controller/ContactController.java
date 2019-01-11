package pl.onsight.wypozyczalnia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public ModelAndView contactPage(ModelAndView modelAndView) {
        modelAndView.setViewName("contact");
        return modelAndView;
    }

}
