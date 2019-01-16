package com.example.libraryservice.service;

import com.example.libraryservice.model.Patron;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatronServiceImpl implements PatronService {

    @Override
    public List<Patron> getUsers() {
        List<Patron> patrons = new ArrayList<>();
        patrons.add(new Patron(1L, "Tatjana", "", "Lobza", "", LocalDate.now(), "Test Address"));
        patrons.add(new Patron(2L, "Tatjana", "", "Lobza", "", LocalDate.now(), "Test Address"));
        return patrons;
    }

    @Override
    public Patron getPatron(Long id) {
        return new Patron(3L, "Tatjana", "", "Lobza", "", LocalDate.now(), "Test Address");
    }

}
