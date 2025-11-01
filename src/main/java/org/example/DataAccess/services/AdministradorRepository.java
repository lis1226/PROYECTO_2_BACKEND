package org.example.DataAccess.repositories;

import org.example.Domain.models.Administrador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

public class AdministradorRepository {
    private final SessionFactory sessionFactory;

    public AdministradorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Administrador admin) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(admin);
            session.getTransaction().commit();
        }
    }

    public void update(Administrador admin) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(admin);
            session.getTransaction().commit();
        }
    }

    public void delete(Administrador admin) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(admin);
            session.getTransaction().commit();
        }
    }

    public Optional<Administrador> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Administrador.class, id));
        }
    }

    public List<Administrador> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Administrador", Administrador.class).list();
        }
    }
}
