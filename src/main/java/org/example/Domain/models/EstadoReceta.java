package org.example.Domain.models;

public enum EstadoReceta {
    CONFECCIONADA("confeccionada"),
    PROCESO("proceso"),
    LISTA("lista"),
    ENTREGADA("entregada");

    private final String valor;
    EstadoReceta(String v) { this.valor = v; }
    public String getValor() { return valor; }

    public static EstadoReceta fromValor(String valor) {
        for (EstadoReceta e : values()) {
            if (e.valor.equalsIgnoreCase(valor)) return e;
        }
        throw new IllegalArgumentException("Estado no válido: " + valor);
    }
}
