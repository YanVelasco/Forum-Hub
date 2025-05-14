CREATE TABLE cursos
(
    id        BIGSERIAL PRIMARY KEY,
    nome      VARCHAR(255) NOT NULL UNIQUE,
    categoria VARCHAR(20)  NOT NULL CHECK (categoria IN
                                           ('PROGRAMACAO', 'IA', 'FRONTEND', 'DADOS', 'INOVACAO', 'MARKETING', 'DESIGN'))
);