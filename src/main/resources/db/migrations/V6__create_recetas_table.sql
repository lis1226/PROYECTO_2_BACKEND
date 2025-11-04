CREATE TABLE recetas (
                         id VARCHAR(50) PRIMARY KEY,
                         id_paciente VARCHAR(50),
                         id_medico VARCHAR(50),
                         fecha_confeccion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         fecha_retiro TIMESTAMP NULL,
                         estado VARCHAR(50) DEFAULT 'CONFECCIONADA'
);
CREATE TABLE receta_items (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              receta_id VARCHAR(50) NOT NULL,
                              codigo_medicamento VARCHAR(50),
                              cantidad INT,
                              indicaciones VARCHAR(500),
                              duracion_dias INT,
                              CONSTRAINT fk_receta_items_receta FOREIGN KEY (receta_id) REFERENCES recetas(id)
);

CREATE TABLE despachos (
                           id VARCHAR(50) PRIMARY KEY,
                           receta_id VARCHAR(50),
                           farmaceutico_id VARCHAR(50),
                           fecha_despacho TIMESTAMP NULL,
                           estado VARCHAR(50) DEFAULT 'PENDIENTE',
                           observaciones VARCHAR(500),
                           CONSTRAINT fk_despachos_receta FOREIGN KEY (receta_id) REFERENCES recetas(id)
);