package org.example.Domain.dtos.dashboard;

public class DatoRowDto {
    private String fecha;
    private int valor;
    private String medicamentoCodigo;

    public DatoRowDto() {}
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public int getValor() { return valor; }
    public void setValor(int valor) { this.valor = valor; }
    public String getMedicamentoCodigo() { return medicamentoCodigo; }
    public void setMedicamentoCodigo(String medicamentoCodigo) { this.medicamentoCodigo = medicamentoCodigo; }
}
