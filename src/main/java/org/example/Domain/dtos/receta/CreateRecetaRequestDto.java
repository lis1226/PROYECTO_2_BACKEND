package org.example.Domain.dtos.receta;

import java.util.Date;
import java.util.List;

public class CreateRecetaRequestDto {
    private Date fechaRetiro;
    private String idPaciente;
    private String idMedico;
    private List<DetalleRecetaDto> detalles;

    public CreateRecetaRequestDto() {}

    // getters / setters
    public Date getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(Date fechaRetiro) { this.fechaRetiro = fechaRetiro; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public List<DetalleRecetaDto> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRecetaDto> detalles) { this.detalles = detalles; }
}
