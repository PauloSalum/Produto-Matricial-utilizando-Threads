/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplicamatrizesthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulosalum
 */
public class MultiplicaMatrizesThreads {

    static int TAM;
    static int NUM_THREADS;
    static int numLinhas;

    public static void main(String[] args) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MultiplicaMatrizesThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
         * inicio dos parametros
         *
         */
        //TAM = 4;
        //TAM = 6;
        TAM = 5120;

        //NUM_THREADS = 2;
       //NUM_THREADS = 3;
        NUM_THREADS = Integer.valueOf(args[0]); 

        int[][] m1 = new int[TAM][TAM];//inicio uma nova Matriz com o tamanho que eu quero
        int[][] m2 = new int[TAM][TAM];//inicio uma nova Matriz com o tamanho que eu quero
        int[][] m3 = new int[TAM][TAM];//inicio uma nova Matriz com o tamanho que eu quero

        IniciarCalculo(m1, m2, m3);

        if (args.length > 1 && "p".equals(args[1])) {
            ImprimirMatriz(m1, m2, m3);
        }
    }

    private static void IniciarCalculo(int[][] m1, int[][] m2, int[][] m3) {

        List<Thread> threads = new ArrayList<>();//Crio uma lista onde as threads ser�o colocadas
        numLinhas = TAM / NUM_THREADS;// calculo o numero de linhas por thread

        /**
         * Povoar de Matrizes. Neste bloco iremos gerar os valores aleat�rios
         * das matrizes 1 e 2, e encheremos a matriz 3 com zeros. ao final
         * adicionamos cada thread iniciada a uma lista.
         *
         */
        for (int i = 0; i < NUM_THREADS; i++) {
            int posicao = i * numLinhas;
            Thread thread = new Thread(() -> {
                povoar(m1, posicao);
                povoarB(m2, posicao);
                povoarComZero(m3, posicao);
            });

            thread.start();
            threads.add(thread);
        }
        /**
         * Comando Join. comando utilizado para dizer ao sistema que n�o
         * prossiga com a execu��o do c�digo caso algum thread que tenha cido
         * iniciado com join n�o tenha terminado a sua execu��o isso garantir�
         * que o tempo final n�o ser� recebido at� que todas as threads sejam
         * executadas.
         *
         */
        threads.stream().forEach((Thread t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println("Erro");
            }
        });

        threads.clear();//limpo a lista de threads

        /**
         * Multiplica��o de Matrizes.
         *
         */
        for (int i = 0; i < NUM_THREADS; i++) {
            int posicao = i * numLinhas;
            Thread t = new Thread(() -> {

                Multiplicar(m1, m2, m3, posicao);// a posi��o � a posi��o da matriz 3 que ser� povoada por cada thread.
            });
            t.start();
            threads.add(t);
        }
        /**
         * Comando Join. comando utilizado para dizer ao sistema que n�o
         * prossiga com a execu��o do c�digo caso algum thread que tenha cido
         * iniciado com join n�o tenha terminado a sua execu��o isso garantir�
         * que o tempo final n�o ser� recebido at� que todas as threads sejam
         * executadas.
         *
         */
        threads.stream().forEach((Thread t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println("Erro");
            }
        });
    }

    /**
     * Fun��o utilizada para preencher a matriz recebida com numeros aleat�rios.
     *
     */
    private static void povoar(int[][] matriz, int posicao) {
        for (int i = posicao; i < posicao + numLinhas; i++) {
            for (int j = 0; j < TAM; j++) {
                matriz[i][j] = 10*(i+1)+(j+1);
            }
        }
    }
    
    /**
     * Fun��o utilizada para preencher a matriz recebida com numeros aleat�rios.
     *
     */
    private static void povoarB(int[][] matriz, int posicao) {
        int numero = 10;
        for (int i = posicao; i < posicao + numLinhas; i++) {
            for (int j = 0; j < TAM; j++) {
                matriz[i][j] = 11*(i+1)+(j+1);
            }
        }
    }

    /**
     * Fun��o utilizada para preencher a matriz recebida com zeros.
     *
     */
    private static void povoarComZero(int[][] matriz, int posicao) {
        for (int i = posicao; i < posicao + numLinhas; i++) {
            for (int j = 0; j < TAM; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    /**
     * Recebe as 3 matrizes e a posi��o de calculo das mesmas.
     *
     */
    private static void Multiplicar(int[][] m1, int[][] m2, int[][] m3, int posicao) {
        for (int i = posicao; i < posicao + numLinhas; i++) {
            for (int j = 0; j < TAM; j++) {
                m3[i][j] = CalcularMatriz(m1, m2, i, j);//efetua o calculo de linha x coluna passando aposi��o inicial das mesmas.
            }
        }
    }

    private static int CalcularMatriz(int[][] m1, int[][] m2, int i, int j) {
        int retorno = 0;
        for (int k = 0; k < TAM; k++) {
            retorno += m1[i][k] * m2[k][j];//soma da multiplica��o de cada linha por cada coluna.
        }
        return retorno;
    }

    private static void ImprimirMatriz(int[][] m1, int[][] m2, int[][] m3) {
        System.out.println("Matriz 1");
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                System.out.print(m1[i][j] + ", ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("Matriz 2");
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                System.out.print(m2[i][j] + ", ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("Matriz 3");
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                System.out.print(m3[i][j] + ", ");
            }
            System.out.println("");
        }
    }

}
