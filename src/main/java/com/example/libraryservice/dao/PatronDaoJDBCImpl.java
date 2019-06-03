package com.example.libraryservice.dao;

import com.example.libraryservice.model.Patron;
import com.example.libraryservice.util.Supplier;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.DriverManager.getConnection;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Component
public class PatronDaoJDBCImpl implements PatronDao {

    private final static Logger log = Logger.getLogger(PatronDaoJDBCImpl.class);
    private Supplier<Connection> connectionSupplier = () -> getConnection("jdbc:mysql://localhost:3306/library","root","Ape5988Zoo");

    @Override
    public Optional<Patron> findPatron(Long id) {
        try (Connection connection = connectionSupplier.get();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM patrons WHERE id = ? LIMIT 1")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return Optional.of(mapPatron(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error has occurred during database call! ", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Patron> listPatrons() {
        List<Patron> patrons = new ArrayList<>();
        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM patrons")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                patrons.add(mapPatron(resultSet));
            }
        } catch (Exception e) {
            log.error("Error occurred during the database call ", e);
        }
        return patrons;
    }

    @Override
    public boolean deletePatron(Long id) {
        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM patrons WHERE id = ?")) {

            statement.setLong(1, id);
            return statement.executeUpdate() != 0;
        } catch (Exception e) {
            log.error("Error occurred during the database call ", e);
            return false;
        }
    }

    @Override
    public Long addPatron(Patron newPatron) {
        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO patrons (salutation, first_name, middle_name, last_name, date_of_birth, address) " +
                             "VALUES (?, ?, ?, ?, ?, ?);", RETURN_GENERATED_KEYS)) {

             statement.setString(1, newPatron.getSalutation());
             statement.setString(2, newPatron.getFirstName());
             statement.setString(3, newPatron.getMiddleName());
             statement.setString(4, newPatron.getLastName());
             statement.setDate(5, Date.valueOf(newPatron.getDateOfBirth()));
             statement.setString(6, newPatron.getAddress());

             if (statement.executeUpdate() != 0) {
                 try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                     if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                     } else {
                        throw new SQLException("Creating user failed, no rows affected.");
                     }
                 }
             } else {
                throw new SQLException("Creating user failed, no rows affected.");
             }
        } catch (Exception e) {
            log.error("Error occurred during the database call ", e);
            throw new RuntimeException("Error occurred during the database call ", e);
        }
    }

    private Patron mapPatron(ResultSet rs) throws SQLException {
        return new Patron(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("salutation"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("address"));
    }

}
