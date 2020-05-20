package com.company.eshop.repository;

import com.company.eshop.application.MyApplication;
import com.company.eshop.model.User;
import com.company.eshop.repository.templates.UserTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class UserRepository {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());
    //Singleton Pattern
    private static UserRepository instance;
    private List<User> users = new ArrayList<>();

    //Empty private Constructor
    private UserRepository() {
    }

    //initializes the list with mock data
    public static void init() {
        instance = new UserRepository();
        instance.users.add(new User(instance.getNewUserId(), "mapmis1@somewhere.com", "mapmis1", "mpampis", "lakis"));
        instance.users.add(new User(instance.getNewUserId(), "someone@hotmail.com", "someone", "someone", "someone"));
    }

    public static UserRepository getInstance() {
        return instance;
    }

    //END Singleton
    private long getNewUserId() {
        return users.size() + 1;
    }


    /*
     * CRUD OPERATIONS
     *
     * CREATE -> add
     * READ -> get
     * UPDATE -> get -> edit
     * DELETE -> remove
     *
     * */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        //(1) Connect to DB
        Connection connection = null;
        Statement statement = null;
        //(2) Create the query : SELECT * FROM USERS;
        try {
            connection = DataBaseUtils.createConnection();

            statement = connection.createStatement();

            //(3) execute the query in DB
            //(4) returns Result Set
            ResultSet resultSet = statement.executeQuery(UserTemplate.QUERY_SELECT_ALL_USERS);

            //(5) map Result Set to Java User
            while (resultSet.next()) {
                // rs[0]---> ...
                // rs[1]---> users[10]
                // rs[2]---> users[1]
                User user = parseUserFromDB(resultSet);
                users.add(user);
            }

            connection.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return users;
    }

    private User parseUserFromDB(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setLastName(resultSet.getString(UserTemplate.COLUMN_LASTNAME));
        user.setFirstName(resultSet.getString(UserTemplate.COLUMN_FIRSTNAME));
        user.setUsername(resultSet.getString(UserTemplate.COLUMN_USERNAME));
        user.setEmail(resultSet.getString(UserTemplate.COLUMN_EMAIL));
        user.setUserId(resultSet.getLong(UserTemplate.COLUMN_USER_ID));
        return user;
    }


    public User getUser(long userId) {

        //Create Connection

        //Prepare Statement with Query

        //

        return null;
    }

    public User addUser(User user) {
        User newUser = null;
        //Prepared Statement object for variables in the query
        //(2) Create the query : SELECT * FROM USERS;
        // try with resources block
        try (Connection connection = DataBaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement getUserStatement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_ID)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());

            int result = statement.executeUpdate();
            if (result == 0)
                return null;

            //GET USER ID FROM GENERATED KEYS
            ResultSet resultSet = statement.getGeneratedKeys();
            long userId= -10;
            while (resultSet.next()) {
                userId = resultSet.getLong(1);
                //Retrieve User From DB using userId retrieved from generatedKeys
                getUserStatement.setLong(1, userId);

                ResultSet rs = getUserStatement.executeQuery();
                while (rs.next()) {
                    newUser = parseUserFromDB(rs);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newUser;
    }

    public User deleteUser(long userId) {
        log.info("Deleting user with id: " + userId);

        User user = getUser(userId);
        if (user == null)
            return null;

        users.remove(user);
        log.info("User with id:" + user.getUserId() + " deleted");
        return user;
    }
}
