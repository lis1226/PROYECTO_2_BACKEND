package org.example.DataAccess.services;

import org.example.Domain.models.Paciente;
import org.example.Domain.models.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class PacienteService {
    private final SessionFactory sessionFactory;
    public PacienteService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // -------------------------
    // CREATE
    // -------------------------

    public Paciente createPaciente(String pacienteId, Long usuarioId, String nombre, Date fechaNacimiento) {
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
                usuario.setUsername("temp_paciente_" + pacienteId);
                usuario.setEmail("temp" + pacienteId + "@hospital.local");
                usuario.setPasswordHash(generateTemporaryPasswordHash());
                usuario.setSalt("temp_salt");
                usuario.setRol("PACIENTE");
            }

            Paciente paciente = new Paciente(pacienteId, usuario, nombre, fechaNacimiento);
            session.persist(paciente); // Ya no necesitas session.persist(usuario)
            tx.commit();
            return paciente;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[PacienteService] Error: " + e.getMessage());
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
    public Paciente getPacienteById(String pacienteId) {
        try (Session session = sessionFactory.openSession()) {
            Paciente paciente = session.find(Paciente.class, pacienteId);
            if (paciente != null) Hibernate.initialize(paciente.getUsuario());
            return paciente;
        } catch (Exception e) {
            System.err.println("[PacienteService] Error in getPacienteById: " + e.getMessage());
            throw e;
        }
    }

    public List<Paciente> getAllPacientes() {
        try (Session session = sessionFactory.openSession()) {
            List<Paciente> pacientes = session.createQuery("from Paciente", Paciente.class).list();
            pacientes.forEach(m -> Hibernate.initialize(m.getUsuario()));
            return pacientes;
        } catch (Exception e) {
            System.err.println("[PacienteService] Error in getAllPacientes: " + e.getMessage());
            throw e;
        }
    }

    // -------------------------
    // UPDATE
    // -------------------------

    public Paciente updatePaciente(String medicoId, String nuevoNombre, Date fechaNacimiento) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Paciente paciente = session.find(Paciente.class, medicoId);
            if (paciente == null) {
                tx.rollback();
                return null;
            }

            // Actualiza los campos del médico
            if (nuevoNombre != null && !nuevoNombre.isBlank()) {
                paciente.setNombre(nuevoNombre);
            }
            if (fechaNacimiento != null) {
                paciente.setFechaNacimiento(fechaNacimiento);
            }

            // Ejemplo: si quisieras actualizar datos del usuario también
            Usuario usuario = paciente.getUsuario();
            if (usuario != null) {
                usuario.setUpdatedAt(java.time.LocalDateTime.now());
                // Si quisieras cambiar username o email podrías hacerlo aquí
                // usuario.setUsername("nuevo_nombre");
            }

            session.merge(paciente);
            tx.commit();
            return paciente;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[PacienteService] Error in updatePaciente: " + e.getMessage());
            throw e;
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    public boolean deletePaciente(String pacienteId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Paciente paciente = session.find(Paciente.class, pacienteId);
            if (paciente == null) {
                tx.rollback();
                return false;
            }

            session.remove(paciente);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("[PacienteService] Error in deletePaciente: " + e.getMessage());
            throw e;
        }
    }




}
