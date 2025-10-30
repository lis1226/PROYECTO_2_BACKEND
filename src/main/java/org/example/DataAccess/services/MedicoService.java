package org.example.DataAccess.services;

import org.example.Domain.models.Medico;
import org.example.Domain.models.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MedicoService {
    private final SessionFactory sessionFactory;

    public MedicoService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // -------------------------
    // CREATE
    // -------------------------
    public Medico createMedico(String medicoId, Long usuarioId, String nombre, String especialidad) {
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
                usuario.setUsername("temp_medico_" + medicoId);
                usuario.setEmail("temp" + medicoId + "@hospital.local");
                usuario.setPasswordHash(generateTemporaryPasswordHash());
                usuario.setSalt("temp_salt");
                usuario.setRol("MEDICO");
            }

            Medico medico = new Medico(medicoId, usuario, nombre, especialidad);
            session.persist(medico); // Ya no necesitas session.persist(usuario)
            tx.commit();
            return medico;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[MedicoService] Error: " + e.getMessage());
            throw e;
        }
    }

    private String generateTemporaryPasswordHash() {
        String tempPassword = "Temp" + (int) (Math.random() * 10000);
        return tempPassword; // Temporal (sin hashing real)
    }

    // -------------------------
    // READ
    // -------------------------
    public Medico getMedicoById(String medicoId) {
        try (Session session = sessionFactory.openSession()) {
            Medico medico = session.find(Medico.class, medicoId);
            if (medico != null) Hibernate.initialize(medico.getUsuario());
            return medico;
        } catch (Exception e) {
            System.err.println("[MedicoService] Error in getMedicoById: " + e.getMessage());
            throw e;
        }
    }

    public List<Medico> getAllMedicos() {
        try (Session session = sessionFactory.openSession()) {
            List<Medico> medicos = session.createQuery("from Medico", Medico.class).list();
            medicos.forEach(m -> Hibernate.initialize(m.getUsuario()));
            return medicos;
        } catch (Exception e) {
            System.err.println("[MedicoService] Error in getAllMedicos: " + e.getMessage());
            throw e;
        }
    }

    // -------------------------
    // UPDATE
    // -------------------------

    public Medico updateMedico(String medicoId, String nuevoNombre, String nuevaEspecialidad) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Medico medico = session.find(Medico.class, medicoId);
            if (medico == null) {
                tx.rollback();
                return null;
            }

            // Actualiza los campos del médico
            if (nuevoNombre != null && !nuevoNombre.isBlank()) {
                medico.setNombre(nuevoNombre);
            }
            if (nuevaEspecialidad != null && !nuevaEspecialidad.isBlank()) {
                medico.setEspecialidad(nuevaEspecialidad);
            }

            // Ejemplo: si quisieras actualizar datos del usuario también
            Usuario usuario = medico.getUsuario();
            if (usuario != null) {
                usuario.setUpdatedAt(java.time.LocalDateTime.now());
                // Si quisieras cambiar username o email podrías hacerlo aquí
                // usuario.setUsername("nuevo_nombre");
            }

            session.merge(medico);
            tx.commit();
            return medico;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[MedicoService] Error in updateMedico: " + e.getMessage());
            throw e;
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    public boolean deleteMedico(String medicoId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Medico medico = session.find(Medico.class, medicoId);
            if (medico == null) {
                tx.rollback();
                return false;
            }

            session.remove(medico);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[MedicoService] Error in deleteMedico: " + e.getMessage());
            throw e;
        }
    }
}
