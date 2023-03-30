package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.service.CustomerService;
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
class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();
    private Customer customer;

    private static final int SIZE_SETUP = 2;
    @Autowired
    private CustomerService customerService ;

    @BeforeEach
    void setup(){
        userRepository = new UserRepository();
        customer = new Customer.CustomerBuilder()
                .withFirstname("fn1")
                .withLastname("ln1")
                .withEmail("email@email.com")
                .withAddress(new Address("street1", "streetNber1", "postalCode1", "city1", "country1"))
                .withPhoneNumber(123456789L)
                .build();
        userRepository.save(customer);
    }

    @Test
    void save_givenACustomerToSave_thenSavedCustomerIsContainedInRepo() {
        //Given
        Customer customerToSave = customer;

        //When
        User savedCustomer = userRepository.save(customerToSave);

        //Then
        assertTrue(userRepository.getAllUsers().contains(savedCustomer));
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
        userRepository.save(customer2);

        //When
        Collection<User> allCustomers = userRepository.getAllUsers();

        //Then
        assertTrue(((allCustomers.contains(customer2)) && (allCustomers.contains(customer))));
        assertEquals(SIZE_SETUP, allCustomers.size());
    }

    @Test
    void getOneCustomerByUuid_givenAProvidedCustomerID_thenReturnTheCorrespondingCustomer() {
        //Given
        UUID randomUUID = customer.getUuid();

        //Then
        assertEquals(customer, userRepository.getOneCustomerByUuid(randomUUID));
    }

    @Test
    void getOneCustomerByUuid_givenAnInvalidProvidedCustomerID_thenShouldReturnNull(){
        //Given
        UUID randomUUID = UUID.randomUUID();

        //Then
        assertThat(userRepository.getOneCustomerByUuid(randomUUID)).isNull();
    }
}