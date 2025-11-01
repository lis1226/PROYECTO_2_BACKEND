package org.example.Domain.dtos.paciente;

public class DeletePacienteRequestDto {
    private String id;

    public DeletePacienteRequestDto() {
    }

    public DeletePacienteRequestDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


