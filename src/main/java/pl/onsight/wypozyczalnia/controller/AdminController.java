package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

@Controller
public class AdminController {

    private ProductOrderService productOrderService;
    private ProductService productService;

    @Autowired
    public AdminController(ProductOrderService productOrderService, ProductService productService) {
        this.productOrderService = productOrderService;
        this.productService = productService;
    }

    @GetMapping("/admin-page")
    public ModelAndView adminPage(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        modelAndView.addObject("orderList", productOrderService.findAllProductOrders());
        modelAndView.addObject("productList", productService.findAllProducts());
        modelAndView.addObject("addProduct", new ProductEntity());
        return modelAndView;
    }

    @PostMapping("/admin-page")
    public ModelAndView addProduct(@ModelAttribute ProductEntity product, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/shop");
        productService.addProductByAdmin(product.getProductName(), product.getPrice(), product.getDescription(), product.getSmallImage(), product.getBigImage(), product.getTags(), product.getQuantity());
        modelAndView.addObject("addProduct", product);
        return modelAndView;
    }

    @DeleteMapping(path = "/product-order/{id}")
    @ResponseBody
    public void removeProductOrder(@PathVariable Long id) {
        productOrderService.removeProductOrder(id);
    }

    @DeleteMapping(path = "/product/{id}")
    @ResponseBody
    public void removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
    }
}
