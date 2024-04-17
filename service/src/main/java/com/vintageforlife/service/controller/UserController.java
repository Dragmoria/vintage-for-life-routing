package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
        UserDTO createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
