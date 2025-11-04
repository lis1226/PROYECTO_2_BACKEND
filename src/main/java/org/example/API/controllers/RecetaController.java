package org.example.API.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.DataAccess.services.RecetaService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.models.Receta;

import java.lang.reflect.Type;
import java.util.List;

public class RecetaController {

    private final RecetaService recetaService;
    private final Gson gson = new Gson();

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    public ResponseDto handle(RequestDto request) {
        String req = request.getRequest();
        try {
            switch (req) {
                case "list":
                    return list();
                case "create":
                    return create(request);
                case "get":
                    return getById(request);
                case "update":
                    return update(request);
                default:
                    return new ResponseDto(false, "Action no soportada: " + req, "");
            }
        } catch (Exception e) {
            return new ResponseDto(false, "Error en RecetaController: " + e.getMessage(), "");
        }
    }

    private ResponseDto list() {
        List<Receta> recetas = recetaService.obtenerTodas();
        Type t = new TypeToken<List<Receta>>(){}.getType();
        return new ResponseDto(true, gson.toJson(recetas, t),"");
    }

    private ResponseDto create(RequestDto request) {
        Receta receta = gson.fromJson(request.getData(), Receta.class);
        recetaService.guardar(receta);
        return new ResponseDto(true, "Receta creada", "");
    }

    private ResponseDto getById(RequestDto request) {
        String id = request.getData();
        Receta r = recetaService.obtenerPorId(id);
        if (r == null) return new ResponseDto(false, "No encontrada","");
        return new ResponseDto(true, gson.toJson(r),"");
    }

    private ResponseDto update(RequestDto request) {
        Receta receta = gson.fromJson(request.getData(), Receta.class);
        recetaService.actualizar(receta);
        return new ResponseDto(true, "Receta actualizada","");
    }
}
