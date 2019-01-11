package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.repository.UserRepository;
import pl.onsight.wypozyczalnia.service.UserService;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveUsers(List<UserEntity> users) {
        users.forEach(this::saveUser);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        Iterable<UserEntity> allUsersIterable = userRepository.findAll();
        List<UserEntity> allUsers = new ArrayList<>();
        allUsersIterable.forEach(allUsers::add);
        return allUsers;
    }


}
