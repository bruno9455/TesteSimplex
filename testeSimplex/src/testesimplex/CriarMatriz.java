/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesimplex;

import java.util.Formatter;

/**
 *
 * @author bruno
 */
public class CriarMatriz {

    public final static String[] MAIOR_OU_IGUAL = {">=", "=>", "\u2265", "\u2267"};
    public final static String[] MENOR_OU_IGUAL = {"<=", "=<", "\u2264", "\u2266"};
    public final static String[] OPERADOR = {"+", "-", "*", "/"};
    public final static char IGUAL = '=';
    public final static char MENOS = '-';
    public final static char MAIS = '+';
    public static Formatter escrever = new Formatter(System.out);

    public static double[][] processarDados(String[] linhas) {

//        imprimir.
        String[][] variaveisDaPrimeiraLinha = getVariaveisDaPrimeiraLinha(linhas[0]);//vai apanhar as variaveis, valores e operadores na primeira linha

        int nLinhas = linhas.length;
        int nColunas = variaveisDaPrimeiraLinha.length + nLinhas;
        int nVariaveis = variaveisDaPrimeiraLinha.length;
        double[][] matrizInicial = new double[nLinhas][nColunas];

        //preenchimento da linha de Z
        double[] valorPrimeiraLinha = getValorPrimeiraLinha(nColunas, variaveisDaPrimeiraLinha);
        for (int i = 0; i < nVariaveis; i++) {
            matrizInicial[0][i] = valorPrimeiraLinha[i] * -1;
        }
        getValoresLinhasDasRestricoes(nLinhas, linhas, nColunas, nVariaveis, variaveisDaPrimeiraLinha, matrizInicial);

        return matrizInicial;
    }

    public static void getValoresLinhasDasRestricoes(int numeroDeLinhas, String[] linhas, int nColunas, int nVariaveis, String[][] variaveisDaPrimeiraLinha, double[][] matrizInicial) {
        try {
            for (int i = 1; i < numeroDeLinhas; i++) {
                String linha = linhas[i];
                double[] linhaParaMatriz = new double[nColunas];

                //<editor-fold desc="SLACK">
                int indexSlack = i + nVariaveis;
                linhaParaMatriz[indexSlack - 1] = 1;
            //</editor-fold>

                //<editor-fold desc="Variaveis">
                double[] valores = new double[nVariaveis];

                valores = valoresDasVariaveisNasRestricoes(variaveisDaPrimeiraLinha, linha);
                for (int j = 0; j < nVariaveis; j++) {
                    linhaParaMatriz[j] = valores[j];

                }

                //</editor-fold>
                //<editor-fold desc="B">
                linhaParaMatriz[nColunas - 1] = getB(linha);

                //</editor-fold>
                matrizInicial[i] = linhaParaMatriz;

            }
        } catch (NumberFormatException nfe) {
        }
    }

    public static double[] getValorPrimeiraLinha(int numeroDeColunas, String[][] variaveisDaPrimeiraLinha) {
        double[] valorPrimeiraLinha = new double[variaveisDaPrimeiraLinha.length];
        for (int i = 0; i < variaveisDaPrimeiraLinha.length; i++) { //Preencher a linha de Z
            try {
                variaveisDaPrimeiraLinha[i][1] = variaveisDaPrimeiraLinha[i][2] + variaveisDaPrimeiraLinha[i][1];
                valorPrimeiraLinha[i] = Double.parseDouble(variaveisDaPrimeiraLinha[i][1]);
            } catch (NumberFormatException nfe) {

            }
        }
        return valorPrimeiraLinha;
    }

    private static double getB(String linha) {

        //TODO: ver se é preciso fazer tb por =
        double output;
        int index = -1;

        for (String charMaior : MAIOR_OU_IGUAL) {
            if (linha.indexOf(charMaior) > 0) {
                index = linha.indexOf(charMaior) + charMaior.length();

                break;
            }
        }
        if (index == -1) {
            for (String charMenor : MENOR_OU_IGUAL) {
                if (linha.indexOf(charMenor) > 0) {
                    index = linha.indexOf(charMenor) + charMenor.length();

                    break;
                }
            }
        }
        if (index != -1) {

            output = Double.parseDouble(linha.substring(index).trim());
            if (output < 0) {
                System.out.printf("É preciso verificar os b's,\npois não podem ser menores que 0\n");
                System.exit(0);

            }
        } else {
            output = 0;
        }

        return output;
    }

    public static String[][] getVariaveisDaPrimeiraLinha(String linha) {

        String[][] output = null;

        if (linha != null) {

            if (linha.contains("=")) {

                int charIndex = 0;

                while (charIndex < linha.length()) {

                    char caracter = linha.charAt(charIndex);

                    if (eUmaLetra(caracter) && depoisDeUmIgual(linha, charIndex)) {
                        //Encontrei uma variavel
                        output = Utils.expandirArray(output);

                        extrairValorDaVariavel(charIndex, linha, output);
                        charIndex = extrairNomeDaVariavel(charIndex, linha, output);
                    }
                    charIndex++;
                }
            }
        }
        return output;
    }

    public static double[] valoresDasVariaveisNasRestricoes(String[][] variaveis, String linha) {
        double[] output = new double[variaveis.length];
        int indexInicial = 0;
        for (int i = 0; i < variaveis.length; i++) {
            int index = -1;
            if (linha.contains(variaveis[i][0])) {
//                while (index<linha.length()){

                index = linha.indexOf(variaveis[i][0]);

                String temp = linha.substring(indexInicial, index).trim();
                temp = temp.replaceAll(" ", "");

                indexInicial = index + variaveis[i][0].length();

                if (temp.matches(".*\\d+.*")) {

                    output[i] = Double.parseDouble(temp);
                } else {

                    if (temp.contains("-")) {
                        output[i] = -1;

                    } else {
                        output[i] = 1;
//                        }
//
                    }
                }

//                }
            } else {
                output[i] = 0;
            }
        }

        return output;
    }

    public static boolean eUmaLetra(char caracter) {

        boolean output = false;

        if (String.valueOf(caracter).matches("[A-z]")) {

            output = true;
        }
        return output;
    }

    public static int extrairNomeDaVariavel(int charIndex, String linha, String[][] variaveisEncontradas) {

        int idx = charIndex;
        String nome = "";
        char carater = linha.charAt(idx);

        while (!nomeDaVariavelTerminou(carater) && idx < linha.length()) {

            carater = linha.charAt(idx);

            if (String.valueOf(carater).matches("[0-z]")) {
                nome += carater;
            }
            idx++;
        }

        int indexsOndeInserirVariavel = variaveisEncontradas.length - 1;
        variaveisEncontradas[indexsOndeInserirVariavel][0] = nome;

        return charIndex + nome.length() - 1;
    }

    public static boolean depoisDeUmIgual(String linha, int charIndex) {

        int idx = linha.indexOf(String.valueOf(IGUAL));
        boolean output = idx > -1 && idx < charIndex;

        return output;
    }

    public static boolean nomeDaVariavelTerminou(char caracter) {

        boolean output = true;

        String car = String.valueOf(caracter);

        if (!car.matches("\\s+")) {

            boolean eOperador = false;

            for (String MAIOR_OU_IGUAL1 : MAIOR_OU_IGUAL) {
                if (car.equals(MAIOR_OU_IGUAL1)) {
                    eOperador = true;
                    break;
                }
            }

            if (!eOperador) {

                for (String MENOR_OU_IGUAL1 : MENOR_OU_IGUAL) {
                    if (car.equals(MENOR_OU_IGUAL1)) {
                        eOperador = true;
                        break;
                    }
                }

                if (!eOperador) {

                    if (caracter != MAIS && caracter != IGUAL && caracter != MENOS) {

                        output = false;
                    }
                }
            }
        }
        return output;
    }

    public static boolean valorDaVariavelTerminou(char caracter, int index) {

        boolean output = true;

        String car = String.valueOf(caracter);

        boolean eOperador = false;

        for (String OPERADOR1 : OPERADOR) {
            if (car.equals(OPERADOR1) || index < 0) {
                eOperador = true;
                break;
            }
        }

        return output;
    }

    public static void extrairValorDaVariavel(int charIndex, String linha, String[][] variaveisEncontradas) {

        int ultimaVariavelEncontrada = variaveisEncontradas.length - 1;
        String[] quantOperador = extrairValorDaVariavel(charIndex, linha);
        variaveisEncontradas[ultimaVariavelEncontrada][1] = quantOperador[0];
        variaveisEncontradas[ultimaVariavelEncontrada][2] = quantOperador[1];

    }

    public static String[] extrairValorDaVariavel(int charIndex, String linha) {

        String[] output = new String[2];

        int idx = charIndex - 1;
        String numero = "";
        char carater = '+';
        if (charIndex > -1) {

            carater = linha.charAt(idx);

            while ((!valorDaVariavelTerminou(carater, idx))) {

                carater = linha.charAt(idx);

                if (String.valueOf(carater).matches("[0-9]")) {
                    numero = carater + numero;
                }
                idx--;
            }
        }
        if (numero.equals("")) {
            numero = "1";
        }

        String operador = "+";

        if (carater == MENOS) {
            operador = "-";
        }

        output[0] = numero.replaceAll(" ","");
        output[1] = operador;
        return output;
    }

    public static String[][] criarMatrizString(double[][] matrizSimplexD, String[] linhas) {
        String[][] matrizSimplexS = new String[matrizSimplexD.length + 1][matrizSimplexD[0].length + 1];
        String[][] variaveisDaPrimeiraLinha = getVariaveisDaPrimeiraLinha((linhas[0]));
        for (int i = 1; i < matrizSimplexS.length; i++) {
            for (int j = 1; j < matrizSimplexS[0].length; j++) {
                matrizSimplexS[i][j] = String.format("%8.2f|", matrizSimplexD[i - 1][j - 1]);
            }
            matrizSimplexS[0][0] = "";
            matrizSimplexS[1][0] = "Z ";
            matrizSimplexS[0][matrizSimplexS[0].length - 1] = "b  ";
        }
        for (int i = 2; i < matrizSimplexS.length; i++) {
            matrizSimplexS[i][0] = String.format("f%d ", i - 1);
        }

        for (int i = 1; i < variaveisDaPrimeiraLinha.length + 1; i++) {
            matrizSimplexS[0][i] = String.format("%s ", variaveisDaPrimeiraLinha[i - 1][0]);
        }

        for (int i = variaveisDaPrimeiraLinha.length + 1; i < matrizSimplexS[0].length - 1; i++) {
            matrizSimplexS[0][i] = String.format("f%d ", i - variaveisDaPrimeiraLinha.length);
        }

        return matrizSimplexS;
    }

}
