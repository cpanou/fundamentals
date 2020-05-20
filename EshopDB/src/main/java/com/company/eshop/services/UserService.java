package com.company.eshop.services;

import com.company.eshop.application.MyApplication;
import com.company.eshop.dtomappers.UserDtoMapper;
import com.company.eshop.dtos.*;
import com.company.eshop.exceptions.ApplicationError;
import com.company.eshop.exceptions.ApplicationException;
import com.company.eshop.model.Cart;
import com.company.eshop.model.Order;
import com.company.eshop.model.OrderStatus;
import com.company.eshop.model.User;
import com.company.eshop.repository.CartStore;
import com.company.eshop.repository.OrderRepository;
import com.company.eshop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class UserService {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());

    //Gets a reference to the Repositories
    private UserRepository userRepository = new UserRepository();
    private OrderRepository orderRepository = OrderRepository.getInstance();
    private CartStore cartStore = CartStore.getInstance();

    public UsersListResponse getUsers() {
        log.info("Get All Users invoked ");
        List<User> users = userRepository.getAllDBUsers();

        //One exception for all Application Errors!!!
        if (users.isEmpty())
            throw new ApplicationException(ApplicationError.NO_USERS_FOUND);

        return UserDtoMapper.mapUserListToGetUserResponseList(users);
    }

    public GetUserResponse getUser(long id) {
        log.info("Get user with id: " + id);

        User user = userRepository.getDBUser(id);
        if (user == null)
            throw new ApplicationException(ApplicationError.USER_NOT_FOUND);

        return UserDtoMapper.mapUserToUserResponse(user);
    }

    public CreateUserResponse addUser(CreateUserRequest createUserRequest) {
        log.info("Adding user " + createUserRequest.getUsername());
        User newUser = UserDtoMapper.mapCreateUserRequestToUser(createUserRequest);

        //check if username exists
        if (userRepository.getDBUser(newUser.getUsername()) != null)
                throw new ApplicationException(ApplicationError.USERNAME_EXISTS);
        User user = userRepository.createDBUser(newUser);

        return UserDtoMapper.mapUserToCreateUserResponse(user);
    }

    public CheckoutResponse checkout(long id) {
        User user = userRepository.getDBUser(id);
        if (user == null)
            throw new ApplicationException(ApplicationError.USER_NOT_FOUND);
        Cart userCart = cartStore.getCart(id);
        if (userCart == null || userCart.getProductList().isEmpty())
            throw new ApplicationException(ApplicationError.CART_EMPTY);

        Order order = new Order();
        order.setProducts(userCart.getProductList());
        order.setUserId(user.getUserId());
        order.setStatus(OrderStatus.SUBMITTED);
        order.setSubmittedDate(LocalDateTime.now());
        Order newOrder = orderRepository.addOrder(order);
        return UserDtoMapper.mapCheckoutResponseFromOrder(user.checkout(), newOrder);
    }

    // Generic Response Message
    public ResponseMessage deleteAllUsers() {
        List<User> users = userRepository.getAllDBUsers();
        if (users.isEmpty())
            throw new ApplicationException(ApplicationError.NO_USERS_FOUND);
        users.clear();
        return new ResponseMessage("0", "OK");
    }

}
