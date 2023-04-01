package com.switchfully.eurder.user.service.dtos;

import com.switchfully.eurder.user.domain.Address;

import java.util.UUID;

public class CustomerDto {
    private UUID uuid;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
    private Long phoneNumber;

    //Constructors

    public CustomerDto(UUID uuid, String firstname, String lastname, String email, Address address, Long phoneNumber) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Methods

    public UUID getUuid() {
        return uuid;
    }

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

    @Override
    public String toString() {
        return "CustomerDto{" +
                "uuid=" + uuid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
