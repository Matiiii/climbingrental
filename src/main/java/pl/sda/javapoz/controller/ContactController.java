package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.service.NavbarLinkService;

/**
 * Created by RENT on 2017-03-27.
 */
@Controller
public class ContactController {

    @Autowired
    private NavbarLinkService navbarLinkService;

    @GetMapping("/contact")
    public ModelAndView showContact(ModelAndView modelAndView){
        modelAndView.addObject("navbarLinks",navbarLinkService.fetchLinks());
        modelAndView.setViewName("/contact");
        return modelAndView;
    }

}
