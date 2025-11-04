package org.example.Domain.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario {

    public Administrador() {
        super();
        this.setRol("ADMIN");
    }

    public Administrador(Long id, String username, String email) {
        super();
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
