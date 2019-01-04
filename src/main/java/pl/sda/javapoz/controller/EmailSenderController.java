package pl.sda.javapoz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.sda.javapoz.service.EmailSender;

import javax.mail.MessagingException;


@Controller
public class EmailSenderController {

    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    public EmailSenderController(EmailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }


    @RequestMapping("/")
    public String send() throws MessagingException {
        Context context = new Context();
        context.setVariable("header", "Nowy artykuł na CodeCouple");
        context.setVariable("title", "#8 Spring Boot – email - szablon i wysyłanie");
        context.setVariable("description", "Tutaj jakis opis...");
        String body = templateEngine.process("template", context);
        emailSender.sendEmail("your.email.here@gmail.com", "CodeCouple Newsletter", body);
        return "index";
    }
}
