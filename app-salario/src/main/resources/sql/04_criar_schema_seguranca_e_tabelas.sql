-- 04_criar_schema_seguranca_e_tabelas.sql
BEGIN;

CREATE SCHEMA IF NOT EXISTS seguranca AUTHORIZATION admin;

-- Tabela: seguranca.usuario
CREATE TABLE IF NOT EXISTS seguranca.usuario (
                                                 id SERIAL PRIMARY KEY,
                                                 login varchar(100) UNIQUE NOT NULL,
    senha_hash varchar(255) NOT NULL,
    nome varchar(150) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
    );
ALTER TABLE seguranca.usuario OWNER TO admin;

-- Adicionar permissões ao usuário
GRANT ALL PRIVILEGES ON SCHEMA seguranca TO admin;
GRANT USAGE ON SCHEMA seguranca TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA seguranca TO admin;

-- Inserts para seguranca.usuario
INSERT
INTO seguranca.usuario (login, senha_hash, nome, ativo)
VALUES
    ('admin', md5('segredo'), 'Administrador', true);

COMMIT;