package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.FilterProducts;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.service.NewsService;
import pl.sda.javapoz.service.ProductService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class ShopController {

    private NewsService newsService;
    private ProductService productService;

    @Autowired
    public ShopController(NewsService newsService, ProductService productService) {
        this.newsService = newsService;
        this.productService = productService;
    }

    @GetMapping(value = "/")
    public ModelAndView shop(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageIndex) {
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("pagination", newsService.getPaginationForPage(pageIndex));
        modelAndView.addObject("news", newsService.findFiveNews(pageIndex));
        modelAndView.addObject("tagsLinks", newsService.findAllTag());
        modelAndView.addObject("page", pageIndex);
        return modelAndView;
    }


    @GetMapping(value = "/shop")
    public ModelAndView foundProducts(@RequestParam(value = "productName", defaultValue = "") String prodName,
                                      @RequestParam(value = "orderStart", defaultValue = "") String orderStart,
                                      @RequestParam(value = "orderEnd", defaultValue = "") String orderEnd,
                                      ModelAndView modelAndView) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        modelAndView.setViewName("shopProducts");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("filterProducts", new FilterProducts());

        if ("".equals(prodName) && "".equals(orderStart) && "".equals(orderEnd)) {
            modelAndView.addObject("countProducts", productService.countAllProductsByName());
        } else if (!"".equals(prodName) && "".equals(orderStart) && "".equals(orderEnd)) {
            modelAndView.addObject("countProducts", productService.countAllProductsByNameFiltered(prodName));
        } else if ("".equals(prodName) && !"".equals(orderStart) && !"".equals(orderEnd)) {
            modelAndView.addObject("countProducts", productService.countAllAvailableProductsByName(formatter.parse(orderStart),
                    formatter.parse(orderEnd)));
        } else if (!"".equals(prodName) && !"".equals(orderStart) && !"".equals(orderEnd)) {
            modelAndView.addObject("countProducts", productService.countAllAvailableProductsByNameFiltered(formatter.parse(orderStart),
                    formatter.parse(orderEnd), prodName));
        }

        return modelAndView;
    }
}
