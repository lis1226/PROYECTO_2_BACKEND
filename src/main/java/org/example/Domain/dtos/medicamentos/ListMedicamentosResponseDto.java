package org.example.Domain.dtos.medicamentos;

import java.util.List;

public class ListMedicamentosResponseDto {
    private List<MedicamentoResponseDto> medicamentos;

    public ListMedicamentosResponseDto() {}
    public ListMedicamentosResponseDto(List<MedicamentoResponseDto> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<MedicamentoResponseDto> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<MedicamentoResponseDto> medicamentos) { this.medicamentos = medicamentos; }
}
