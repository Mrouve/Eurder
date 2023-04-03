package com.switchfully.eurder.user.api;

import com.switchfully.eurder.user.domain.Address;
import com.switchfully.eurder.user.domain.Admin;
import com.switchfully.eurder.user.domain.Customer;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.service.CustomerService;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {
    private UserRepository userRepository;
    private CustomerMapper customerMapper;
    private CustomerService customerService;
    private Customer customer1;
    private Customer customer2;
    private Admin admin1;
    private CreateCustomerDto createCustomerDto;
    private CustomerController customerController;
    private CustomerDto c1Dto;
    private CustomerDto c2Dto;


    @BeforeEach
    void setup(){
        userRepository = new UserRepository();
        customerMapper = new CustomerMapper();
        customerService = new CustomerService(userRepository,customerMapper);
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
        admin1 = new Admin("admin1", LocalDate.of(2023,1,1));
        userRepository.save(customer1);
        userRepository.save(customer2);
        userRepository.save(admin1);

        createCustomerDto = new CreateCustomerDto("fndto1", "lndto1", "email@fdsf.fr",
                new Address("zaea","ezae","azeaz","ezae","azeae"),
                55555555555555L);

        c1Dto = customerMapper.toDto(customer1);
        c2Dto = customerMapper.toDto(customer2);

        customerController = new CustomerController(customerService);
    }

    @Test
    void create_GivenAValidCreateCustomerDto_ThenIsReturnedACustomerDto() {
        //Given createCustomerDto as defined in Setup
        //When
        CustomerDto customerDtoReturnedToController = customerController.create(createCustomerDto);

        //Then
        assertEquals(customerDtoReturnedToController.getPhoneNumber(),createCustomerDto.getPhoneNumber());
        assertEquals(customerDtoReturnedToController.getLastname(),createCustomerDto.getLastname());
        assertEquals(customerDtoReturnedToController.getFirstname(),createCustomerDto.getFirstname());
        assertEquals(customerDtoReturnedToController.getEmail(),createCustomerDto.getEmail());
        assertEquals(customerDtoReturnedToController.getAddress(),createCustomerDto.getAddress());
    }

//    @Test
//    void getAllCustomers_givenAValidAdminUUID_thenIsReturnedAListOfAllMembersAsListMembersDto() {
//        //Given adminUUId as defined in Setup
//        //When
//        List<CustomerDto> listCustomerDtoReturnedToController = customerController.getAllCustomers(admin1.getUuid().toString());
//
//        //Then
//        assertEquals(2, listCustomerDtoReturnedToController.size());
//        assertEquals(listCustomerDtoReturnedToController.get(0).getUuid(), customer1.getUuid()) ;
//        assertEquals(listCustomerDtoReturnedToController.get(1).getUuid(), customer2.getUuid());
//        assertEquals(listCustomerDtoReturnedToController.get(0).getAddress(), customer1.getAddress());
//        assertEquals(listCustomerDtoReturnedToController.get(0).getPhoneNumber(), customer1.getPhoneNumber());
//        assertEquals(listCustomerDtoReturnedToController.get(1).getAddress(), customer2.getAddress());
//        assertEquals(listCustomerDtoReturnedToController.get(1).getPhoneNumber(), customer2.getPhoneNumber());
//    }

    @Test
    void getOneCustomerByUuid_GivenAValidAdminUUIDAndAValidMemberId_ThenReturnTheGoodMemberDto() {
        //Given adminUUID & Customer UUID as defined in Setup
        //When
        CustomerDto customerDtoReturnedToController = customerController.getOneCustomerByUuid(customer1.getUuid().toString(),
                admin1.getUuid().toString());

        //Then
        assertEquals(customerDtoReturnedToController.toString(), c1Dto.toString());
    }
}