CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    nome_de_usuario VARCHAR(255) UNIQUE,
    biografia TEXT,
    mini_biografia VARCHAR(255)
);