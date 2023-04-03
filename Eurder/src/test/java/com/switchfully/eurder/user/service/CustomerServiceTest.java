package com.switchfully.eurder.user.service;

import com.switchfully.eurder.user.domain.Address;
import com.switchfully.eurder.user.domain.Admin;
import com.switchfully.eurder.user.domain.Customer;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    private CustomerService customerService;
    private Customer customer1;
    private Customer customer2;
    private Admin admin1;
    private CreateCustomerDto createCustomerDto;
    private static final int SIZE_SETUP = 2;


    @BeforeEach
    void setup(){
        UserRepository userRepository = new UserRepository();
        CustomerMapper customerMapper = new CustomerMapper();
        customerService = new CustomerService(userRepository, customerMapper);
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
    }

    @Test
    void save_givenAValidCreateCustomerDto_thenShouldBeReturnedAValidCustomerDto() {
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
    void getAllCustomers_givenANonNullRepositoryOfCustomers_thenShouldReturnADtoOfAllCustomers_CaseUserIsAdmin() {
        //Given
        List<CustomerDto> getAllCustomersResult = customerService.getAllCustomers(admin1.getUuid().toString());

        //Then
        assertEquals(SIZE_SETUP, getAllCustomersResult.size());
        assertTrue(getAllCustomersResult.stream().anyMatch(customerDto -> customerDto.getUuid().equals(customer1.getUuid())));
        assertTrue(getAllCustomersResult.stream().anyMatch(customerDto -> customerDto.getUuid().equals(customer2.getUuid())));
    }

    @Test
    void getAllCustomers_givenANonNullRepositoryOfCustomers_thenShouldThrowUnauthorizedEndPointException_CaseUserIsNotAdmin() {
        //Given
        UUID notAdminUuid = customer1.getUuid();

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.getAllCustomers(notAdminUuid.toString()))
                .withMessage("Unauthorized End Point !");
    }

    @Test
    void getAllCustomers_givenANonNullRepositoryOfCustomers_thenShouldThrowUnauthorizedEndPointException_CaseUserIdIsNotAValidUserId() {
        //Given
        String badUuid = "fsdfsdf";

        //Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> customerService.getAllCustomers(badUuid))
                .withMessage("Invalid UUID string: " + badUuid);
    }

    @Test
    void getOneCustomerByUuid_GivenAValidUUID_thenShouldReturnADtoOfTheCorrespondingCustomer_CaseUserIsAdmin() {
        //Given
        UUID existingUUID = customer1.getUuid();

        //When
        CustomerDto desiredCustomerDto = customerService.getOneCustomerByUuid(existingUUID.toString(), admin1.getUuid().toString());

        //Then
        assertEquals(customer1.getFirstname(), desiredCustomerDto.getFirstname());
        assertEquals(customer1.getLastname(), desiredCustomerDto.getLastname());
        assertEquals(customer1.getEmail(), desiredCustomerDto.getEmail());
        assertEquals(customer1.getAddress(), desiredCustomerDto.getAddress());
        assertEquals(customer1.getPhoneNumber(), desiredCustomerDto.getPhoneNumber());
    }

    @Test
    void getOneCustomerByUuid_GivenAValidUUID_thenShouldReturnADtoOfTheCorrespondingCustomer_CaseUserIsNotAdmin() {
        //Given
        UUID notAdminUuid = customer1.getUuid();
        UUID existingUUID = customer1.getUuid();

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.getOneCustomerByUuid(existingUUID.toString(), notAdminUuid.toString()))
                .withMessage("Unauthorized End Point !");
    }

    @Test
    void getOneCustomerByUuid_GivenAnInvalidUUID_thenShouldReturnADtoOfTheCorrespondingCustomer_CaseUserIsAdmin() {
        //Given
        UUID randomUUID = UUID.randomUUID();

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.getOneCustomerByUuid(randomUUID.toString(), admin1.getUuid().toString()))
                .withMessage("This Unique Id does not exists");
    }

    @Test
    void getOneCustomerByUuid_GivenANonExistingUserId_ShouldReturnInvalidUuidException() {
        //Given
        UUID existingUUID = customer1.getUuid();
        UUID randomUUID = UUID.randomUUID();

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.getOneCustomerByUuid(existingUUID.toString(), randomUUID.toString()))
                .withMessage("This Unique Id does not exists");
    }

    @Test
    void save_givenAnEmptyFirstname_thenThrowTheCorrespondingError(){
        //Given
        CreateCustomerDto customerNoFirstname = new CreateCustomerDto("","fsdf","fsdfsdf@gfgf.com",
                new Address("street2", "streetNber2", "postalCode2", "city2", "country2"),
                123456789L);

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.save(customerNoFirstname))
                .withMessage("A mandatory information is missing");
    }

    @Test
    void save_givenAnEmptyLastname_thenThrowCorrespondingError(){
        //Given
        CreateCustomerDto customerNoLastname = new CreateCustomerDto("sdfdsf","","fsdfsdf@gfgf.com",
                new Address("street2", "streetNber2", "postalCode2", "city2", "country2"),
                123456789L);

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.save(customerNoLastname))
                .withMessage("A mandatory information is missing");
    }

    @Test
    void save_givenAWrongFormatEmail_thenThrowCorrespondingError(){
        CreateCustomerDto customerNoValidEmail = new CreateCustomerDto("sdfdsf","gfdgf","notValid",
                new Address("street2", "streetNber2", "postalCode2", "city2", "country2"),
                123456789L);

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.save(customerNoValidEmail))
                .withMessage("Invalid email: notValid");
    }

    @Test
    void save_givenAPhoneNumberTooShort_thenThrowCorrespondingError(){
        CreateCustomerDto customerNoValidPhoneNumber = new CreateCustomerDto("sdfdsf","gfdgf","fdsfds@gdg.com",
                new Address("street2", "streetNber2", "postalCode2", "city2", "country2"),
                1234567L);

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.save(customerNoValidPhoneNumber))
                .withMessage("This phone number is too short to be of any valid format");
    }

    @Test
    void save_givenAnIncompleteAddress_thenThrowCorrespondingError(){
        CreateCustomerDto customerNoFullAddress = new CreateCustomerDto("sdfdsf","gfdgf","fdsfds@gdg.com",
                new Address("street2", "streetNber2", "postalCode2", "", "country2"),
                123456789L);

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> customerService.save(customerNoFullAddress))
                .withMessage("The address is not complete");
    }

}