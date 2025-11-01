package org.example.Domain.dtos.administrador;

public class DeleteAdministradorRequestDto {
    private Long id;
    public DeleteAdministradorRequestDto() {}
    public DeleteAdministradorRequestDto(Long id) { this.id = id; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
