package com.example.libraryservice.dao;

import com.example.libraryservice.model.Patron;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Component
public class PatronDaoJDBCImpl implements PatronDao {

    private final static Logger log = Logger.getLogger(PatronDaoJDBCImpl.class);

    @Override
    public Optional<Patron> findPatron(Long id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","Ape5988Zoo");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM patrons WHERE id = ? LIMIT 1")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                Long patronId = resultSet.getLong("id");
                String salutation = resultSet.getString("salutation");
                String firstName = resultSet.getString("first_name");
                String middleName = resultSet.getString("middle_name");
                String lastName = resultSet.getString("last_name");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                String address = resultSet.getString("address");

                return Optional.of(new Patron(patronId, salutation, firstName, middleName, lastName, dateOfBirth.toLocalDate(), address));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("Error has occurred during database call! ", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Patron> listPatrons() {
        return null;
    }

    @Override
    public boolean deletePatron(Long id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Ape5988Zoo");
             PreparedStatement statement = connection.prepareStatement("DELETE * FROM patrons WHERE id = ? LIMIT 1")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return true;
            } catch (SQLException e) {
            log.error("Error occurred during the database call ", e);
            return false;
        }
    }
}
