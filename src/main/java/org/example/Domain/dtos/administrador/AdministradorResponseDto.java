package org.example.Domain.dtos.administrador;

public class AdministradorResponseDto {
    private Long id;
    private String username;
    private String email;
    private String rol;
    private String createdAt;
    private String updatedAt;

    public AdministradorResponseDto() {}

    public AdministradorResponseDto(Long id, String username, String email, String rol, String createdAt, String updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
