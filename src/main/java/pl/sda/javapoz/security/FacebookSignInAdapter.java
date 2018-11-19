package pl.sda.javapoz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.model.UserRole;
import pl.sda.javapoz.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pablo on 24.03.17.
 */
@Service
public class FacebookSignInAdapter implements SignInAdapter {

    public FacebookSignInAdapter(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserService userService;

    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        String[] split = connection.getDisplayName().split(" ");
        User user = userService.getUserByNameAndLastName(split[0], split[1]);
        org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), convertAuthorities(user.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        springUser, null,
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));

        return null;
    }

    private Set<GrantedAuthority> convertAuthorities(Set<UserRole> userRoles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRole ur : userRoles) {
            authorities.add(new SimpleGrantedAuthority(ur.getRole()));
        }
        return authorities;
    }
}
