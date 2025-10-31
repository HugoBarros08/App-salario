-- 03_gerar_consolidado.sql
BEGIN;

-- Popula financeiro.pessoa_salario_consolidado seguindo a lógica: CREDITO = +valor, DEBITO = -valor
INSERT INTO financeiro.pessoa_salario_consolidado(pessoa_id, nome_pessoa, nome_cargo, salario)
SELECT p.id AS pessoa_id,
       p.nome AS nome_pessoa,
       c.nome AS nome_cargo,
       COALESCE( SUM( CASE WHEN v.tipo = 'CREDITO' THEN v.valor
                           WHEN v.tipo = 'DEBITO'  THEN -v.valor
                           ELSE 0 END ), 0 ) AS salario
FROM pessoal.pessoa p
         LEFT JOIN pessoal.cargo c ON p.cargo_id = c.id
         LEFT JOIN financeiro.cargo_vencimentos cv ON c.id = cv.cargo_id
         LEFT JOIN financeiro.vencimentos v ON cv.vencimento_id = v.id
GROUP BY p.id, p.nome, c.nome;

COMMIT;