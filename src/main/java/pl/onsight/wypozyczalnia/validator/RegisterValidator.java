package pl.onsight.wypozyczalnia.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.UserService;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegisterValidator {


    private UserService userService;

    @Autowired
    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean isEmailTaken(UserEntity userEntity) {
        String email = userEntity.getEmail();
        List<UserEntity> allUsers = userService.findAllUsers();
        List<String> allEmails = allUsers.stream().map(UserEntity::getEmail).collect(Collectors.toList());
        return allEmails.contains(email);
    }
}
