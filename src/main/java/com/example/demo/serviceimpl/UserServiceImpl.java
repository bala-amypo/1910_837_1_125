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
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository r, PasswordEncoder p) { this.repository = r; this.passwordEncoder = p; }

    public User register(RegisterRequest req) {
        if (repository.findByEmailIgnoreCase(req.getEmail()).isPresent()) throw new BadRequestException("Email already in use");
        User user = new User();
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole() == null ? "ROLE_USER" : req.getRole());
        return repository.save(user);
    }
    public User findByEmailIgnoreCase(String email) { return repository.findByEmailIgnoreCase(email).orElse(null); }
}