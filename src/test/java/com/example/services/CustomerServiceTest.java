package com.example.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.dao.CustomerRepository;
import com.example.dtos.CustomerRequestDto;
import com.example.dtos.CustomerResponseDto;
import com.example.entities.Customer;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
  
  @Mock
  private CustomerRepository customerRepository;
  
  private CustomerService customerService;
  private CustomerRequestDto customer3;
  private CustomerResponseDto customer1;
  private CustomerResponseDto customer2;
  private List<CustomerResponseDto> mockCustomers;

  @BeforeEach
  void setUp() {
    this.customerService = new CustomerService(this.customerRepository);
    
    customer3 = new CustomerRequestDto("John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    customer1 = new CustomerResponseDto(1, "John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    customer2 = new CustomerResponseDto(2, "Jane", 12936126, "Current", "BSDAUCAVB", 1500.0);
    
    mockCustomers = Arrays.asList(customer1, customer2);
  }
  
  @Test
  void createCustomer() {
    Customer customer = new Customer(1, "John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    
    // Test Normal Scenario
    when(customerRepository.save(any())).thenReturn(customer);
    CustomerResponseDto createdCustomer = customerService.createCustomer(this.customer3);
    assertEquals(createdCustomer.getId(), customer1.getId());
    assertEquals(createdCustomer.getAccNo(),customer1.getAccNo());
    
    // When Exception is Raised
    when(customerRepository.save(any())).thenThrow(new RuntimeException("Duplicate Account Number"));
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        customerService.createCustomer(this.customer3);
    });
    assertEquals("Error creating customer: Duplicate Account Number", exception.getMessage());
    
  }
  
  @Test
  void getAllCustomers() {
    // Mock data
    Customer customer1 = new Customer(1, "John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    Customer customer2 = new Customer(2, "Jane", 12936126, "Current", "BSDAUCAVB", 1500.0);
    
    List<Customer> mockCustomers = Arrays.asList(customer1, customer2);

    when(customerRepository.findAll()).thenReturn(mockCustomers);

    // Test when all parameters are null
    List<CustomerResponseDto> result = customerService.getAllCustomers(null, null, null);
    assertEquals(2, result.size());

    // Test when filtering by name
    result = customerService.getAllCustomers(null, "John", null);
    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getName());

    // Test when filtering by account number
    result = customerService.getAllCustomers(null, null, 6128137L);
    assertEquals(1, result.size());
    assertEquals(6128137L, result.get(0).getAccNo());

    // Test when filtering by balance
    result = customerService.getAllCustomers(">1200", null, null);
    assertEquals(1, result.size());
    assertEquals(1500.0, result.get(0).getBalance());

    // Test when no matching customers
    result = customerService.getAllCustomers("<500", "John", null);
    assertEquals(0, result.size());
  }
  
  @Test
  void getCustomerById() {
    Customer customer = new Customer(1, "John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

    // Test case 1: Get customer by ID
    CustomerResponseDto customerEntity = customerService.getCustomerById(1);
    assertNotNull(customerEntity);
    assertEquals(1, customerEntity.getId());
    assertEquals("John", customerEntity.getName());
    
  }
  
  @Test
  void updateCustomer() {
    int id = 1;
    Customer customer = new Customer(1, "John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    
    // When customer exists
    when(customerRepository.existsById(id)).thenReturn(true);
    when(customerRepository.save(any())).thenReturn(customer);
    CustomerResponseDto updatedCustomer = customerService.updateCustomer(id,customer3);
    assertThat(updatedCustomer.toString()).isEqualTo(customer1.toString());
    
    // When customer doesn't exists
    when(customerRepository.existsById(id)).thenReturn(false);
    updatedCustomer = customerService.updateCustomer(id,customer3);
    assertThat(updatedCustomer).isNull();
    
  }
  
  @Test
  void deleteCustomer() {
    int id = 1;
    customerService.deleteCustomer(id);
    verify(customerRepository).deleteById(id);
  }

}
