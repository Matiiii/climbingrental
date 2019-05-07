package pl.onsight.wypozyczalnia.model.entity;


import pl.onsight.wypozyczalnia.model.listeners.InsertListener;
import pl.onsight.wypozyczalnia.model.listeners.UpdateListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "user")
@EntityListeners({UpdateListener.class, InsertListener.class})
public class UserEntity extends AbstractEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="user_id")
  private Long id;

  @OneToOne(cascade = CascadeType.PERSIST)
  private AddressEntity address;

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String password;
  private String confirmPassword;
  private boolean isActivated;

  @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "role", nullable = false)
  private UserRoleEntity role;


  public UserEntity() {
  }

  public UserEntity(AddressEntity address, String firstName, String lastName, String email, String phoneNumber, String password, String confirmPassword, boolean isActivated, UserRoleEntity role) {
    this.address = address;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.isActivated = isActivated;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AddressEntity getAddress() {
    return address;
  }

  public void setAddress(AddressEntity address) {
    this.address = address;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRoleEntity getRole() {
    return role;
  }

  public void setRole(UserRoleEntity role) {
    this.role = role;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public boolean isActivated() {
    return isActivated;
  }

  public void setActivated(boolean activated) {
    isActivated = activated;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
      "id=" + id +
      ", address=" + address +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", email='" + email + '\'' +
      ", phoneNumber='" + phoneNumber + '\'' +
      ", password='" + password + '\'' +
      ", role=" + role +
      '}';
  }
}


