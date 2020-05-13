package com.company.eshop.services;

import com.company.eshop.application.MyApplication;
import com.company.eshop.dtomappers.UserDtoMapper;
import com.company.eshop.dtos.*;
import com.company.eshop.exceptions.ApplicationError;
import com.company.eshop.exceptions.ApplicationException;
import com.company.eshop.model.Order;
import com.company.eshop.model.User;
import com.company.eshop.repository.OrderRepository;
import com.company.eshop.repository.UserRepository;

import java.util.List;
import java.util.logging.Logger;

public class UserService {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());

    //Gets a reference to the Repositories
    private UserRepository userRepository = UserRepository.getInstance();
    private OrderRepository orderRepository = OrderRepository.getInstance();

    public UsersListResponse getUsers() {
        log.info("Get All Users invoked ");
        List<User> users = userRepository.getUsers();

        //One exception for all Application Errors!!!
        if (users.isEmpty())
            throw new ApplicationException(ApplicationError.NO_USERS_FOUND);

        return UserDtoMapper.mapUserListToGetUserResponseList(users);
    }

    public GetUserResponse getUser(long id) {
        log.info("Get user with id: " + id);

        User user = userRepository.getUser(id);
        if (user == null)
            throw new ApplicationException(ApplicationError.USER_NOT_FOUND);

        return UserDtoMapper.mapUserToUserResponse(user);
    }

    public CreateUserResponse addUser(CreateUserRequest createUserRequest) {
        log.info("Adding user " + createUserRequest.getUsername());
        User newUser = UserDtoMapper.mapCreateUserRequestToUser(createUserRequest);

        //check if username exists
        for(User user: userRepository.getUsers()){
            if (user.getUsername().equalsIgnoreCase(newUser.getUsername()))
                throw new ApplicationException(ApplicationError.USERNAME_EXISTS);
        }
        User user = userRepository.addUser(newUser);

        return UserDtoMapper.mapUserToCreateUserResponse(user);
    }

    public CheckoutResponse checkout(long id) {
        User user = userRepository.getUser(id);
        if (user == null)
            throw new ApplicationException(ApplicationError.USER_NOT_FOUND);

        if (user.getCart().getProductList().isEmpty())
            throw new ApplicationException(ApplicationError.CART_EMPTY);

        Order order = new Order();
        order.setProducts(user.getCart().getProductList());
        order.setUserId(user.getUserId());
        Order newOrder = orderRepository.addOrder(order);

        return UserDtoMapper.mapCheckoutResponseFromOrder(user.checkout(), newOrder);
    }


    // Generic Response Message
    public ResponseMessage deleteAllUsers() {
        List<User> users = userRepository.getUsers();
        if (users.isEmpty())
            throw new ApplicationException(ApplicationError.NO_USERS_FOUND);
        users.clear();
        return new ResponseMessage("0", "OK");
    }

}
