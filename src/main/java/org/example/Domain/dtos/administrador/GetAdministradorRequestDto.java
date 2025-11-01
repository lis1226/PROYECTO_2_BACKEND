package org.example.Domain.dtos.administrador;

public class GetAdministradorRequestDto {
    private Long id;

    public GetAdministradorRequestDto() {}
    public GetAdministradorRequestDto(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
