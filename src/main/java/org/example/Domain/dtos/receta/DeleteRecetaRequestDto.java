package org.example.Domain.dtos.receta;

public class DeleteRecetaRequestDto {
    private String id;

    public DeleteRecetaRequestDto() {}

    public DeleteRecetaRequestDto(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
