package org.example.Domain.models;

import jakarta.persistence.*;
import org.example.Domain.models.Usuario;

@Entity
@DiscriminatorValue("FARMACEUTICO")
public class Farmaceutico {

    @Id
    @Column(length = 20)
    private String id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    public Farmaceutico() {
    }

    public Farmaceutico(String id, Usuario usuario, String nombre) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}