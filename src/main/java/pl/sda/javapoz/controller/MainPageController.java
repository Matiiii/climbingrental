package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.service.NavbarLinkService;

@Controller
public class MainPageController {

    private NavbarLinkService navbarLinkService;

    @Autowired
    public MainPageController(NavbarLinkService navbarLinkService) {
        this.navbarLinkService = navbarLinkService;
    }

    @GetMapping("/single")
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("single");
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
        return modelAndView;
    }
}
