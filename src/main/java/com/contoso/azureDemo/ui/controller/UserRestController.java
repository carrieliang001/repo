package com.contoso.azureDemo.ui.controller;

import com.contoso.azureDemo.exceptions.UserServiceException;
import com.contoso.azureDemo.repository.UserRestRepository;
import com.contoso.azureDemo.ui.model.response.UserRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ui.model.request.UpdateUserDetailsRequestModel;

import javax.validation.Valid;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

//@RestController
//public class HelloController {
//
//    @Autowired
//    private StringRedisTemplate template;
//
//    @RequestMapping("/")
//    // Define the Hello World controller.
//    public String hello() {
//
//        ValueOperations<String, String> ops = this.template.opsForValue();
//
//        // Add a Hello World string to your cache.
//        String key = "greeting";
//        if (!this.template.hasKey(key)) {
//            ops.set(key, "Hello World!");
//        }
//
//        // Return the string from your cache.
//        return ops.get(key);
//    }
//}
@RestController
@RequestMapping("/userRest")
public class UserRestController {

    @Autowired
    private UserRestRepository userrestRepository;
    @Autowired
    private StringRedisTemplate template;


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRest createUserRest(@RequestBody UserRest userrest) throws JsonProcessingException {

        UserRest rest = userrestRepository.save(userrest);
        ObjectMapper details = new ObjectMapper();
        ValueOperations<String, String> ops = this.template.opsForValue();

        ops.set(rest.getUserId(), details.writeValueAsString(rest));

        return rest;
    }

    @GetMapping("/")
    public Iterable<UserRest> getUserRest() {

        return userrestRepository.findAll();
    }

    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> getUserRest(@PathVariable String userId) throws JsonProcessingException {
//                if(true)
//                        throw new UserServiceException("A user service exception is thrown");

        if (this.template.hasKey(userId)) {
            ValueOperations<String, String> ops = this.template.opsForValue();

            String userDetails = ops.get(userId);
            ObjectMapper details = new ObjectMapper();
            UserRest rest = details.readValue(userDetails, UserRest.class);
            return new ResponseEntity<UserRest>(rest, HttpStatus.OK);
        }

        else{

        UserRest userrest = userrestRepository.getById(userId);
        ObjectMapper details = new ObjectMapper();
        ValueOperations<String, String> ops = this.template.opsForValue();

        ops.set(userId, details.writeValueAsString(userrest));

        return new ResponseEntity<UserRest>(userrest, HttpStatus.OK);
    }
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUserRest(@PathVariable String userId) throws JsonProcessingException {

        if (this.template.hasKey(userId)) {
//            ValueOperations<String, String> ops = this.template.opsForValue();
//
//            String userDetails = ops.get(userId);
//            ObjectMapper details = new ObjectMapper();
//            UserRest rest = details.readValue(userDetails, UserRest.class);
            this.template.delete(userId);
        }

        userrestRepository.deleteAllById(Arrays.asList(userId));
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping(path = "/")
    public ResponseEntity<Void> deleteUserRest() {

        userrestRepository.deleteAll();
        return ResponseEntity.noContent().build();

    }

    @PutMapping(path = "/{userId}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUserRest(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) throws JsonProcessingException {
        if (this.template.hasKey(userId)) {

            ValueOperations<String, String> ops = this.template.opsForValue();


            UserRest user = new UserRest();
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            ObjectMapper details = new ObjectMapper();
            ops.set(userId, details.writeValueAsString(user));

        }

            UserRest storedUserDetails = userrestRepository.getById(userId);

            storedUserDetails.setFirstName(userDetails.getFirstName());
            storedUserDetails.setLastName(userDetails.getLastName());

            return userrestRepository.save(storedUserDetails);


        }


}

