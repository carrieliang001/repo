package com.contoso.azureDemo.exceptions;

public class UserServiceException extends RuntimeException{


    private static final long serialVersionUID = -2434568335453211196L;

    public UserServiceException(String message){
        super(message);
    }

}
