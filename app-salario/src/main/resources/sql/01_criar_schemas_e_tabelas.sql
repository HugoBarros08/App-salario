-- 01_criar_schemas_e_tabelas.sql
BEGIN;

CREATE SCHEMA IF NOT EXISTS pessoal AUTHORIZATION admin;
CREATE SCHEMA IF NOT EXISTS financeiro AUTHORIZATION admin;

-- Tabela: pessoal.cargo
CREATE TABLE IF NOT EXISTS pessoal.cargo (
    id serial PRIMARY KEY,
    nome varchar(255)
);
ALTER TABLE pessoal.cargo OWNER TO admin;

-- Tabela: pessoal.pessoa
CREATE TABLE IF NOT EXISTS pessoal.pessoa (
    id serial PRIMARY KEY,
    nome varchar(255),
    cidade varchar(255),
    email varchar(255),
    cep varchar(255),
    enderco varchar(255),
    pais varchar(255),
    usuario varchar(255),
    telefone varchar(255),
    data_nascimento varchar(255),
    cargo_id integer,
    CONSTRAINT fk_pessoa_cargo FOREIGN KEY (cargo_id) REFERENCES pessoal.cargo (id)
);
ALTER TABLE pessoal.pessoa OWNER TO admin;

-- Tipo ENUM: financeiro.tipo_vencimento
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'tipo_vencimento') THEN
    CREATE TYPE financeiro.tipo_vencimento AS ENUM ('CREDITO', 'DEBITO');
    END IF;
    END
$$;

-- Tabela: financeiro.vencimentos
CREATE TABLE IF NOT EXISTS financeiro.vencimentos (
    id serial PRIMARY KEY,
    descricao varchar(255),
    valor integer,
    tipo financeiro.tipo_vencimento
);
ALTER TABLE financeiro.vencimentos OWNER TO admin;

-- Tabela: financeiro.cargo_vencimentos
CREATE TABLE IF NOT EXISTS financeiro.cargo_vencimentos (
    id serial PRIMARY KEY,
    cargo_id integer,
    vencimento_id integer,
    CONSTRAINT fk_cargovenc_cargo FOREIGN KEY (cargo_id) REFERENCES pessoal.cargo (id),
    CONSTRAINT fk_cargovenc_vencimento FOREIGN KEY (vencimento_id) REFERENCES financeiro.vencimentos (id)
);
ALTER TABLE financeiro.cargo_vencimentos OWNER TO admin;

-- Tabela: financeiro.pessoa_salario_consolidado
CREATE TABLE IF NOT EXISTS financeiro.pessoa_salario_consolidado (
    pessoa_id serial PRIMARY KEY,
    nome_pessoa varchar(255),
    nome_cargo varchar(255),
    salario numeric(12,2)
);
ALTER TABLE financeiro.pessoa_salario_consolidado OWNER TO admin;

-- Adicionar permissões ao usuário
GRANT ALL PRIVILEGES ON SCHEMA pessoal TO admin;
GRANT ALL PRIVILEGES ON SCHEMA financeiro TO admin;
GRANT USAGE ON SCHEMA pessoal TO admin;
GRANT USAGE ON SCHEMA financeiro TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA pessoal TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA financeiro TO admin;

COMMIT;