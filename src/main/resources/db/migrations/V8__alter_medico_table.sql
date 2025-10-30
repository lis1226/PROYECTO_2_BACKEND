ALTER TABLE medicos
    ADD COLUMN usuario_id BIGINT NOT NULL,
ADD CONSTRAINT fk_medico_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id);
