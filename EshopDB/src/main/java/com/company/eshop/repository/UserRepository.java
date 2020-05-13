package com.company.eshop.repository;

import com.company.eshop.application.MyApplication;
import com.company.eshop.model.User;

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
        instance.users.add(new User(instance.getNewUserId(),"mapmis1@somewhere.com", "mapmis1", "mpampis", "lakis"));
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
        return users;
    }


    public User getUser(long userId) {
        log.info("fetching user with id : " + userId);
        for (User user : users) {
            if (user.getUserId() == userId)
                return user;
        }
        log.info("user with id : " + userId + " not found");
        return null;
    }

    public User addUser(User user) {
        log.info("Creating user: " + user.getUsername());
        user.setUserId(getNewUserId());
        users.add(user);
        log.info("User : " + user.getUsername() + " created, id:" + user.getUserId());
        return user;
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
