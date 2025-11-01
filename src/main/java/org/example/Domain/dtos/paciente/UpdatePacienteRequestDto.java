package org.example.Domain.dtos.paciente;

import java.util.Date;

public class UpdatePacienteRequestDto {

    private String id;
    private String nombre;
    private Date fechaNacimiento;

    public UpdatePacienteRequestDto() {
    }

    public UpdatePacienteRequestDto(String id, String nombre, Date fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

