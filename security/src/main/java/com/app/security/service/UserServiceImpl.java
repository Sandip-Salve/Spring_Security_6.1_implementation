package com.app.security.service;

import com.app.security.dtos.UserDto;
import com.app.security.entities.Role;
import com.app.security.entities.User;
import com.app.security.respository.IRoleRepository;
import com.app.security.respository.IUserRepository;
import com.app.security.utilities.CustomApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomApiResponse createNewUser(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = userDto.getRoles().stream().map(roleName->roleRepository.findByRoleName(roleName).orElseThrow(()->new RuntimeException("Role Not Found!"))).collect(Collectors.toSet());
        user.setRoles(roles);
        user.setBlocked(1);
        user.setCreatedAt(LocalDate.now());
        user = userRepository.save(user);
        return new CustomApiResponse("New User created with User-ID::"+user.getUserId());
    }
}
