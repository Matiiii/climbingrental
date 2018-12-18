package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.Address;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.model.UserRole;
import pl.sda.javapoz.repository.UserRepository;
import pl.sda.javapoz.repository.UserRoleRepository;
import pl.sda.javapoz.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveUsers(List<User> users){
        users.forEach(this::saveUser);
    }

    @Override
    public void addUserWithRole(User user, UserRole userRole) {
        userRoleRepository.save(userRole);
        user.getRoles().add(userRole);
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
