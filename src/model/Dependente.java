package model;

import java.time.LocalDate;
import java.time.Period;
import exception.DependenteException;
import enums.Parentesco;

public class Dependente extends Pessoa {
    private Parentesco parentesco;
    private int codigo;

    public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco)
            throws DependenteException {
        super(nome, cpf, dataNascimento);

        if (Period.between(dataNascimento, LocalDate.now()).getYears() >= 18) {
            throw new DependenteException("Dependente deve ser menor que 18 anos.");
        }
        this.parentesco = parentesco;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Nome: " + getNome() +
                " | CPF: " + getCpf() +
                " | Nascimento: " + getDataNascimento() +
                " | Parentesco: " + parentesco;
    }
}