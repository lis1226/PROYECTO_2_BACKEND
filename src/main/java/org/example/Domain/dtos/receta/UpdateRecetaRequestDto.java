package org.example.Domain.dtos.receta;

public class UpdateRecetaRequestDto {
    private String id;
    private String estado;

    public UpdateRecetaRequestDto() {}

    public UpdateRecetaRequestDto(String id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
