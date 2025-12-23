package ma.ensah.taskflowprojecthahnsoftwarebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.LoginRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.RegisterRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.AuthResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.UserResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")

public class AuthController {
    private final IAuthService authService;
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description =" Create a new User account")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody RegisterRequest request){
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate useraccount and return JWT token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get authenticated user information")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserResponse response = authService.getCurrentUser(email);
        return ResponseEntity.ok(response);
    }
}
