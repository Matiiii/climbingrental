package pl.onsight.wypozyczalnia.model.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity(name = "product")
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String productName;
  private Double price;
  @Column(length = 10000)
  private String description;
  private String smallImage;
  private String bigImage;
  private String tags;
  private Integer quantity;
  private Double deposit;
  private Boolean isAvailable;
  private Boolean isStaffMember;

  public ProductEntity() {
  }

  public ProductEntity(String productName, Double price, String description, String smallImage, String bigImage, String tags, Integer quantity, Double deposit, Boolean isAvailable, Boolean isStaffMember) {
    this.productName = productName;
    this.price = price;
    this.description = description;
    this.smallImage = smallImage;
    this.bigImage = bigImage;
    this.tags = tags;
    this.quantity = quantity;
    this.deposit = deposit;
    this.isAvailable = isAvailable;
    this.isStaffMember = isStaffMember;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSmallImage() {
    return smallImage;
  }

  public void setSmallImage(String smallImage) {
    this.smallImage = smallImage;
  }

  public String getBigImage() {
    return bigImage;
  }

  public void setBigImage(String bigImage) {
    this.bigImage = bigImage;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getDeposit() {
    return deposit;
  }

  public void setDeposit(Double deposit) {
    this.deposit = deposit;
  }

  public Boolean getAvailable() {
    return isAvailable;
  }

  public void setAvailable(Boolean available) {
    isAvailable = available;
  }

  public Boolean getStaffMember() {
    return isStaffMember;
  }

  public void setStaffMember(Boolean staffMember) {
    isStaffMember = staffMember;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductEntity that = (ProductEntity) o;
    return Objects.equals(id, that.id) &&
      Objects.equals(productName, that.productName) &&
      Objects.equals(price, that.price) &&
      Objects.equals(description, that.description) &&
      Objects.equals(smallImage, that.smallImage) &&
      Objects.equals(bigImage, that.bigImage) &&
      Objects.equals(tags, that.tags) &&
      Objects.equals(quantity, that.quantity) &&
      Objects.equals(deposit, that.deposit) &&
      Objects.equals(isAvailable, that.isAvailable) &&
      Objects.equals(isStaffMember, that.isStaffMember);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productName, price, description, smallImage, bigImage, tags, quantity, deposit, isAvailable, isStaffMember);
  }

}
