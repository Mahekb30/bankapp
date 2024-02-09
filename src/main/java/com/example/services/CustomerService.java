package com.example.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.dao.CustomerRepository;
import com.example.dtos.CustomerRequestDto;
import com.example.dtos.CustomerResponseDto;
import com.example.dtos.LoanResponseDto;
import com.example.entities.Customer;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Lazy
    private RestTemplate template = new RestTemplate();

    public List<CustomerResponseDto> getAllCustomers(String balance, String name, Long accNo) {
      
        List<Customer> customers =  customerRepository.findAll();
        customers = customers.stream().filter(customer -> 
               (accNo == null || customer.getAccNo().equals(accNo))
            && (name == null || customer.getName().equals(name))
            && (balance == null || checkBalance(customer.getBalance(), balance))
        ).collect(Collectors.toList());

        
        return customers.stream().map(customer -> mapToDto(customer)).toList();
    }

    public CustomerResponseDto getCustomerById(int id) {
        return mapToDto(customerRepository.findById(id).orElse(null));
    }
    
    public LoanResponseDto getLoanByCustomerId(int id) {
      
      LoanResponseDto loanData = template.getForObject("http://localhost:8082/loans/customer/"+id, LoanResponseDto.class);
      return loanData;
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto customer) {
        try {
          Customer customerEntity = mapToEntity(customer);
          return mapToDto(customerRepository.save(customerEntity));
        } catch (Exception e) {
          throw new RuntimeException("Error creating customer: " + e.getMessage());
        }
    }

    public CustomerResponseDto updateCustomer(int id, CustomerRequestDto customer) {
      
        Customer customerEntity = mapToEntity(customer);
        
        if (customerRepository.existsById(id)) {
            customerEntity.setId(id);
            return mapToDto(customerRepository.save(customerEntity));
        }
        return null; // Handle not found scenario
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
    
    private boolean checkBalance(Double customerBalance, String balance) {
      if (balance.charAt(0) == '>') {
          double threshold = Double.parseDouble(balance.substring(1));
          return customerBalance >= threshold;
      } else if (balance.charAt(0) == '<') {
          double threshold = Double.parseDouble(balance.substring(1));
          return customerBalance <= threshold;
      } else {
          return false;
      }
  }

    public CustomerService(CustomerRepository customerRepository) {
      super();
      this.customerRepository = customerRepository;
    }
    
    private Customer mapToEntity(CustomerRequestDto customerRequest) {
      
      if(customerRequest == null) return null;
      
      Customer customer = new Customer();
      customer.setName(customerRequest.getName());
      customer.setAccNo(customerRequest.getAccNo());
      customer.setAccType(customerRequest.getAccType());
      customer.setBalance(customerRequest.getBalance());
      customer.setPanCardNo(customerRequest.getPanCardNo());
      return customer;
     
    }
    
    private CustomerResponseDto mapToDto(Customer customer) {
      
      if(customer == null) return null;
      
      CustomerResponseDto customerResponse = new CustomerResponseDto();
      customerResponse.setId(customer.getId());
      customerResponse.setName(customer.getName());
      customerResponse.setAccNo(customer.getAccNo());
      customerResponse.setAccType(customer.getAccType());
      customerResponse.setBalance(customer.getBalance());
      customerResponse.setPanCardNo(customer.getPanCardNo());
      return customerResponse;
     
    }
}
