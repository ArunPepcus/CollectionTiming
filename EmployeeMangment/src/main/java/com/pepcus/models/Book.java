package com.pepcus.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "book_name")
  @NotEmpty
  private String name;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date addedOn;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date modifiedOn=new Date();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date issueOn;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getAddedOn() {
    return addedOn;
  }

  public void setAddedOn(Date addedOn) {
    this.addedOn = addedOn;
  }

  public Date getModifiedOn() {
    return modifiedOn;
  }

  public void setModifiedOn(Date modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  public Date getIssueOn() {
    return issueOn;
  }

  public void setIssueOn(Date issueOn) {
    this.issueOn = issueOn;
  }

  @Override
  public String toString() {
    return "Book [id=" + id + ", name=" + name + ", addedOn=" + addedOn + ", modifiedOn=" + modifiedOn + ", issueOn="
        + issueOn + "]";
  }
  
}
