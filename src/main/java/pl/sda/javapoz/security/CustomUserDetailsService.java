package pl.sda.javapoz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.model.UserRole;
import pl.sda.javapoz.repository.UserRepository;
import pl.sda.javapoz.service.NavbarLinkService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pablo on 23.03.17.
 */
public class CustomUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;
    private NavbarLinkService navbarLinkService;

    @Autowired
    public void setNavbarLinkService(NavbarLinkService navbarLinkService) {
        this.navbarLinkService = navbarLinkService;
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        convertAuthorities(user.getRoles()));
        navbarLinkService.afterLogin();
        return userDetails;
    }

    private Set<GrantedAuthority> convertAuthorities(Set<UserRole> userRoles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(UserRole ur: userRoles) {
            authorities.add(new SimpleGrantedAuthority(ur.getRole()));
        }
        return authorities;
    }
}
