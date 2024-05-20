package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.services.database.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/info", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_PLANNER', 'ROLE_CARRIER')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<UserDTO> getUserInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        String username = userDetails.getUsername();

        UserDTO user = userService.getUserByEmail(username);
        return ResponseEntity.ok(user);
    }
    @GetMapping(value = "/userbeheer", produces = "application/json")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping(value = "/userbeheer/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/userbeheer/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }


}
