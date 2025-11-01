package org.example.API.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.example.DataAccess.services.PacienteService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.paciente.*;
import org.example.Domain.models.Paciente;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteController {
    private final PacienteService pacienteService;
    private final Gson gson;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
        // Configurar Gson con adaptador para Date
        this.gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy")
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, context) -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    return new JsonPrimitive(sdf.format(date));
                })
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, type, context) -> {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        sdf.setLenient(false);
                        return sdf.parse(json.getAsString());
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing date: " + e.getMessage());
                    }
                })
                .create();
    }

    public ResponseDto route(RequestDto requestDto) {
        try {
            System.out.println("[PacienteController] Routing request: " + requestDto.getRequest());
            System.out.println("[PacienteController] Data received: " + requestDto.getData());

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
        } catch (Exception e) {
            System.err.println("[PacienteController] Error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    // -------------------------
    // ADD
    // -------------------------
    private ResponseDto handleAddPaciente(RequestDto requestDto) {
        try {
            System.out.println("[PacienteController] handleAddPaciente - Raw data: " + requestDto.getData());

            AddPacienteRequestDto dto = gson.fromJson(requestDto.getData(), AddPacienteRequestDto.class);

            System.out.println("[PacienteController] Parsed DTO - ID: " + dto.getId() +
                    ", Nombre: " + dto.getNombre() +
                    ", Fecha: " + dto.getFechaNacimiento());

            Paciente paciente = pacienteService.createPaciente(
                    dto.getId(),
                    dto.getUsuarioId(),
                    dto.getNombre(),
                    dto.getFechaNacimiento()
            );

            return new ResponseDto(true, "Paciente added successfully", gson.toJson(toResponseDto(paciente)));
        } catch (Exception e) {
            System.err.println("[PacienteController] Error in handleAddPaciente: " + e.getMessage());
            e.printStackTrace();
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
            System.err.println("[PacienteController] Error in handleUpdatePaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    private ResponseDto handleDeletePaciente(RequestDto requestDto) {
        try {
            DeletePacienteRequestDto dto = gson.fromJson(requestDto.getData(), DeletePacienteRequestDto.class);
            boolean deleted = pacienteService.deletePaciente(dto.getId());
            if (!deleted) return new ResponseDto(false, "Paciente not found", null);
            return new ResponseDto(true, "Paciente deleted successfully", null);
        } catch (Exception e) {
            System.err.println("[PacienteController] Error in handleDeletePaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // -------------------------
    // LIST
    // -------------------------
    private ResponseDto handleListPaciente(RequestDto requestDto) {
        try {
            List<Paciente> pacientes = pacienteService.getAllPacientes();
            List<PacienteResponseDto> pacDtos = pacientes.stream()
                    .map(this::toResponseDto)
                    .collect(Collectors.toList());
            return new ResponseDto(true, "Pacientes found", gson.toJson(new ListPacientesResponseDto(pacDtos)));
        } catch (Exception e) {
            System.err.println("[PacienteController] Error in handleListPaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // -------------------------
    // GET
    // -------------------------
    private ResponseDto handleGetPaciente(RequestDto requestDto) {
        try {
            DeletePacienteRequestDto dto = gson.fromJson(requestDto.getData(), DeletePacienteRequestDto.class);
            Paciente paciente = pacienteService.getPacienteById(dto.getId());
            if (paciente == null)
                return new ResponseDto(false, "Paciente not found", null);
            return new ResponseDto(true, "Paciente found", gson.toJson(toResponseDto(paciente)));
        } catch (Exception e) {
            System.err.println("[PacienteController] Error in handleGetPaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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