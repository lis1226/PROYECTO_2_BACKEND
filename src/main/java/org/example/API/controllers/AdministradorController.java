package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.AdministradorService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.administrador.*;
import org.example.Domain.models.Administrador;

import java.util.List;
import java.util.stream.Collectors;

public class AdministradorController {

    private final AdministradorService administradorService;
    private final Gson gson = new Gson();

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    public ResponseDto route(RequestDto req) {
        try {
            switch (req.getRequest()) {
                case "add": return handleAdd(req);
                case "update": return handleUpdate(req);
                case "delete": return handleDelete(req);
                case "list": return handleList();
                case "get": return handleGet(req);
                default:
                    return new ResponseDto(false, "Unknown request: " + req.getRequest(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto(false, "Error: " + e.getMessage(), null);
        }
    }

    private ResponseDto handleAdd(RequestDto req) {
        AddAdministradorRequestDto dto = gson.fromJson(req.getData(), AddAdministradorRequestDto.class);
        Administrador admin = administradorService.createAdministrador(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return new ResponseDto(true, "Administrador creado correctamente", gson.toJson(toResponse(admin)));
    }

    private ResponseDto handleUpdate(RequestDto req) {
        UpdateAdministradorRequestDto dto = gson.fromJson(req.getData(), UpdateAdministradorRequestDto.class);
        Administrador admin = administradorService.updateAdministrador(dto.getId(), dto.getUsername(), dto.getEmail());
        return admin != null
                ? new ResponseDto(true, "Administrador actualizado", gson.toJson(toResponse(admin)))
                : new ResponseDto(false, "Administrador no encontrado", null);
    }

    private ResponseDto handleDelete(RequestDto req) {
        DeleteAdministradorRequestDto dto = gson.fromJson(req.getData(), DeleteAdministradorRequestDto.class);
        boolean ok = administradorService.deleteAdministrador(dto.getId());
        return ok
                ? new ResponseDto(true, "Administrador eliminado", null)
                : new ResponseDto(false, "Administrador no encontrado", null);
    }

    private ResponseDto handleList() {
        List<Administrador> admins = administradorService.getAllAdministradores();
        List<AdministradorResponseDto> responseList = admins.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseDto(true, "Administradores encontrados", gson.toJson(responseList));
    }

    private ResponseDto handleGet(RequestDto req) {
        GetAdministradorRequestDto dto = gson.fromJson(req.getData(), GetAdministradorRequestDto.class);
        Administrador admin = administradorService.getAdministradorById(dto.getId());
        return admin != null
                ? new ResponseDto(true, "Administrador encontrado", gson.toJson(toResponse(admin)))
                : new ResponseDto(false, "Administrador no encontrado", null);
    }

    private AdministradorResponseDto toResponse(Administrador admin) {
        return new AdministradorResponseDto(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getRol(),
                "", // createdAt opcional
                ""  // updatedAt opcional
        );
    }
}
