CREATE TABLE detalles_receta (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 id_receta VARCHAR(20),
                                 codigo_medicamento VARCHAR(20),
                                 cantidad INT,
                                 indicaciones VARCHAR(255),
                                 FOREIGN KEY (id_receta) REFERENCES recetas(id),
                                 FOREIGN KEY (codigo_medicamento) REFERENCES medicamentos(codigo)
);