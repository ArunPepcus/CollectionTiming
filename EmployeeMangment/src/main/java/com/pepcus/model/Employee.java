package com.pepcus.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "employees")
public class Employee {

  

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotEmpty
  @Column(name = "name")
  @Size(min = 2, message = "name should not be less then two characters")
  private String name;

  @Column(name = "email")
  @Email
  @NotEmpty
  private String email;

  @Column(name = "phoneNumber")
  @NotEmpty
  @Size(min = 10, message = "name should not be less then ten characters")
  private String phoneNumber;

//  @Column(name = "department")
//  @NotEmpty
//  @Size(min = 2, message = "name should not be less then two characters")
//  private String department;

  @JoinColumn(name = "employee_id")
  @OneToMany(cascade = CascadeType.ALL)
//  @JsonManagedReference
  @Valid
  private List<Address> addressList;
  
  @JoinColumn(name = "employee_id")
  @OneToMany(cascade = CascadeType.ALL)
  private List<Department> departmentList;
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public List<Address> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<Address> addressList) {
    this.addressList = addressList;
  }

  public List<Department> getDepartmentList() {
    return departmentList;
  }

  public void setDepartmentList(List<Department> departmentList) {
    this.departmentList = departmentList;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
        + ", addressList=" + addressList + ", departmentList=" + departmentList + "]";
  }
  
  
}
