package com.example.libraryservice.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Value

public class Patron {
    private Long id;
    @NotBlank (message = "This is a required field") private String firstName;
    private String middleName;
     @NotBlank (message = "This is a required field")private String lastName;
    private String salutation;
    @NotBlank (message = "This is a required field")private LocalDate dateOfBirth;
    @NotBlank (message = "This is a required field")private String address;


   // public Patron(){};

}
