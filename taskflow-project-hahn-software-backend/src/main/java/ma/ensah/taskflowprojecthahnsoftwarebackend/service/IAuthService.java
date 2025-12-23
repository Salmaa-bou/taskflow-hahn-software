package ma.ensah.taskflowprojecthahnsoftwarebackend.service;

import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.LoginRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.RegisterRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.AuthResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.UserResponse;

public interface IAuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    UserResponse getCurrentUser(String email);
}