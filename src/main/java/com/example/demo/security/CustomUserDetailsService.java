package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;
    public CustomUserDetailsService(UserRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repo.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException(""));
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), List.of(new SimpleGrantedAuthority(u.getRole())));
    }
}