package pl.onsight.wypozyczalnia.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user_role")
public class UserRoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String role;
  private double discount;
  @OneToMany(mappedBy = "role")
  private List<UserEntity> users;


  public UserRoleEntity() {
  }


  public UserRoleEntity(String role, double discount, List<UserEntity> users) {
    this.role = role;
    this.discount = discount;
    this.users = users;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }

  public List<UserEntity> getUsers() {
    return users;
  }

  public void setUsers(List<UserEntity> users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return "UserRoleEntity{" +
      "id=" + id +
      ", role='" + role + '\'' +
      ", discount=" + discount +
      ", users=" + users +
      '}';
  }
}
