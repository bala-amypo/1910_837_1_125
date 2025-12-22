package com.example.demo.service.impl;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // <-- declare this

    // Constructor injection
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterRequest request) {

        if (userRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : "MANAGER");
        user.setPassword(passwordEncoder.encode(request.getPassword()));  // safe now

        return userRepository.save(user);
    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }
}
