package com.example.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.dtos.CustomerRequestDto;
import com.example.dtos.CustomerResponseDto;
import com.example.dtos.LoanResponseDto;
import com.example.entities.Customer;
import com.example.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  
    //creating a logger 
    Logger logger = LoggerFactory.getLogger(CustomerController.class);
  
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(
        @RequestParam(required = false) String balance,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long accNo
    ) {
        logger.info("Getting all customers");
        List<CustomerResponseDto> customers = customerService.getAllCustomers(balance, name, accNo);
        if (customers.size() <= 0) {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable int id) {
      logger.info("Getting customer with id: {}", id);
      CustomerResponseDto customer = customerService.getCustomerById(id);
        if (customer != null) {
          return ResponseEntity.ok(customer);
      } else {
          return ResponseEntity.notFound().build();
      }
    }
    
    @GetMapping("/loan/{id}")
    public ResponseEntity<LoanResponseDto> getLoanByCustomerId(@PathVariable int id) {
      logger.info("Getting customer loan with id: {}", id);
      LoanResponseDto loanData = customerService.getLoanByCustomerId(id);
        if (loanData != null) {
          return ResponseEntity.ok(loanData);
      } else {
          return ResponseEntity.notFound().build();
      }
    }


    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerRequestDto customer) {
        try {
          logger.info("Creating customer: {}", customer);
          CustomerResponseDto createdCustomer = customerService.createCustomer(customer);
          return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch(Exception e) {
          String errorMessage = "Error creating customer: " + e.getMessage();
          logger.error(errorMessage, e);
          return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable int id, @RequestBody CustomerRequestDto customer) {
      logger.info("Updating customer with id: {}", id);  
      CustomerResponseDto updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        logger.info("Deleting customer with id: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}

