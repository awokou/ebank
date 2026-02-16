package com.server.api.ebank.service;

import com.server.api.ebank.dto.LoginDto;
import com.server.api.ebank.dto.UserDto;
import com.server.api.ebank.dto.AuthResponse;

import java.util.List;

public interface UserService {

    AuthResponse authenticate(LoginDto loginDto);

    UserDto saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

    UserDto getUsersById(Integer id);

    UserDto updateUser(UserDto userDto, Integer id);

    void deleteUserById(Integer id);
}
