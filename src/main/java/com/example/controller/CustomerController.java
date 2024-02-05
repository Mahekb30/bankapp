package com.example.controller;

import java.util.List;
import java.util.Optional;
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
import com.example.entities.Customer;
import com.example.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(
        @RequestParam(required = false) String balance,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long accNo
    ) {
        List<Customer> customers = customerService.getAllCustomers(balance, name, accNo);
        if (customers.size() <= 0) {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
          return ResponseEntity.ok(customer);
      } else {
          return ResponseEntity.notFound().build();
      }
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        try {
          Customer createdCustomer = customerService.createCustomer(customer);
          return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch(Exception e) {
          String errorMessage = "Error creating customer: " + e.getMessage();
          System.err.println(errorMessage);
          return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}

