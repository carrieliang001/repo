package com.contoso.azureDemo.userservice.impl;

import com.contoso.azureDemo.shared.Utils;
import com.contoso.azureDemo.ui.model.response.UserRest;
import com.contoso.azureDemo.userservice.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ui.model.request.UserDetailsRequestModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
//will create the instance of it to make it available (AutoWired)
public class UserServiceImpl implements UserService {
    Map<String,UserRest> users;

    Utils utils;

    public UserServiceImpl(){

    }

    @Autowired
    public UserServiceImpl(Utils utils){
        this.utils = utils;
    }


    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());


        String userId = utils.generateUserId();
        returnValue.setUserId(userId);

        if(users==null)
            users = new HashMap<>();
        users.put(userId,returnValue);

        return returnValue;
    }
}
