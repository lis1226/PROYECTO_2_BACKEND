package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.DespachoService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.models.Despacho;

import java.util.List;

public class DespachoController {

    private final DespachoService despachoService;
    private final Gson gson = new Gson();

    public DespachoController(DespachoService despachoService) {
        this.despachoService = despachoService;
    }

    public ResponseDto handle(RequestDto request) {
        String req = request.getRequest();
        try {
            switch (req) {
                case "list":
                    List<Despacho> all = despachoService.obtenerTodos();
                    return new ResponseDto(true, gson.toJson(all),"");
                case "create":
                    Despacho d = gson.fromJson(request.getData(), Despacho.class);
                    despachoService.guardar(d);
                    return new ResponseDto(true, "Despacho creado","");
                case "get":
                    Despacho found = despachoService.obtenerPorId(request.getData());
                    if (found == null) return new ResponseDto(false, "No encontrado","");
                    return new ResponseDto(true, gson.toJson(found),"");
                case "update":
                    Despacho updated = gson.fromJson(request.getData(), Despacho.class);
                    despachoService.actualizar(updated);
                    return new ResponseDto(true, "Despacho actualizado","");
                default:
                    return new ResponseDto(false, "Acción no válida","");
            }
        } catch (Exception e) {
            return new ResponseDto(false, "Error DespachoController: " + e.getMessage(),"");
        }
    }
}
