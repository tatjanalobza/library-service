package com.example.libraryservice.service;

import com.example.libraryservice.dao.PatronDao;
import com.example.libraryservice.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronDao patronDao;

    @Override
    public List<Patron> getPatrons() {
        return patronDao.listPatrons();
    }

    @Override
    public Patron getPatron(Long id) {
        Optional<Patron> patron = patronDao.findPatron(id);
        if (!patron.isPresent()) {
            throw new RuntimeException("The specified id[ " + id + "] does not exist!");
        }

        return patron.get();
    }

    public void deletePatron(Long id) {
       patronDao.deletePatron(id);
       System.out.println("The patron with id " + id + "has been deleted");
    }

}
