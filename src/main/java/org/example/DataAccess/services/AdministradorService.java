package org.example.DataAccess.services;

import org.example.DataAccess.repositories.AdministradorRepository;
import org.example.Domain.models.Administrador;

import java.util.List;
import java.util.Optional;

public class AdministradorService {

    private final AdministradorRepository repo;

    public AdministradorService(AdministradorRepository repo) {
        this.repo = repo;
    }

    public Administrador createAdministrador(String username, String email, String password) {
        Administrador admin = new Administrador();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPasswordHash(password != null ? password : "");
        admin.setRol("ADMIN");
        repo.save(admin);
        return admin;
    }

    public Administrador updateAdministrador(Long id, String username, String email) {
        Optional<Administrador> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        Administrador admin = opt.get();
        if (username != null && !username.isEmpty()) admin.setUsername(username);
        if (email != null && !email.isEmpty()) admin.setEmail(email);
        repo.update(admin);
        return admin;
    }

    public boolean deleteAdministrador(Long id) {
        Optional<Administrador> opt = repo.findById(id);
        if (opt.isEmpty()) return false;
        repo.delete(opt.get());
        return true;
    }

    public List<Administrador> getAllAdministradores() {
        return repo.findAll();
    }

    public Administrador getAdministradorById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
