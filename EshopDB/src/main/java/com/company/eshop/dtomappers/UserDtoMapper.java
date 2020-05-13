package com.company.eshop.dtomappers;

import com.company.eshop.dtos.*;
import com.company.eshop.model.Order;
import com.company.eshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {

    // CreateUserRequest --> User
    public static User mapCreateUserRequestToUser(CreateUserRequest createUserRequest) {
        return new User(createUserRequest.getEmail(),
                createUserRequest.getUsername(),
                createUserRequest.getFirstname(),
                createUserRequest.getLastname());
    }

    // User -> CreateUserResponse
    public static CreateUserResponse mapUserToCreateUserResponse(User user){
        return new CreateUserResponse(String.valueOf(user.getUserId()),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName());

    }

    // User -> GetUserResponse
    public static GetUserResponse mapUserToUserResponse(User user) {
        return new GetUserResponse(""+user.getUserId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    // List of Users -> UsersListResponse
    // Uses the single map method mapUserToUserResponse
    public static UsersListResponse mapUserListToGetUserResponseList(List<User> users) {
        UsersListResponse response = new UsersListResponse();
        List<GetUserResponse> responseList = new ArrayList<>();
        for(User user: users){
            responseList.add( mapUserToUserResponse(user) );
        }
        response.setUserList(responseList);
        return response;
    }

    public static CheckoutResponse mapCheckoutResponseFromOrder(double cost, Order newOrder) {
        return new CheckoutResponse(String.valueOf(cost), newOrder);
    }
}
