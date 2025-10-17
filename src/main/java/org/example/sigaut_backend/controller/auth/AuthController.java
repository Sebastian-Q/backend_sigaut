package org.example.sigaut_backend.controller.auth;

import org.example.sigaut_backend.controller.auth.dto.Login;
import org.example.sigaut_backend.models.User;
import org.example.sigaut_backend.repository.UserRepository;
import org.example.sigaut_backend.services.AuthService;
import org.example.sigaut_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"*"})
public class AuthController {
    private AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return service.signIn(login.getUsername(), login.getPassword());
    }
}
