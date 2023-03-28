package com.switchfully.eurder.customer.domain;

import com.switchfully.eurder.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRepositoryTest {

    private CustomerRepository customerRepository = new CustomerRepository();
    private Customer customer;

    private static final int SIZE_SETUP = 2;
    @Autowired
    private CustomerService customerService ;

    @BeforeEach
    void setup(){
        customerRepository = new CustomerRepository();
        customer = new Customer.CustomerBuilder()
                .withFirstname("fn1")
                .withLastname("ln1")
                .withEmail("email@email.com")
                .withAddress(new Address("street1", "streetNber1", "postalCode1", "city1", "country1"))
                .withPhoneNumber(123456789L)
                .build();
        customerRepository.save(customer);
    }

    @Test
    void save_givenACustomerToSave_thenSavedCustomerIsContainedInRepo() {
        //Given
        Customer customerToSave = customer;

        //When
        Customer savedCustomer = customerRepository.save(customerToSave);

        //Then
        assertTrue(customerRepository.getAllCustomers().contains(savedCustomer));
    }

    @Test
    void getAllCustomers_givenANonEmptyListOfCustomers_thenTheEntireListIsReturned() {
        //Given
        Customer customer2 = new Customer.CustomerBuilder()
                .withFirstname("fn2")
                .withLastname("ln2")
                .withEmail("email2@email.com")
                .withAddress(new Address("street2", "streetNber2", "postalCode2", "city2", "country2"))
                .withPhoneNumber(22222222222222L)
                .build();
        customerRepository.save(customer2);

        //When
        Collection<Customer> allCustomers = customerRepository.getAllCustomers();

        //Then
        assertTrue(((allCustomers.contains(customer2)) && (allCustomers.contains(customer))));
        assertEquals(SIZE_SETUP, allCustomers.size());
    }

    @Test
    void getOneCustomerByUuid_givenAProvidedCustomerID_thenReturnTheCorrespondingCustomer() {
        //Given
        UUID randomUUID = customer.getUuid();

        //Then
        assertEquals(customer, customerRepository.getOneCustomerByUuid(randomUUID));
    }

    @Test
    void getOneCustomerByUuid_givenAnInvalidProvidedCustomerID_thenShouldReturnNull(){
        //Given
        UUID randomUUID = UUID.randomUUID();

        //Then
        assertThat(customerRepository.getOneCustomerByUuid(randomUUID)).isNull();
    }
}