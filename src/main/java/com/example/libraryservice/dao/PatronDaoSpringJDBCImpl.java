package com.example.libraryservice.dao;

import com.example.libraryservice.model.Patron;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class PatronDaoSpringJDBCImpl implements PatronDao {
    @Override
    public Optional<Patron> findPatron(Long id) {
        return null;
    }

    @Override
    public List<Patron> listPatrons() {
        return null;
    }

    @Override
    public boolean deletePatron(Long id) {
        return false;
    }

    @Override
    public Long addPatron(Patron newPatron) {
        return null;
    }
}
