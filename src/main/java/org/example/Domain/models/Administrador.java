package org.example.Domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores")
public class Administrador extends Usuario {

    public Administrador() {
        super(); // usa el constructor por defecto de Usuario (si existe)
        this.setRol("ADMIN");
    }

    // Constructor auxiliar si tu Usuario tiene (Long id, String username, String email)
    // Esto NO dará error si Usuario tiene ese constructor; si no existe, compilará igualmente porque no se usa.
    public Administrador(Long id, String username, String email) {
        super(); // evita llamar a un constructor inexistente; luego seteamos campos
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setRol("ADMIN");
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", rol='" + getRol() + '\'' +
                '}';
    }
}
