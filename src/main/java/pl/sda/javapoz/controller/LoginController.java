package pl.sda.javapoz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.UserEntity;


@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        modelAndView.addObject("user", new UserEntity());
        return modelAndView;
    }
}
