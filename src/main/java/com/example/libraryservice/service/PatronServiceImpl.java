package com.example.libraryservice.service;

import com.example.libraryservice.dao.PatronDao;
import com.example.libraryservice.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PatronServiceImpl implements PatronService {

    @Autowired
    @Qualifier("patronDaoJDBCImpl")
    private PatronDao patronJDBCDao;

    @Autowired
    @Qualifier("patronDaoSpringJDBCImpl")
    private PatronDao patronSpringJDBCDao;

    @Override
    public List<Patron> getPatrons() {
        return patronJDBCDao.listPatrons();
    }

    @Override
    public Patron getPatron(Long id) {
        Optional<Patron> patron = patronJDBCDao.findPatron(id);
        if (!patron.isPresent()) {
            throw new RuntimeException("The specified id[ " + id + "] does not exist!");
        }

        return patron.get();
    }

    @Override
    public boolean deletePatron(Long id) {
       if(patronJDBCDao.deletePatron(id)) {
           return true;
       } else {
           throw new RuntimeException("The specified id has not been deleted");
       }
    }

    @Override
    public Long addPatron(Patron newPatron) {
        return patronJDBCDao.addPatron(newPatron);
    }

}
