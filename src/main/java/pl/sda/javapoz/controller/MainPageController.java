package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.service.NavbarLinkService;

/**
 * Created by RENT on 2017-03-22.
 */
@RestController
public class MainPageController {


    @Autowired
    private NavbarLinkService navbarLinkService;

    @RequestMapping("/single")
        public ModelAndView mainPage() {
            ModelAndView modelAndView = new ModelAndView("single");
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
            return modelAndView;
        }
}
