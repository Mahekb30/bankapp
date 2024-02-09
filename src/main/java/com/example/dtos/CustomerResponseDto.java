package com.example.dtos;


public class CustomerResponseDto {
  private int id;
  
  private String name;
  
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

  public long getAccNo() {
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

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public CustomerResponseDto(int id, String name, long accNo, String accType, String panCardNo,
      Double balance) {
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
    return "CustomerResponseDto [id=" + id + ", name=" + name + ", accNo=" + accNo + ", accType="
        + accType + ", panCardNo=" + panCardNo + ", balance=" + balance + "]";
  }

  public CustomerResponseDto() {
    super();
    // TODO Auto-generated constructor stub
  }
  
}
