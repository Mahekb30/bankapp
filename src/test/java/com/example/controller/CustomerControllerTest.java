package com.example.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.dtos.CustomerRequestDto;
import com.example.dtos.CustomerResponseDto;
import com.example.entities.Customer;
import com.example.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;

  @Autowired
  private ObjectMapper objectMapper;
  private CustomerRequestDto customer;
  private CustomerResponseDto customer1;
  private CustomerResponseDto customer2;
  private List<CustomerResponseDto> mockCustomers;
 
  @BeforeEach
  public void init() {
    customer = new CustomerRequestDto("John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    customer1 = new CustomerResponseDto(1, "John", 6128137, "Savings", "GVGSVVCU", 1000.0);
    customer2 = new CustomerResponseDto(2, "Jane", 12936126, "Current", "BSDAUCAVB", 1500.0);
    
    mockCustomers = Arrays.asList(customer1, customer2);
  }
  
  @Test
  void getAllCustomers() throws Exception {
    
    when(customerService.getAllCustomers(null, null, null)).thenReturn(mockCustomers);
    ResultActions response = mockMvc.perform(get("/customers"));
    response.andExpect(MockMvcResultMatchers.status().isOk());
    
    // When Customers are not present
    when(customerService.getAllCustomers(null, null, null)).thenReturn(Arrays.asList());
    response = mockMvc.perform(get("/customers"));
    response.andExpect(MockMvcResultMatchers.status().isNotFound());
    
  }
  
  @Test
  void getCustomerById() throws Exception {
    int id = 1;
    when(customerService.getCustomerById(id)).thenReturn(customer1);
    ResultActions response = mockMvc.perform(get("/customers/"+id));
    response.andExpect(MockMvcResultMatchers.status().isOk());
    
    id = 3;
    // When Customer is not present
    when(customerService.getCustomerById(id)).thenReturn(null);
    response = mockMvc.perform(get("/customers/"+id));
    response.andExpect(MockMvcResultMatchers.status().isNotFound());
    
  }
  
  @Test
  void createCustomer() throws Exception {

    when(customerService.createCustomer(customer)).thenReturn(customer1);
    ResultActions response = mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(customer)));
    response.andExpect(MockMvcResultMatchers.status().isCreated());
    
    // When Duplicate Account No is sent
    when(customerService.createCustomer(ArgumentMatchers.any())).thenThrow(new RuntimeException("Duplicate Account Number"));
    response = mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(customer)));
    response.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    
  }
  
  @Test
  void updateCustomer() throws Exception {

    int id = 1;
   
    when(customerService.updateCustomer(eq(id), ArgumentMatchers.any())).thenReturn(customer1);
    ResultActions response = mockMvc.perform(put("/customers/"+id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(customer)));
    response.andExpect(MockMvcResultMatchers.status().isOk());
    
    when(customerService.updateCustomer(eq(id), ArgumentMatchers.any())).thenReturn(null);
    response = mockMvc.perform(put("/customers/"+id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(customer)));
    response.andExpect(MockMvcResultMatchers.status().isNotFound());
  }
  
  @Test
  void deleteCustomer() throws Exception {

    int id = 1;
    
    ResultActions response = mockMvc.perform(delete("/customers/"+id));
    response.andExpect(MockMvcResultMatchers.status().isNoContent());
    
  }

}
