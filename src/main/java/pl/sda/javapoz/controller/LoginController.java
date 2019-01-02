package pl.sda.javapoz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.User;


@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView showContact(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
