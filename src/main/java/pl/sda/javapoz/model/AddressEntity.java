package pl.sda.javapoz.model;

import javax.persistence.*;

@Entity(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String street;
    private String flatNumber;
    private String zipcode;

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String flatNumber, String zipcode) {
        this.city = city;
        this.street = street;
        this.flatNumber = flatNumber;
        this.zipcode = zipcode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}

