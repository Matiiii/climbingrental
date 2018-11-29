package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.sda.javapoz.service.NavbarLinkService;


@Controller
public class LoginController {
	
        private NavbarLinkService navbarLinkService;

        @Autowired
        public LoginController(NavbarLinkService navbarLinkService) {
            this.navbarLinkService = navbarLinkService;
        }

        @GetMapping("/login")
        public String showContact(ModelAndView modelAndView) {
            modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
            modelAndView.setViewName("/login");
            return "login";
    }
}
