CREATE TABLE topicos
(
    id                   BIGSERIAL PRIMARY KEY,
    titulo               VARCHAR(255) NOT NULL,
    mensagem             TEXT         NOT NULL,
    autor                VARCHAR(255) NOT NULL,
    categoria            VARCHAR(20)  NOT NULL CHECK (categoria IN
                                                      ('PROGRAMACAO', 'IA', 'FRONTEND', 'DADOS', 'INOVACAO',
                                                       'MARKETING', 'DESIGN')),
    data_criacao         TIMESTAMP    NOT NULL,
    status               VARCHAR(20)  NOT NULL CHECK (status IN ('NAO_RESPONDIDO', 'RESPONDIDO', 'RESOLVIDO')),
    aberto               BOOLEAN      NOT NULL,
    quantidade_respostas INT          NOT NULL,
    curso_id             BIGINT,
    CONSTRAINT fk_curso FOREIGN KEY (curso_id) REFERENCES cursos (id) ON DELETE SET NULL
);