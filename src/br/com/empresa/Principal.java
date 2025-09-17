package br.com.empresa;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        // Força UTF-8 na JVM (resolve problema de acentuação no console Windows)
        System.setProperty("file.encoding", "UTF-8");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 – Remover João
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // 3.3 – Imprimir todos os funcionários formatados
        System.out.println("---- Funcionários ----");
        for (Funcionario f : funcionarios) {
            System.out.println("Nome: " + f.getNome() +
                    " | Nascimento: " + f.getDataNascimento().format(dtf) +
                    " | Salário: " + nf.format(f.getSalario()) +
                    " | Função: " + f.getFuncao());
        }

        // 3.4 – Aumento de 10%
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));

        // 3.5 – Agrupar por função
        Map<String, List<Funcionario>> porFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n---- Agrupados por Função ----");
        porFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println("  " + f.getNome()));
        });

        // 3.8 – Aniversariantes Outubro e Dezembro
        System.out.println("\n---- Aniversariantes Outubro/Dezembro ----");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome()));

        // 3.9 – Funcionário mais velho
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("\nMais velho: " + maisVelho.getNome() + " | Idade: " + idade);
        }

        // 3.10 – Ordem alfabética
        System.out.println("\n---- Ordem Alfabética ----");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // 3.11 – Total salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal salários: " + nf.format(totalSalarios));

        // 3.12 – Salários mínimos
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n---- Salários Mínimos ----");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + " recebe " + qtd + " salários mínimos");
        });
    }
}
