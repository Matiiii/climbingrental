package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.ConfirmationToken;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.repository.ConfirmationTokenRepository;
import pl.onsight.wypozyczalnia.repository.UserRepository;
import pl.onsight.wypozyczalnia.service.EmailSenderService;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.validator.RegisterValidator;

import javax.validation.Valid;


@Controller
public class UserRegisterController {

  private UserService userService;
  private RegisterValidator registerValidator;
  private ConfirmationTokenRepository confirmationTokenRepository;
  private EmailSenderService emailSenderService;
  private UserRepository userRepository;

  @Autowired
  public UserRegisterController(UserService userService, RegisterValidator registerValidator, ConfirmationTokenRepository confirmationTokenRepository, EmailSenderService emailSenderService, UserRepository userRepository) {
    this.userService = userService;
    this.registerValidator = registerValidator;
    this.confirmationTokenRepository = confirmationTokenRepository;
    this.emailSenderService = emailSenderService;
    this.userRepository = userRepository;
  }


  @GetMapping("/register")
  public ModelAndView registrationPage(ModelAndView modelAndView) {
    modelAndView.setViewName("register");
    modelAndView.addObject("user", new UserEntity());
    return modelAndView;
  }

  @PostMapping("/register")
  public ModelAndView addUser(@ModelAttribute @Valid UserEntity user,
                              BindingResult bindResult, ModelAndView modelAndView) {
    if (bindResult.hasErrors()) {
      modelAndView.setViewName("register");
    } else if (registerValidator.isEmailTaken(user)) {
      modelAndView.addObject("info", new Info("Użytkownik o podanym mailu " + user.getEmail() + " już istnieje! ", false));
      return registrationPage(modelAndView);
    } else {
      userService.saveUserByRegistration(user);
      ConfirmationToken confirmationToken = new ConfirmationToken(user);
      confirmationTokenRepository.save(confirmationToken);
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(user.getEmail());
      mailMessage.setSubject("Rejestracja zakończona!");
      mailMessage.setFrom("climbingrental@gmail.com");
      mailMessage.setText("Żeby potwierdzić nowo utworzone konto, proszę kliknąc w poniższy link : "
        + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

      emailSenderService.sendEmail(mailMessage);
      modelAndView.addObject("emailToConfirm", user.getEmail());
      modelAndView.setViewName("succesfullRegistration");
    }

    return modelAndView;
  }

  @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
    ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

    if (token != null) {
      UserEntity user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
      user.setActivated(true);
      userService.saveUserByRegistration(user);
      modelAndView.setViewName("successRegistration");
    } else {
      modelAndView.addObject("message", "Link jest zepsuty bądź nieprawidłowy!");
      modelAndView.setViewName("error");
    }

    return modelAndView;
  }
}
