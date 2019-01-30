package pl.onsight.wypozyczalnia.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.validator.RegisterValidator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class RegisterValidatorImpl implements RegisterValidator {

    private UserService userService;


    @Autowired
    public RegisterValidatorImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isEmailTaken(UserEntity userEntity) {
        String email = userEntity.getEmail();
        List<UserEntity> allUsers = userService.findAllUsers();
        List<String> allEmails = allUsers.stream().map(UserEntity::getEmail).collect(Collectors.toList());
        return allEmails.contains(email);
    }


}
