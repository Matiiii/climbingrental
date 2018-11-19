package pl.sda.javapoz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.News;
import pl.sda.javapoz.service.NavbarLinkService;
import pl.sda.javapoz.service.NewsService;

/**
 * Created by RENT on 2017-03-22.
 */
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NavbarLinkService navbarLinkService;

    @RequestMapping("/news/{id}")
    public ModelAndView modelAndView(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("news");
        modelAndView.addObject("navbarLinks",navbarLinkService.fetchLinks());
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        News news = newsService.findNewsById(id);
        modelAndView.addObject("news", news);
        if (news == null) {
            modelAndView = null;
        }
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = {"tag"})
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