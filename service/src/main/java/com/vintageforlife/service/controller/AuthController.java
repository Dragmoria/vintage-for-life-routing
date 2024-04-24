package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.AuthenticationDTO;
import com.vintageforlife.service.dto.AuthenticationResponseDTO;
import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.service.AuthenticationService;
import com.vintageforlife.service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        try {
            String token = authenticationService.authenticate(authenticationDTO.getEmail(), authenticationDTO.getPassword());

            AuthenticationResponseDTO response = new AuthenticationResponseDTO(token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }
}
