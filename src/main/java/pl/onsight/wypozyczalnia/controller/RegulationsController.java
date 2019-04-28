package pl.onsight.wypozyczalnia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegulationsController {
    @GetMapping("/regulations")
    public ModelAndView regulationsPage(ModelAndView modelAndView) {
        modelAndView.setViewName("regulations");
        return modelAndView;
    }

}
