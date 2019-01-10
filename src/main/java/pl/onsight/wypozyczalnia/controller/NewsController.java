package pl.onsight.wypozyczalnia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.service.NewsService;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;

@Controller
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/{id}")
    public ModelAndView modelAndView(@PathVariable Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("news");
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        NewsEntity news = newsService.findNewsById(id);
        modelAndView.addObject("news", news);

        return modelAndView;
    }

    @GetMapping(value = "/", params = {"tag"})
    public ModelAndView foundNews(@RequestParam(value = "tag", defaultValue = "") String tag, ModelAndView modelAndView, @RequestParam(value = "page", defaultValue = "1", required = false) Integer pageIndex) {
        modelAndView.setViewName("shop");
        modelAndView.addObject("news", new NewsEntity());
        modelAndView.addObject("news", newsService.findNewsByTag(tag));
        modelAndView.addObject("pagination", newsService.getPaginationForPage(pageIndex));
        modelAndView.addObject("tagsLinks", newsService.findAllTag());

        return modelAndView;
    }

    @GetMapping("/newsForm")
    public ModelAndView newsForm(ModelAndView modelAndView) {
        modelAndView.setViewName("newsForm");
        modelAndView.addObject("news", new NewsEntity());
        return modelAndView;
    }

    @PostMapping("/newsForm")
    public ModelAndView addNews(@ModelAttribute NewsEntity news, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/");
        modelAndView.addObject("news", news);
        newsService.addNews(news);
        return modelAndView;
    }
}