package pl.sda.javapoz.service;

import pl.sda.javapoz.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    void saveUsers(List<User> users);

    User getUserByEmail(String email);
}
