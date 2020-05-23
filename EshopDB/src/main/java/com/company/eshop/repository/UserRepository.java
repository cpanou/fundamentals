package com.company.eshop.repository;

import com.company.eshop.model.User;
import com.company.eshop.repository.templates.UserTemplate;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    //STANDARD - STATEMENT EXAMPLE
    //With a simple try catch
    public List<User> getAllDBUsers() {
        List<User> usersList = new ArrayList<>();

        Connection dbConn = null;
        Statement statement = null;
        try {
            // (1) Create a connection to the Database
            dbConn = DataBaseUtils.createConnection();

            //(2) Once we have a Connection with the database we can use it
            //to create a Statement to query the data
            statement = dbConn.createStatement();

            //(3) Define the query we want to execute in the Database
            String query = UserTemplate.QUERY_SELECT_ALL_USERS;
            System.out.println(query);

            //(5) Execute the query from the statement and acquire the Result-Set
            //(6) the executeQuery() method returns a result set from the Database
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

    //Prepared Statement example using the parameterised Query SELECT_USER_ID from the UserTemplate
    //try with resources block
    public User getDBUser(long userId) {
        //(1) We declare the user object we will retrieve
        User user = null;
        //(2) We initialize the connection and prepared statement objects inside the try with resources block,
        // try ( AutoClosable ){
        // ...
        // } catch (Exception e) {
        // ...
        // }
        try (Connection connection = DataBaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_ID)) {

            //(3) we pass the userId parameter to the first parameter '?' declared in the query.
            statement.setLong(1, userId);

            //(4) we execute the query using the custom executeFetchUser() method.
            //and assign the result to the user object we will return
            user = executeFetchUser(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //(1) Used to check for the existing usernames
    //(2) The implementation is the same as the method getDBUser(long userId) above but instead of the userId we use the username
    //(3) We use the QUERY_SELECT_USER_USERNAME template that uses the column username in the db as the search parameter
    public User getDBUser(String username) {
        User user = null;

        try (Connection connection = DataBaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_USERNAME)) {

            statement.setString(1, username);
            user = executeFetchUser(statement);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return user;
    }

    //(1) Create a new User
    //(2) Multiple PreparedStatements Example
    public User createDBUser(User user) {
        //(4) Since we want to first INSERT a new user in the database and THEN retrieve him we will need
        // to execute 2 operations, an INSERT first and then a retrieve, we will need a prepared statement for each
        // parameterised query
        String insertUserQuery = "INSERT INTO eshop.users "
                + " ( username, firstname, lastname, email )" +
                "VALUES (?, ?, ?, ?);";
        try (Connection connection = DataBaseUtils.createConnection();
             //(5) the PreparedStatement.RETURN_GENERATED_KEYS value specifies to the statement that we will need
             // it to return a ResultSet containing any possible key auto generated from the operation
             PreparedStatement createUserStatement = connection.prepareStatement(UserTemplate.QUERY_INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement fetchUserStatement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_ID)) {

            //(6) The parameter Index value specifies the position of a '?' in the query.
            // (e.g.) SELECT * FROM users WHERE userId = ? AND username = ?;
            // the index for the userId is 1, and for the username is 2
            createUserStatement.setString(1, user.getUsername());
            createUserStatement.setString(2, user.getFirstName());
            createUserStatement.setString(3, user.getLastName());
            createUserStatement.setString(4, user.getEmail());

            //(7) The executeUpdate method needs to execute an INSERT, UPDATE or DELETE method
            // that return the number of rows changed(if any) or else 0
            int result = createUserStatement.executeUpdate();
            //(8) The INSERT statement result should be 1 if it was successful so if the result is 0
            // something went wrong and we exit the method with a null value.
            if (result == 0)
                return null;

            //(9) we use the getGeneratedKeys from the statement object to obtain the new UserId generated in the db
            ResultSet resultSet = createUserStatement.getGeneratedKeys();
            if(!resultSet.next()){
                return null;
            }
            //(10) we get the user from the db using the id from the insert statement
            fetchUserStatement.setLong(1, resultSet.getLong(1));
            user = executeFetchUser(fetchUserStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //(1) Since we need to fetch a user from multiple methods, getDBUser(String username), getDBUser(long userId) and createDBUser(User user)
    // we use a method that wraps the same logic inside so we do not have to repeat it in all the other methods
    // we do the same thing with the parseUserFromResultSet() method.
    private User executeFetchUser(PreparedStatement statement) throws SQLException {
        User user = null;
        //(2) The executeQuery method returns the ResultSet Object.
        //(3) The PreparedStatement needs to be initialised with a query that retrieves data ( SELECT )
        //or the returned ResultSet will be closed.
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            //(4) We parse a User from the retrieved ResultSet
            user = parseUserFromResultSet(rs);
        }
        return user;
    }

    //We use a method to parse the result set to a user since we will need to do this from multiple other methods.
    //Similar to the mapper layer in the service
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

        try (Connection connection = DataBaseUtils.createConnection();
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
