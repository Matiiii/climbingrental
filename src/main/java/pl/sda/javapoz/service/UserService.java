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

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(x -> users.add(x));
        return users;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findOne(id);
    }

    public List<User> createMockUser(Boolean isAdmin) {
        List<User> mockUsers = new ArrayList<>();

        User u1 = new User();
        Address address1 = new Address();
        address1.setCity("Poznan");
        address1.setZipcode("61-142");
        address1.setFlatNumber("15");
        address1.setStreet("Maltanska");
        u1.setAddress(address1);

        u1.setFirstName("Andrzej");
        u1.setLastName("Kowalski");
        u1.setAdmin(isAdmin);
        u1.setEmail("email");
        u1.setPassword(passwordEncoder.encode("haslo"));
        u1.setPhoneNumber("666 746 666");

        Set<UserRole> roles = new HashSet<>();
        UserRole role1 = new UserRole();
        role1.setRole("ROLE_ADMIN");
        roles.add(role1);
        u1.setRoles(roles);

        u1.setAdmin(true);
        mockUsers.add(u1);

        User u2 = new User();
        Address address = new Address();
        address.setCity("Poznan");
        address.setZipcode("60-100");
        address.setFlatNumber("8");
        address.setStreet("Baraniaka");
        u2.setAddress(address);

        u2.setFirstName("Janina");
        u2.setLastName("Nowak");
        u2.setEmail("janina@gmail.com");
        u2.setPassword(passwordEncoder.encode("haslo"));
        u2.setPhoneNumber("123 456 789");

        UserRole role2 = new UserRole();
        role2.setRole("ROLE_USER");
        roles.add(role2);
        u2.setRoles(roles);

        u2.setAdmin(false);
        mockUsers.add(u2);
        return mockUsers;
    }

    public User getMockUserFromDatabase() {
        return userRepository.findOne(1l);
    }

    public void addUserWitRole(User user, UserRole userRole) {

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
