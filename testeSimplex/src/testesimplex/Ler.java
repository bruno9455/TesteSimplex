/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesimplex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author bruno
 */
public class Ler {

    
    public static String[] lerFicheiro(String caminhoDoFicheiroInput) {

        String[] linhas = null;

        File file = new File(caminhoDoFicheiroInput);

        if (file.exists()) {

            try {

                Scanner scanner = new Scanner(file);
                String texto = "";

                while (scanner.hasNextLine()) {

                    if (!texto.equals("")) {
                        texto += "#";//verifica se a linha do texto é vazia senão
                    }               //for acrescenta um # ao final do String texto

                    texto += scanner.nextLine().trim();//acrescenta a nova linha ao String texto 
                }
                scanner.close();    // Dinis, não falta aqui fechar o ficheiro??
                if (!texto.equals("")) {

                    linhas = texto.split("#");//envia para o array linhas o string texto com split #
                }

            } catch (FileNotFoundException fnfe) {

                System.out.println("Houve um problema ao ler o ficheiro.");
            }
        } else {
            System.out.println("não existe ficheiro de leitura");
            linhas = null;
        }
        for (int i=0;i<linhas.length;i++){
            linhas[i]=linhas[i].toLowerCase();
        }
        
        return linhas;
    }
     
}
