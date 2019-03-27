package pl.onsight.wypozyczalnia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.service.NewsService;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;
import pl.onsight.wypozyczalnia.service.SessionService;

@Controller
public class NewsController {

    private NewsService newsService;
    private SessionService sessionService;

    @Autowired
    public NewsController(NewsService newsService, SessionService sessionService) {
        this.newsService = newsService;
        this.sessionService = sessionService;
    }

    @GetMapping(value = "/")
    public ModelAndView newsPage(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageIndex, ModelAndView modelAndView) {
        modelAndView.setViewName("info");
        modelAndView.addObject("pagination", newsService.getPaginationForPage(pageIndex));
        modelAndView.addObject("news", newsService.findFiveNews(pageIndex));
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        modelAndView.addObject("page", pageIndex);
        return modelAndView;
    }

    @GetMapping("/news/{id}")
    public ModelAndView modelAndView(@PathVariable Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("news");
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        NewsEntity news = newsService.findNewsById(id);
        modelAndView.addObject("news", news);
        return modelAndView;
    }

    @DeleteMapping("/news/remove/{id}")
    public ModelAndView deleteNews(@PathVariable Long id, ModelAndView modelAndView) {
        newsService.removeNews(id);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


    @GetMapping(value = "/", params = {"tag"})
    public ModelAndView foundNews(@RequestParam(value = "tag", defaultValue = "") String tag, ModelAndView modelAndView, @RequestParam(value = "page", defaultValue = "1", required = false) Integer pageIndex) {
        modelAndView.setViewName("info");
        modelAndView.addObject("news", new NewsEntity());
        modelAndView.addObject("news", newsService.findNewsByTag(tag));
        modelAndView.addObject("pagination", newsService.getPaginationForPage(pageIndex));
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        modelAndView.addObject("currentUser", sessionService.getCurrentUser());
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
