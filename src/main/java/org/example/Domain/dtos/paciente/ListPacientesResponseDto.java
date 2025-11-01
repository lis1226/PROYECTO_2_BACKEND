package org.example.Domain.dtos.paciente;

import java.util.List;

public class ListPacientesResponseDto {
    private List<PacienteResponseDto> pacientes;


    public ListPacientesResponseDto(List<PacienteResponseDto> pacientes) {
        this.pacientes = pacientes;
    }

    public List<PacienteResponseDto> getPacientes() {
        return pacientes;
    }
    public void setPacientes(List<PacienteResponseDto> pacientes) {
        this.pacientes = pacientes;
    }

}
