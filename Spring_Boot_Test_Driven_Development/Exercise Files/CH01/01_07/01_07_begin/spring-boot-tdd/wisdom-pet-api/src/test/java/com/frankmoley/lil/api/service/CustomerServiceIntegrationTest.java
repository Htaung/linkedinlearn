package com.frankmoley.lil.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.frankmoley.lil.api.web.error.ConflictException;
import com.frankmoley.lil.api.web.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(replace= Replace.ANY)
public class CustomerServiceIntegrationTest {

  @Autowired
  CustomerService service;

  @Test
  void getAllCustomers(){
    List<Customer> customers = this.service.getAllCustomers();
    assertEquals(5, customers.size());
  }
  
  @Test
  void findByEmailAddress(){
    Customer customer = this.service.findByEmailAddress("penatibus.et@lectusa.com");
    assertEquals("penatibus.et@lectusa.com", customer.getEmailAddress());
  }
  
  @Test
  void addCustomer(){
    Customer customer = new Customer(UUID.randomUUID().toString(), "Aung", "Aung", "aung@gmail.com", "093434343", "Avenu 1");
    Customer saveCustomer = this.service.addCustomer(customer);
    assertEquals(customer.getFirstName(), saveCustomer.getFirstName());
    assertThrows(ConflictException.class, () -> this.service.addCustomer(customer));
    
  }
  
  @Test
  void getCustomer(){
    Customer customer = this.service.getCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
    assertEquals("054b145c-ddbc-4136-a2bd-7bf45ed1bef7", customer.getCustomerId());
  }
  
  @Test
  void updateCustomer(){
	Customer customer = this.service.getCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
	customer.setFirstName("Thiri");
    Customer updatedCustomer = this.service.updateCustomer(customer);
    assertEquals(customer.getFirstName(), updatedCustomer.getFirstName());
  }
  
  @Test
  void deleteCustomer(){
    Customer customer = this.service.findByEmailAddress("sit@vitaealiquetnec.net");
    this.service.deleteCustomer(customer.getCustomerId());
  }

}
