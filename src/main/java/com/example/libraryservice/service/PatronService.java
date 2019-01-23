package com.example.libraryservice.service;

import com.example.libraryservice.model.Patron;

import java.sql.ResultSet;
import java.util.List;

public interface PatronService {

    List<Patron> getPatrons();


    Patron getPatron(Long id);

    boolean deletePatron(Long id);

}
