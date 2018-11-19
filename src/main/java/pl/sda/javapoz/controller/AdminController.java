package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.sda.javapoz.model.NavbarLink;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.model.ProductOrder;
import pl.sda.javapoz.service.NavbarLinkService;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.ProductService;

@Controller
public class AdminController {
	
	@Autowired
    private NavbarLinkService navbarLinkService;

	@Autowired
	private ProductOrderService productOrderService;

	@Autowired
	private ProductService productService;

	@GetMapping("/admin-page")
    public ModelAndView showNavbarForm(ModelAndView modelAndView){

    	modelAndView.setViewName("admin");
    	modelAndView.addObject("navbarLink",new NavbarLink());
		modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
		modelAndView.addObject("orderList", productOrderService.findAllProductOrders());
		modelAndView.addObject("productList", productService.findAllProducts());
		modelAndView.addObject("addProduct", new Product());
		return modelAndView;
    }
    
    @PostMapping("/admin-page")
    public ModelAndView postNavbarForm(@ModelAttribute NavbarLink navbarLink, @ModelAttribute Product product, ModelAndView modelAndView){

    	modelAndView.setViewName("redirect:/shop");
		productService.addProductByAdmin(product.getProductName(), product.getPrice(), product.getDescription(), product.getSmallImage(), product.getBigImage(), product.getTags());
    	modelAndView.addObject("addProduct", product);
    	navbarLinkService.save(navbarLink);
    	return modelAndView;
    }

    @RequestMapping("/admin-page-orders")
	public ModelAndView orderList(ProductOrder productOrder){

		ModelAndView modelAndView = new ModelAndView("adminsOrderList");
		modelAndView.addObject("orderList", productOrderService.findAllProductOrders());

		return modelAndView;
	}

    @RequestMapping(path = "/product-order/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeProductOrder(@PathVariable Long id) {

        productOrderService.removeProductOrderByAdmin(id);
        return "order removed";
    }

    @RequestMapping(path = "/product/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String removeProduct(@PathVariable Long id){

		productService.removeProduct(id);
		return  "product removed";
	}

}
