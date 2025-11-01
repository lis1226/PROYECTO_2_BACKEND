package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.PacienteService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.paciente.*;
import org.example.Domain.models.Paciente;

import java.util.List;
import java.util.stream.Collectors;

public class PacienteController {
    private final PacienteService pacienteService;
    private final Gson gson = new Gson();

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public ResponseDto route(RequestDto requestDto) {
        try {
            switch (requestDto.getRequest()) {
                case "add":
                    return handleAddPaciente(requestDto);
                case "update":
                    return handleUpdatePaciente(requestDto);
                case "delete":
                    return handleDeletePaciente(requestDto);
                case "list":
                    return handleListPaciente(requestDto);
                case "get":
                    return handleGetPaciente(requestDto);
                default:
                    return new ResponseDto(false, "Unknown request: " + requestDto.getRequest(), null);
            }
        }catch (Exception e){
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    // -------------------------
    // ADD
    // -------------------------

    private ResponseDto handleAddPaciente(RequestDto requestDto) {
        try {
            AddPacienteRequestDto dto = gson.fromJson(requestDto.getData(), AddPacienteRequestDto.class);
            Paciente paciente = pacienteService.createPaciente(dto.getId(), dto.getUsuarioId(), dto.getNombre(), dto.getFechaNacimiento());

            return new ResponseDto(true, "Paciente added successfully", gson.toJson(toResponseDto(paciente)));
        } catch (Exception e) {
            System.err.println("[PacienteController] Error in handleAddPaciente: " + e.getMessage());
            throw e;
        }
    }

    // -------------------------
    // UPDATE
    // -------------------------
    private ResponseDto handleUpdatePaciente(RequestDto requestDto) {
        try {
            UpdatePacienteRequestDto dto = gson.fromJson(requestDto.getData(), UpdatePacienteRequestDto.class);
            Paciente updated = pacienteService.updatePaciente(dto.getId(), dto.getNombre(), dto.getFechaNacimiento());

            if (updated == null)
                return new ResponseDto(false, "Paciente not found", null);

            return new ResponseDto(true, "Paciente updated successfully", gson.toJson(toResponseDto(updated)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    private ResponseDto handleDeletePaciente(RequestDto requestDto) {
        DeletePacienteRequestDto dto = gson.fromJson(requestDto.getData(), DeletePacienteRequestDto.class);
        boolean deleted = pacienteService.deletePaciente(dto.getId());
        if (!deleted) return new ResponseDto(false, "Paciente not found", null);
        return new ResponseDto(true, "Paciente deleted successfully", null);
    }

    // -------------------------
    // LIST
    // -------------------------
    private ResponseDto handleListPaciente(RequestDto requestDto) {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        List<PacienteResponseDto> pacDtos = pacientes.stream().map(this::toResponseDto).collect(Collectors.toList());
        return new ResponseDto(true, "Pacientes found", gson.toJson(new ListPacientesResponseDto(pacDtos)));
    }

    // -------------------------
    // GET
    // -------------------------
    private ResponseDto handleGetPaciente(RequestDto requestDto) {
        DeletePacienteRequestDto dto = gson.fromJson(requestDto.getData(), DeletePacienteRequestDto.class);
        Paciente paciente = pacienteService.getPacienteById(dto.getId());
        if (paciente == null)
            return new ResponseDto(false, "Paciente not found", null);
        return new ResponseDto(true, "Paciente found", gson.toJson(toResponseDto(paciente)));
    }




    // -------------------------
    // HELPER
    // -------------------------
    private PacienteResponseDto toResponseDto(Paciente paciente) {
        return new PacienteResponseDto(
                paciente.getId(),
                paciente.getUsuario().getId(),
                paciente.getNombre(),
                paciente.getFechaNacimiento()
        );
    }









}
