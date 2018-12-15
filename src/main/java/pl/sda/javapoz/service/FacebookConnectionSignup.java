package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.Address;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.model.UserRole;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    private UserService userService;

    @Autowired
    public FacebookConnectionSignup(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(Connection<?> connection) {
        Facebook facebook = (Facebook) connection.getApi();
        org.springframework.social.facebook.api.User profile = facebook.userOperations().getUserProfile();
        User user = new User();
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setEmail(profile.getEmail());
        user.setAdmin(false);
        user.setPassword(randomAlphabetic(8));
        Address address = new Address("N/A", "N/A", "N/A", "N/A");
        user.setAddress(address);
        user.setPhoneNumber("N/A");
        UserRole role = new UserRole();
        role.setRole("ROLE_USER");
        userService.addUserWithRole(user, role);
        return user.getFirstName() + " " + user.getLastName();
    }
}
