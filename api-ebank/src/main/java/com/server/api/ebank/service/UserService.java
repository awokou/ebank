package com.server.api.ebank.service;

import java.util.List;

import com.server.api.ebank.domain.dto.request.LoginDto;
import com.server.api.ebank.domain.dto.request.UserDto;
import com.server.api.ebank.domain.dto.response.AuthResponse;

public interface UserService {

    AuthResponse authenticate(LoginDto loginDto);

    UserDto saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

    UserDto getUsersById(Integer id);

    UserDto updateUser(UserDto userDto, Integer id);

    void deleteUserById(Integer id);
}
