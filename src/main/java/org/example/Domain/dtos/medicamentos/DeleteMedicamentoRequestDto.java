package org.example.Domain.dtos.medicamentos;

public class DeleteMedicamentoRequestDto {
    private String codigo;

    public DeleteMedicamentoRequestDto() {}
    public DeleteMedicamentoRequestDto(String codigo) { this.codigo = codigo; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
