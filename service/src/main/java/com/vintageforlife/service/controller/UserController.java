package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/info", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_CARRIER', 'ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<UserDTO> getUserInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        String username = userDetails.getUsername();

        UserDTO user = userService.getUserByEmail(username);
        return ResponseEntity.ok(user);
    }
}
