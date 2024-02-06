package com.example.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.CustomerRepository;
import com.example.entities.Customer;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(String balance, String name, Long accNo) {
      
        List<Customer> customers =  customerRepository.findAll();
        customers = customers.stream().filter(customer -> 
               (accNo == null || customer.getAccNo().equals(accNo))
            && (name == null || customer.getName().equals(name))
            && (balance == null || checkBalance(customer.getBalance(), balance))
        ).collect(Collectors.toList());

        return customers;
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        try {
          return customerRepository.save(customer);
        } catch (Exception e) {
          throw new RuntimeException("Error creating customer: " + e.getMessage());
        }
    }

    public Customer updateCustomer(int id, Customer customer) {
        if (customerRepository.existsById(id)) {
            customer.setId(id);
            return customerRepository.save(customer);
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
}
