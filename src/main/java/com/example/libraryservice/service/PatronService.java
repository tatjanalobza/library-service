package com.example.libraryservice.service;

import com.example.libraryservice.model.Patron;

import java.util.List;

public interface PatronService {

    List<Patron> getUsers();

    Patron getPatron(Long id);

}
