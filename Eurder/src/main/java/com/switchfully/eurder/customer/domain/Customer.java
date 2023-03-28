package com.switchfully.eurder.customer.domain;

import com.switchfully.eurder.customer.exceptions.IncompleteAddressException;
import com.switchfully.eurder.customer.exceptions.InvalidCustomerFieldFormatException;
import com.switchfully.eurder.customer.exceptions.InvalidEmailFormatException;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    //Vars
    private final UUID uuid;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
    private Long phoneNumber;

    //Constructors

    public Customer(String firstname, String lastname, String email, Address address, Long phoneNumber) {
        this.uuid = UUID.randomUUID();
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
        return Objects.equals(uuid, customer.uuid) && Objects.equals(firstname, customer.firstname) && Objects.equals(lastname, customer.lastname) && Objects.equals(email, customer.email) && Objects.equals(address, customer.address) && Objects.equals(phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, firstname, lastname, email, address, phoneNumber);
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
            this.firstname = firstname;
            return this;
        }

        public CustomerBuilder withLastname(String lastname) {
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

}
