package org.example.Domain.dtos.auth;

public class ChangePasswordRequestDto {
    private String usernameOrEmail;
    private String currentPassword;
    private String newPassword;

    public ChangePasswordRequestDto() {}

    public ChangePasswordRequestDto(String usernameOrEmail, String currentPassword, String newPassword) {
        this.usernameOrEmail = usernameOrEmail;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    // Getters y Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}