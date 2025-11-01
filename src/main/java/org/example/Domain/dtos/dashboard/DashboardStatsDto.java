package org.example.Domain.dtos.dashboard;

public class DashboardStatsDto {
    private long totalMedicos;
    private long totalFarmaceuticos;
    private long totalPacientes;
    private long totalMedicamentos;
    private long totalRecetas;

    public DashboardStatsDto() {}

    public DashboardStatsDto(long totalMedicos, long totalFarmaceuticos, long totalPacientes,
                             long totalMedicamentos, long totalRecetas) {
        this.totalMedicos = totalMedicos;
        this.totalFarmaceuticos = totalFarmaceuticos;
        this.totalPacientes = totalPacientes;
        this.totalMedicamentos = totalMedicamentos;
        this.totalRecetas = totalRecetas;
    }

    public long getTotalMedicos() { return totalMedicos; }
    public void setTotalMedicos(long totalMedicos) { this.totalMedicos = totalMedicos; }

    public long getTotalFarmaceuticos() { return totalFarmaceuticos; }
    public void setTotalFarmaceuticos(long totalFarmaceuticos) { this.totalFarmaceuticos = totalFarmaceuticos; }

    public long getTotalPacientes() { return totalPacientes; }
    public void setTotalPacientes(long totalPacientes) { this.totalPacientes = totalPacientes; }

    public long getTotalMedicamentos() { return totalMedicamentos; }
    public void setTotalMedicamentos(long totalMedicamentos) { this.totalMedicamentos = totalMedicamentos; }

    public long getTotalRecetas() { return totalRecetas; }
    public void setTotalRecetas(long totalRecetas) { this.totalRecetas = totalRecetas; }
}
