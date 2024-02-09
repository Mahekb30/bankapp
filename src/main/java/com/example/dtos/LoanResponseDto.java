package com.example.dtos;

public class LoanResponseDto {
  
  private int id;
  private int customerId;
  private double amount;
  private double interestRate;
  private int termMonths;
  private String status;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Integer getCustomerId() {
    return customerId;
  }
  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }
  public double getAmount() {
    return amount;
  }
  public void setAmount(double amount) {
    this.amount = amount;
  }
  public double getInterestRate() {
    return interestRate;
  }
  public void setInterestRate(double interestRate) {
    this.interestRate = interestRate;
  }
  public int getTermMonths() {
    return termMonths;
  }
  public void setTermMonths(int termMonths) {
    this.termMonths = termMonths;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  @Override
  public String toString() {
    return "LoanResponseDto [id=" + id + ", customerId=" + customerId + ", amount=" + amount
        + ", interestRate=" + interestRate + ", termMonths=" + termMonths + ", status=" + status
        + "]";
  }
  public LoanResponseDto(int id, int customerId, double amount, double interestRate,
      int termMonths, String status) {
    super();
    this.id = id;
    this.customerId = customerId;
    this.amount = amount;
    this.interestRate = interestRate;
    this.termMonths = termMonths;
    this.status = status;
  }
  public LoanResponseDto() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  
}
