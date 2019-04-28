package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.NewsService;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.validator.ProductValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private ProductOrderService productOrderService;
    private ProductService productService;
    private ProductValidator productValidator;
    private NewsService newsService;
    private UserService userService;

    @Autowired
    public AdminController(ProductOrderService productOrderService, ProductService productService, ProductValidator productValidator, NewsService newsService, UserService userService) {
        this.productOrderService = productOrderService;
        this.productService = productService;
        this.productValidator = productValidator;
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/admin-page")
    public ModelAndView adminPage(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        modelAndView.addObject("orderList", productOrderService.findAllProductOrders());
        modelAndView.addObject("productList", productService.findAllProducts());
        modelAndView.addObject("usersList", userService.findAllUsers());
        modelAndView.addObject("addProduct", new ProductEntity());
        modelAndView.addObject("news", new NewsEntity());
        return modelAndView;
    }

    @DeleteMapping(path = "/product-order/{id}")
    @ResponseBody
    public void removeProductOrder(@PathVariable Long id) {
        productOrderService.removeProductOrder(id);
    }


    @PostMapping("/admin-page")
    public ModelAndView addProduct(@ModelAttribute @Valid ProductEntity product,
                                   BindingResult bindingResult, ModelAndView modelAndView) {
        List<ProductEntity> allProducts = productService.findAllProducts();
        List<String> allNamesOfProducts = allProducts.stream().map(name -> name.getProductName()).collect(Collectors.toList());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin");
        } else if (allNamesOfProducts.contains(product.getProductName())) {
            modelAndView.addObject("info", new Info("Produkt o nazwie " + product.getProductName() + " już istnieje!", false));
            return adminPage(modelAndView);
        } else {
            productService.addProduct(product);
            modelAndView.setViewName("redirect:/shop");
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditFormForProduct(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("editProduct");
        ProductEntity product = productService.findProductById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id, @Valid ProductEntity product,
                                    ModelAndView modelAndView) {
        if (productValidator.isNameUsed(product)) {
            modelAndView.addObject("info", new Info("Produkt o nazwie " + product.getProductName() + " już istnieje!", false));
            adminPage(modelAndView);
        } else {
            productService.addProduct(product);
            adminPage(modelAndView);
            modelAndView.setViewName("redirect:/shop");
        }
        return modelAndView;
    }

    @GetMapping("/editUsers/{id}")
    public ModelAndView showEditFormForUser(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("editUser");
        UserEntity user = userService.getUserById(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/editUsers/{id}")
    public ModelAndView editUser(@Valid UserEntity user,
                                 ModelAndView modelAndView) {
        userService.editUser(user);
        adminPage(modelAndView);
        modelAndView.setViewName("redirect:/shop");
        return modelAndView;
    }

    @GetMapping("/editNews/{id}")
    public ModelAndView showEditFormForNews(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("newsForm");
        NewsEntity news = newsService.findNewsById(id);
        modelAndView.addObject("news", news);
        return modelAndView;
    }


    @GetMapping("/addNews")
    public ModelAndView newsPage(ModelAndView modelAndView) {
        modelAndView.setViewName("newsForm");
        modelAndView.addObject("news", new NewsEntity());
        return modelAndView;
    }

    @PostMapping("/addNews")
    public ModelAndView addNews(@ModelAttribute @Valid NewsEntity news,
                                BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin");
        } else {
            newsService.addNews(news);
            modelAndView.setViewName("shop");
        }
        return modelAndView;
    }

}


