package pl.onsight.wypozyczalnia.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ConfirmationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "token_id")
  private Long tokenid;

  @Column(name = "confirmation_token")
  private String confirmationToken;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private UserEntity user;

  public ConfirmationToken() {
  }

  public ConfirmationToken(UserEntity user) {
    this.user = user;
    createdDate = new Date();
    confirmationToken = UUID.randomUUID().toString();
  }

  public Long getTokenid() {
    return tokenid;
  }

  public void setTokenid(Long tokenid) {
    this.tokenid = tokenid;
  }

  public String getConfirmationToken() {
    return confirmationToken;
  }

  public void setConfirmationToken(String confirmationToken) {
    this.confirmationToken = confirmationToken;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
