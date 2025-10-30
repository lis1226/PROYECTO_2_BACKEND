package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.MedicoService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.medico.*;
import org.example.Domain.models.Medico;

import java.util.List;
import java.util.stream.Collectors;

public class MedicoController {
    private final MedicoService medicoService;
    private final Gson gson = new Gson();

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    public ResponseDto route(RequestDto requestDto) {
        try {
            switch (requestDto.getRequest()) {
                case "add":
                    return handleAddMedico(requestDto);
                case "update":
                    return handleUpdateMedico(requestDto);
                case "delete":
                    return handleDeleteMedico(requestDto);
                case "list":
                    return handleListMedico(requestDto);
                case "get":
                    return handleGetMedico(requestDto);
                default:
                    return new ResponseDto(false, "Unknown request: " + requestDto.getRequest(), null);
            }
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    // -------------------------
    // ADD
    // -------------------------
    private ResponseDto handleAddMedico(RequestDto requestDto) {
        try {
//            if (requestDto.getToken() == null || requestDto.getToken().isEmpty())
//                return new ResponseDto(false, "Unauthorized", null);

            AddMedicoRequestDto dto = gson.fromJson(requestDto.getData(), AddMedicoRequestDto.class);
            Medico medico = medicoService.createMedico(dto.getId(), dto.getUsuarioId(), dto.getNombre(), dto.getEspecialidad());

            return new ResponseDto(true, "Medico added successfully", gson.toJson(toResponseDto(medico)));
        } catch (Exception e) {
            System.err.println("[MedicoController] Error in handleAddMedico: " + e.getMessage());
            throw e;
        }
    }

    // -------------------------
    // UPDATE
    // -------------------------
    private ResponseDto handleUpdateMedico(RequestDto requestDto) {
        try {
            UpdateMedicoRequestDto dto = gson.fromJson(requestDto.getData(), UpdateMedicoRequestDto.class);
            Medico updated = medicoService.updateMedico(dto.getId(), dto.getNombre(), dto.getEspecialidad());

            if (updated == null)
                return new ResponseDto(false, "Medico not found", null);

            return new ResponseDto(true, "Medico updated successfully", gson.toJson(toResponseDto(updated)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    private ResponseDto handleDeleteMedico(RequestDto requestDto) {
        DeleteMedicoRequestDto dto = gson.fromJson(requestDto.getData(), DeleteMedicoRequestDto.class);
        boolean deleted = medicoService.deleteMedico(dto.getId());
        if (!deleted) return new ResponseDto(false, "Medico not found", null);
        return new ResponseDto(true, "Medico deleted successfully", null);
    }

    // -------------------------
    // LIST
    // -------------------------
    private ResponseDto handleListMedico(RequestDto requestDto) {
        List<Medico> medicos = medicoService.getAllMedicos();
        List<MedicoResponseDto> medDtos = medicos.stream().map(this::toResponseDto).collect(Collectors.toList());
        return new ResponseDto(true, "Medicos found", gson.toJson(new ListMedicosResponseDto(medDtos)));
    }

    // -------------------------
    // GET
    // -------------------------
    private ResponseDto handleGetMedico(RequestDto requestDto) {
        DeleteMedicoRequestDto dto = gson.fromJson(requestDto.getData(), DeleteMedicoRequestDto.class);
        Medico medico = medicoService.getMedicoById(dto.getId());
        if (medico == null)
            return new ResponseDto(false, "Medico not found", null);
        return new ResponseDto(true, "Medico found", gson.toJson(toResponseDto(medico)));
    }

    // -------------------------
    // HELPER
    // -------------------------
    private MedicoResponseDto toResponseDto(Medico medico) {
        return new MedicoResponseDto(
                medico.getId(),
                medico.getUsuario().getId(),
                medico.getNombre(),
                medico.getEspecialidad()
        );
    }
}
