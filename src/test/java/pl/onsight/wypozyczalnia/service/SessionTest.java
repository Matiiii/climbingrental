package pl.onsight.wypozyczalnia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;

import javax.transaction.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class SessionTest {

  @Autowired
  private SessionService sessionService;

  @Test
  @Transactional
  public void shouldReturnAdmin() {

    //given
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("email", "admin"));
    //when
    UserEntity currentUser = sessionService.getCurrentUser();
    //then
    assertThat(currentUser).isNotNull();
    assertThat(currentUser.getRole().getRole()).isEqualTo("ROLE_ADMIN");

  }

  @Test
  @Transactional
  public void shouldReturnStaff() {

    //given
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("nowak", "nowak"));
    //when

    UserEntity currentUser = sessionService.getCurrentUser();
    //then
    assertThat(currentUser).isNotNull();
    assertThat(currentUser.getRole().getRole()).isEqualTo("ROLE_STAFF");

  }

  @Test
  @Transactional
  public void shouldReturnOrdinaryUser() {

    //given
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("adam", "adam"));
    //when
    UserEntity currentUser = sessionService.getCurrentUser();
    //then
    assertThat(currentUser).isNotNull();
    assertThat(currentUser.getRole().getRole()).isEqualTo("ROLE_USER");

  }


}

