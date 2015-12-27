/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesimplex;

/**
 *
 * @author bruno
 */
public class ResolverSimplex {

    public static void executarSimplex(double[][] matrizSimplex, String caminhoDoFicheiroDeOutput, String[] linhas) {

        int countIteracoes = 0;

        String[][] matrizSimplexS = testesimplex.CriarMatriz.criarMatrizString(matrizSimplex, linhas);

        for (int i = 1; i < matrizSimplexS.length; i++) {
            for (int j = 1; j < matrizSimplexS[0].length; j++) {
                matrizSimplexS[i][j] = String.format("%6.2f ", matrizSimplex[i - 1][j - 1]);
            }
        }

        testesimplex.Escrever.ImprimirMatrizesS(matrizSimplexS, countIteracoes, caminhoDoFicheiroDeOutput);

        while (existemNumerosNegativos(matrizSimplex[0])) {
            countIteracoes++;
            int[] indicesDoPivot = encontraNumPivot(matrizSimplex);

            passarLinhaPivotParaUm(matrizSimplex[indicesDoPivot[0]][indicesDoPivot[1]],
                    indicesDoPivot, matrizSimplex[indicesDoPivot[0]]);

            for (int linha = 0; linha < matrizSimplex.length; linha++) {

                if ((linha != indicesDoPivot[0]) & ((matrizSimplex[linha][indicesDoPivot[1]]) != 0)) {

                    zerarElementosDaColunaPivot(indicesDoPivot, linha, matrizSimplex[linha], matrizSimplex[indicesDoPivot[0]]);

                    passarLinhaPivotParaUm(matrizSimplex[indicesDoPivot[0]][indicesDoPivot[1]],
                            indicesDoPivot, matrizSimplex[indicesDoPivot[0]]);

                    for (linha = 0; linha < matrizSimplex.length; linha++) {

                        if ((linha != indicesDoPivot[0]) & ((matrizSimplex[linha][indicesDoPivot[1]]) != 0)) {

                            zerarElementosDaColunaPivot(indicesDoPivot, linha, matrizSimplex);

                        }
                    }
                }
            }
            for (int i = 1; i < matrizSimplexS.length; i++) {
                for (int j = 1; j < matrizSimplexS[0].length; j++) {
                    matrizSimplexS[i][j] = String.format("%6.2f", matrizSimplex[i - 1][j - 1]);
                }
            }
            matrizSimplexS[indicesDoPivot[0]+1][0]=matrizSimplexS[0][indicesDoPivot[1]+1];

            testesimplex.Escrever.ImprimirMatrizesS(matrizSimplexS, countIteracoes, caminhoDoFicheiroDeOutput);


        }
        
testesimplex.Escrever.escreverResultados(matrizSimplexS, caminhoDoFicheiroDeOutput);
    }

    public static boolean existemNumerosNegativos(double[] primeiraLinha) {

        int coluna = 0;
        boolean existemNumNegat;
        while ((primeiraLinha[coluna] >= 0) && (coluna < primeiraLinha.length - 1)) {
            coluna++;
        }
        if (coluna == primeiraLinha.length - 1) {
            existemNumNegat = false;
        } else {
            existemNumNegat = true;
        }
        return existemNumNegat;
    }

    public static int[] encontraNumPivot(double[][] matrizSimplex) {

        int[] indicesDoPivot = new int[2];

        int indiceColunaPivot = encontraColunaPivot(matrizSimplex[0], matrizSimplex);
        indicesDoPivot[1] = indiceColunaPivot;

        double[] colunaPivotRestricoes = criaColunaRestricoes(indiceColunaPivot, matrizSimplex);
        for (int i = 0; i < colunaPivotRestricoes.length; i++) {
        }
        double[] ultimaColunaRestricoes = criaColunaRestricoes(matrizSimplex[0].length - 1, matrizSimplex);
        for (int i = 0; i < ultimaColunaRestricoes.length; i++) {
        }
        int indiceLinhaPivot = encontraLinhaPivot(colunaPivotRestricoes, ultimaColunaRestricoes);
        indicesDoPivot[0] = indiceLinhaPivot;

        return indicesDoPivot;
    }

    public static int encontraColunaPivot(double[] primeiraLinha, double[][] matrizSimplex) {

        double menor = matrizSimplex[0][0];
        int indiceColunaPivot = 0;
        for (int coluna = 1; coluna < matrizSimplex[0].length; coluna++) {
            if (matrizSimplex[0][coluna] < menor) {
                menor = matrizSimplex[0][coluna];
                indiceColunaPivot = coluna;
            }
        }
        return indiceColunaPivot;
    }

    public static double[] criaColunaRestricoes(int indiceColuna, double[][] matrizSimplex) {
        double[] colunaRestricoes = new double[matrizSimplex.length - 1];

        for (int linha = 1; linha < matrizSimplex.length; linha++) {
            colunaRestricoes[linha - 1] = matrizSimplex[linha][indiceColuna];
        }
        return colunaRestricoes;
    }

    public static int encontraLinhaPivot(double[] colunaPivotRestricoes, double[] ultimaColunaRestricoes) {
        double[] quocienteColunas
                = calculaQuocienteColunas(colunaPivotRestricoes, ultimaColunaRestricoes);
        int indiceLinhaPivot = getIndiceLinhaPivot(quocienteColunas);
        return indiceLinhaPivot;
    }

    public static double[] calculaQuocienteColunas(double[] colunaPivotRestricoes, double[] ultimaColunaRestricoes) {
        double[] quocienteColunas = new double[colunaPivotRestricoes.length];
        for (int linha = 0; linha < colunaPivotRestricoes.length; linha++) {
            if (colunaPivotRestricoes[linha] == 0) {
                colunaPivotRestricoes[linha] = -1;
            }
            quocienteColunas[linha] = ultimaColunaRestricoes[linha] / colunaPivotRestricoes[linha];

        }
        return quocienteColunas;
    }

    public static int getIndiceLinhaPivot(double[] quocienteColunas) {

        int linha = 0;
        int indiceLinhaPivot = -1;

        while (quocienteColunas[linha] < 0 & linha < quocienteColunas.length) {
            linha++;
        }

        if (linha == (quocienteColunas.length)) {
            System.out.println("Problema não tem solução. Todos os quocientes são negativos");
            indiceLinhaPivot = -1;

        } else if (quocienteColunas[linha] == 0) {
            indiceLinhaPivot = linha + 1;
        } else {
            double menor = quocienteColunas[linha];
            indiceLinhaPivot = linha + 1;

            for (int j = linha + 1; j < quocienteColunas.length; j++) {

                if (quocienteColunas[j] > 0 & quocienteColunas[j] < menor) {
                    menor = quocienteColunas[j];
                    indiceLinhaPivot = j + 1;
                }
            }
        }
        return indiceLinhaPivot;
    }

    public static void passarLinhaPivotParaUm(double valorDoPivot, int[] indicesDoPivot, double[] linhaPivot) {

        for (int coluna = 0; coluna < linhaPivot.length; coluna++) {
            linhaPivot[coluna] = linhaPivot[coluna] / valorDoPivot;
        }
    }

    public static void zerarElementosDaColunaPivot(int[] indicesDoPivot, int linha, double[] linhaMatriz, double[] linhaPivot) {

        double multiplicador = (-1) * linhaMatriz[indicesDoPivot[1]];
        for (int coluna = 0; coluna < linhaMatriz.length; coluna++) {

            linhaMatriz[coluna] = multiplicador * linhaPivot[coluna] + linhaMatriz[coluna];
        }
    }

    public static void passarLinhaPivotParaUm(int[] indicesDoPivot, double[][] matrizSimplex) {

        double valorDoPivot = matrizSimplex[indicesDoPivot[0]][indicesDoPivot[1]];

        for (int coluna = 0; coluna < matrizSimplex[indicesDoPivot[0]].length; coluna++) {

            matrizSimplex[indicesDoPivot[0]][coluna] = matrizSimplex[indicesDoPivot[0]][coluna] / valorDoPivot;
        }
    }

    public static void zerarElementosDaColunaPivot(int[] indicesDoPivot, int linha, double[][] matrizSimplex) {

        double multiplicador = (-1) * matrizSimplex[linha][indicesDoPivot[1]];
        for (int coluna = 0; coluna < matrizSimplex[0].length; coluna++) {

            matrizSimplex[linha][coluna]
                    = multiplicador * matrizSimplex[indicesDoPivot[0]][coluna] + matrizSimplex[linha][coluna];
        }
    }

}
