package org.example;

import org.example.model.Funcionario;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;

public class Main {
    private static final BigDecimal salarioMinimo = new BigDecimal("1212.00");
    public static void main(String[] args) {
        ArrayList<Funcionario> funcionarios = generateList();

        //Remove o funcionário 'João' da lista
        removeFuncionarioByName(funcionarios, "João");

        //Aumentando o salário de todos os funcionários em 10%
        aumentarSalarioParaTodos(funcionarios, 10);

        //Criando um Map e agrupando os funcinários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        //Imprimindo todos os funcionários da lista
        printFuncionarios(funcionarios);

        //Imprimindo os funcionários, agrupados por função
        printFuncionariosPorFuncao(funcionariosPorFuncao);

        //Imprimindo os funcionários aniversariantes do mês 10 e 12
        imprimirAniversariantes(funcionarios, 10, 12);

        //Imprimindo funcionário com a maior idade
        imprimirFuncionarioMaisVelho(funcionarios);

        //Imprimindo a lista de funcionários por ordem alfabética
        imprimirFuncionariosPorOrdemAlfabetica(funcionarios);

        //Imprimindo o total dos salários dos funcionários
        imprimirTotalSalarios(funcionarios);

        //Imprimindo salários em função de quantos salários mínimos o funcionário ganha
        imprimirSalariosMinimos(funcionarios, salarioMinimo);
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println("Funcionário: " + funcionario.getNome() +
                    ", Salários Mínimos: " + salariosMinimos);
        });
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total dos salários dos funcionários: " + totalSalarios);
    }

    private static void imprimirFuncionariosPorOrdemAlfabetica(List<Funcionario> funcionarios) {
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> System.out.println(funcionario));
    }

    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        funcionarios.stream()
                .max(Comparator.comparing(funcionario ->
                        Period.between(funcionario.getDataNascimento(), LocalDate.now()).getYears()))
                .ifPresentOrElse(
                        funcionario -> {
                            System.out.println("Funcionário mais velho:");
                            System.out.println("Nome: " + funcionario.getNome());
                            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento());
                            System.out.println("Idade: " + calcularIdade(funcionario.getDataNascimento()));
                            System.out.println("---------------");
                        },
                        () -> System.out.println("Não há funcionários na lista.")
                );
    }

    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... mesesAniversario) {
        Map<Integer, List<Funcionario>> aniversariantesPorMes = new HashMap<>();

        // Inicializar listas para cada mês
        for (int mes : mesesAniversario) {
            aniversariantesPorMes.put(mes, new ArrayList<>());
        }

        // Agrupar funcionários por mês de aniversário
        for (Funcionario funcionario : funcionarios) {
            int mesNascimento = funcionario.getDataNascimento().getMonthValue();
            if (aniversariantesPorMes.containsKey(mesNascimento)) {
                aniversariantesPorMes.get(mesNascimento).add(funcionario);
            }
        }

        // Imprimir os aniversariantes agrupados
        for (Map.Entry<Integer, List<Funcionario>> entry : aniversariantesPorMes.entrySet()) {
            int mesAniversario = entry.getKey();
            String mes = obterMesEmPortugues(mesAniversario);
            System.out.println("Aniversariantes de " + mes + ":");

            List<Funcionario> aniversariantesDoMes = entry.getValue();
            if (aniversariantesDoMes.isEmpty()) {
                System.out.println("   Nenhum aniversariante neste mês.");
            } else {
                for (Funcionario funcionario : aniversariantesDoMes) {
                    System.out.println("   " + funcionario);
                }
            }
            System.out.println("---------------");
        }
    }

    private static String obterMesEmPortugues(int mes) {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("pt", "BR"));
        return symbols.getMonths()[mes - 1];
    }

    private static void printFuncionarios(ArrayList<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
    }

    private static void printFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            System.out.println(" ");
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("   " + funcionario);
            }
            System.out.println("---------------");
        }
    }

    private static ArrayList<Funcionario> generateList() {
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 18), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    //Remove um usuário da lista por nome
    private static void removeFuncionarioByName(ArrayList<Funcionario> funcionarios, String nome) {
        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario funcionario = iterator.next();
            if (funcionario.getNome().equals(nome)) {
                iterator.remove();
            }
        }
    }

    private static void aumentarSalarioParaTodos(ArrayList<Funcionario> funcionarios, double percentualAumento) {
        for (Funcionario funcionario : funcionarios) {
            funcionario.aumentarSalario(percentualAumento);
        }
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();

            // Se a função ainda não estiver no mapa, criar uma nova lista
            funcionariosPorFuncao.putIfAbsent(funcao, new ArrayList<>());

            // Adicionar o funcionário à lista correspondente à sua função
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }

        return funcionariosPorFuncao;
    }
}