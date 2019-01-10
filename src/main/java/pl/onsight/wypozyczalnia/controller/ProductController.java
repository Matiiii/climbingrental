package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.service.SessionService;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.service.CartService;

import java.util.List;

@Controller
@SessionAttributes("cart")
public class ProductController {

    private ProductService productService;
    private ProductOrderService productOrderService;
    private SessionService sessionService;
    private CartService cartService;

    @Autowired
    public ProductController(ProductService productService, ProductOrderService productOrderService, SessionService sessionService, CartService cartService) {
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.sessionService = sessionService;
        this.cartService = cartService;
    }

    @GetMapping("/product/{id}")
    public ModelAndView productPage(@PathVariable Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        modelAndView.addObject("product", productService.findProductById(id));
        modelAndView.addObject("productOrder", new ProductOrderEntity());
        modelAndView.addObject("tags", productService.findRelatedProducts(productService.findProductById(id)));
        modelAndView.addObject("cart", cartService.getCart());
        String dateFilter = "";

        if(dateFilter.isEmpty()){
            modelAndView.addObject("countAvailableProducts", productService.countAllProductsByNameFiltered(productService.findProductById(id).getProductName()));
        }else {
            modelAndView.addObject("countAvailableProducts", productService.countAllAvailableProductsByNameFiltered(dateFilter ,productService.findProductById(id).getProductName()));

        }

        return modelAndView;
    }

    @PostMapping("/product/{id}")
    public RedirectView addProductToCart(@PathVariable Long id,
                                         @RequestParam(value = "productCount") Integer productCount,
                                         @RequestParam(value = "productName") String productName,
                                         @ModelAttribute("cart") Cart cart,
                                         RedirectAttributes attributes,
                                         ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        ProductEntity productById = productService.findProductById(id);


        if (productCount == null || productCount < 1) {
            modelAndView.addObject("info", new Info("Nieprawidłowa ilość", false));
        } else {
            modelAndView.addObject("info", new Info("Dodano do koszyka " + productCount + " " + productName, true));
            //cartService.addProductToCart(productById, productCount);
            cart.getProducts().add(productById);
        }
        attributes.addFlashAttribute("cart", cart);
        return new RedirectView("/cart.html");
        //return productPage(id, modelAndView);
    }

    @GetMapping("/products-availability/{id}")
    @ResponseBody
    public List<String> getAvailabilitiesForProduct(@PathVariable Long id) {
        return productOrderService.getListOfDatesWhenProductIsReserved(id);
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }
}