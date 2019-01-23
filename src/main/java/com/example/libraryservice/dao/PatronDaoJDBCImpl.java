package com.example.libraryservice.dao;

import com.example.libraryservice.model.Patron;
import com.example.libraryservice.util.Supplier;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.DriverManager.getConnection;

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
            e.printStackTrace();
            log.error("Error occurred during the database call ", e);
        }
        return patrons;
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

    @Override
    public boolean deletePatron(Long id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Ape5988Zoo");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM patrons WHERE id = ?")) {

            statement.setLong(1, id);
            int result = statement.executeUpdate();
                return result != 0;

            } catch (SQLException e) {
            log.error("Error occurred during the database call ", e);
            return false;
        }
    }

    @Override
    public Long addPatron(String salutation, String firstName, String middleName, String lastName, LocalDate dateOfBirth, String address) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Ape5988Zoo");
             PreparedStatement statement = connection.prepareStatement
                     ("INSERT INTO patrons (salutation, first_name, middle_name, last_name, date_of_birth, address) " +
                             "VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, salutation);
                    statement.setString(2, firstName);
                    statement.setString(3, middleName);
                    statement.setString(4, lastName);
                    statement.setDate(5, Date.valueOf(dateOfBirth));
                    statement.setString(6, address);
                    //ResultSet patron = statement.executeQuery();
                    //Long result = patron.getLong(1);
                    //Long result = statement.executeLargeUpdate();
                    //Long result = Long.valueOf(statement.executeUpdate());
                    int affectedRows = statement.executeUpdate();
                    Long result;
                    if (affectedRows == 0) {
                        throw new SQLException("Creating user failed, no rows affected.");
                    }
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            result = generatedKeys.getLong(1);
                        } else {
                            throw new SQLException("Creating user failed, no rows affected.");
                        }
                    }
            return result;

        } catch (SQLException e) {
            log.error("Error occurred during the database call ", e);
        }
        return null;
    }
}
