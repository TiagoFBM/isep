package lapr1;

import static lapr1.main.*;
import org.la4j.Matrix;

public class testClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Teste a: removerPrimeiroChar");
        boolean teste01 = testeRemoverPrimeiroChar();
        imprimirResultado(teste01);

        System.out.println("Teste a: removerPrimeiraLinha");
        boolean teste02 = testeRemoverPrimeiraLinha();
        imprimirResultado(teste02);

        System.out.println("Teste a: guardarDados");
        boolean teste03 = testeGuardarDados();
        imprimirResultado(teste03);

        System.out.println("Teste nº1 a: preencherMatrizAdjacencias para grafo não orientado");
        boolean teste04 = teste1PreencherMatrizAdjacencias();
        imprimirResultado(teste04);

        System.out.println("Teste nº2 a: preencherMatrizAdjacencias para grafo orientado");
        boolean teste22 = teste2PreencherMatrizAdjacencias();
        imprimirResultado(teste22);

        System.out.println("Teste a: somaLinha");
        boolean teste05 = testeSomaLinha();
        imprimirResultado(teste05);

        System.out.println("Teste a: matrizValorProprio e calcularValorProprio");
        boolean teste06 = testeCalcularValorProprio();
        imprimirResultado(teste06);

        System.out.println("Teste a: gruaMedio");
        boolean teste07 = testeGrauMedio();
        imprimirResultado(teste07);

        System.out.println("Teste a: somaMatrizDouble");
        boolean teste08 = testeSomaMatrizDouble();
        imprimirResultado(teste08);

        System.out.println("Teste a: calculoDensidade");
        boolean teste09 = testeCalculoDensidade();
        imprimirResultado(teste09);

        System.out.println("Teste a: multiplicacao");
        boolean teste10 = testeMultiplicacao();
        imprimirResultado(teste10);

        System.out.println("Teste a: verifRamosIguais");
        boolean teste11 = testeVerifRamosIguais();
        imprimirResultado(teste11);

        System.out.println("Teste a: procurarIDNos");
        boolean teste12 = testeProcurarIDNos();
        imprimirResultado(teste12);

        System.out.println("Teste a: tresCasasDecimais");
        boolean teste13 = testeTresCasasDecimais();
        imprimirResultado(teste13);

        System.out.println("Teste a: procurarMaiorVecDouble");
        boolean teste14 = testeProcurarMaiorVecDouble();
        imprimirResultado(teste14);

        System.out.println("Teste a: somarMatrizes");
        boolean teste15 = testeSomarMatrizes();
        imprimirResultado(teste15);

        System.out.println("Teste a: calcularDA");
        boolean teste16 = testeCalcularDA();
        imprimirResultado(teste16);

        System.out.println("Teste a: preencherMatrizCom1");
        boolean teste17 = testePreencherMatrizCom1();
        imprimirResultado(teste17);

        System.out.println("Teste a: somaColuna");
        boolean teste18 = testeSomaColuna();
        imprimirResultado(teste18);

        System.out.println("Teste a: calcularRestante");
        boolean teste19 = testeCalcularRestante();
        imprimirResultado(teste19);

        System.out.println("Teste a: calcularM");
        boolean teste20 = testeCalcularM();
        imprimirResultado(teste20);

        System.out.println("Teste a: procurarStringNoVetor");
        boolean teste21 = testeProcurarStringNoVetor();
        imprimirResultado(teste21);
        
        System.out.println("Teste a: normalizarVec");
        boolean teste23 = testeNormalizarVec();
        imprimirResultado(teste23);

        System.out.println("Teste a: testeSomaVetorDouble");
        boolean teste24 = testeSomaVetorDouble();
        imprimirResultado(teste24);
    }

    /**
     * Testa método removerPrimeiroChar.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeRemoverPrimeiroChar() {
        String matrix[][] = {{"steste01", "s02", "sTESTE03"},
        {"S04", ".s05", "s06"}};

        String esperado[][] = {{"tete01", "02", "sTESTE03"},
        {"S04", ".05", "s06"}};
        removerPrimeiroChar(matrix, matrix.length);

        imprimirEsperadoObtidoString(esperado, matrix);

        return compararMatrizString(esperado, matrix);
    }

    /**
     * Testa método removerPrimeiraLinha.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeRemoverPrimeiraLinha() {
        String matrix[][] = {{"Linha a", " remover", "."},
        {"Linha", " a ", "Subir", "1"}, {"Linha", " a ", "Subir", "2"}};
        String esperado[][] = {{"Linha", " a ", "Subir", "1"},
        {"Linha", " a ", "Subir", "2"}, {"Linha", " a ", "Subir", "2"}};
        int nLinhas = 3, nLinhasEsp = 2;
        nLinhas = removerPrimeiraLinha(matrix, nLinhas);

        imprimirEsperadoObtidoString(esperado, matrix);

        if (nLinhas == nLinhasEsp) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (!matrix[i][j].equals(esperado[i][j])) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Testa método guardarDados.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeGuardarDados() {
        String linha1 = "Primeira,linha,a,ser,copiada";
        String linha2 = "Linha,a,copiar,numero,2";
        String dados[][] = new String[2][5];
        String esperado[][] = {{"Primeira", "linha", "a", "ser", "copiada"},
        {"Linha", "a", "copiar", "numero", "2"}};
        guardarDados(linha1, dados, 0, 5, "nomeFich");
        guardarDados(linha2, dados, 1, 5, "nomeFich");

        imprimirEsperadoObtidoString(esperado, dados);

        return compararMatrizString(esperado, dados);
    }

    /**
     * Testa método preencherMatrizAdjacencias.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean teste1PreencherMatrizAdjacencias() {
        String ramos[][] = {{"1", "2", "1"}, {"1", "3", "1"}};
        double matAdj[][] = new double[3][3];
        double matAdjEsp[][] = {{0.0, 1.0, 1.0}, {1.0, 0.0, 0.0},
        {1.0, 0.0, 0.0}};
        preencherMatrizAdjacencias(matAdj, ramos, 2, false);

        imprimirEsperadoObtidoDouble(matAdjEsp, matAdj);

        return compararMatrizDouble(matAdjEsp, matAdj);
    }

    /**
     * Testa método SomaLinha.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeSomaLinha() {
        double matrix[][] = {{1.0, 5.0, 0.0, 3.0, 0.0}};
        double esperado = 9.0;
        double obtido = somaLinha(matrix, 0);

        System.out.printf("%n%s%f%n", "Resultado esperado: ", esperado);
        System.out.printf("%s%f%n", "Resultado obtido: ", obtido);

        return esperado == obtido;
    }

    /**
     * Testa método calcularValorProprio.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeCalcularValorProprio() {
        double matrix[][] = {{0.0, 1.0, 1.0}, {1.0, 0.0, 1.0},
        {0.0, 1.0, 0.0}};
        double esperado = 1.0;

        Matrix mat = matrizValorProprio(matrix);
        double obtido = calcularValorProprio(mat);

        System.out.printf("%n%s%f%n", "Resultado esperado: ", esperado);
        System.out.printf("%s%f%n", "Resultado obtido: ", obtido);

        return esperado == obtido;
    }

    /**
     * Testa método grauMedio.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeGrauMedio() {
        double matrix[][] = {{0.0, 1.0, 1.0}, {1.0, 0.0, 0.0}, {1.0, 1.0, 0.0}};
        double esperado = 5.0 / 3;
        double obtido = grauMedio(matrix);

        System.out.printf("%n%s%f%n", "Resultado esperado: ", esperado);
        System.out.printf("%s%f%n", "Resultado obtido: ", obtido);

        return esperado == obtido;
    }

    /**
     * Testa método somaMatrizDouble.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeSomaMatrizDouble() {
        double matrix[][] = {{0.0, 1.0, 1.0}, {1.0, 0.0, 0.0},
        {1.0, 1.0, 0.0}};
        double esperado = 2.5;
        double obtido = somaMatrizDouble(matrix);

        System.out.printf("%n%s%f%n", "Resultado esperado: ", esperado);
        System.out.printf("%s%f%n", "Resultado obtido: ", obtido);

        return esperado == obtido;
    }

    /**
     * Testa método calculoDensidade.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeCalculoDensidade() {
        double matrix[][] = {{0.0, 1.0, 1.0}, {1.0, 0.0, 0.0},
        {1.0, 1.0, 0.0}};
        double esperado = 2.5 / 3;
        double obtido = calculoDensidade(matrix);

        System.out.printf("%n%s%f%n", "Resultado esperado: ", esperado);
        System.out.printf("%s%f%n", "Resultado obtido: ", obtido);

        return esperado == obtido;
    }

    /**
     * Testa método multiplicação.
     *
     * @return true caso o teste seja bem sucedido e false caso não o seja.
     */
    public static boolean testeMultiplicacao() {
        double mat1[][] = {{2.0, 3.0, -2.0}, {1.0, 0.0, -3.0},
        {1.0, 5.0, 0.0}};
        double mat2[][] = {{3}, {1}, {-2}};
        double matEsp[][] = {{13}, {9}, {8}};
        double matObt[][] = multiplicacao(mat1, mat2);

        imprimirEsperadoObtidoDouble(matEsp, matObt);

        return compararMatrizDouble(matEsp, matObt);
    }

    /**
     * Testa método verifRamosIguais.
     *
     * @return um booleano dos resultados 1 e contrário do 2.
     */
    public static boolean testeVerifRamosIguais() {
        String matrix[][] = {{"teste01", "02"}, {"03", "teste04"}};
        String linha1[] = {"teste01", "02"};
        String linha2[] = {"03", "teste01"};
        boolean resultado1 = verifRamosIguais(linha1, matrix, 2);
        boolean resultado2 = verifRamosIguais(linha2, matrix, 2);

        System.out.printf("%n%s%n%s%n%s%n", "Resultado esperado: ",
                "resultado1 == true", "resultado2 == false");
        System.out.printf("%n%s%n%s%b%n%s%b%n", "Resultado obtido: ",
                "resultado1 == ", resultado1, "resultado2 == ", resultado2);

        return resultado1 && !resultado2;
    }

    /**
     * Testa método procurarIDNos.
     *
     * @return true se (resultado 1 = 1) e (resultado 2 = -1), ou false caso não
     * se satisfaça a condição anterior.
     */
    public static boolean testeProcurarIDNos() {
        String matrix[][] = {{"teste01", "02"}, {"03", "teste04"}};
        int resultado1 = procurarIDNos(matrix, 3, "03");
        int resultado2 = procurarIDNos(matrix, 3, "teste05");

        System.out.printf("%n%s%n%s%n%s%n", "Resultado esperado:", "resultado1 == 1",
                "resultado2 == -1");
        System.out.printf("%n%s%n%s%d%n%s%d%n", "Resultado obtido:", "resultado1 == ", resultado1,
                "resultado2 == ", resultado2);

        return resultado1 == 1 && resultado2 == -1;
    }

    /**
     * Testa método tresCasasDecimais.
     *
     * @return true se (aprox = 3.142) ou false caso não se satisfaça a condição
     * anterior.
     */
    public static boolean testeTresCasasDecimais() {

        double aprox = tresCasasDecimais(3.14159265359);
        double esperado = 3.142;

        System.out.printf("%s%f", "Resultado esperado: ", esperado);
        System.out.printf("%n%s%f", "Resultado obtido: ", aprox);

        return aprox == esperado;
    }

    /**
     * Testa método procurarMaiorVecDouble.
     *
     * @return true se o resultado esperado e o obtido forem iguais ou false
     * caso sejam diferentes
     */
    public static boolean testeProcurarMaiorVecDouble() {

        double vec[] = {-5.0, -3.3, -3.1, -4.0};
        int esperado = 2;
        int obtido = procurarMaiorVecDouble(vec);

        System.out.printf("%s%d", "Resultado esperado: ", esperado);
        System.out.printf("%n%s%d", "Resultado obtido: ", obtido);

        return obtido == esperado;
    }

    /**
     * Testa método somarMatrizes.
     *
     * @return true se a matriz esperada e a obtida forem iguais ou false caso
     * sejam diferentes
     */
    public static boolean testeSomarMatrizes() {

        double vec1[][] = {{-3.0, 1.2, 0.0}, {1.5, -4.0, 1.0}, {2.2, 3.1, -0.2}};
        double vec2[][] = {{1.4, -1.2, 0.0}, {4.2, 7.0, 2.0}, {1.0, -1.1, 0.0}};
        double esperado[][] = {{-1.6, 0.0, 0.0}, {5.7, 3.0, 3.0}, {3.2, 2.0, -0.2}};
        double obtido[][] = somarMatrizes(vec1, vec2);

        imprimirEsperadoObtidoDouble(esperado, obtido);

        return compararMatrizDouble(esperado, obtido);
    }

    /**
     * Testa método calcularDA.
     *
     * @return true se a matriz esperada e a obtida forem iguais ou false caso
     * sejam diferentes.
     */
    public static boolean testeCalcularDA() {

        double obtido[][] = {{1.3, 0.0, -3.0}, {-1.2, 0.3, 1.0}};
        double num = -2.0;
        double esperado[][] = {{-2.6, 0.0, 6.0}, {2.4, -0.6, -2.0}};
        calcularDA(num, obtido);

        imprimirEsperadoObtidoDouble(esperado, obtido);

        return compararMatrizDouble(esperado, obtido);
    }

    /**
     * Testa método preencherMatrizcom1.
     *
     * @return true se a matriz esperada e a obtida forem iguais ou false caso
     * sejam diferentes.
     */
    public static boolean testePreencherMatrizCom1() {

        double obtido[][] = new double[4][2];
        double esperado[][] = {{1.0, 1.0}, {1.0, 1.0}, {1.0, 1.0}, {1.0, 1.0}};
        preencherMatrizCom1(obtido);

        imprimirEsperadoObtidoDouble(esperado, obtido);

        return compararMatrizDouble(esperado, obtido);
    }

    /**
     * Testa método somaColuna.
     *
     * @return true se (resultado 1 = 2.0) e (resultado 2 = 4.9), ou false caso
     * não se satisfaça a condição anterior.
     */
    public static boolean testeSomaColuna() {

        double vec[][] = {{1.0, -4.0}, {2.1, 7.2}, {-1.1, -1.0}, {0.0, 2.7}};
        double resultado1 = somaColuna(vec, 0);
        double resultado2 = somaColuna(vec, 1);

        System.out.printf("%n%s%n%s%n%s%n", "Resultado esperado:", "resultado1 == 2.0",
                "resultado2 == 4.9");
        System.out.printf("%n%s%n%s%.1f%n%s%.1f%n", "Resultado obtido:", "resultado1 == ", resultado1,
                "resultado2 == ", resultado2);

        return resultado1 == 2.0 && resultado2 == 4.9;
    }

    /**
     * Testa método calcularRestante.
     *
     * @return true se a matriz esperada e a obtida forem iguais ou false caso
     * sejam diferentes.
     */
    public static boolean testeCalcularRestante() {

        double vec[][] = {{1.2, 6.1, -1.0}, {0.0, 0.0, 0.4}, {-3.2, 0.9, -4.0}};
        double esperado[][] = {{-2.4, -12.2, 2.0}, {0.0, 0.0, -0.8}, {6.4, -1.8, 8.0}};
        double obtido[][] = calcularRestante(7.0, 3, vec);

        imprimirEsperadoObtidoDouble(esperado, obtido);

        return compararMatrizDouble(esperado, obtido);
    }

    /**
     * Testa método calcularM.
     *
     * @return true se a matriz esperada e a obtida forem iguais ou false caso
     * sejam diferentes.
     */
    public static boolean testeCalcularM() {

        double vec1[][] = {{1.4, 6.1, -1.0}, {0.0, 0.0, 0.4}, {-3.1, 0.9, -4.0}};
        double vec2[][] = {{1.3, 0.0, -3.0}, {-1.2, 0.3, 1.0}, {4.1, -2.0, 0.6}};
        double esperado[][] = {{6.3, -12.2, -19.0}, {-8.4, 2.1, 6.2}, {34.9, -15.8, 12.2}};
        double obtido[][] = calcularM(vec2, 7.0, 3, vec1);

        imprimirEsperadoObtidoDouble(esperado, obtido);

        return compararMatrizDouble(esperado, obtido);
    }

    /**
     * Testa método calcularDA.
     *
     * @return true se o resultado esperado e o obtido forem iguais ou false
     * caso sejam diferentes.
     */
    public static boolean testeProcurarStringNoVetor() {

        String vec[] = {"a", "b", "3", "teste", ".teste"};
        int esperado = 3;
        int obtido = procurarStringNoVetor(vec, "teste");

        System.out.printf("%s%d", "Resultado esperado: ", esperado);
        System.out.printf("%n%s%d", "Resultado obtido: ", obtido);

        return obtido == esperado;
    }

    /**
     * Testa método calcularDA.
     *
     * @return true se a matriz esperada e a obtida forem iguais ou false caso
     * sejam diferentes.
     */
    public static boolean teste2PreencherMatrizAdjacencias() {

        String vec[][] = {{"2", "1", "1"}, {"3", "1", "1"}, {"1", "2", "1"}, {"1", "3", "1"}};
        double esperado[][] = {{0.0, 1.0, 1.0}, {1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}};
        double obtido[][] = new double[3][3];
        preencherMatrizAdjacencias(obtido, vec, 4, true);

        imprimirEsperadoObtidoDouble(esperado, obtido);

        return compararMatrizDouble(esperado, obtido);
    }

    public static boolean testeNormalizarVec() {

        double obtido[] = {1.0, 2.0, 3.0, 4.0, 5.0};
        double soma = 4.1;
        double esperado[] = {1.0 / 4.1, 2.0 / 4.1, 3.0 / 4.1, 4.0 / 4.1, 5.0 / 4.1};
        normalizarVec(obtido, soma);
        
        System.out.println("\nResultado esperado:");
        for (int i = 0; i < esperado.length; i++) {
                System.out.printf("%.3f%s", esperado[i], " ");
            System.out.println();
        }

        System.out.println("\nResultado obtido:");
        for (int i = 0; i < obtido.length; i++) {
                System.out.printf("%.3f%s", obtido[i], " ");
            System.out.println();
        }
        
        for (int i = 0; i < obtido.length; i++) {
                if (!(esperado[i] == obtido[i])) {
                    return false;
                }
        }
        return true;
    }

    public static boolean testeSomaVetorDouble() {
    
        double vec[] = {1.0, 0.0, -2.3, 4.8, -0.7};
        double esperado = 2.8;
        double obtido = somaVetorDouble(vec);
        
        System.out.printf("%s%s", "Resultado esperado: ", esperado);
        System.out.printf("%n%s%s", "Resultado obtido: ", obtido);

        return obtido == esperado;
    }
    
    /**
     * Imprime o resultado de um método (teste).
     *
     * @param resultado resultado obtido nos testes a imprimir o resultado.
     */
    public static void imprimirResultado(boolean resultado) {
        if (resultado == true) {
            System.out.println("\nResultado: SUCESSO!!");
        } else {
            System.out.println("\nResultado: FALHA!!!");
        }
        System.out.println("-----------------------------------");
    }

    /**
     * Imprime o resultado de um método (teste) que envolva matrizes de doubles.
     *
     * @param esperado matriz esperada;
     * @param obtido matriz obtida no teste.
     */
    public static void imprimirEsperadoObtidoDouble(double[][] esperado, double[][] obtido) {
        System.out.println("\nResultado esperado:");
        for (int i = 0; i < esperado.length; i++) {
            for (int j = 0; j < esperado[0].length; j++) {
                System.out.printf("%s%s", esperado[i][j], " ");
            }
            System.out.println();
        }

        System.out.println("\nResultado obtido:");
        for (int i = 0; i < obtido.length; i++) {
            for (int j = 0; j < obtido[0].length; j++) {
                System.out.printf("%s%s", obtido[i][j], " ");
            }
            System.out.println();
        }
    }

    /**
     * Imprime o resultado de um método (teste) que envolva matrizes de strings.
     *
     * @param esperado matriz esperada;
     * @param obtido matriz obtida no teste.
     */
    public static void imprimirEsperadoObtidoString(String[][] esperado, String[][] obtido) {
        System.out.println("\nResultado esperado:");
        for (int i = 0; i < esperado.length; i++) {
            for (int j = 0; j < esperado[0].length; j++) {
                System.out.printf("%s%s", esperado[i][j], " ");
            }
            System.out.println();
        }

        System.out.println("\nResultado obtido:");
        for (int i = 0; i < obtido.length; i++) {
            for (int j = 0; j < obtido[0].length; j++) {
                System.out.printf("%s%s", obtido[i][j], " ");
            }
            System.out.println();
        }
    }

    /**
     * Compara duas matrizes de doubles.
     *
     * @param esperado matriz esperada;
     * @param obtido matriz obtida no teste;
     * @return true se as matrizes forem iguais e false caso não o sejam.
     */
    public static boolean compararMatrizDouble(double[][] esperado, double[][] obtido) {

        for (int i = 0; i < obtido.length; i++) {
            for (int j = 0; j < obtido[0].length; j++) {
                if (!(esperado[i][j] == obtido[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Compara duas matrizes de strings.
     *
     * @param esperado matriz esperada;
     * @param obtido matriz obtida no teste;
     * @return true se as matrizes forem iguais e false caso não o sejam.
     */
    public static boolean compararMatrizString(String[][] esperado, String[][] obtido) {

        for (int i = 0; i < obtido.length; i++) {
            for (int j = 0; j < obtido[0].length; j++) {
                if (!(esperado[i][j].equals(obtido[i][j]))) {
                    return false;
                }
            }
        }
        return true;
    }
}
