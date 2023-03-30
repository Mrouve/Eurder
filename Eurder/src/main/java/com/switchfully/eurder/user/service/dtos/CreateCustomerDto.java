package com.switchfully.eurder.user.service.dtos;

import com.switchfully.eurder.user.domain.Address;

public class CreateCustomerDto {
    //Vars
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
    private Long phoneNumber;

    //Constructors

    public CreateCustomerDto(String firstname, String lastname, String email, Address address, Long phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    //Methods
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
