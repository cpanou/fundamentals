package com.company.eshop.dtos;

import com.company.eshop.model.User;

import java.util.List;

public class UsersListResponse {

    private List<GetUserResponse> userList;

    public UsersListResponse(){}

    public UsersListResponse(List<GetUserResponse> userList) {
        this.userList = userList;
    }

    public List<GetUserResponse> getUserList() {
        return userList;
    }

    public void setUserList(List<GetUserResponse> userList) {
        this.userList = userList;
    }

}
