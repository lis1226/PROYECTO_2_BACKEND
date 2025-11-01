package org.example.Domain.dtos.paciente;

import java.util.Date;

public class AddPacienteRequestDto {
    private String id;
    private Long usuarioId;
    private String nombre;
    private Date fechaNacimiento;

    public AddPacienteRequestDto() {
    }

    public String getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
}
