package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.crud.user.UserRequest;
import com.camperfire.marketflow.model.user.User;

public interface UserService {

    User createUser(UserRequest request);

    User readUser(Long id);

    User updateUser(UserRequest request);

    void deleteUser(Long id);
}
