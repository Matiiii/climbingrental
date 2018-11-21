package pl.sda.javapoz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.News;
import pl.sda.javapoz.service.NavbarLinkService;
import pl.sda.javapoz.service.NewsService;

@Controller
public class NewsController {

    private NewsService newsService;
    private NavbarLinkService navbarLinkService;

    @Autowired
    public NewsController(NewsService newsService, NavbarLinkService navbarLinkService) {
        this.newsService = newsService;
        this.navbarLinkService = navbarLinkService;
    }

    @GetMapping("/news/{id}")
    public ModelAndView modelAndView(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("news");
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        News news = newsService.findNewsById(id);
        modelAndView.addObject("news", news);
        if (news == null) {
            modelAndView = null;
        }
        return modelAndView;
    }

    @GetMapping(value = "/", params = {"tag"})
    public ModelAndView foundNews(@RequestParam(value = "tag", defaultValue = "") String tag, ModelAndView modelAndView, @RequestParam(value = "page", defaultValue = "1", required = false) Integer pageIndex) {
        modelAndView.setViewName("shop");
        modelAndView.addObject("news", new News());
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
        modelAndView.addObject("news", newsService.findNewsByTag(tag));
        modelAndView.addObject("pagination", newsService.getPaginationForPage(pageIndex));
        modelAndView.addObject("tagsLinks", newsService.findAllTag());

        return modelAndView;
    }
}