CREATE SCHEMA IF NOT EXISTS trabalho_final_poo
CREATE TABLE trabalho_final_poo.funcionario (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cpf CHAR(11) NOT NULL UNIQUE,
  data_nascimento DATE NOT NULL,
  salario_bruto NUMERIC(10,2) NOT NULL
);

