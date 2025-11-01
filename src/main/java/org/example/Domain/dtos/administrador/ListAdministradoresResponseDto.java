package org.example.Domain.dtos.administrador;

import java.util.List;

public class ListAdministradoresResponseDto {
    private List<AdministradorResponseDto> administradores;
    public ListAdministradoresResponseDto() {}
    public ListAdministradoresResponseDto(List<AdministradorResponseDto> administradores) {
        this.administradores = administradores;
    }
    public List<AdministradorResponseDto> getAdministradores() { return administradores; }
    public void setAdministradores(List<AdministradorResponseDto> administradores) { this.administradores = administradores; }
}
