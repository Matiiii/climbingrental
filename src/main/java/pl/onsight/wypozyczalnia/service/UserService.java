package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.entity.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(UserEntity user);

    void saveUserByRegistration(UserEntity user);

    void saveUsers(List<UserEntity> users);

    UserEntity getUserByEmail(String email);

    UserEntity getUserById (Long id);

    List<UserEntity> findAllUsers();

    void editUser(UserEntity user);


}
