package pl.sda.javapoz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by pablo on 24.03.17.
 */
@Controller
public class LoginController {

    @GetMapping("login")
    public String login() {


        return "/login";
    }

}
