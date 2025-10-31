package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.FarmaceuticoService;
import org.example.Domain.Farmaceutico.*;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
//import org.example.Domain.dtos.ListFarmaceuticosResponseDto;
import org.example.Domain.models.Farmaceutico;

import java.util.List;
import java.util.stream.Collectors;

public class FarmaceuticoController {
    private final FarmaceuticoService service;
    private final Gson gson = new Gson();

    public FarmaceuticoController(FarmaceuticoService service) {
        this.service = service;
    }

    public ResponseDto route(RequestDto request) {
        try {
            switch (request.getRequest()) {
                case "add":
                    return handleAdd(request);
                case "update":
                    return handleUpdate(request);
                case "delete":
                    return handleDelete(request);
                case "list":
                    return handleList(request);
                case "get":
                    return handleGet(request);
                default:
                    return new ResponseDto(false, "Unknown request: " + request.getRequest(), null);
            }
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    private ResponseDto handleAdd(RequestDto request) {
        AddFarmaceuticoRequestDto dto = gson.fromJson(request.getData(), AddFarmaceuticoRequestDto.class);
        Farmaceutico f = service.createFarmaceutico(dto.getId(), dto.getUsuarioId(), dto.getNombre());
        return new ResponseDto(true, "Farmaceutico added successfully", gson.toJson(toResponseDto(f)));
    }

    private ResponseDto handleUpdate(RequestDto request) {
        UpdateFarmaceuticoRequestDto dto = gson.fromJson(request.getData(), UpdateFarmaceuticoRequestDto.class);
        Farmaceutico updated = service.updateFarmaceutico(dto.getId(), dto.getNombre());
        if (updated == null) return new ResponseDto(false, "Farmaceutico not found", null);
        return new ResponseDto(true, "Farmaceutico updated successfully", gson.toJson(toResponseDto(updated)));
    }

    private ResponseDto handleDelete(RequestDto request) {
        DeleteFarmaceuticoRequestDto dto = gson.fromJson(request.getData(), DeleteFarmaceuticoRequestDto.class);
        boolean deleted = service.deleteFarmaceutico(dto.getId());
        if (!deleted) return new ResponseDto(false, "Farmaceutico not found", null);
        return new ResponseDto(true, "Farmaceutico deleted successfully", null);
    }

    private ResponseDto handleList(RequestDto request) {
        List<Farmaceutico> list = service.getAllFarmaceuticos();
        List<FarmaceuticoResponseDto> dtos = list.stream().map(this::toResponseDto).collect(Collectors.toList());
        return new ResponseDto(true, "Farmaceuticos found", gson.toJson(new ListFarmaceuticosResponseDto(dtos)));
    }

    private ResponseDto handleGet(RequestDto request) {
        DeleteFarmaceuticoRequestDto dto = gson.fromJson(request.getData(), DeleteFarmaceuticoRequestDto.class);
        Farmaceutico f = service.getFarmaceuticoById(dto.getId());
        if (f == null) return new ResponseDto(false, "Farmaceutico not found", null);
        return new ResponseDto(true, "Farmaceutico found", gson.toJson(toResponseDto(f)));
    }

    private FarmaceuticoResponseDto toResponseDto(Farmaceutico f) {
        return new FarmaceuticoResponseDto(
                f.getId(),
                f.getUsuario().getId(),
                f.getNombre()
        );
    }
}