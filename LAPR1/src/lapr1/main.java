package lapr1;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Scanner;
import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

public class main {

    static Formatter out = null;

    /**
     * Procura uma string num vetor;
     *
     * @param vec vetor onde procurar;
     * @param nome string a procurar;
     * @return posição da string caso a encontre e -1 caso não a encontre.
     */
    public static int procurarStringNoVetor(String[] vec, String nome) {
        int resultado = -1;
        for (int i = 0; i < vec.length; i++) {
            if (nome.equals(vec[i])) {
                resultado = i;
            }
        }
        return resultado;
    }

    /**
     * Obtém o número de colunas do ficheiro;
     *
     * @param nomeFich ficheiro para contar as linhas;
     * @return número de colunas da primeira linha;
     * @throws FileNotFoundException
     */
    public static int colunasPrimeiraLinha(String nomeFich) throws FileNotFoundException {
        int colunas;
        Scanner fInput = new Scanner(new File("Ficheiros/" + nomeFich));
        String linha = fInput.nextLine();
        String tmp[] = linha.split(configs.SEPARADOR_FICH);
        colunas = tmp.length;

        fInput.close();
        return colunas;
    }

    /**
     * Conta o número de linhas num ficheiro;
     *
     * @param nomeFich nome do ficheiro do qual se vão contar as linhas;
     * @return número de linhas existentes;
     * @throws FileNotFoundException
     */
    public static int contarLinhas(String nomeFich) throws FileNotFoundException {
        int cont = 0;
        Scanner fInput = new Scanner(new File("Ficheiros/" + nomeFich));
        while (fInput.hasNextLine()) {
            if (fInput.nextLine().trim().length() > 0) {
                cont++;
            }
        }
        fInput.close();

        return cont;
    }

    /**
     * Guarda num vetor a data atual;
     *
     * @param data vetor de inteiros para guardar a data atual;
     */
    public static void diaMesAnoAtual(int[] data) {
        Calendar hoje = Calendar.getInstance();
        data[0] = hoje.get(Calendar.DAY_OF_MONTH);
        data[1] = hoje.get(Calendar.MONTH) + 1;
        data[2] = hoje.get(Calendar.YEAR);
    }

    /**
     * Lê ramos.
     *
     * @param nLinhasRamos número de linhas de ramos que contém o ficheiro;
     * @param dadosRamos matriz de strings para guardar os dados dos ramos;
     * @param colunasRamos número de colunas do ficheiro dos ramos;
     * @param fileRamos nome do ficheiro;
     * @param orientado flag que indica se o tipo de grafo é ou não orientado;
     * @throws FileNotFoundException
     */
    public static void lerRamos(int nLinhasRamos, String[][] dadosRamos, int colunasRamos, String fileRamos, boolean orientado) throws FileNotFoundException {
        //RAMOS
        Scanner InputRamos = new Scanner(new File("Ficheiros/" + fileRamos));
        String linha1 = InputRamos.nextLine();
        int i = 0;
        while (InputRamos.hasNextLine() && i < nLinhasRamos) {
            String linha = InputRamos.nextLine();
            if (!linha.trim().equals("") && !linha.equals(linha1)) {

                guardarDados(linha, dadosRamos, i, colunasRamos, fileRamos);
                String vec[] = linha.split(configs.SEPARADOR_FICH);

                if (verifRamosIguais(vec, dadosRamos, i) == true && orientado == false) {
                    System.out.println("Existem dados repetidos no ficheiro '" + fileRamos + "' na linha " + (i + 2));
                }
                i++;
            }
        }
        InputRamos.close();
    }

    /**
     * Verifica se os ramos são iguais.
     *
     * @param linha vetor de strings com a linha para verificar;
     * @param dadosRamos matriz de strings que guarda os dados dos ramos;
     * @param nLinhasRamos posição da linha a verificar;
     * @return true caso ramos sejam iguais, false caso ramos sejam diferentes;
     */
    public static boolean verifRamosIguais(String[] linha, String[][] dadosRamos, int nLinhasRamos) {
        boolean resultado = false;
        for (int i = 0; i < nLinhasRamos; i++) {
            if ((dadosRamos[i][0].equals(linha[0]) && dadosRamos[i][1].equals(linha[1])) || (dadosRamos[i][1].equals(linha[0]) && dadosRamos[i][0].equals(linha[1]))) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * Guarda os dados de um ficheiro numa matriz.
     *
     * @param linha linha a guardar;
     * @param dados matriz de strings que guarda os dados;
     * @param i posição da linha a guardar;
     * @param maxCampos máximo de campos a preencher na matriz;
     * @param nomeFich nome do ficheiro;
     */
    public static void guardarDados(String linha, String[][] dados, int i, int maxCampos, String nomeFich) {
        String[] tmp = linha.split(configs.SEPARADOR_FICH);
        int cont = 0;
        for (int j = 0; j < tmp.length; j++) {
            if (!tmp[j].equals("")) {
                cont++;
            }
        }
        if (cont == maxCampos) {
            dados[i] = tmp;
        } else {
            System.out.println("Os dados não estão introduzidos corretamente no ficheiro: " + nomeFich);
            System.exit(0);
        }
    }

    /**
     * Remove a primeira linha de uma matriz;
     *
     * @param dados matriz de strings que guarda os dados;
     * @param nLinhas número de linhas que contém o ficheiro;
     * @return novo número de linhas existentes no ficheiro;
     */
    public static int removerPrimeiraLinha(String[][] dados, int nLinhas) {
        for (int i = 0; i < nLinhas - 1; i++) {
            dados[i] = dados[i + 1];
        }
        return (nLinhas - 1);
    }

    /**
     * Remove o primeiro caracter de todas as linhas;
     *
     * @param dadosRamos matriz de strings que guarda os dados dos ramos;
     * @param nLinhasRamos número de linhas de ramos que contém o ficheiro;
     */
    public static void removerPrimeiroChar(String[][] dadosRamos, int nLinhasRamos) {
        for (int i = 0; i < nLinhasRamos; i++) {
            dadosRamos[i][0] = dadosRamos[i][0].replace("s", "");
            dadosRamos[i][1] = dadosRamos[i][1].replace("s", "");
        }
    }

    /**
     * Imprime uma matriz de string.
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param matriz matriz de strings;
     * @param valor número de linhas da matriz;
     * @throws FileNotFoundException
     */
    public static void imprimirMatrizString(Formatter out, String[][] matriz, int valor) throws FileNotFoundException {
        for (int i = 0; i < valor; i++) {
            if (i > 0) {
                out.format("%n");
            }
            for (int j = 0; j < matriz[0].length; j++) {
                out.format("%s", matriz[i][j] + " | ");
            }
        }
    }

    /**
     * Preenche a nova matriz adjacências;
     *
     * @param matrizAdj matriz de doubles para guardar as ligações entre os nós;
     * @param matrizRamos matriz de Strings que guarda os dados dos ramos;
     * @param nLinhasRamos número de linhas de ramos que contém o ficheiro;
     * @param orientado flag que indica se o tipo de grafo é ou não orientado;
     *
     */
    public static void preencherMatrizAdjacencias(double matrizAdj[][], String matrizRamos[][], int nLinhasRamos, boolean orientado) {
        for (int i = 0; i < nLinhasRamos; i++) {
            int p = Integer.parseInt(matrizRamos[i][0]) - 1;
            int s = Integer.parseInt(matrizRamos[i][1]) - 1;

            if (p != s) {
                matrizAdj[s][p] = Double.parseDouble(matrizRamos[i][2]);
                if (orientado == false) {
                    matrizAdj[p][s] = Double.parseDouble(matrizRamos[i][2]);
                }
            } else {
                System.out.println("A linha " + (i + 3) + " apresenta uma relação de 'self-loop'. Essa ligação não será tomada em conta.");
            }
        }
    }

    /**
     * Imprime uma matriz de doubles;
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param matriz matriz de doubles;
     * @param valor número de linhas da matriz;
     */
    public static void imprimirMatrizDouble(Formatter out, double[][] matriz, int valor) {
        for (int i = 0; i < valor; i++) {
            if (i > 0) {
                out.format("%n");
            }
            for (int j = 0; j < matriz[0].length; j++) {
                out.format("%s", matriz[i][j] + " | ");
            }
        }
    }

    /**
     * Lê os nós através de um ficheiro;
     *
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param dadosNos matriz de strings para guardar os dados dos nós;
     * @param colunasNos número de colunas do ficheiro dos ramos;
     * @param fileNos nome do ficheiro para leitura;
     * @throws FileNotFoundException
     */
    public static void lerNos(int qntNos, String[][] dadosNos, int colunasNos, String fileNos) throws FileNotFoundException {
        //NOS
        Scanner InputNos = new Scanner(new File("Ficheiros/" + fileNos));
        int i = 0;
        while (InputNos.hasNextLine() && i < qntNos) {
            String linha = InputNos.nextLine();
            if (!linha.trim().equals("")) {
                guardarDados(linha, dadosNos, i, colunasNos, fileNos);
                if (i > 0) {
                    if ((procurarIDNos(dadosNos, i, dadosNos[i][0])) != -1) {
                        System.out.println("Existe mais do que um nó com o mesmo ID.");
                        System.exit(0);
                    }
                }
                i++;
            }
        }
        InputNos.close();
    }

    /**
     * Verifica se o ficheiro tem menos de 200linhas.
     *
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param nomeFich nome do ficheiro;
     */
    public static void verifPossivel(int qntNos, String nomeFich) {
        if (qntNos > 200) {
            System.out.println("Não é possivel executar o programa visto que o arquivo '" + nomeFich + "' tem mais de 200 linhas.");
            System.exit(0);
        }
    }

    /**
     * Procura o "ID" dos nós.
     *
     * @param dadosNos matriz de strings que guarda os dados dos nós;
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param valor string a procurar;
     * @return posição do id caso o encontre, -1 caso não o encontre.
     */
    public static int procurarIDNos(String[][] dadosNos, int qntNos, String valor) {
        int resultado = -1;
        for (int i = 0; i < qntNos - 1; i++) {
            if (dadosNos[i][0].equals(valor)) {
                resultado = i;
            }
        }
        return resultado;
    }

    /**
     * Soma todos os valores de uma linha;
     *
     * @param matrizAdj de doubles que guarda as ligações entre os nós;
     * @param linha linha a somar;
     * @return soma obtida;
     */
    public static double somaLinha(double matrizAdj[][], int linha) {
        double soma = 0;
        for (int j = 0; j < matrizAdj[0].length; j++) {
            soma = soma + matrizAdj[linha][j];
        }
        return soma;
    }

    /**
     * Imprime cabeçalho para uma secção;
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param texto nome do cabeçalho;
     */
    public static void cabecalhoGeral(Formatter out, String texto) {
        out.format("%n");
        for (int i = 0; i < texto.length(); i++) {
            out.format("=");
        }
        out.format("%n");
        out.format(texto);
        out.format("%n");
        for (int i = 0; i < texto.length(); i++) {
            out.format("=");
        }
        out.format("%n");
    }

    /**
     * Calcula o grau dos nós.
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @param dadosNos matriz de strings que guarda dados dos nos.
     */
    public static double[] grauDeNo(Formatter out, double matrizAdj[][], String[][] dadosNos) {
        double[] grauNo = new double[dadosNos.length];

        double soma = 0;
        for (int i = 0; i < matrizAdj.length; i++) {
            soma = somaLinha(matrizAdj, i);

            grauNo[i] = soma;

            out.format(dadosNos[i][1] + ": " + soma);
            out.format("%n");
        }
        return grauNo;
    }

    /**
     * Determina a matriz do valor próprio;
     *
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @return matriz de valores próprios.
     */
    public static Matrix matrizValorProprio(double matrizAdj[][]) {
        Matrix MatrizAdjV = new Basic2DMatrix(matrizAdj);
        EigenDecompositor eigenD = new EigenDecompositor(MatrizAdjV);
        Matrix[] mattD = eigenD.decompose();
        return (mattD[1]);
    }

    /**
     * Calcula o maior valor próprio;
     *
     * @param mattD recebe uma matriz;
     * @return coluna do maior valor próprio.
     */
    public static int calcularValorProprio(Matrix mattD) {
        double maior = mattD.get(0, 0);
        int resultado = 0;

        for (int i = 0; i < mattD.rows(); i++) {
            if (mattD.get(i, i) > maior) {
                maior = mattD.get(i, i);
                resultado = i;
            }
        }
        return resultado;
    }

    /**
     * Converte um double para apenas um número com três casa decimais;
     *
     * @param num número a converter;
     * @return número com apenas três casas decimais;
     */
    public static double tresCasasDecimais(double num) {
        num = num * 1000;
        num = Math.round(num);
        num = num / 1000;
        return num;
    }

    /**
     * Converte um double para apenas um número com quatro casa decimais;
     *
     * @param num número a converter;
     * @return número com apenas quatro casas decimais;
     */
    public static double quatroCasasDecimais(double num) {
        num = num * 10000;
        num = Math.round(num);
        num = num / 10000;
        return num;
    }

    /**
     * Calcula o grau médio;
     *
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @return grau médio(média da soma de nós);
     */
    public static double grauMedio(double matrizAdj[][]) {
        double somaTotal = 0;
        for (int i = 0; i < matrizAdj.length; i++) {
            double soma = somaLinha(matrizAdj, i);
            somaTotal = somaTotal + soma;
        }
        return (somaTotal / (double) matrizAdj.length);
    }

    /**
     * Calcular densidade da rede;
     *
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @return densidade da rede.
     */
    public static double calculoDensidade(double matrizAdj[][]) {
        double soma = somaMatrizDouble(matrizAdj);
        double nElemMatrizAdj = (matrizAdj.length * (matrizAdj.length - 1)) / 2;
        double densidade = soma / nElemMatrizAdj;
        return densidade;
    }

    /**
     * Calcula matriz do vetor próprio.
     *
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @return matriz de vetores próprios.
     */
    public static Matrix matrizVetorProprio(double matrizAdj[][]) {
        Matrix MatrizAdjV = new Basic2DMatrix(matrizAdj);
        EigenDecompositor eigenD = new EigenDecompositor(MatrizAdjV);
        Matrix[] mattD = eigenD.decompose();
        return (mattD[0]);
    }

    /**
     * Imprime e guarda num vetor a centralidade.
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param matriz recebe uma matriz de vetores próprios;
     * @param pos coluna do maior vetor próprio;
     * @param dadosNos matriz de strings que guarda dados dos nós;
     * @return vetor de doubles com a centralidade.
     */
    public static double[] centralidade(Formatter out, Matrix matriz, int pos, String[][] dadosNos) {
        double[] centr = new double[matriz.rows()];

        double resultado;
        for (int i = 0; i < matriz.rows(); i++) {
            resultado = calcularCentralidade(matriz, pos, i);
            centr[i] = resultado;
        }
        return centr;
    }

    /**
     * Imprime resultado da centralidade.
     *
     * @param dadosNos matriz de strings que guarda dados dos nos;
     * @param vec vetor de doubles onde procurar;
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param orientado flag que indica se o tipo de grafo é ou não orientado.
     */
    public static void imprimeResultadoCentr(String[][] dadosNos, double[] vec, Formatter out, boolean orientado) {
        for (int j = 0; j < vec.length; j++) {
            if (orientado == false) {
                out.format("%s", dadosNos[j][1] + ": " + quatroCasasDecimais(vec[j]));
            } else {
                out.format("%s", dadosNos[j][1] + ": " + vec[j]);
            }
            out.format("%n");
        }
    }

    /**
     * Calcula a centralidade.
     *
     * @param matriz recebe uma matriz de vetores próprios;
     * @param pos coluna do maior vetor próprio;
     * @param i linha a percorrer;
     * @return centralidade.
     */
    public static double calcularCentralidade(Matrix matriz, int pos, int i) {
        double resultado;
        resultado = Math.abs(matriz.get(i, pos));
        return resultado;
    }

    /**
     * Soma números de ramos da rede;
     *
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @return soma dos números de ramos da rede;
     */
    public static double somaMatrizDouble(double matrizAdj[][]) {
        double soma = 0;
        for (int i = 0; i < matrizAdj.length; i++) {
            for (int j = 0; j < matrizAdj[0].length; j++) {
                if (matrizAdj[i][j] != 0) {
                    soma++;
                }
            }
        }
        return soma / 2;
    }

    /**
     * Imprime por ordem decrescente as potências das matrizes até ao k
     * (excetuando o 1);
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @param num ordem da potência;
     * @return matriz resultado da ordem de potência pedida;
     */
    public static double[][] potenciaMatriz(Formatter out, double[][] matrizAdj, int num) {
        double[][] tmp = matrizAdj;
        for (int k = 0; k < num; k++) {
            if (k == 0) {
                cabecalhoPotencia(out, k + 1);
                imprimirMatrizDouble(out, matrizAdj, matrizAdj.length);
            } else {
                tmp = multiplicacao(matrizAdj, tmp);
                cabecalhoPotencia(out, k + 1);
                imprimirMatrizDouble(out, tmp, tmp.length);
            }
        }
        return tmp;
    }

    /**
     * Multiplica duas matrizes;
     *
     * @param matrizA matriz de doubles;
     * @param matrizB matriz de doubles;
     * @return matriz resultante da multiplicação.
     */
    public static double[][] multiplicacao(double matrizA[][], double matrizB[][]) {
        double[][] matrizNova = new double[matrizA.length][matrizB[0].length];

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizB[0].length; j++) {
                for (int k = 0; k < matrizA[0].length; k++) {
                    matrizNova[i][j] = matrizNova[i][j] + (matrizA[i][k] * matrizB[k][j]);
                }
            }
        }
        return matrizNova;
    }

    /**
     * Imprime para a potência.
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param num ordem da potência;
     */
    public static void cabecalhoPotencia(Formatter out, int num) {
        out.format("%n");
        out.format("%n");
        out.format("%s", "====================");
        out.format("%n");
        out.format("%s", "POTÊNCIA DE ORDEM " + num);
        out.format("%n");
        out.format("%s", "====================");
        out.format("%n");
    }

    /**
     * Verifica se o grafo é ou não orientado.
     *
     * @param fileRamos nome do ficheiro;
     * @param orientado flag que indica se o tipo de grafo é orientado;
     * @return true caso o grafo seja orientado e false caso não o seja.
     * @throws FileNotFoundException
     */
    public static boolean verificarOrientado(String fileRamos, boolean orientado) throws FileNotFoundException {
        Scanner InputRamos = new Scanner(new File("Ficheiros/" + fileRamos));
        String linha = InputRamos.nextLine();
        String verif = linha.substring(linha.lastIndexOf(":") + 1).trim();
        if (verif.equalsIgnoreCase("oriented")) {
            orientado = true;
        }
        return orientado;
    }

    /**
     * Calcula o grau de saída.
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @param dadosNos matriz de strings que guarda dados dos nos;
     * @param grauSaida vetor de doubles para guardar os graus de saída.
     */
    public static double[] grauDeSaida(Formatter out, double matrizAdj[][], String[][] dadosNos) {
        double[] grauSaida = new double[dadosNos.length];

        double soma = 0;
        for (int i = 0; i < matrizAdj.length; i++) {
            soma = somaColuna(matrizAdj, i);
            out.format(dadosNos[i][1] + ": " + soma);
            grauSaida[i] = soma;
            out.format("%n");
        }
        return grauSaida;
    }

    /**
     * Soma a coluna pedida.
     *
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @param coluna coluna a somar;
     * @return resultado da soma.
     */
    public static double somaColuna(double matrizAdj[][], int coluna) {
        double soma = 0;
        for (int j = 0; j < matrizAdj.length; j++) {
            soma = soma + matrizAdj[j][coluna];
        }
        return soma;
    }

    /**
     * Preenche a matriz A.
     *
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param grauSaida vetor de doubles que guarda os graus de saída.
     * @param matrizAdj matriz de doubles que guarda as ligações entre os nós;
     * @param matrizA matriz a ser preenchida;
     */
    public static void preencherMatrizA(int qntNos, double[] grauSaida, double[][] matrizAdj, double[][] matrizA) {
        for (int i = 0; i < matrizAdj.length; i++) {
            for (int j = 0; j < matrizAdj.length; j++) {
                if (matrizAdj[i][j] == 1.0) {
                    matrizA[i][j] = tresCasasDecimais(1 / grauSaida[j]);
                } else {
                    if (grauSaida[j] == 0) {
                        matrizA[i][j] = tresCasasDecimais(1 / qntNos);
                    } else {
                        matrizA[i][j] = 0;
                    }
                }
            }
        }
    }

    /**
     * Preenche uma matriz com 1.
     *
     * @param matrizDe1 matriz de doubles a peencher;
     */
    public static void preencherMatrizCom1(double[][] matrizDe1) {
        for (int i = 0; i < matrizDe1.length; i++) {
            for (int j = 0; j < matrizDe1[0].length; j++) {
                matrizDe1[i][j] = 1.0;
            }
        }
    }

    /**
     * Caclula a matriz que reprensenta a rede social.
     *
     * @param matrizA matriz de doubles com coeficientes definidos em
     * preencheMatrizA.
     * @param d constante (damping factor);
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param matrizDe1 matriz de doubles constituída por 1;
     * @return matriz M.
     */
    public static double[][] calcularM(double matrizA[][], double d, int qntNos, double matrizDe1[][]) {
        calcularDA(d, matrizA);

        double[][] resultado = calcularRestante(d, qntNos, matrizDe1);
        double[][] matrizM = somarMatrizes(matrizA, resultado);

        return matrizM;
    }

    /**
     * Calcula a multiplicação da constante d pela matriz A.
     *
     * @param d constante (damping factor);
     * @param matrizA matriz de doubles com coeficientes definidos em
     * preencheMatrizA.
     */
    public static void calcularDA(double d, double[][] matrizA) {
        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[0].length; j++) {
                matrizA[i][j] = matrizA[i][j] * d;
            }
        }
    }

    /**
     * Calcula parte da expressão que permite calcular a matriz M.
     *
     * @param d constante (damping factor);
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param matrizDe1 matriz de doubles constituída por 1.
     * @return matriz de doubles resultado das operações indicadas.
     */
    public static double[][] calcularRestante(double d, int qntNos, double matrizDe1[][]) {
        double[][] resultado = new double[qntNos][qntNos];
        double valor = (1 - d) / qntNos;

        for (int i = 0; i < resultado.length; i++) {
            for (int j = 0; j < resultado.length; j++) {
                resultado[i][j] = valor * matrizDe1[i][j];
            }
        }
        return resultado;
    }

    /**
     * Soma ambas as matizes da opeação para calcular a matriz M (matriz
     * resultado de calcularDA e matriz resultado de calcularRestante).
     *
     * @param matrizA matriz de doubles resultado de calcularDA;
     * @param matrizB matriz de doubles resultado de calcularRestante.
     * @return resultado da adição, correspondente à MatrizM.
     */
    public static double[][] somarMatrizes(double[][] matrizA, double[][] matrizB) {
        double[][] resultado = new double[matrizA.length][matrizA.length];

        for (int i = 0; i < matrizB.length; i++) {
            for (int j = 0; j < matrizB.length; j++) {
                resultado[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }
        return resultado;
    }

    /**
     * Calcula e imprime o Page Rank.
     *
     * @param matrizM matriz que representa a rede social.
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param k número de iterações.
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param dadosNos matriz de strings que guarda dados dos nos.
     * @return vetor de doubles correspondente ao Page Rank.
     */
    public static double[] calcularImprimirXK(double[][] matrizM, int qntNos, int k, Formatter out, String[][] dadosNos) {
        double[][] x = new double[qntNos][1];
        double[][] resultado = new double[qntNos][1];
        double[] xr = new double[qntNos];

        preencherMatrizCom1(x);

        for (int i = 0; i < k; i++) {
            if (i == 0) {
                resultado = multiplicacao(matrizM, x);
                cabecalhoGeral(out, "MATRIZ X_" + (i + 1));
                imprimeXK(dadosNos, resultado, out);
            } else {
                resultado = multiplicacao(matrizM, resultado);
                cabecalhoGeral(out, "MATRIZ X_" + (i + 1));
                if (i == (k - 1)) {
                    xr = imprimeXK(dadosNos, resultado, out);
                } else {
                    imprimeXK(dadosNos, resultado, out);
                }
                out.format("\n");
            }
        }
        return xr;
    }

    /**
     * Calcula e imprime o Page Rank.
     *
     * @param out usado para imprimir para ficheiro ou ecrã;
     * @param resultado matriz de doubles resultado do número de iterações
     * pedidas;
     * @param dadosNos matriz de strings que guarda dados dos nos.
     * @return Page Rank.
     */
    public static double[] imprimeXK(String[][] dadosNos, double[][] resultado, Formatter out) {
        double vec[] = new double[resultado.length];

        for (int j = 0; j < resultado.length; j++) {
            vec[j] = resultado[j][0];
            out.format("%s", dadosNos[j][1] + ": " + resultado[j][0]);
            out.format("%n");
        }
        return vec;
    }

    /**
     * Imprime o xk os nós.
     *
     * @param dadosNos matriz de strings que guarda dados dos nos;
     * @param resultados matriz de doubles resultado do número de iterações
     * pedidas;
     * @param qntNos número de linhas de nós que contém o ficheiro;
     * @param out usado para imprimir para ficheiro ou ecrã;
     */
    public static void imprimirXRNos(String[][] dadosNos, double[][] resultados, int qntNos, Formatter out) {
        for (int i = 0; i < qntNos; i++) {
            out.format(dadosNos[i][1] + ": " + resultados[i][0]);
            out.format("\n");
        }
    }

    /**
     * Abre o link do nó mais popular.
     *
     * @param dadosNos matriz de strings que guarda dados dos nós;
     * @param no nó do link a abrir.
     */
    public static void abrirLinkNo(String[][] dadosNos, int no) {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI(dadosNos[no][4].trim());
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Procura maior resultado num vetor de doubles.
     *
     * @param vec vetor de doubles onde procurar.
     * @return o maior resultado no vetor.
     */
    public static int procurarMaiorVecDouble(double[] vec) {
        double maior = vec[0];
        int resultado = 0;
        for (int i = 1; i < vec.length; i++) {
            if (vec[i] > maior) {
                resultado = i;
                maior = vec[i];
            }
        }
        return resultado;
    }

    /**
     * Soma todos os elementos de um vetor de doubles.
     *
     * @param vec vetor de doubles onde procurar.
     * @return soma dos elementos.
     */
    public static double somaVetorDouble(double[] vec) {
        double soma = 0;
        for (int i = 0; i < vec.length; i++) {
            soma = soma + vec[i];
        }
        return soma;
    }

    /**
     * Normaliza o vetor.
     *
     * @param vec vetor de doubles onde procurar.
     * @param soma soma de todos os elementos do vetor em questão.
     */
    public static void normalizarVec(double[] vec, double soma) {
        for (int i = 0; i < vec.length; i++) {
            vec[i] = vec[i] / soma;
        }
    }

    public static void verificarD(double d) {
        if ((d > 1) || (d < 0)) {
            System.out.println("O valor de D deve ser definido entre 0 e 1.");
            System.exit(0);
        }
    }
}
