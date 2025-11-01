package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.RecetaService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.receta.*;
import org.example.Domain.models.DetalleReceta;
import org.example.Domain.models.Receta;

import java.util.List;
import java.util.stream.Collectors;

public class RecetaController {
    private final RecetaService recetaService;
    private final Gson gson = new Gson();

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    public ResponseDto route(RequestDto requestDto) {
        try {
            switch (requestDto.getRequest()) {
                case "add":
                    return handleAdd(requestDto);
                case "update":
                    return handleUpdate(requestDto);
                case "delete":
                    return handleDelete(requestDto);
                case "list":
                    return handleList(requestDto);
                case "get":
                    return handleGet(requestDto);
                default:
                    return new ResponseDto(false, "Unknown request: " + requestDto.getRequest(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    private ResponseDto handleAdd(RequestDto req) {
        AddRecetaRequestDto dto = gson.fromJson(req.getData(), AddRecetaRequestDto.class);
        Receta receta = recetaService.createReceta(dto.getId(), dto.getIdPaciente(), dto.getIdMedico(), dto.getEstado(), dto.getDetalles());
        return new ResponseDto(true, "Receta creada exitosamente", gson.toJson(toResponse(receta)));
    }

    private ResponseDto handleUpdate(RequestDto req) {
        UpdateRecetaRequestDto dto = gson.fromJson(req.getData(), UpdateRecetaRequestDto.class);
        Receta receta = recetaService.updateReceta(dto.getId(), dto.getEstado());
        return receta != null
                ? new ResponseDto(true, "Receta actualizada", gson.toJson(toResponse(receta)))
                : new ResponseDto(false, "Receta no encontrada", null);
    }

    private ResponseDto handleDelete(RequestDto req) {
        DeleteRecetaRequestDto dto = gson.fromJson(req.getData(), DeleteRecetaRequestDto.class);
        boolean ok = recetaService.deleteReceta(dto.getId());
        return ok ? new ResponseDto(true, "Receta eliminada", null) : new ResponseDto(false, "No encontrada", null);
    }

    private ResponseDto handleList(RequestDto req) {
        List<Receta> list = recetaService.getAllRecetas();
        List<RecetaResponseDto> mapped = list.stream().map(this::toResponse).collect(Collectors.toList());
        return new ResponseDto(true, "Recetas encontradas", gson.toJson(mapped));
    }

    private ResponseDto handleGet(RequestDto req) {
        DeleteRecetaRequestDto dto = gson.fromJson(req.getData(), DeleteRecetaRequestDto.class);
        Receta receta = recetaService.getRecetaById(dto.getId());
        if (receta == null)
            return new ResponseDto(false, "Receta no encontrada", null);
        return new ResponseDto(true, "Receta encontrada", gson.toJson(toResponse(receta)));
    }

    private RecetaResponseDto toResponse(Receta r) {
        List<DetalleRecetaDto> detalles = r.getDetalles().stream()
                .map(d -> new DetalleRecetaDto(d.getIdMedicamento(), d.getCantidad(), d.getIndicaciones(), d.getDuracionDias()))
                .collect(Collectors.toList());

        return new RecetaResponseDto(
                r.getId(),
                r.getIdPaciente(),
                r.getIdMedico(),
                r.getEstado(),
                r.getFechaConfeccion(),
                r.getFechaRetiro(),
                detalles
        );
    }
}
