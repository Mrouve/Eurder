package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.exceptions.IncompleteAddressException;
import com.switchfully.eurder.user.exceptions.InvalidCustomerFieldFormatException;
import com.switchfully.eurder.user.exceptions.InvalidEmailFormatException;
import com.switchfully.eurder.user.exceptions.MissingMandatoryInformationException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer extends User{
    //Vars
    //private final UUID uuid;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final Address address;
    private final Long phoneNumber;
    //private Role role;

    //Constructors

    public Customer(String firstname, String lastname, String email, Address address, Long phoneNumber) {
        //this.uuid = UUID.randomUUID();
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    //Methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstname, customer.firstname) && Objects.equals(lastname, customer.lastname) && Objects.equals(email, customer.email) && Objects.equals(address, customer.address) && Objects.equals(phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email, address, phoneNumber);
    }

    //==================================================================================================================
    //Builder
    public static class CustomerBuilder {
        private String firstname;
        private String lastname;
        private String email;
        private Address address;
        private Long phoneNumber;

        public CustomerBuilder withFirstname(String firstname) {
            if (firstname ==null || firstname.isEmpty()) {
                throw new MissingMandatoryInformationException();
            }
            this.firstname = firstname;
            return this;
        }

        public CustomerBuilder withLastname(String lastname) {
            if (lastname ==null || lastname.isEmpty()) {
                throw new MissingMandatoryInformationException();
            }
            this.lastname = lastname;
            return this;
        }

        public CustomerBuilder withEmail(String email) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-z]+[.][a-z]+$");
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                this.email = email;
            }else {
                throw new InvalidEmailFormatException(email);
            }
            return this;
        }

        public CustomerBuilder withAddress(Address address){
            if (address == null || address.city() == null || address.city().isEmpty()){
                throw new IncompleteAddressException();

            }
            else{
                this.address = address;
            }
            return this;
        }

        public CustomerBuilder withPhoneNumber(Long phoneNumber){
            if(String.valueOf(phoneNumber).length() < 8){
                throw new InvalidCustomerFieldFormatException();
            }
            else {
                this.phoneNumber = phoneNumber;
            }
            return this;
        }

        public Customer build(){
            return new Customer(firstname, lastname, email, address, phoneNumber);
        }
    }
    //==================================================================================================================
    // Getters
//    public UUID getUuid() {
//        return uuid;
//    }

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
