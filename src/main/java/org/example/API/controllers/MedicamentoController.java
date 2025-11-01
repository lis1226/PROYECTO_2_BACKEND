package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.MedicamentoService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.medicamentos.*;

import org.example.Domain.models.Medicamento;

import java.util.List;
import java.util.stream.Collectors;

public class MedicamentoController {
    private final MedicamentoService medicamentoService;
    private final Gson gson = new Gson();

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
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
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    private ResponseDto handleAdd(RequestDto requestDto) {
        AddMedicamentoRequestDto dto = gson.fromJson(requestDto.getData(), AddMedicamentoRequestDto.class);
        Medicamento m = medicamentoService.createMedicamento(dto.getCodigo(), dto.getDescripcion(), dto.getPresentacion());
        return new ResponseDto(true, "Medicamento creado", gson.toJson(toResponseDto(m)));
    }

    private ResponseDto handleUpdate(RequestDto requestDto) {
        UpdateMedicamentoRequestDto dto = gson.fromJson(requestDto.getData(), UpdateMedicamentoRequestDto.class);
        Medicamento m = medicamentoService.updateMedicamento(dto.getCodigo(), dto.getDescripcion(), dto.getPresentacion());
        if (m == null) return new ResponseDto(false, "Medicamento no encontrado", null);
        return new ResponseDto(true, "Medicamento actualizado", gson.toJson(toResponseDto(m)));
    }

    private ResponseDto handleDelete(RequestDto requestDto) {
        DeleteMedicamentoRequestDto dto = gson.fromJson(requestDto.getData(), DeleteMedicamentoRequestDto.class);
        boolean ok = medicamentoService.deleteMedicamento(dto.getCodigo());
        if (!ok) return new ResponseDto(false, "Medicamento no encontrado", null);
        return new ResponseDto(true, "Medicamento eliminado", null);
    }

    private ResponseDto handleList(RequestDto requestDto) {
        List<Medicamento> list = medicamentoService.getAllMedicamentos();
        List<MedicamentoResponseDto> dtos = list.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        return new ResponseDto(true, "Medicamentos listados", gson.toJson(new ListMedicamentosResponseDto(dtos)));
    }

    private ResponseDto handleGet(RequestDto requestDto) {
        DeleteMedicamentoRequestDto dto = gson.fromJson(requestDto.getData(), DeleteMedicamentoRequestDto.class);
        Medicamento m = medicamentoService.getMedicamentoByCodigo(dto.getCodigo());
        if (m == null) return new ResponseDto(false, "Medicamento no encontrado", null);
        return new ResponseDto(true, "Medicamento encontrado", gson.toJson(toResponseDto(m)));
    }

    private MedicamentoResponseDto toResponseDto(Medicamento m) {
        return new MedicamentoResponseDto(m.getCodigo(), m.getDescripcion(), m.getPresentacion());
    }
}
