package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.repository.UserRepository;
import pl.onsight.wypozyczalnia.service.CartService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.service.impl.SessionServiceImpl;
import pl.onsight.wypozyczalnia.validator.DateValidator;
import pl.onsight.wypozyczalnia.validator.InputValidator;

import java.text.ParseException;

@Controller
@SessionAttributes("cart")
public class ShopController {

  private ProductService productService;
  private CartService cartService;
  private DateValidator dateValidator;
  private InputValidator inputValidator;
  private UserRepository userRepository;

  @Autowired
  public ShopController(ProductService productService, CartService cartService, UserRepository userRepository, DateValidator dateValidator,
                        InputValidator inputValidator) {
    this.productService = productService;
    this.cartService = cartService;
    this.dateValidator = dateValidator;
    this.inputValidator = inputValidator;
    this.userRepository = userRepository;
  }

  @GetMapping(value = "/shop")
  public ModelAndView foundProducts(@RequestParam(value = "productName", defaultValue = "") String prodName,
                                    @RequestParam(value = "datefilter", defaultValue = "") String dateFilter,
                                    @ModelAttribute("cart") Cart cart,
                                    ModelAndView modelAndView) throws ParseException {
    modelAndView.setViewName("shop");

    if (!dateFilter.isEmpty() && !dateValidator.isDateValid(dateFilter)) {
      modelAndView.addObject("info", new Info("Data niepoprawna!", false));
      return modelAndView;
    }
    if (!prodName.isEmpty() && !inputValidator.isInputValid(prodName)) {
      modelAndView.addObject("info", new Info("Użyto niedozwolonych znaków!", false));
      return modelAndView;
    }
    boolean hasNoParameters = prodName.equals("") && dateFilter.equals("") && cart.getDate() == null;
    boolean hasOnlyProductName = !prodName.equals("") && dateFilter.equals("") && cart.getDate() == null;
    boolean hasOnlyDates = prodName.equals("") && (!dateFilter.equals("") || cart.getDate() != null);

    if (hasNoParameters) {
      modelAndView.addObject("countProducts", productService.countAllProductsByName());
    } else if (hasOnlyProductName) {
      modelAndView.addObject("countProducts", productService.countAllProductsByNameFiltered(prodName));
      if (productService.countAllProductsByNameFiltered(prodName).size() > 0) {
        modelAndView.addObject("info", new Info("Produkty zawierające frazę: <b>" + prodName + "</b>", true));
      } else {
        modelAndView.addObject("info", new Info("Nie znaleziono produktów zawierających frazę: <b>" + prodName + "</b>", false));
      }
    } else if (hasOnlyDates) {
      if (dateFilter.isEmpty()) {
        dateFilter = cart.getDate();
      }
      modelAndView.addObject("countProducts", productService.countAllAvailableProductsByName(dateFilter));
      modelAndView.addObject("info", new Info("Produkty dostępne: <b>" + dateFilter + "</b>", true));
    } else {
      if (dateFilter.isEmpty()) {
        dateFilter = cart.getDate();
      }
      modelAndView.addObject("countProducts", productService.countAllAvailableProductsByNameFiltered(dateFilter, prodName));
      if (productService.countAllAvailableProductsByNameFiltered(dateFilter, prodName).size() > 0) {
        modelAndView.addObject("info", new Info("Produkty zawierające frazę: <b>" + prodName + "</b> dostępne: <b>" + dateFilter + "</b>", true));
      } else {
        modelAndView.addObject("info", new Info("Nie znaleziono produktów zawierających frazę: <b>" + prodName + "</b>", false));
      }
    }

    cartService.addDateToCart(cart, dateFilter);
    return modelAndView;
  }

  @PostMapping("/shop/{id}")
  public ModelAndView addProductToCart(@PathVariable Long id,
                                       @RequestParam(value = "productCount") Integer productCount,
                                       @ModelAttribute("cart") Cart cart,
                                       RedirectAttributes attributes,
                                       ModelAndView modelAndView) throws ParseException {
    modelAndView.setViewName("info");
    ProductEntity productById = productService.findProductById(id);

    if (productCount == null || productCount < 1) {
      modelAndView.addObject("info", new Info("Nieprawidłowa ilość ", false));
    } else {
      modelAndView.addObject("info", new Info("Dodano do koszyka <b>" + productCount + " x " + productById.getProductName() + "</b>", true));
      cartService.addProductToCart(cart, productById, productCount);
    }
    attributes.addFlashAttribute("cart", cart);

    if (cart.getDate() != null) {
      return foundProducts("", cart.getDate(), cart, modelAndView);
    }
    return foundProducts("", "", cart, modelAndView);
  }

  @ModelAttribute("cart")
  public Cart cart() {
    return new Cart();
  }
}
