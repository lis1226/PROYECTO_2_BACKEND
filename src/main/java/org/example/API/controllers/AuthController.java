package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.AuthService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.auth.ChangePasswordRequestDto;
import org.example.Domain.dtos.auth.LoginRequestDto;
import org.example.Domain.dtos.auth.RegisterRequestDto;
import org.example.Domain.dtos.auth.UsuarioResponseDto;
import org.example.Domain.models.Usuario;

public class AuthController {
    private final AuthService authService;
    private final Gson gson = new Gson();

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Single routing entry point for the handler
    public ResponseDto route(RequestDto request) {
        try {
            switch (request.getRequest()) {
                case "login":
                    return handleLogin(request);
                case "register":
                    return handleRegister(request);
                case "logout":
                    return handleLogout(request);
                case "changePassword":
                    return handleChangePassword(request);
                default:
                    return new ResponseDto(false, "Unknown request: " + request.getRequest(), null);
            }
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    // --- LOGIN ---
    private ResponseDto handleLogin(RequestDto request) {
        try {
            LoginRequestDto loginDto = gson.fromJson(request.getData(), LoginRequestDto.class);

            boolean success = authService.login(loginDto.getUsernameOrEmail(), loginDto.getPassword());
            if (!success) {
                return new ResponseDto(false, "Invalid credentials", null);
            }

            UsuarioResponseDto userDto = getUserByUsername(loginDto.getUsernameOrEmail());
            return new ResponseDto(true, "Login successful", gson.toJson(userDto));
        } catch (Exception e) {
            System.out.println("Error in handleLogin: " + e.getMessage());
            throw e;
        }
    }

    // --- REGISTER ---
    private ResponseDto handleRegister(RequestDto request) {
        try {
            RegisterRequestDto regDto = gson.fromJson(request.getData(), RegisterRequestDto.class);
            Usuario user = authService.register(regDto.getUsername(), regDto.getEmail(), regDto.getPassword(), regDto.getRole());

            UsuarioResponseDto userDto = new UsuarioResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRol(),
                    user.getCreatedAt().toString(),
                    user.getUpdatedAt().toString()
            );

            return new ResponseDto(true, "User registered successfully", gson.toJson(userDto));
        } catch (Exception e) {
            System.out.println("Error in handleRegister: " + e.getMessage());
            throw e;
        }
    }

    // --- CHANGE PASSWORD ---
    private ResponseDto handleChangePassword(RequestDto request) {
        try {
            ChangePasswordRequestDto changePasswordDto = gson.fromJson(request.getData(), ChangePasswordRequestDto.class);

            // Validaciones básicas
            if (changePasswordDto.getUsernameOrEmail() == null || changePasswordDto.getUsernameOrEmail().trim().isEmpty()) {
                return new ResponseDto(false, "Username or email is required", null);
            }

            if (changePasswordDto.getCurrentPassword() == null || changePasswordDto.getCurrentPassword().trim().isEmpty()) {
                return new ResponseDto(false, "Current password is required", null);
            }

            if (changePasswordDto.getNewPassword() == null || changePasswordDto.getNewPassword().trim().isEmpty()) {
                return new ResponseDto(false, "New password is required", null);
            }

            if (changePasswordDto.getNewPassword().length() < 4) {
                return new ResponseDto(false, "New password must be at least 4 characters long", null);
            }

            if (changePasswordDto.getCurrentPassword().equals(changePasswordDto.getNewPassword())) {
                return new ResponseDto(false, "New password must be different from current password", null);
            }

            // Intentar cambiar la contraseña
            boolean success = authService.changePassword(
                    changePasswordDto.getUsernameOrEmail(),
                    changePasswordDto.getCurrentPassword(),
                    changePasswordDto.getNewPassword()
            );

            if (success) {
                return new ResponseDto(true, "Password changed successfully", null);
            } else {
                return new ResponseDto(false, "Failed to change password. Please verify your current password.", null);
            }

        } catch (Exception e) {
            System.out.println("Error in handleChangePassword: " + e.getMessage());
            e.printStackTrace();
            return new ResponseDto(false, "Server error: " + e.getMessage(), null);
        }
    }

    // --- LOGOUT ---
    private ResponseDto handleLogout(RequestDto request) {
        try {
            return new ResponseDto(true, "Logout successful", null);
        } catch (Exception e) {
            System.out.println("Error in handleLogout: " + e.getMessage());
            throw e;
        }
    }

    // --- HELPER: GET USER BY USERNAME ---
    public UsuarioResponseDto getUserByUsername(String username) {
        try {
            Usuario user = authService.getUserByUsername(username);
            if (user == null) {
                user = authService.getUserByEmail(username);
            }
            if (user == null) return null;

            return new UsuarioResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRol(),
                    user.getCreatedAt().toString(),
                    user.getUpdatedAt().toString()
            );
        } catch (Exception e) {
            System.out.println("Error in getUserByUsername: " + e.getMessage());
            throw e;
        }
    }
}