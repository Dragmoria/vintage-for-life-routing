package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/info", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> getUserById() {
        UserDTO user = userService.getUserById(1);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "test")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
