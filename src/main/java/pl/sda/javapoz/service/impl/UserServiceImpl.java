package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.VerificationTokenEntity;
import pl.sda.javapoz.model.entity.UserEntity;
import pl.sda.javapoz.repository.UserRepository;
import pl.sda.javapoz.repository.UserRoleRepository;
import pl.sda.javapoz.service.UserService;

import java.util.ArrayList;
import java.util.List;


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
    public void saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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

    @Override
    public void createVerificationToken(UserEntity user, String token) {

    }

    @Override
    public VerificationTokenEntity getVerificationToken(String VerificationToken) {
        return null;
    }


}
