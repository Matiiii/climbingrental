package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ModelAndView showEditForm(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("editProduct");
        ProductEntity product = productService.findProductById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id, @Valid ProductEntity product,
                                    ModelAndView modelAndView) {
        List<ProductEntity> allProducts = productService.findAllProducts();
        List<String> allNamesOfProducts = allProducts.stream().map(name -> name.getProductName()).collect(Collectors.toList());
        String tempName = productService.findProductById(id).getProductName();
        String newName = product.getProductName();
        allNamesOfProducts.remove(tempName);
        if (allNamesOfProducts.contains(newName)) {
            modelAndView.addObject("info", new Info("Produkt o nazwie " + product.getProductName() + " już istnieje!", false));
            adminPage(modelAndView);
        } else {
            productService.addProduct(product);
            adminPage(modelAndView);
            modelAndView.setViewName("redirect:/shop");
        }
        return modelAndView;
    }
}


