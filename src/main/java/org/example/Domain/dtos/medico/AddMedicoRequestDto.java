package org.example.Domain.dtos.medico;

public class AddMedicoRequestDto {
    private String id;
    private Long usuarioId;
    private String nombre;
    private String especialidad;

    public AddMedicoRequestDto() {}

    public String getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
}
