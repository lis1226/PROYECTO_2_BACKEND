package org.example.Domain.dtos.receta;

import java.util.List;

public class AddRecetaRequestDto {
    private String id;
    private String idPaciente;
    private String idMedico;
    private String estado;
    private List<DetalleRecetaDto> detalles;

    public AddRecetaRequestDto() {}

    public AddRecetaRequestDto(String id, String idPaciente, String idMedico, String estado, List<DetalleRecetaDto> detalles) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.estado = estado;
        this.detalles = detalles;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleRecetaDto> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRecetaDto> detalles) { this.detalles = detalles; }
}
