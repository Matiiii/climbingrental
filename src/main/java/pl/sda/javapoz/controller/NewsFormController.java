package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.News;
import pl.sda.javapoz.repository.NewsRepository;

@Controller
public class NewsFormController {

    private NewsRepository newsRepository;

    @Autowired
    public NewsFormController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/newsForm")
    public ModelAndView showNews(ModelAndView modelAndView) {
        modelAndView.addObject("news", new News());
        return modelAndView;
    }

    @PostMapping("/newsForm")
    public ModelAndView newsForm(@ModelAttribute News news, ModelAndView modelAndView) {
        modelAndView.addObject("news", news);
        newsRepository.save(news);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
