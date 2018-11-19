package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.FilterProducts;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.service.NavbarLinkService;
import pl.sda.javapoz.service.NewsService;
import pl.sda.javapoz.service.ProductService;
import pl.sda.javapoz.service.SessionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by RENT on 2017-03-22.
 */
@Controller
public class ShopController {

    @Autowired
    private NavbarLinkService navbarLinkService;

    @Autowired
    NewsService newsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SessionService sessionService;


    @RequestMapping("/")
    public String homePage(){
        return "redirect:shop";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView shop(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageIndex) {
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
        modelAndView.addObject("pagination", newsService.getPaginationForPage(pageIndex));
        modelAndView.addObject("news", newsService.findFiveNews(pageIndex));
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        modelAndView.addObject("page", pageIndex);
        return modelAndView;

    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public ModelAndView foundProducts(@RequestParam(value="productName", defaultValue = "") String prodName,
                                      @RequestParam(value="orderStart", defaultValue = "") String orderStart,
                                      @RequestParam(value="orderEnd", defaultValue = "")  String orderEnd,
                                      ModelAndView modelAndView) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        modelAndView.setViewName("shopProducts");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
        modelAndView.addObject("filterProducts", new FilterProducts());

        if("".equals(prodName) && "".equals(orderStart) && "".equals(orderEnd) ){
            modelAndView.addObject("countProducts", productService.contAllProdactsByName());
        }
        else if (!"".equals(prodName) && "".equals(orderStart) && "".equals(orderEnd)){
            modelAndView.addObject("countProducts", productService.contAllProdactsByNameFitered(prodName));
        }
        else if ("".equals(prodName) && !"".equals(orderStart) && !"".equals(orderEnd)) {
            modelAndView.addObject("countProducts", productService.contAllAvailableProdactsByName(formatter.parse(orderStart),
                    formatter.parse(orderEnd)));
        }
        else if (!"".equals(prodName) && !"".equals(orderStart) && !"".equals(orderEnd)) {
            modelAndView.addObject("countProducts", productService.contAllAvailableProdactsByNameFiltered(formatter.parse(orderStart),
                    formatter.parse(orderEnd),prodName));
        }

        return modelAndView;
    }
}
