package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.NewsEntity;
import pl.sda.javapoz.service.NewsService;

@Controller
public class NewsFormController {

    private NewsService newsService;

    @Autowired
    public NewsFormController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/newsForm")
    public ModelAndView showNews(ModelAndView modelAndView) {
        modelAndView.setViewName("newsForm");
        modelAndView.addObject("news", new NewsEntity());
        return modelAndView;
    }

    @PostMapping("/newsForm")
    public ModelAndView newsForm(@ModelAttribute NewsEntity news, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/");
        modelAndView.addObject("news", news);
        newsService.addNews(news);
        return modelAndView;
    }

}
