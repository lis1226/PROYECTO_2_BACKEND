package org.example.DataAccess.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Servicio de acceso a datos para estadísticas y datos del Dashboard.
 * Compatible con el DashboardController actual.
 */
public class DashboardService {
    private final SessionFactory sessionFactory;

    public DashboardService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // ---------------------------
    // MÉTODOS DE ESTADÍSTICAS
    // ---------------------------

    public long getTotalMedicos() {
        return count("Medico");
    }

    public long getTotalFarmaceuticos() {
        return count("Farmaceutico");
    }

    public long getTotalPacientes() {
        return count("Paciente");
    }

    public long getTotalMedicamentos() {
        return count("Medicamento");
    }

    public long getTotalRecetas() {
        return count("Receta");
    }

    private long count(String entityName) {
        try (Session session = sessionFactory.openSession()) {
            Long total = session.createQuery("select count(e) from " + entityName + " e", Long.class)
                    .getSingleResult();
            return total != null ? total : 0;
        } catch (Exception e) {
            System.err.println("[DashboardService] Error counting " + entityName + ": " + e.getMessage());
            return 0;
        }
    }

    // ---------------------------
    // LISTA DE MEDICAMENTOS
    // ---------------------------
    /**
     * Devuelve los medicamentos en forma de filas sin mapear a objetos.
     * Se usa en DashboardController con MedicamentoResponseDto.
     */
    public List<Object[]> listMedicamentosRaw() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "select m.codigo, m.descripcion, m.presentacion from Medicamento m",
                    Object[].class
            ).list();
        } catch (Exception e) {
            System.err.println("[DashboardService] Error loading medicamentos: " + e.getMessage());
            throw e;
        }
    }
}
