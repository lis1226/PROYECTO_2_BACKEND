package org.example.DataAccess.services;

import org.example.Domain.models.Medicamento;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MedicamentoService {
    private final SessionFactory sessionFactory;

    public MedicamentoService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // CREATE
    public Medicamento createMedicamento(String codigo, String descripcion, String presentacion) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Medicamento m = new Medicamento(codigo, descripcion, presentacion);
            session.persist(m);
            tx.commit();
            return m;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[MedicamentoService] Error create: " + e.getMessage());
            throw e;
        }
    }

    // READ single
    public Medicamento getMedicamentoByCodigo(String codigo) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Medicamento.class, codigo);
        } catch (Exception e) {
            System.err.println("[MedicamentoService] Error get: " + e.getMessage());
            throw e;
        }
    }

    // READ all
    public List<Medicamento> getAllMedicamentos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Medicamento", Medicamento.class).list();
        } catch (Exception e) {
            System.err.println("[MedicamentoService] Error list: " + e.getMessage());
            throw e;
        }
    }

    // UPDATE
    public Medicamento updateMedicamento(String codigo, String descripcion, String presentacion) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Medicamento m = session.find(Medicamento.class, codigo);
            if (m == null) {
                tx.rollback();
                return null;
            }
            if (descripcion != null) m.setDescripcion(descripcion);
            if (presentacion != null) m.setPresentacion(presentacion);
            session.merge(m);
            tx.commit();
            return m;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[MedicamentoService] Error update: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public boolean deleteMedicamento(String codigo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Medicamento m = session.find(Medicamento.class, codigo);
            if (m == null) {
                tx.rollback();
                return false;
            }
            session.remove(m);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[MedicamentoService] Error delete: " + e.getMessage());
            throw e;
        }
    }
}
