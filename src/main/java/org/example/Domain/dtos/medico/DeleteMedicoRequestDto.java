package org.example.Domain.dtos.medico;

public class DeleteMedicoRequestDto {
    private String id;

    public DeleteMedicoRequestDto() {
    }

    public DeleteMedicoRequestDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
