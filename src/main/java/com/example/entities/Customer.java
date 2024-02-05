package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  private String name;
  
  @Column(unique = true)
  private long accNo;
  
  private String accType;
  
  private String panCardNo;
  
  private Double balance;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getAccNo() {
    return accNo;
  }

  public void setAccNo(long accNo) {
    this.accNo = accNo;
  }

  public String getAccType() {
    return accType;
  }

  public void setAccType(String accType) {
    this.accType = accType;
  }

  public String getPanCardNo() {
    return panCardNo;
  }

  public void setPanCardNo(String panCardNo) {
    this.panCardNo = panCardNo;
  }

  public Customer(int id, String name, long accNo, String accType, String panCardNo, double balance) {
    super();
    this.id = id;
    this.name = name;
    this.accNo = accNo;
    this.accType = accType;
    this.panCardNo = panCardNo;
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", name=" + name + ", accNo=" + accNo + ", accType=" + accType
        + ", panCardNo=" + panCardNo + ", balance=" + balance + "]";
  }

  public Customer() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
  
}
