package org.example.DataAccess.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.Domain.models.Despacho;

import java.util.List;

public class DespachoService {

    private final EntityManager em;

    public DespachoService(EntityManager em) {
        this.em = em;
    }

    public Despacho guardar(Despacho d) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(d);
            tx.commit();
            return d;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Despacho obtenerPorId(String id) {
        return em.find(Despacho.class, id);
    }

    public List<Despacho> obtenerTodos() {
        return em.createQuery("SELECT d FROM Despacho d", Despacho.class).getResultList();
    }

    public Despacho actualizar(Despacho d) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Despacho merged = em.merge(d);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
