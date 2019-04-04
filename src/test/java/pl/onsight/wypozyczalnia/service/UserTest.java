package pl.onsight.wypozyczalnia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class UserTest {

  @Autowired
  UserService userService;

  @Test
  @Transactional
  public void shouldFindAllUsers(){
    //given
    //data from MySQL Database
    //when
    List<UserEntity> allUsersBefore=new ArrayList<>();
    List<UserEntity> allUsers=userService.findAllUsers();
    //then
    assertThat(allUsers).isNotNull();
    assertThat(allUsers).isNotEqualTo(allUsersBefore);
    assertThat(allUsers.size()>0);
  }

/* @Test
  @Transactional
  public void shouldFindUserByEmail(){
    //given
    UserRoleEntity roleEntity=new UserRoleEntity();
    roleEntity.setRole("ROLE_ADMIN");
    roleEntity.setDiscount(0);

    UserEntity userEntity=new UserEntity();
    userEntity.setEmail("janusz@gmail.com");
    userEntity.setPassword("admin");
    userEntity.setRole(roleEntity);
    userService.saveUser(userEntity);

    String mail="janusz@gmail.com";
    //when
    UserEntity userWithMail=userService.getUserByEmail(mail);
    //then
    assertThat(userWithMail).isNotNull();
    assertThat(userEntity.getFirstName()).isEqualTo(userWithMail.getFirstName());
    assertThat(userEntity.getLastName()).isEqualTo(userWithMail.getLastName());
    assertThat(userEntity.getEmail()).isEqualTo(userWithMail.getEmail());
  }*/
}
