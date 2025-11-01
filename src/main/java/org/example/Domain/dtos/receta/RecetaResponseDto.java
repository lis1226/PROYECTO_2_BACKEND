package org.example.Domain.dtos.receta;

import java.util.Date;
import java.util.List;

public class RecetaResponseDto {
    private String id;
    private String idPaciente;
    private String idMedico;
    private String estado;
    private Date fechaConfeccion;
    private Date fechaRetiro;
    private List<DetalleRecetaDto> detalles;

    public RecetaResponseDto() {}

    public RecetaResponseDto(String id, String idPaciente, String idMedico, String estado,
                             Date fechaConfeccion, Date fechaRetiro, List<DetalleRecetaDto> detalles) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.estado = estado;
        this.fechaConfeccion = fechaConfeccion;
        this.fechaRetiro = fechaRetiro;
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

    public Date getFechaConfeccion() { return fechaConfeccion; }
    public void setFechaConfeccion(Date fechaConfeccion) { this.fechaConfeccion = fechaConfeccion; }

    public Date getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(Date fechaRetiro) { this.fechaRetiro = fechaRetiro; }

    public List<DetalleRecetaDto> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRecetaDto> detalles) { this.detalles = detalles; }
}
