package ma.ensah.taskflowprojecthahnsoftwarebackend.service.impl;

import lombok.RequiredArgsConstructor;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.LoginRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.request.RegisterRequest;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.AuthResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.exception.ValidationException;
import ma.ensah.taskflowprojecthahnsoftwarebackend.repository.UserRepository;
import ma.ensah.taskflowprojecthahnsoftwarebackend.security.JwtTokenProvider;
import ma.ensah.taskflowprojecthahnsoftwarebackend.service.IAuthService;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ma.ensah.taskflowprojecthahnsoftwarebackend.dto.response.UserResponse;
import ma.ensah.taskflowprojecthahnsoftwarebackend.domain.enums.UserRole;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email is already in use");
        }
                UserRole role = UserRole.USER;
                if (request.getRole() != null && !request.getRole().isBlank()) {
                        try {
                                role = UserRole.valueOf(request.getRole().toUpperCase());
                        } catch (IllegalArgumentException ex) {
                                throw new ValidationException("Invalid role");
                        }
                }

                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .fullName(request.getFullName())
                                .skills(request.getSkills())
                                .role(role)
                                .build();
        userRepository.save(user);
        String token = jwtTokenProvider.generateTokenFromUsername(user.getEmail());
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                                .skills(user.getSkills())
                                .role(user.getRole() != null ? user.getRole().name() : UserRole.USER.name())
                .createdAt(user.getCreatedAt())
                .build();
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .user(userResponse)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String token = jwtTokenProvider.generateToken(authentication);
       User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ValidationException("User doesn't exist"));
    UserResponse userResponse = UserResponse.builder().id(user.getId())
            .email(user.getEmail())
            .fullName(user.getFullName())
            .skills(user.getSkills())
            .createdAt(user.getCreatedAt())
            .build();
    return AuthResponse.builder()
            .token(token)
            .type("Bearer")
            .user(userResponse)
            .build();
    }

    @Override
   public  UserResponse getCurrentUser(String email){

      User user = userRepository.findByEmail(email).orElseThrow(() -> new ValidationException("User doesn't exist"));
      return UserResponse.builder()
              .id(user.getId())
              .email(user.getEmail())
              .fullName(user.getFullName())
              .skills(user.getSkills())
              .createdAt(user.getCreatedAt())
              .build();
    }
}
