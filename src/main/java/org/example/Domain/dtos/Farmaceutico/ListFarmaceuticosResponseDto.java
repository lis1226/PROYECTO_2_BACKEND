package org.example.Domain.dtos.Farmaceutico;

import java.util.List;

public class ListFarmaceuticosResponseDto {
    private List<FarmaceuticoResponseDto> farmaceuticos;

    public ListFarmaceuticosResponseDto() {}

    public ListFarmaceuticosResponseDto(List<FarmaceuticoResponseDto> farmaceuticos) {
        this.farmaceuticos = farmaceuticos;
    }

    public List<FarmaceuticoResponseDto> getFarmaceuticos() { return farmaceuticos; }
    public void setFarmaceuticos(List<FarmaceuticoResponseDto> farmaceuticos) { this.farmaceuticos = farmaceuticos; }
}