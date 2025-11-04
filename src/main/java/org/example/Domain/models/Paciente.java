package org.example.Domain.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@DiscriminatorValue("PACIENTE")
public class Paciente {

    @Id
    @Column(length = 20)
    private String id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private Date fechaNacimiento;


    public Paciente() {
    }

    public Paciente(String id, Usuario usuario, String nombre, Date fechaNacimiento) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
