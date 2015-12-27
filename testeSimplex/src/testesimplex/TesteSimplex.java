/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesimplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.Formatter;

/**
 *
 * @author bruno
 */
public class TesteSimplex {

    public static Formatter escrever = new Formatter(System.out);
    public static Scanner ler = new Scanner(System.in);
    public static String[] linhas;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String caminhoDoFicheiroDeInput = ("teste\\input.txt");
        String caminhoDoFicheiroDeOutput = ("teste\\output.txt");
//        String caminhoDoFicheiroDeInput = args[0];
//        String caminhoDoFicheiroDeOutput = args[1];
        linhas = testesimplex.Ler.lerFicheiro(caminhoDoFicheiroDeInput);
        double[][] matrizSimplex;

        if (linhas == null) {
            System.out.println("não há dados acessiveis");
        } else {
            testesimplex.Escrever.ImprimirDadosIniciais(linhas, caminhoDoFicheiroDeOutput);
            matrizSimplex = testesimplex.CriarMatriz.processarDados(linhas);
//            testesimplex.Escrever.ImprimirMatrizes(matrizSimplex,0);

            testesimplex.ResolverSimplex.executarSimplex(matrizSimplex, caminhoDoFicheiroDeOutput, linhas);
        }
    }

}