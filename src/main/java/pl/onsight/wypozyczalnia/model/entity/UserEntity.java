package pl.onsight.wypozyczalnia.model.entity;


import javax.persistence.*;

@Entity(name = "user")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.PERSIST)
  private AddressEntity address;

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String password;

  @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "role", nullable = false)
  private UserRoleEntity role;


  public UserEntity() {
  }


  public UserEntity(AddressEntity address, String firstName, String lastName, String email, String phoneNumber, String password, UserRoleEntity role) {
    this.address = address;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
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


