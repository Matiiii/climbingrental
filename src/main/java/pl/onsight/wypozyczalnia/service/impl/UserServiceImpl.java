package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.model.entity.UserRoleEntity;
import pl.onsight.wypozyczalnia.repository.UserRepository;
import pl.onsight.wypozyczalnia.repository.UserRoleRepository;
import pl.onsight.wypozyczalnia.service.RoleService;
import pl.onsight.wypozyczalnia.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveUserByRegistration(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getPassword()));
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setDiscount(0);
        userRoleEntity.setRole("ROLE_USER");
        userRoleRepository.save(userRoleEntity);
        user.setRole(userRoleEntity);
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
    public UserEntity getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        Iterable<UserEntity> allUsersIterable = userRepository.findAll();
        List<UserEntity> allUsers = new ArrayList<>();
        allUsersIterable.forEach(allUsers::add);
        return allUsers;
    }

    @Override
    public void editUser(UserEntity user) {
        roleService.saveRole(user.getRole());
        user.setRole(user.getRole());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setAddress(user.getAddress());
        user.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(user);

    }


}
