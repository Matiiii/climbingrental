package pl.onsight.wypozyczalnia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;

@Service
public class MyUserDetailService implements UserDetailsService {

  private UserService userService;

  @Autowired
  public MyUserDetailService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userService.getUserByEmail(email);
    if (user != null && user.isEnabled()) {
      return new MyUserPrincipal(user);
    }
    throw new UsernameNotFoundException(email);
  }

}
