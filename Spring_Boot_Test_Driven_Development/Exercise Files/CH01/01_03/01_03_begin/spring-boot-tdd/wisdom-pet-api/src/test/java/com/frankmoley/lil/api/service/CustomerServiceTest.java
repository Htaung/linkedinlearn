package com.frankmoley.lil.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.frankmoley.lil.api.data.entity.CustomerEntity;
import com.frankmoley.lil.api.data.repository.CustomerRepository;
import com.frankmoley.lil.api.web.model.Customer;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@InjectMocks
	CustomerService customerService;

	@Mock
	CustomerRepository customerRepository;

	@Test
	void getAllCustomers() {
		Mockito.doReturn(getMockCustomers(2)).when(customerRepository).findAll();
		List<Customer> customers = this.customerService.getAllCustomers();
		assertEquals(2, customers.size());
	}

	@Test
	void findByEmailAddress(String emailAddress) {
		Mockito.doReturn(getMockFindByEmailAddress("test@gmail.com")).when(customerRepository).findByEmailAddress("test@gmail.com");
		Customer entity = this.customerService.findByEmailAddress(emailAddress);
		assertEquals("test@gmail.com", entity.getEmailAddress());
	}

	private CustomerEntity getMockFindByEmailAddress(String email) {
		CustomerEntity entity = new CustomerEntity(UUID.randomUUID(), "firstName", "lastName", email, "phoneNo", "address");
		return entity;
	}

	private List<CustomerEntity> getMockCustomers(int size) {
		List<CustomerEntity> customers = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			customers.add(new CustomerEntity(UUID.randomUUID(), "firstName" + i, "lastName" + i,
					"email" + i + "@gmail.com", "phoneNo" + i, "address" + i));
		}

		return customers;
	}

}
