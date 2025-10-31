package org.example.DataAccess.services;

import org.example.Domain.models.Farmaceutico;
import org.example.Domain.models.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class FarmaceuticoService {
    private final SessionFactory sessionFactory;

    public FarmaceuticoService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // CREATE
    public Farmaceutico createFarmaceutico(String id, Long usuarioId, String nombre) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Usuario usuario;
            if (usuarioId != null) {
                usuario = session.find(Usuario.class, usuarioId);
                if (usuario == null)
                    throw new IllegalArgumentException("Usuario con ID " + usuarioId + " no encontrado");
            } else {
                usuario = new Usuario();
                usuario.setUsername("temp_farma_" + id);
                usuario.setEmail("temp" + id + "@farmacia.local");
                usuario.setPasswordHash("temp_hash");
                usuario.setSalt("temp_salt");
                usuario.setRol("FARMACEUTICO");
            }

            Farmaceutico f = new Farmaceutico(id, usuario, nombre);
            session.persist(f);

            tx.commit();
            return f;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[FarmaceuticoService] Error: " + e.getMessage());
            throw e;
        }
    }

    // READ
    public Farmaceutico getFarmaceuticoById(String id) {
        try (Session session = sessionFactory.openSession()) {
            Farmaceutico f = session.find(Farmaceutico.class, id);
            if (f != null) Hibernate.initialize(f.getUsuario());
            return f;
        }
    }

    public List<Farmaceutico> getAllFarmaceuticos() {
        try (Session session = sessionFactory.openSession()) {
            List<Farmaceutico> list = session.createQuery("from Farmaceutico", Farmaceutico.class).list();
            list.forEach(f -> Hibernate.initialize(f.getUsuario()));
            return list;
        }
    }

    // UPDATE
    public Farmaceutico updateFarmaceutico(String id, String nombre) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Farmaceutico f = session.find(Farmaceutico.class, id);
            if (f == null) {
                tx.rollback();
                return null;
            }

            if (nombre != null && !nombre.isBlank())
                f.setNombre(nombre);

            session.merge(f);
            tx.commit();
            return f;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    // DELETE
    public boolean deleteFarmaceutico(String id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Farmaceutico f = session.find(Farmaceutico.class, id);
            if (f == null) {
                tx.rollback();
                return false;
            }
            session.remove(f);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}