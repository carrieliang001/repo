package com.contoso.azureDemo.shared;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Utils {


    public String generateUserId(){
        return UUID.randomUUID().toString();
    }
}
