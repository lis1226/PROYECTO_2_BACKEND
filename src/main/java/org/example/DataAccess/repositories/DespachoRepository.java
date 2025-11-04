package org.example.DataAccess.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.Domain.models.Despacho;
import java.util.List;

public class DespachoRepository {
    private final EntityManager em;

    public DespachoRepository(EntityManager em) { this.em = em; }

    public void save(Despacho d) {
        em.getTransaction().begin();
        if (em.find(Despacho.class, d.getId()) == null)
            em.persist(d);
        else
            em.merge(d);
        em.getTransaction().commit();
    }

    public List<Despacho> findAll() {
        TypedQuery<Despacho> q = em.createQuery("FROM Despacho", Despacho.class);
        return q.getResultList();
    }
}
