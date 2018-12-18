package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @Autowired
    public ContactController() {

    }

    @GetMapping("/contact")
    public ModelAndView showContact(ModelAndView modelAndView) {
        modelAndView.setViewName("/contact");
        return modelAndView;
    }

}
