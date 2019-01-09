package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.FilterProducts;
import pl.sda.javapoz.model.Info;
import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.service.CartService;
import pl.sda.javapoz.service.NewsService;
import pl.sda.javapoz.service.ProductService;

@Controller
public class ShopController {

    private NewsService newsService;
    private ProductService productService;
    private CartService cartService;

    @Autowired
    public ShopController(NewsService newsService, ProductService productService, CartService cartService) {

        this.newsService = newsService;
        this.productService = productService;
        this.cartService = cartService;
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
                                      @RequestParam(value = "datefilter", defaultValue = "") String dateFilter,
                                      ModelAndView modelAndView) {
        modelAndView.setViewName("shopProducts");
        modelAndView.addObject("product", new ProductEntity());
        modelAndView.addObject("filterProducts", new FilterProducts());

        boolean hasNoParameters = "".equals(prodName) && "".equals(dateFilter);
        boolean hasOnlyProductName = !"".equals(prodName) && "".equals(dateFilter);
        boolean hasOnlyDates = "".equals(prodName) && !"".equals(dateFilter);

        if (hasNoParameters) {
            modelAndView.addObject("countProducts", productService.countAllProductsByName());
        } else if (hasOnlyProductName) {
            modelAndView.addObject("countProducts", productService.countAllProductsByNameFiltered(prodName));
            modelAndView.addObject("info", new Info("Produkty zawierające frazę: <b>" + prodName + "</b>", true));
        } else if (hasOnlyDates) {
            modelAndView.addObject("countProducts", productService.countAllAvailableProductsByName(dateFilter));
            modelAndView.addObject("info", new Info("Produkty dostępne: <b>" + dateFilter + "</b>", true));
        } else {
            modelAndView.addObject("countProducts", productService.countAllAvailableProductsByNameFiltered(dateFilter, prodName));
            modelAndView.addObject("info", new Info("Produkty zawierające frazę: <b>" + prodName + "</b> dostępne: <b>" + dateFilter + "</b>", true));
        }
        return modelAndView;
    }

    @PostMapping("/shop/{id}")
    public ModelAndView addProductToCart(@PathVariable Long id,
                                         @RequestParam(value = "productCount") Integer productCount,
                                         ModelAndView modelAndView) {
        modelAndView.setViewName("shop");
        ProductEntity productById = productService.findProductById(id);

        if (productCount == null || productCount < 1) {
            modelAndView.addObject("info", new Info("Nieprawidłowa ilość ", false));
        } else {
            modelAndView.addObject("info", new Info("Dodano do koszyka " + productCount + " " + productById.getProductName(), true));
            cartService.addProductToCart(productById, productCount);
        }
        return foundProducts("", "", modelAndView);
    }
}
