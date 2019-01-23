package com.example.libraryservice.service;

import com.example.libraryservice.model.Patron;

import java.time.LocalDate;
import java.util.List;

public interface PatronService {

    List<Patron> getPatrons();


    Patron getPatron(Long id);

    boolean deletePatron(Long id);

    Long addPatron(String salutation, String firstName, String middleName, String lastName, LocalDate dateOfBirth, String address);

}
