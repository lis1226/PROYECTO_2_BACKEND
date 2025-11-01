package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.DashboardService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.dashboard.DashboardStatsDto;
import org.example.Domain.dtos.dashboard.MedicamentoResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    private final DashboardService service;
    private final Gson gson = new Gson();

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    public ResponseDto route(RequestDto req) {
        try {
            switch (req.getRequest()) {
                case "getStats":
                    DashboardStatsDto stats = new DashboardStatsDto(
                            service.getTotalMedicos(),
                            service.getTotalFarmaceuticos(),
                            service.getTotalPacientes(),
                            service.getTotalMedicamentos(),
                            service.getTotalRecetas()
                    );
                    return new ResponseDto(true, "Stats OK", gson.toJson(stats));

                case "listMedicamentos":
                    List<MedicamentoResponseDto> meds = service.listMedicamentosRaw().stream()
                            .map(row -> new MedicamentoResponseDto(
                                    (String) row[0],
                                    (String) row[1],
                                    (String) row[2]
                            ))
                            .collect(Collectors.toList());
                    return new ResponseDto(true, "Medicamentos OK", gson.toJson(meds));

                default:
                    return new ResponseDto(false, "Unknown dashboard action: " + req.getRequest(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto(false, e.getMessage(), null);
        }
    }
}
