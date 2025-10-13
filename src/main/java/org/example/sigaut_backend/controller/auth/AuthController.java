package org.example.sigaut_backend.controller.auth;

import org.example.sigaut_backend.controller.auth.dto.Login;
import org.example.sigaut_backend.models.User;
import org.example.sigaut_backend.repository.UserRepository;
import org.example.sigaut_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"*"})
public class AuthController {
    private UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        Optional<User> user = userRepository.findByUsername(login.getUsername());

        if (user.isPresent() && user.get().getPassword().equals(login.getPassword())) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }
    }
}
