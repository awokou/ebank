package com.server.api.ebank.service.impl;

import com.server.api.ebank.entity.RefreshToken;
import com.server.api.ebank.entity.enums.Role;
import com.server.api.ebank.entity.enums.TokenType;
import com.server.api.ebank.repository.RefreshTokenRepository;
import com.server.api.ebank.service.HistoryService;
import com.server.api.ebank.dto.LoginDto;
import com.server.api.ebank.dto.UserDto;
import com.server.api.ebank.dto.AuthResponse;
import com.server.api.ebank.entity.User;
import com.server.api.ebank.exception.AlreadyExistException;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.UserRepository;
import com.server.api.ebank.security.jwt.JwtUtils;
import com.server.api.ebank.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final HistoryService historyService;

    @Override
    public AuthResponse authenticate(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        //
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var refreshToken = createRefreshToken(loginDto.getEmail());

        historyService.saveHistory(user, "Connecté  " + user.getName());

        return AuthResponse.builder()
                .accessToken(token)
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles().name())
                .tokenType(TokenType.BEARER.name())
                .build();
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        boolean userExists = userRepository.existsByEmail(userDto.getEmail());
        if (userExists) {
            throw new AlreadyExistException("Email %s is already in use" + userDto.getEmail());
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Role.ADMIN);

        User userSaved = userRepository.save(user);
        createRefreshToken(userSaved.getEmail());

        historyService.saveHistory(user, "Utilisateur créé avec success: " + user.getName());

        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserDto)
                .toList();

    }

    @Override
    public UserDto getUsersById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

        return mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exists with given id:" + id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRoles(Role.valueOf(userDto.getRoles()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        
        userRepository.save(user);

        return userDto;
    }

    @Override
    public void deleteUserById(Integer id) {
        this.userRepository.findById(id);
    }

    private RefreshToken createRefreshToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        long refreshTokenValidity = 30L * 1000;

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenValidity))
                .revoked(false)
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Map User entity to UserDto.
     *
     * @param user the taxe entity
     * @return the user data transfer object
     */
    private UserDto mapToUserDto(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().name());

        return userDto;
    }
}
