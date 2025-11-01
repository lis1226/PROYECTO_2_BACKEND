package org.example.Domain.dtos.paciente;

import java.util.Date;

public class PacienteResponseDto {
    private String id;
    private Long usuarioId;
    private String nombre;
    private Date fechaNacimiento;

    public PacienteResponseDto(String id, Long usuarioId, String nombre, Date fechaNacimiento) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
