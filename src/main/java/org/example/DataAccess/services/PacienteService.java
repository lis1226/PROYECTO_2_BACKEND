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
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Usuario usuario;
            if (usuarioId != null) {
                usuario = session.find(Usuario.class, usuarioId);
                if (usuario == null)
                    throw new IllegalArgumentException("Usuario con ID " + usuarioId + " no encontrado");
            } else {
                // Crear usuario temporal con ROL PACIENTE
                usuario = new Usuario();
                usuario.setUsername("temp_paciente_" + pacienteId);
                usuario.setEmail("temp" + pacienteId + "@hospital.local");
                usuario.setPasswordHash(generateTemporaryPasswordHash());
                usuario.setSalt("temp_salt");
                // CRÍTICO: Asignar el rol ANTES de persistir
                usuario.setRol("PACIENTE");
            }

            Paciente paciente = new Paciente(pacienteId, usuario, nombre, fechaNacimiento);
            session.persist(paciente);
            tx.commit();

            // Inicializar el usuario antes de cerrar la sesión
            Hibernate.initialize(paciente.getUsuario());

            return paciente;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("[PacienteService] Error during rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("[PacienteService] Error in createPaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error creating paciente: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception closeEx) {
                    System.err.println("[PacienteService] Error closing session: " + closeEx.getMessage());
                }
            }
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
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Paciente paciente = session.find(Paciente.class, pacienteId);
            if (paciente != null) {
                Hibernate.initialize(paciente.getUsuario());
            }
            return paciente;
        } catch (Exception e) {
            System.err.println("[PacienteService] Error in getPacienteById: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error getting paciente: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Paciente> getAllPacientes() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<Paciente> pacientes = session.createQuery("from Paciente", Paciente.class).list();
            pacientes.forEach(p -> Hibernate.initialize(p.getUsuario()));
            return pacientes;
        } catch (Exception e) {
            System.err.println("[PacienteService] Error in getAllPacientes: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error getting all pacientes: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Paciente updatePaciente(String pacienteId, String nuevoNombre, Date fechaNacimiento) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Paciente paciente = session.find(Paciente.class, pacienteId);
            if (paciente == null) {
                return null;
            }

            if (nuevoNombre != null && !nuevoNombre.isBlank()) {
                paciente.setNombre(nuevoNombre);
            }
            if (fechaNacimiento != null) {
                paciente.setFechaNacimiento(fechaNacimiento);
            }

            Usuario usuario = paciente.getUsuario();
            if (usuario != null) {
                usuario.setUpdatedAt(java.time.LocalDateTime.now());
                // Asegurarse de que el rol permanece correcto
                if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                    usuario.setRol("PACIENTE");
                }
            }

            session.merge(paciente);
            tx.commit();

            Hibernate.initialize(paciente.getUsuario());
            return paciente;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("[PacienteService] Error during rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("[PacienteService] Error in updatePaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating paciente: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    public boolean deletePaciente(String pacienteId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Paciente paciente = session.find(Paciente.class, pacienteId);
            if (paciente == null) {
                return false;
            }

            session.remove(paciente);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("[PacienteService] Error during rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("[PacienteService] Error in deletePaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error deleting paciente: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}