package org.example.sigaut_backend.controller.user;

import org.example.sigaut_backend.config.ApiResponse;
import org.example.sigaut_backend.models.User;
import org.example.sigaut_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"*"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateUserById(@RequestBody User updatedUser) {
        return userService.updateUserById(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
