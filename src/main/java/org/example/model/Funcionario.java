package org.example.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void aumentarSalario(double percentualAumento) {
        BigDecimal aumento = salario.multiply(BigDecimal.valueOf(percentualAumento / 100.0));
        salario = salario.add(aumento);
    }
    @Override
    public String toString() {
        // Format the salary with a comma for the thousands separator and a dot for the decimal separator
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        String formattedSalary = df.format(salario);

        return "Nome: " + getNome() +
                "\nData de Nascimento: " + getDataNascimento() +
                "\nSalário: " + formattedSalary +
                "\nFunção: " + funcao +
                "\n---------------";
    }
}
