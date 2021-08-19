package com.contoso.azureDemo.userservice;

import com.contoso.azureDemo.ui.model.response.UserRest;
import ui.model.request.UserDetailsRequestModel;

public interface UserService {
    UserRest createUser(UserDetailsRequestModel userDetails);


}
