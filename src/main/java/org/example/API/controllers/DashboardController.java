package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.HibernateUtil;
import org.example.Domain.dtos.dashboard.DashboardStatsDto;
import org.example.Domain.dtos.dashboard.MedicamentoResponseDto;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    private final SessionFactory sessionFactory;
    private final Gson gson = new Gson();

    public DashboardController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ResponseDto route(RequestDto request) {
        try {
            switch (request.getRequest()) {
                case "getStats":
                    return getStats();
                case "listMedicamentos":
                    return listMedicamentos();
                default:
                    return new ResponseDto(false, "Unknown dashboard request: " + request.getRequest(), null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseDto(false, "Server error: " + ex.getMessage(), null);
        }
    }

    private ResponseDto getStats() {
        try (Session session = sessionFactory.openSession()) {
            // Contar entidades usando HQL; usamos 0 si no existen tablas
            Long totalMedicos = ((Long) session.createQuery("select count(m) from Medico m").uniqueResult());
            Long totalFarmaceuticos = ((Long) session.createQuery("select count(f) from Farmaceutico f").uniqueResult());
            Long totalPacientes = ((Long) session.createQuery("select count(p) from Paciente p").uniqueResult());
            Long totalMedicamentos = ((Long) session.createQuery("select count(md) from Medicamento md").uniqueResult());
            Long totalRecetas = ((Long) session.createQuery("select count(r) from Receta r").uniqueResult());

            DashboardStatsDto dto = new DashboardStatsDto(
                    totalMedicos != null ? totalMedicos : 0L,
                    totalFarmaceuticos != null ? totalFarmaceuticos : 0L,
                    totalPacientes != null ? totalPacientes : 0L,
                    totalMedicamentos != null ? totalMedicamentos : 0L,
                    totalRecetas != null ? totalRecetas : 0L
            );

            String data = gson.toJson(dto);
            return new ResponseDto(true, "OK", data);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto(false, "Error building stats: " + e.getMessage(), null);
        }
    }

    private ResponseDto listMedicamentos() {
        try (Session session = sessionFactory.openSession()) {
            @SuppressWarnings("unchecked")
            List<org.example.Domain.models.Medicamento> meds = session.createQuery("from Medicamento").list();

            List<MedicamentoResponseDto> dtos = meds.stream()
                    .map(m -> new MedicamentoResponseDto(m.getCodigo(), m.getDescripcion(), m.getPresentacion()))
                    .collect(Collectors.toList());

            String data = gson.toJson(dtos);
            return new ResponseDto(true, "OK", data);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto(false, "Error listing medicamentos: " + e.getMessage(), null);
        }
    }

}
