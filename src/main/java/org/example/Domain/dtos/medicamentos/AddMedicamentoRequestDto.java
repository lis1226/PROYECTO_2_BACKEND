package org.example.Domain.dtos.medicamentos;

public class AddMedicamentoRequestDto {
    private String codigo;
    private String descripcion;
    private String presentacion;

    public AddMedicamentoRequestDto() {}

    public AddMedicamentoRequestDto(String codigo, String descripcion, String presentacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }
}
