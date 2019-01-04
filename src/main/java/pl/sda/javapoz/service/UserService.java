package pl.sda.javapoz.service;

import pl.sda.javapoz.model.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(UserEntity user);

    void saveUsers(List<UserEntity> users);

    UserEntity getUserByEmail(String email);

     List<UserEntity> findAllUsers();

}
