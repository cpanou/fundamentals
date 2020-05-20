package com.company.eshop.repository;

import com.company.eshop.model.User;
import com.company.eshop.repository.templates.UserTemplate;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    //STANDARD - STATEMENT EXAMPLE
    public List<User> getAllDBUsers() {
        List<User> usersList = new ArrayList<>();

        Connection dbConn = null;
        Statement statement = null;
        try {
            // (1) Create a connection to the Database
            dbConn = DatabaseUtils.createConnection();

            //(2) Once we have a Connection with the database we can use it
            //to create a Statement to query the data
            statement = dbConn.createStatement();

            //(3) Define the query we want to execute in the Database
            String query = UserTemplate.QUERY_SELECT_ALL_USERS;
            System.out.println(query);

            //(5) Execute the query from the statement and acquire the Result-Set
            ResultSet rs = statement.executeQuery(query);
            if (rs.isClosed())
                System.out.println("closed result set");

            //(6) parse the Result-Set to a known object (User)
            while (rs.next()) {
                User user = parseUserFromResultSet(rs);
                usersList.add(user);
            }

            //(7) clean up environment
            rs.close();
            statement.close();
            dbConn.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {

            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (dbConn != null)
                    dbConn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return usersList;
    }

    // PREPARED - STATEMENT EXAMPLE
    public User getDBUser(long userId) {
        User user = null;

        try (Connection connection = DatabaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_ID)) {

            statement.setLong(1, userId);
            user = executeFetchUser(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // PREPARED - STATEMENT EXAMPLE
    public User getDBUser(String username) {
        User user = null;

        try (Connection connection = DatabaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_USERNAME)) {

            statement.setString(1, username);
            user = executeFetchUser(statement);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return user;
    }

    private User executeFetchUser(PreparedStatement statement) throws SQLException {
        User user = null;
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            user = parseUserFromResultSet(rs);
        }
        return user;
    }

    // PREPARED - STATEMENT EXAMPLE
    public User createDBUser(User user) {
        try (Connection connection = DatabaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement fetchUserStatement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_ID)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());

            int result = statement.executeUpdate();

            if (result == 0)
                return null;
            ResultSet resultSet = statement.getGeneratedKeys();
            if(!resultSet.next()){
                return null;
            }

            fetchUserStatement.setLong(1, resultSet.getLong(1));
            user = executeFetchUser(fetchUserStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User parseUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong(UserTemplate.COLUMN_USER_ID));
        user.setEmail(rs.getString(UserTemplate.COLUMN_EMAIL));
        user.setUsername(rs.getString(UserTemplate.COLUMN_USERNAME));
        user.setFirstName(rs.getString(UserTemplate.COLUMN_FIRSTNAME));
        user.setLastName(rs.getString(UserTemplate.COLUMN_LASTNAME));
        return user;
    }


    public User deleteDBUser(long userId) {
        User toBeDeleted = this.getDBUser(userId);
        if (toBeDeleted == null)
            return null;

        try (Connection connection = DatabaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_DELETE_USER)) {
            statement.setLong(1, userId);

            int result = statement.executeUpdate();
            if (result == 0)
                return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return toBeDeleted;
    }


}
