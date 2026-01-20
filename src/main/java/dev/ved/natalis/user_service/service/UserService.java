package dev.ved.natalis.user_service.service;
import dev.ved.natalis.user_service.entity.User;
import dev.ved.natalis.user_service.enums.Role;
import dev.ved.natalis.user_service.repository.UserRepository;
import dev.ved.natalis.user_service.requests.LoginRequest;
import dev.ved.natalis.user_service.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public User registerUser(RegisterRequest request, Role forcedRole) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(forcedRole); // IMPORTANT
        user.setIsActive(true);
        user.setCreatedAt(Instant.now());

        return userRepository.save(user);
    }

    public User authenticate(LoginRequest loginRequest) {

        User user = userRepository
                .findByEmailAndIsActiveTrue(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void deactivateUser(String userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setIsActive(false);
            userRepository.save(user);
        });
    }
}
