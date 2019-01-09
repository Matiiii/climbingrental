package pl.sda.javapoz.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.sda.javapoz.model.entity.UserEntity;
import pl.sda.javapoz.model.entity.UserRoleEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MyUserPrincipal implements UserDetails {

    private UserEntity user;

    public MyUserPrincipal(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new LinkedList<>();
        Set<UserRoleEntity> userRoles = user.getRoles();
        for(UserRoleEntity role: userRoles){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
