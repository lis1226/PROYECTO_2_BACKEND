package org.example.Domain.Farmaceutico;

public class FarmaceuticoResponseDto {
    private String id;
    private Long usuarioId;
    private String nombre;

    public FarmaceuticoResponseDto(String id, Long usuarioId, String nombre) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getNombre() { return nombre; }

    public void setId(String id) { this.id = id; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
