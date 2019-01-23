package com.example.libraryservice.dao;

import com.example.libraryservice.model.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronDao {

    Optional<Patron> findPatron(Long id);

    List<Patron> listPatrons();

    boolean deletePatron(Long id);

}
