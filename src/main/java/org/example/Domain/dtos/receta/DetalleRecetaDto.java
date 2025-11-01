package org.example.Domain.dtos.receta;

public class DetalleRecetaDto {
    private String idMedicamento;
    private int cantidad;
    private String indicaciones;
    private int duracionDias;

    public DetalleRecetaDto() {}

    public DetalleRecetaDto(String idMedicamento, int cantidad, String indicaciones, int duracionDias) {
        this.idMedicamento = idMedicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracionDias = duracionDias;
    }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public int getDuracionDias() { return duracionDias; }
    public void setDuracionDias(int duracionDias) { this.duracionDias = duracionDias; }
}
