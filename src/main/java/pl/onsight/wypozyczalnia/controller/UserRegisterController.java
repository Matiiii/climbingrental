package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;

import javax.validation.Valid;

@Controller
public class UserRegisterController {

    private UserService userService;

    @Autowired
    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView registrationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("user", new UserEntity());
        return modelAndView;
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid UserEntity user,
                          BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "register";
        } else {
            userService.saveUser(user);
            return "redirect:/login";
        }
    }
}
