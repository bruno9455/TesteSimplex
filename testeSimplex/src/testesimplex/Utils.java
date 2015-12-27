/*
 * Classe com métodos utilitários
 */
package testesimplex;

/**
 *
 * @author Grupo 9
 */
public class Utils {
        
    /**
     * Devolve index do menor numero do array
     * @param array
     * @return 
     */
    public static int encontarMenorNumEmArray(double[] array){
        
        int output = -1;
        
        return output;
    }
    
    /**
     * Devolve index do menor positivo de um numero do array
     * @param array
     * @return 
     */
    public static int encontrarMenorNumPositivoEmArray(double[] array){
        
        int output = -1;
        
        return output;
    }
    
    /**
     * Torna o valor da coluna pivot do array "linha" a 0, pressupondo que este
     * Devolve a linha resultante da operação, devidamente calculada
     * @param linhaPivot
     * @param linha
     * @param indexDaColunaPivot
     * @return 
     */
    public static double[] neutralizarLinha(double[] linhaPivot, 
            double[] linha, int indexDaColunaPivot){
    
        double[] output = null;
        
        return output;
    }
    
    /**
     * REMOVER
     * @param a
     * @param b
     * @return 
     */
    public static int somaDeValores(int a, int b){
        
        return a+b;
        
    }
        
    public static boolean arrayContemValor(String val, String[] array){
    
        boolean output = false;
        
        for (String str : array) {
            if (str.equals(val)) {
                output = true;
                break;
            }
        }
        
        return output;
    }

    public static String[][] expandirArray(String[][] output) {
        if (output == null) {
            output = new String[1][3];
        } else {
            int tamanho = output.length;
            String[][] tempArray = new String[tamanho + 1][3];
            System.arraycopy(output, 0, tempArray, 0, tamanho);
            output = tempArray;
        }
        return output;
    }
}
