package com.app.security.service;

import com.app.security.entities.Role;
import com.app.security.respository.IRoleRepository;
import com.app.security.utilities.CustomApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private IRoleRepository roleRepository;

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public CustomApiResponse createNewRole(Role role) {
        logger.debug("Inside createNewRole method::{}",role);
        Role newRole = roleRepository.save(role);
        return new CustomApiResponse("New Role created with Role ID::"+newRole.getRoleId());
    }
}
