package com.app.security.service;

import com.app.security.entities.Role;
import com.app.security.utilities.CustomApiResponse;

public interface IRoleService {

    CustomApiResponse createNewRole(Role role);
}
