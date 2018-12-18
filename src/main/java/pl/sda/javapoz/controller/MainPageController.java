package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    @Autowired
    public MainPageController() {

    }

    @GetMapping("/single")
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("single");
        return modelAndView;
    }
}
