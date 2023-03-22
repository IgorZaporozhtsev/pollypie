package com.zeecoder.common;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContactDetailsTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidContactDetails() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890123")
                .address("123 Main St, Anytown, USA")
                .email("johndoe@example.com")
                .build();

        assertThat(validator.validate(contactDetails)).isEmpty();
    }

    @Test
    void testInvalidFirstName() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("J")
                .lastName("Doe")
                .phoneNumber("1234567890123")
                .address("123 Main St, Anytown, USA")
                .email("johndoe@example.com")
                .build();

        assertThat(validator.validate(contactDetails)).hasSize(1);
    }

    @Test
    void testInvalidLastName() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("John")
                .lastName("D")
                .phoneNumber("1234567890123")
                .address("123 Main St, Anytown, USA")
                .email("johndoe@example.com")
                .build();

        assertThat(validator.validate(contactDetails)).hasSize(1);
    }

    @Test
    void testInvalidPhoneNumber() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("")
                .address("123 Main St, Anytown, USA")
                .email("johndoe@example.com")
                .build();

        assertThat(validator.validate(contactDetails)).hasSize(1);
    }

    @Test
    void testNullPhoneNumber() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber(null)
                .address("123 Main St, Anytown, USA")
                .email("johndoe@example.com")
                .build();

        assertThat(validator.validate(contactDetails)).hasSize(1);
    }

    @Test
    void testInvalidAddress() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890123")
                .address("1")
                .email("johndoe@example.com")
                .build();

        assertThat(validator.validate(contactDetails)).hasSize(1);
    }

    @Test
    void testInvalidEmail() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890123")
                .address("123 Main St, Anytown, USA")
                .email("@johndoeexample")
                .build();

        assertThat(validator.validate(contactDetails)).hasSize(1);
    }

    @Test
    void testNullEmail() {
        ContactDetails contactDetails = ContactDetails.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890123")
                .address("123 Main St, Anytown, USA")
                .email(null).build();


    }

}