package com.busbooking.backend.controller;

import com.busbooking.backend.entity.User;
import com.busbooking.backend.repository.UserRepository;
import com.busbooking.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //register
    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody Map<String, String> request) {

        if (userRepository.findByUsername(request.get("username")).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User admin = new User();
        admin.setUsername(request.get("username"));
        admin.setPassword(passwordEncoder.encode(request.get("password")));
        admin.setRole("ROLE_ADMIN");

        userRepository.save(admin);

        return "Admin registered successfully";
    }

    //login
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        User user = userRepository.findByUsername(request.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.get("password"), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return Map.of("token", token);
    }
}
