package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.sda.javapoz.model.User;


@Controller
public class LoginController {

        @Autowired
        public LoginController() {
        }

        @GetMapping("/login")
        public String showContact(ModelAndView modelAndView) {
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/login");
            return "login";
    }
}
