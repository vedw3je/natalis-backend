package dev.ved.natalis.user_service.controller;
import dev.ved.natalis.user_service.dto.UserResponse;
import dev.ved.natalis.user_service.entity.User;
import dev.ved.natalis.user_service.enums.Role;
import dev.ved.natalis.user_service.repository.UserRepository;
import dev.ved.natalis.user_service.requests.LoginRequest;
import dev.ved.natalis.user_service.requests.RegisterRequest;
import dev.ved.natalis.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = userService.registerUser(request, Role.MOTHER);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* =========================
       LOGIN
       ========================= */

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {

        User user = userService.authenticate(request);

//        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                Map.of(

                        "user", UserResponse.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build()
                )
        );
    }
}
