package org.example.Domain.dtos.medico;

public class MedicoResponseDto {
    private String id;
    private Long usuarioId;
    private String nombre;
    private String especialidad;

    public MedicoResponseDto(String id, Long usuarioId, String nombre, String especialidad) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public String getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
}
