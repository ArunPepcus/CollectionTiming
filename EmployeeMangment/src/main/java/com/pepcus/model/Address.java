package com.pepcus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "address")
public class Address {
  @Id
  private long id;
  @NotEmpty
  @Column(name = "addressType")
  @Size(min = 2, message = "name should not be less then two characters")
  private String addressType;

  @Column(name = "street1", nullable = false)
  private String street1;

  @Column(name = "street2", nullable = false)
  private String street2;

  @Column(name = "city")
  @Size(min = 2, message = " city name should not be less then two characters")
  private String city;

  @Column(name = "state")
  @Size(min = 2, message = "name should not be less then two characters")
  private String state;

  @Column(name = "country")
  @Size(min = 2, message = "name should not be less then two characters")
  private String country;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAddressType() {
    return addressType;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  public String getStreet1() {
    return street1;
  }

  public void setStreet1(String street1) {
    this.street1 = street1;
  }

  public String getStreet2() {
    return street2;
  }

  public void setStreet2(String street2) {
    this.street2 = street2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  
}
