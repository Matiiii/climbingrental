package pl.sda.javapoz.service;

import pl.sda.javapoz.model.User;
import pl.sda.javapoz.model.UserRole;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    void saveUsers(List<User> users);

    void addUserWithRole(User user, UserRole userRole);

    User getUserByEmail(String email);
}
