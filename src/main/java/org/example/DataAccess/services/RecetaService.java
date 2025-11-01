package org.example.DataAccess.services;

import org.example.Domain.dtos.receta.DetalleRecetaDto;
import org.example.Domain.models.DetalleReceta;
import org.example.Domain.models.Receta;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.*;
import java.util.stream.Collectors;

public class RecetaService {
    private final SessionFactory sessionFactory;

    public RecetaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Receta createReceta(String id, String idPaciente, String idMedico, String estado, List<DetalleRecetaDto> detallesDto) {
        Receta receta = new Receta();
        receta.setId(id != null ? id : UUID.randomUUID().toString());
        receta.setIdPaciente(idPaciente);
        receta.setIdMedico(idMedico);
        receta.setEstado(estado != null ? estado : "confeccionada");
        receta.setFechaConfeccion(new Date());
        receta.setFechaRetiro(new Date(System.currentTimeMillis() + 86400000));

        receta.setDetalles(detallesDto.stream().map(d ->
                new DetalleReceta(d.getIdMedicamento(), d.getCantidad(), d.getIndicaciones(), d.getDuracionDias())
        ).collect(Collectors.toList()));

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(receta);
            tx.commit();
        }

        return receta;
    }

    public Receta updateReceta(String id, String nuevoEstado) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Receta receta = session.get(Receta.class, id);
            if (receta != null) {
                receta.setEstado(nuevoEstado);
                session.merge(receta);
                tx.commit();
                return receta;
            }
            tx.rollback();
            return null;
        }
    }

    public boolean deleteReceta(String id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Receta receta = session.get(Receta.class, id);
            if (receta != null) {
                session.remove(receta);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        }
    }

    public List<Receta> getAllRecetas() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Receta", Receta.class).list();
        }
    }

    public Receta getRecetaById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Receta.class, id);
        }
    }
}
