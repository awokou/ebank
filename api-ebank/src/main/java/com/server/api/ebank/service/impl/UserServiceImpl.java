package com.server.api.ebank.service.impl;

import com.server.api.ebank.repository.RefreshTokenRepository;
import com.server.api.ebank.service.HistoryService;
import com.server.api.ebank.domain.dto.request.LoginDto;
import com.server.api.ebank.domain.dto.request.UserDto;
import com.server.api.ebank.domain.dto.response.AuthResponse;
import com.server.api.ebank.domain.entity.RefreshToken;
import com.server.api.ebank.domain.entity.User;
import com.server.api.ebank.domain.enums.Role;
import com.server.api.ebank.domain.enums.TokenType;
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
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        //
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var refreshToken = createRefreshToken(loginDto.email());

        historyService.saveHistory(user, "Connecté  " + user.getName());

        return AuthResponse.builder()
                .accessToken(token)
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .tokenType(TokenType.BEARER.name())
                .build();
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        boolean userExists = userRepository.existsByEmail(userDto.email());
        if (userExists) {
            throw new AlreadyExistException("Email %s is already in use" + userDto.email());
        }
        User user = new User();
        user.setEmail(userDto.email());
        user.setName(userDto.name());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(Role.ADMIN);
        user.setGender(userDto.gender());
        user.setPhoneNumber(userDto.phoneNumber());

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

        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setRole(Role.valueOf(userDto.role()));
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setGender(userDto.gender());
        user.setPhoneNumber(userDto.phoneNumber());

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

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(30L * 1000))
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
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                user.getGender(),
                user.getPhoneNumber()
        );
    }
}
