package dev.ved.natalis.user_service.dto;

import dev.ved.natalis.user_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;

}