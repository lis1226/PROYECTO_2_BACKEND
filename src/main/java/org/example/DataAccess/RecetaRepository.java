package org.example.DataAccess;

import org.example.Domain.models.Receta;
import java.util.*;

public class RecetaRepository {
    private final List<Receta> recetas = new ArrayList<>();

    public void save(Receta receta) { recetas.add(receta); }

    public void update(Receta receta) {
        for (int i = 0; i < recetas.size(); i++) {
            if (recetas.get(i).getId().equals(receta.getId())) {
                recetas.set(i, receta);
                return;
            }
        }
    }

    public void delete(Receta receta) { recetas.remove(receta); }

    public List<Receta> findAll() { return new ArrayList<>(recetas); }

    public Optional<Receta> findById(String id) {
        return recetas.stream().filter(r -> r.getId().equals(id)).findFirst();
    }
}
