CREATE TABLE recetas (
                         id VARCHAR(20) PRIMARY KEY,
                         fecha_confeccion DATE NOT NULL,
                         fecha_retiro DATE,
                         estado VARCHAR(50),
                         id_paciente VARCHAR(20),
                         id_medico VARCHAR(20),
                         detalles TEXT,
                         FOREIGN KEY (id_paciente) REFERENCES pacientes(id),
                         FOREIGN KEY (id_medico) REFERENCES medicos(id)
);