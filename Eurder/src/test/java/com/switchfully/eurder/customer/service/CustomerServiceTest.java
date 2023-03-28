package com.switchfully.eurder.customer.service;

import com.switchfully.eurder.customer.domain.Address;
import com.switchfully.eurder.customer.domain.Customer;
import com.switchfully.eurder.customer.domain.CustomerRepository;
import com.switchfully.eurder.customer.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.customer.service.dtos.CustomerDto;
import com.switchfully.eurder.customer.service.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    private CustomerRepository customerRepository ;
    private CustomerMapper customerMapper;
    private CustomerService customerService;
    private Customer customer1;
    private Customer customer2;
    private CreateCustomerDto createCustomerDto;
    private static final int SIZE_SETUP = 2;


    @BeforeEach
    void setup(){
        customerRepository = new CustomerRepository();
        customerMapper = new CustomerMapper();
        customerService = new CustomerService(customerRepository,customerMapper);
        customer1 = new Customer.CustomerBuilder()
                .withFirstname("fn1")
                .withLastname("ln1")
                .withEmail("email@email.com")
                .withAddress(new Address("street1", "streetNber1", "postalCode1", "city1", "country1"))
                .withPhoneNumber(123456789L)
                .build();
        customer2 = new Customer.CustomerBuilder()
                .withFirstname("fn2")
                .withLastname("ln2")
                .withEmail("email2@email.com")
                .withAddress(new Address("street2", "streetNber2", "postalCode2", "city2", "country2"))
                .withPhoneNumber(22222222222222L)
                .build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        createCustomerDto = new CreateCustomerDto("fndto1", "lndto1", "email@fdsf.fr",
                new Address("zaea","ezae","azeaz","ezae","azeae"),
                55555555555555L);
    }

    @Test
    void save_givenAValidCreateCustomerDto_thenShouldBeReturnedAValidCustomerDto_CaseUserIsAdmin() {
        //Given
        CustomerDto customerToSaveDto = customerService.save(createCustomerDto);

        //Then
        assertNotNull(customerToSaveDto.getUuid());
        assertEquals(customerToSaveDto.getFirstname(), createCustomerDto.getFirstname());
        assertEquals(customerToSaveDto.getLastname(), createCustomerDto.getLastname());
        assertEquals(customerToSaveDto.getEmail(), createCustomerDto.getEmail());
        assertEquals(customerToSaveDto.getAddress(), createCustomerDto.getAddress());
        assertEquals(customerToSaveDto.getPhoneNumber(), createCustomerDto.getPhoneNumber());
    }

    @Test
    void save_givenAValidCreateCustomerDto_thenShouldThrowUnauthorizedEndPointException_CaseUserIsNotAdmin() {

    }

    @Test
    void getAllCustomers_givenANonNullRepositoryOfCustomers_thenShouldReturnADtoOfAllCustomers_CaseUserIsAdmin() {
        //Given
        List<CustomerDto> getAllCustomersResult = customerService.getAllCustomers();

        //Then
        assertEquals(SIZE_SETUP, getAllCustomersResult.size());
        assertTrue(getAllCustomersResult.stream().anyMatch(customerDto -> customerDto.getUuid().equals(customer1.getUuid())));
        assertTrue(getAllCustomersResult.stream().anyMatch(customerDto -> customerDto.getUuid().equals(customer2.getUuid())));
    }

    @Test
    void getAllCustomers_givenANonNullRepositoryOfCustomers_thenShouldThrowUnauthorizedEndPointException_CaseUserIsNotAdmin() {

    }

    @Test
    void getOneCustomerByUuid_GivenAValidUUID_thenShouldReturnADtoOfTheCorrespondingCustomer_CaseUserIsAdmin() {
        //Given
        UUID existingUUID = customer1.getUuid();

        //When
        CustomerDto desiredCustomerDto = customerService.getOneCustomerByUuid(existingUUID);

        //Then
        assertEquals(customer1.getFirstname(), desiredCustomerDto.getFirstname());
        assertEquals(customer1.getLastname(), desiredCustomerDto.getLastname());
        assertEquals(customer1.getEmail(), desiredCustomerDto.getEmail());
        assertEquals(customer1.getAddress(), desiredCustomerDto.getAddress());
        assertEquals(customer1.getPhoneNumber(), desiredCustomerDto.getPhoneNumber());
    }

    @Test
    void getOneCustomerByUuid_GivenAnInvalidUUID_thenShouldReturnADtoOfTheCorrespondingCustomer_CaseUserIsAdmin() {
        //Given
        UUID randomUUID = UUID.randomUUID();

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.getOneCustomerByUuid(randomUUID))
                .withMessage("This Unique Id does not exists");
    }

    @Test
    void checkIfAdmin() {
    }
}