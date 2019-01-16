package com.example.libraryservice.model;

import lombok.Value;

import java.time.LocalDate;

@Value
public class Patron {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String salutation;
    private LocalDate dateOfBirth;
    private String address;
}
