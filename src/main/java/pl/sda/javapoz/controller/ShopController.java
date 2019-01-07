package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.FilterProducts;
import pl.sda.javapoz.model.Info;
import pl.sda.javapoz.model.entity.ProductEntity;
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
    public ModelAndView shop(@RequestParam(value = "page", defaultValue = "1", required = false) Integer pageIndex, ModelAndView modelAndView) {
        modelAndView.setViewName("shop");
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
                                      @RequestParam(value = "datefilter", defaultValue = "") String datefilter,
                                      ModelAndView modelAndView) throws ParseException {
        modelAndView.setViewName("shopProducts");
        modelAndView.addObject("product", new ProductEntity());
        modelAndView.addObject("filterProducts", new FilterProducts());
        
        boolean hasNoParameters = "".equals(prodName) && "".equals(datefilter);
        boolean hasOnlyProductName = !"".equals(prodName) && "".equals(datefilter);
        boolean hasOnlyDates = "".equals(prodName) && !"".equals(datefilter);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if (hasNoParameters) {
            modelAndView.addObject("countProducts", productService.countAllProductsByName());
        } else if (hasOnlyProductName) {
            modelAndView.addObject("countProducts", productService.countAllProductsByNameFiltered(prodName));
            modelAndView.addObject("info", new Info("Produkty zawierające frazę: <b>" + prodName + "</b>", true));
        } else if (hasOnlyDates) {
            modelAndView.addObject("countProducts", productService.countAllAvailableProductsByName(datefilter));
            modelAndView.addObject("info", new Info("Produkty dostępne: <b>" + datefilter + "</b>", true));
        } else  {
            modelAndView.addObject("countProducts" , productService.countAllAvailableProductsByNameFiltered(datefilter, prodName));
            modelAndView.addObject("info", new Info("Produkty zawierające frazę: <b>" + prodName + "</b> dostępne: <b>" + datefilter + "</b>", true));
        }
        return modelAndView;
    }
}
