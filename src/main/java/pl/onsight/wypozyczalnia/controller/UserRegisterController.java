package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.validator.RegisterValidator;

import javax.validation.Valid;


@Controller
public class UserRegisterController {

    private UserService userService;
    private RegisterValidator registerValidator;

    @Autowired
    public UserRegisterController(UserService userService, RegisterValidator registerValidator) {
        this.userService = userService;
        this.registerValidator = registerValidator;
    }

    @GetMapping("/register")
    public ModelAndView registrationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        modelAndView.addObject("user", new UserEntity());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView addUser(@ModelAttribute @Valid UserEntity user,
                                BindingResult bindResult, ModelAndView modelAndView) {
        if (bindResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else if (registerValidator.isEmailTaken(user)) {
            modelAndView.addObject("emailIsTaken", new Info("Użytkownik o podanym mailu już istniej", true));
            modelAndView.addObject("emailIsTakenInfo", new Info("Wprowadź inny mail", true));
            return registrationPage(modelAndView);
        } else {
            modelAndView.setViewName("redirect:/login");
            userService.saveUser(user);
        }

        return modelAndView;
    }
}
