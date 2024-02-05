package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
