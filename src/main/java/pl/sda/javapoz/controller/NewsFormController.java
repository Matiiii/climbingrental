package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.News;
import pl.sda.javapoz.repository.NewsRepository;
import pl.sda.javapoz.service.NavbarLinkService;
import pl.sda.javapoz.service.NewsService;

/**
 * Created by RENT on 2017-03-25.
 */
@Controller
public class NewsFormController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NavbarLinkService navbarLinkService;

    @GetMapping("newsForm")
    public ModelAndView showNews (ModelAndView modelAndView){
        modelAndView.addObject("navbarLinks",navbarLinkService.fetchLinks());
        modelAndView.addObject("news",new News());
        return modelAndView;
    }

    @PostMapping("newsForm")
    public ModelAndView newsForm (@ModelAttribute News news , ModelAndView modelAndView){
        modelAndView.addObject("news",news);
        newsRepository.save(news);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
