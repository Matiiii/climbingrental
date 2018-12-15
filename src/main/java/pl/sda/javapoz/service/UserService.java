package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.Address;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.model.UserRole;
import pl.sda.javapoz.repository.UserRepository;
import pl.sda.javapoz.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUsers(List<User> users){
        users.forEach(this::saveUser);
    }

    public void addUserWithRole(User user, UserRole userRole) {
        userRoleRepository.save(userRole);
        user.getRoles().add(userRole);
        userRepository.save(user);
    }

    public User getUserByNameAndLastName(String name, String lastName) {
        return userRepository.findUserByFirstNameAndLastName(name, lastName);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
