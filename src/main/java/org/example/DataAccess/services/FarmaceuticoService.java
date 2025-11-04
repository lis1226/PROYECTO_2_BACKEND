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

    // -------------------------
    // CREATE
    // -------------------------
    public Farmaceutico createFarmaceutico(String id, Long usuarioId, String nombre) {
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
                // Crear usuario temporal con ROL FARMACEUTICO
                usuario = new Usuario();
                usuario.setUsername("temp_farma_" + id);
                usuario.setEmail("temp" + id + "@farmacia.local");
                usuario.setPasswordHash(generateTemporaryPasswordHash());
                usuario.setSalt("temp_salt");
                // CRÍTICO: Asignar el rol ANTES de persistir
                usuario.setRol("FARMACEUTICO");
            }

            Farmaceutico farmaceutico = new Farmaceutico(id, usuario, nombre);
            session.persist(farmaceutico);
            tx.commit();

            // Inicializar el usuario antes de cerrar la sesión
            Hibernate.initialize(farmaceutico.getUsuario());

            return farmaceutico;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("[FarmaceuticoService] Error during rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("[FarmaceuticoService] Error in createFarmaceutico: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error creating farmaceutico: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception closeEx) {
                    System.err.println("[FarmaceuticoService] Error closing session: " + closeEx.getMessage());
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
    public Farmaceutico getFarmaceuticoById(String id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Farmaceutico farmaceutico = session.find(Farmaceutico.class, id);
            if (farmaceutico != null) {
                Hibernate.initialize(farmaceutico.getUsuario());
            }
            return farmaceutico;
        } catch (Exception e) {
            System.err.println("[FarmaceuticoService] Error in getFarmaceuticoById: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error getting farmaceutico: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Farmaceutico> getAllFarmaceuticos() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<Farmaceutico> farmaceuticos = session.createQuery("from Farmaceutico", Farmaceutico.class).list();
            farmaceuticos.forEach(f -> Hibernate.initialize(f.getUsuario()));
            return farmaceuticos;
        } catch (Exception e) {
            System.err.println("[FarmaceuticoService] Error in getAllFarmaceuticos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error getting all farmaceuticos: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public Farmaceutico updateFarmaceutico(String id, String nombre) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Farmaceutico farmaceutico = session.find(Farmaceutico.class, id);
            if (farmaceutico == null) {
                return null;
            }

            if (nombre != null && !nombre.isBlank()) {
                farmaceutico.setNombre(nombre);
            }

            Usuario usuario = farmaceutico.getUsuario();
            if (usuario != null) {
                usuario.setUpdatedAt(java.time.LocalDateTime.now());
                // Asegurarse de que el rol permanece correcto
                if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                    usuario.setRol("FARMACEUTICO");
                }
            }

            session.merge(farmaceutico);
            tx.commit();

            Hibernate.initialize(farmaceutico.getUsuario());
            return farmaceutico;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("[FarmaceuticoService] Error during rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("[FarmaceuticoService] Error in updateFarmaceutico: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating farmaceutico: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    public boolean deleteFarmaceutico(String id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Farmaceutico farmaceutico = session.find(Farmaceutico.class, id);
            if (farmaceutico == null) {
                return false;
            }

            session.remove(farmaceutico);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("[FarmaceuticoService] Error during rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("[FarmaceuticoService] Error in deleteFarmaceutico: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error deleting farmaceutico: " + e.getMessage(), e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}