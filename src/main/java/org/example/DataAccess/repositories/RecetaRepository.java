package org.example.DataAccess.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.Domain.models.Receta;
import java.util.List;

public class RecetaRepository {
    private final EntityManager em;

    public RecetaRepository(EntityManager em) { this.em = em; }

    public void save(Receta r) {
        em.getTransaction().begin();
        if (em.find(Receta.class, r.getId()) == null)
            em.persist(r);
        else
            em.merge(r);
        em.getTransaction().commit();
    }

    public Receta findById(String id) { return em.find(Receta.class, id); }

    public List<Receta> findAll() {
        TypedQuery<Receta> q = em.createQuery("FROM Receta", Receta.class);
        return q.getResultList();
    }
}
