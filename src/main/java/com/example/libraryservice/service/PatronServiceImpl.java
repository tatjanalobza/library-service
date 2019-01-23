package com.example.libraryservice.service;

import com.example.libraryservice.dao.PatronDao;
import com.example.libraryservice.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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
    @Override
    public boolean deletePatron(Long id) {
       if(patronDao.deletePatron(id)) {
           return true;
       } else {
           throw new RuntimeException("The specified id has not been deleted");
       }
    }

    @Override
    public Long addPatron(String salutation, String firstName, String middleName, String lastName, LocalDate dateOfBirth, String address) {
        if (patronDao.addPatron(salutation, firstName, middleName, lastName, dateOfBirth, address) > 0) {
            return patronDao.addPatron(salutation, firstName, middleName, lastName, dateOfBirth, address);
        } else {
            throw new RuntimeException("Patron has not been added");
        }
    }

}
