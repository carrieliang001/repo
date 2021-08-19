package com.contoso.azureDemo.ui.controller;

import com.contoso.azureDemo.exceptions.UserServiceException;
import com.contoso.azureDemo.ui.model.response.UserRest;
import com.contoso.azureDemo.userservice.UserService;
import com.contoso.azureDemo.userservice.impl.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ui.model.request.UpdateUserDetailsRequestModel;
import ui.model.request.UserDetailsRequestModel;

import javax.validation.Valid;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users") //http://localhost.8080/users/
public class UserController {

    Map<String,UserRest> users;

    @Autowired
            //create an instance of the user service implementation, and inject that instance into this usercontroller object
    UserService userService;


    @GetMapping
    public String getUser(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        return "get user was called with page =" + page + " and limit " + limit;
    }

    @GetMapping(path = "/{userId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        if(true)
            throw new UserServiceException("A user service exception is thrown");

    if(users.containsKey(userId))
    {
        return new ResponseEntity <UserRest>(users.get(userId),HttpStatus.OK);
    }
    else
        return new ResponseEntity <UserRest>(HttpStatus.NO_CONTENT);


    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
//create an object of userServiceImpl without new
        UserRest returnValue = userService.createUser(userDetails);

        return new ResponseEntity <UserRest>(returnValue,HttpStatus.OK);

    }

    @PutMapping(path = "/{userId}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
        UserRest storedUserDetails = users.get(userId);

        storedUserDetails.setFirstName(userDetails.getFirstName());
        storedUserDetails.setLastName(userDetails.getLastName());

        users.put(userId,storedUserDetails);

        return storedUserDetails;
    }

    @DeleteMapping(path="/{id}")

    public ResponseEntity<Void> deleteUser(@PathVariable String id) {

        users.remove(id);
        return ResponseEntity.noContent().build();
    }
}
