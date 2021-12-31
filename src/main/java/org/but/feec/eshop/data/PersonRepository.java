package org.but.feec.eshop.data;

import org.but.feec.eshop.api.*;
import org.but.feec.eshop.config.DataSourceConfig;
import org.but.feec.eshop.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    public PersonAuthView findPersonByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT email, password_hash" +
                             " FROM bpc_bds_project.users" +
                             " WHERE email = ?")
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public PersonDetailView findPersonDetailedView(String LastName) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT users.first_name, users.last_name,users.email,phone_number,account.account_type, address.street, address.house_number, address.city " +
                     "FROM bpc_bds_project.users " +
                             "INNER JOIN bpc_bds_project.users_has_address ON users.id_user = users_has_address.id_user " +
                             "INNER JOIN  bpc_bds_project.address ON users_has_address.id_address = address.id_address " +
                             "INNER JOIN  bpc_bds_project.users_has_account ON users.id_user = users_has_account.id_user " +
                             "INNER JOIN  bpc_bds_project.account ON users_has_account.id_account = account.id_account " +
                             " WHERE users.last_name = ?")
        ) {
            preparedStatement.setString(1, LastName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public List<PersonBasicView> getPersonsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT  users.email, users.first_name, users.last_name, address.city, users.phone_number " +
                                  "FROM bpc_bds_project.users " +
                                  "INNER JOIN bpc_bds_project.users_has_address ON users.id_user = users_has_address.id_user " +
                                  "INNER JOIN  bpc_bds_project.address ON users_has_address.id_address = address.id_address;");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public List<ProductView> getProductView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT product_brand_name,product_model,product_version,product_color,product_type_description, product_price ,in_stock " +
                             "FROM bpc_bds_project.product " +
                             "LEFT JOIN bpc_bds_project.product_type " +
                             "ON product.id_product_type = product_type.product_type;");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<ProductView> productViews = new ArrayList<>();
            while (resultSet.next()) {
                productViews.add(mapToProductView(resultSet));
            }
            return productViews;
        } catch (SQLException e) {
            throw new DataAccessException("Products could not be loaded.", e);
        }
    }

    public void createPerson(PersonCreateView personCreateView) {
        String insertPersonSQL = "INSERT INTO bpc_bds_project.users(first_name, last_name, email, phone_number, password_hash) VALUES ( ?, ?, ?, ?, ?);";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personCreateView.getFirstName());
            preparedStatement.setString(2, personCreateView.getLastName());
            preparedStatement.setString(3, personCreateView.getEmail());
            preparedStatement.setString(4, personCreateView.getPhoneNumber());
            preparedStatement.setString(5, String.valueOf(personCreateView.getPassword()));


            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating person failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }

    public void injectPerson(PersonCreateView personCreateView) {
        String insertPersonSQL = "INSERT INTO bpc_bds_project.users(first_name, last_name, email, phone_number, password_hash) VALUES ('%s', '%s', '%d', '%s', '%s');";
        insertPersonSQL = String.format(insertPersonSQL, personCreateView.getFirstName(), personCreateView.getLastName(),
                Integer.valueOf(personCreateView.getEmail()), personCreateView.getPhoneNumber(),
                String.valueOf(personCreateView.getPassword())
        );
        System.out.println(insertPersonSQL);
        try (Connection connection = DataSourceConfig.getConnection()) {
            Statement inj = connection.createStatement();
            int injectRows = inj.executeUpdate(insertPersonSQL);
            System.out.println(injectRows);
        } catch (SQLException e) {
            throw new DataAccessException("Person creation  failed operation on the database failed.");
        }
    }

    public void editPerson(PersonEditView personEditView) {
        String insertPersonSQL = "UPDATE bpc_bds_project.users "+
        "SET first_name=?, last_name=?, email=?, phone_number=?, password_hash=? "+
        "WHERE id_user=?; ";
        String checkIfExists = "SELECT email FROM bpc_bds_project.users p WHERE users.id_person = ?";
        try (Connection connection = DataSourceConfig.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, personEditView.getEmail());
            preparedStatement.setString(2, personEditView.getFirstName());
            preparedStatement.setString(3, personEditView.getLastName());
            preparedStatement.setString(4, personEditView.getPhoneNumber());
            preparedStatement.setString(5, String.valueOf(personEditView.getPassword()));

            try {
                connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                // TODO commit the transaction (both queries were performed)
                /* HERE */
            } catch (SQLException e) {
                // TODO rollback the transaction if something wrong occurs
                /* HERE */
            } finally {
                // TODO set connection autocommit back to true
                /* HERE */
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }


    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PersonAuthView person = new PersonAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("password_hash"));
        return person;
    }

    private PersonBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        PersonBasicView personBasicView = new PersonBasicView();
        personBasicView.setEmail(rs.getString("email"));
        personBasicView.setGivenName(rs.getString("first_name"));
        personBasicView.setFamilyName(rs.getString("last_name"));
        personBasicView.setPhoneNumber(rs.getString("phone_number"));
        personBasicView.setCity(rs.getString("city"));
        return personBasicView;
    }

    private ProductView mapToProductView(ResultSet rs) throws SQLException {
        ProductView productView = new ProductView();
        productView.setBrand(rs.getString("product_brand_name"));
        productView.setModel(rs.getString("product_model"));
        productView.setVersion(rs.getString("product_version"));
        productView.setColor(rs.getString("product_color"));
        productView.setType(rs.getString("product_type_description"));
        productView.setPrice(rs.getString("product_price"));
        productView.setStock(rs.getString("in_stock"));
        return productView;
    }

    private PersonDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setAccount(rs.getString("account_type"));
        personDetailView.setEmail(rs.getString("email"));
        personDetailView.setGivenName(rs.getString("first_name"));
        personDetailView.setFamilyName(rs.getString("last_name"));
        personDetailView.setPhoneNumber(rs.getString("phone_number"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.sethouseNumber(rs.getString("house_number"));
        personDetailView.setStreet(rs.getString("street"));
        return personDetailView;
    }


}
