package com.app.security.service;

import com.app.security.dtos.UserDto;
import com.app.security.utilities.CustomApiResponse;

public interface IUserService {

    CustomApiResponse createNewUser(UserDto userDto);
}
