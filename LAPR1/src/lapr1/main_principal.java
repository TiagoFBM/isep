package lapr1;

import java.io.File;
import static lapr1.main.*;
import static lapr1.configs.*;
import java.io.FileNotFoundException;
import java.util.Formatter;
import org.la4j.Matrix;

public class main_principal {

    /**
     * @param args the command line arguments
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        if (args[0].equals("-n")) {

            System.out.println();

            int x = 1, i = 1;
            while (x < 1000) {
                x = (int) Math.pow(2, x);
                i = i + 1;
            }
            System.out.println("i =" + i);
            System.out.println();

            String fileRamos = args[2];
            String fileNos = args[1];

            int nLinhasRamos = 0;
            out = new Formatter(System.out);

            orientado = verificarOrientado(fileRamos, orientado);

            nLinhasRamos = contarLinhas(fileRamos) - 1;
            int qntNos = contarLinhas(fileNos);

            if (orientado == false) {
                String[][] dadosRamos = new String[nLinhasRamos][COLUNAS_RAMOS];
                String[][] dadosNos = new String[qntNos][COLUNAS_NOS];

                double matrizAdj[][] = new double[qntNos - 1][qntNos - 1];

                //CARREGAR INFORMAÇÃO DOS RAMOS
                lerRamos(nLinhasRamos, dadosRamos, COLUNAS_RAMOS, fileRamos, orientado);
                nLinhasRamos = removerPrimeiraLinha(dadosRamos, nLinhasRamos);
                removerPrimeiroChar(dadosRamos, nLinhasRamos);

                //CARREGAR INFORMAÇÃO DOS NÓS
                lerNos(qntNos, dadosNos, COLUNAS_NOS, fileNos);
                qntNos = removerPrimeiraLinha(dadosNos, qntNos);
                verifPossivel(qntNos, fileNos);

                //PREENCHER MATRIZ ADJACÊNCIAS
                preencherMatrizAdjacencias(matrizAdj, dadosRamos, nLinhasRamos, orientado);

                int op;
                int k = -20;
                do {
                    op = menu();
                    switch (op) {
                        case 1:
                            cabecalhoGeral(out, "RAMOS");
                            imprimirMatrizString(out, dadosRamos, nLinhasRamos);
                            System.out.println("\n");
                            break;
                        case 2:
                            cabecalhoGeral(out, "NÓS");
                            imprimirMatrizString(out, dadosNos, qntNos);
                            System.out.println("\n");
                            break;
                        case 3:
                            cabecalhoGeral(out, "GRAU DOS NÓS");
                            double[] grau = grauDeNo(out, matrizAdj, dadosNos);
                            int maior = procurarMaiorVecDouble(grau);
                            System.out.println("\nO nó com maior grau é o " + dadosNos[maior][1]);
                            System.out.println("A abrir website...");
                            abrirLinkNo(dadosNos, maior);
                            break;
                        case 4:
                            Matrix mattD = matrizValorProprio(matrizAdj);
                            int colMaiorValorProp = calcularValorProprio(mattD);
                            Matrix matrizVec = matrizVetorProprio(matrizAdj);
                            cabecalhoGeral(out, "CENTRALIDADE");
                            double[] centralidade = centralidade(out, matrizVec, colMaiorValorProp, dadosNos);
                            imprimeResultadoCentr(dadosNos, centralidade, out, orientado);
                            int maiorCentr = procurarMaiorVecDouble(centralidade);
                            System.out.println("\nO nó com maior popularidade é o " + dadosNos[maiorCentr][1]);
                            System.out.println("A abrir website...");
                            abrirLinkNo(dadosNos, maiorCentr);
                            break;
                        case 5:
                            cabecalhoGeral(out, "GRAU MÉDIO");
                            System.out.println(tresCasasDecimais(grauMedio(matrizAdj)));
                            break;
                        case 6:
                            cabecalhoGeral(out, "DENSIDADE");
                            System.out.println(tresCasasDecimais(calculoDensidade(matrizAdj)));
                            break;
                        case 7:
                            do {
                                System.out.println("Deseja saber as potências da matriz das adjacências até qual valor? ");
                                System.out.print("Introduza um valor: ");
                                k = configs.in.nextInt();
                            } while (k <= 0);
                            potenciaMatriz(out, matrizAdj, k);
                            break;
                        case 8:
                            mattD = matrizValorProprio(matrizAdj);
                            colMaiorValorProp = calcularValorProprio(mattD);
                            matrizVec = matrizVetorProprio(matrizAdj);
                            do {
                                System.out.println("Deseja saber as potências da matriz das adjacências até qual valor? ");
                                System.out.print("Introduza um valor: ");
                                k = configs.in.nextInt();
                            } while (k <= 0);
                            cabecalhoGeral(out, "RAMOS");
                            imprimirMatrizString(out, dadosRamos, nLinhasRamos);

                            cabecalhoGeral(out, "NÓS");
                            imprimirMatrizString(out, dadosNos, qntNos);
                            System.out.println("\n");

                            System.out.println("\n");
                            cabecalhoGeral(out, "GRAU DOS NÓS");
                            grauDeNo(out, matrizAdj, dadosNos);

                            System.out.println("\n");
                            cabecalhoGeral(out, "CENTRALIDADE");
                            centralidade(out, matrizVec, colMaiorValorProp, dadosNos);

                            System.out.println("\n");
                            cabecalhoGeral(out, "GRAU MÉDIO");
                            System.out.println(tresCasasDecimais(grauMedio(matrizAdj)));

                            System.out.println("\n");
                            cabecalhoGeral(out, "DENSIDADE");
                            System.out.println(tresCasasDecimais(calculoDensidade(matrizAdj)));

                            potenciaMatriz(out, matrizAdj, k);
                            break;
                        case 0:
                            System.out.println("Já fez todas as iterações necessárias? Confirma terminar(s/n)?");
                            char resp = (configs.in.next()).charAt(0);
                            if (resp != 's' && resp != 'S') {
                                System.out.println("Opção incorreta.");
                                op = 1;
                            }
                            break;
                        default:
                            System.out.println("Opção incorreta. Repita");
                            break;
                    }
                } while (op != 0);
            } else {
                String[][] dadosRamos = new String[nLinhasRamos][COLUNAS_RAMOS];
                String[][] dadosNos = new String[qntNos][COLUNAS_NOS];
                double matrizAdj[][] = new double[qntNos - 1][qntNos - 1];

                //CARREGAR INFORMAÇÃO DOS RAMOS
                lerRamos(nLinhasRamos, dadosRamos, COLUNAS_RAMOS, fileRamos, orientado);
                nLinhasRamos = removerPrimeiraLinha(dadosRamos, nLinhasRamos);
                removerPrimeiroChar(dadosRamos, nLinhasRamos);

                //CARREGAR INFORMAÇÃO DOS NÓS
                lerNos(qntNos, dadosNos, COLUNAS_NOS, fileNos);
                qntNos = removerPrimeiraLinha(dadosNos, qntNos);
                verifPossivel(qntNos, fileNos);

                double[] grauSaida = new double[qntNos];
                double[][] matrizA = new double[qntNos][qntNos];
                double[][] matrizDe1 = new double[qntNos][qntNos];

                //PREENCHER MATRIZ ADJACÊNCIAS
                preencherMatrizAdjacencias(matrizAdj, dadosRamos, nLinhasRamos, orientado);

                int op;
                int subop;
                boolean case1 = false;
                boolean case2 = false;
                boolean case3 = false;

                do {
                    op = menuOrientado();
                    switch (op) {
                        case 1:
                            cabecalhoGeral(out, "RAMOS");
                            imprimirMatrizString(out, dadosRamos, nLinhasRamos);
                            System.out.println("\n");
                            break;
                        case 2:
                            cabecalhoGeral(out, "NÓS");
                            imprimirMatrizString(out, dadosNos, qntNos);
                            System.out.println("\n");
                            break;
                        case 3:
                            cabecalhoGeral(out, "GRAU DE ENTRADA");
                            double[] grauEntrada = grauDeNo(out, matrizAdj, dadosNos);
                            int posMaiorEntrada = procurarMaiorVecDouble(grauEntrada);
                            System.out.println("\nO nó com maior grau de entrada é o " + dadosNos[posMaiorEntrada][1]);
                            System.out.println("A abrir website...");
                            abrirLinkNo(dadosNos, posMaiorEntrada);
                            case1 = true;
                            break;
                        case 4:
                            cabecalhoGeral(out, "GRAU DE SAÍDA");
                            grauSaida = grauDeSaida(out, matrizAdj, dadosNos);
                            int posMaiorSaida = procurarMaiorVecDouble(grauSaida);
                            System.out.println("\nO nó com maior grau de saida é o " + dadosNos[posMaiorSaida][1]);
                            System.out.println("A abrir website...");
                            abrirLinkNo(dadosNos, posMaiorSaida);
                            case2 = true;
                            break;
                        case 5:
                            // VERIFICAR SE ESTÁ CERTO
                            if ((case1 == true) && (case2 == true)) {
                                preencherMatrizA(qntNos, grauSaida, matrizAdj, matrizA);
                                System.out.print("Introduza o valor de d: ");
                                double d = in.nextDouble();
                                verificarD(d);
                                double[][] matrizM = calcularM(matrizA, d, qntNos, matrizDe1);

                                do {
                                    subop = menuCase5();
                                    switch (subop) {
                                        case 1:
                                            System.out.print("Introduza o valor de k: ");
                                            int k = in.nextInt();
                                            cabecalhoGeral(out, "PAGE RANK - UTILIZANDO " + k + " ITERAÇÕES");
                                            double[] xr = calcularImprimirXK(matrizM, qntNos, k, out, dadosNos);
                                            double somaXR = somaVetorDouble(xr);
                                            normalizarVec(xr, somaXR);
                                            int maiorPageRank1 = procurarMaiorVecDouble(xr);
                                            cabecalhoGeral(out, "PAGE RANK - VETOR X_" + k + " NORMALIZADO");
                                            imprimeResultadoCentr(dadosNos, xr, out, false);
                                            System.out.println("\nO nó com maior popularidade é o " + dadosNos[maiorPageRank1][1]);
                                            System.out.println("A abrir website...");
                                            abrirLinkNo(dadosNos, maiorPageRank1);
                                            case3 = true;
                                            break;
                                        case 2:
                                            Matrix mattD2 = matrizValorProprio(matrizM);
                                            int colMaiorValorProp = calcularValorProprio(mattD2);
                                            Matrix matrizVecM = matrizVetorProprio(matrizM);
                                            System.out.println();
                                            centralidade(out, matrizVecM, colMaiorValorProp, dadosNos);
                                            double[] vecPageRank = centralidade(out, matrizVecM, colMaiorValorProp, dadosNos);
                                            double soma = somaVetorDouble(vecPageRank);
                                            normalizarVec(vecPageRank, soma);
                                            int maiorPageRank2 = procurarMaiorVecDouble(vecPageRank);
                                            cabecalhoGeral(out, "PAGE RANK - UTILIZANDO VETORES PRÓPRIOS ");
                                            imprimeResultadoCentr(dadosNos, vecPageRank, out, false);
                                            System.out.println("\nO nó com maior popularidade é o " + dadosNos[maiorPageRank2][1]);
                                            System.out.println("A abrir website...");
                                            abrirLinkNo(dadosNos, maiorPageRank2);
                                            case3 = true;
                                            break;
                                    }
                                } while (case3 == false);
                            } else {
                                System.out.println("É necessário calcular o grau de entrada e de saida de um nó antes de proceder com essa iteração.");
                            }
                            break;
                    }
                } while (op != 0);
            }
        } else {

            int valor = procurarStringNoVetor(args, "-d");

            String fileRamos = args[args.length - 1];
            String fileNos = args[args.length - 2];

            orientado = verificarOrientado(fileRamos, orientado);

            if ((valor != -1) && (orientado == false)) {
                System.out.println("O Ficheiro utilizado não é um ficheiro orientado pelo que o elemento D não deve ser definido.");
                System.exit(0);
            } else if ((orientado == true) && (valor == -1)) {
                System.out.println("O Ficheiro utilizado é um ficheiro orientado pelo que falta o elemento D.");
                System.exit(0);
            }

            diaMesAnoAtual(data);
            String outputName = fileNos.substring(0, fileNos.lastIndexOf("_")) + data[0] + data[1] + data[2] + ".txt";
            Formatter out = new Formatter(new File("Ficheiros/" + outputName));

            int posk = procurarStringNoVetor(args, "-k");
            int k = Integer.parseInt(args[posk + 1]);

            if (k <= 0) {
                System.out.println("O valor de k tem de ser superior a zero.");
                System.exit(0);
            }

            int nLinhasRamos = contarLinhas(fileRamos) - 1;
            int qntNos = contarLinhas(fileNos);

            String[][] dadosRamos = new String[nLinhasRamos][COLUNAS_RAMOS];
            String[][] dadosNos = new String[qntNos][COLUNAS_NOS];

            double matrizAdj[][] = new double[qntNos - 1][qntNos - 1];

            lerRamos(nLinhasRamos, dadosRamos, COLUNAS_RAMOS, fileRamos, orientado);
            nLinhasRamos = removerPrimeiraLinha(dadosRamos, nLinhasRamos);
            removerPrimeiroChar(dadosRamos, nLinhasRamos);

            lerNos(qntNos, dadosNos, COLUNAS_NOS, fileNos);
            qntNos = removerPrimeiraLinha(dadosNos, qntNos);
            verifPossivel(qntNos, fileNos);

            preencherMatrizAdjacencias(matrizAdj, dadosRamos, nLinhasRamos, orientado);
            cabecalhoGeral(out, "RAMOS");
            imprimirMatrizString(out, dadosRamos, nLinhasRamos);
            out.format("%n");

            cabecalhoGeral(out, "NOS");
            imprimirMatrizString(out, dadosNos, qntNos);
            out.format("%n");

            if (orientado == false) {
                Matrix mattD = matrizValorProprio(matrizAdj);
                int colMaiorValorProp = calcularValorProprio(mattD);
                Matrix matrizVec = matrizVetorProprio(matrizAdj);
                out.format("%n");

                cabecalhoGeral(out, "GRAUS DOS NÓS");
                grauDeNo(out, matrizAdj, dadosNos);

                cabecalhoGeral(out, "CENTRALIDADE");
                double[] centr = centralidade(out, matrizVec, colMaiorValorProp, dadosNos);
                imprimeResultadoCentr(dadosNos, centr, out, orientado);

                cabecalhoGeral(out, "GRAU MÉDIO");
                out.format("%s", tresCasasDecimais(grauMedio(matrizAdj)));
                out.format("%n");

                cabecalhoGeral(out, "DENSIDADE");
                out.format("%s", tresCasasDecimais(calculoDensidade(matrizAdj)));

                potenciaMatriz(out, matrizAdj, k);
                System.out.println("Informação guardada com sucesso no ficheiro '" + outputName + "' !");
                out.close();

            } else {
                double d = Double.parseDouble(args[valor + 1]);
                verificarD(d);

                double[][] matrizA = new double[qntNos][qntNos];
                double[][] matrizDe1 = new double[qntNos][qntNos];
                preencherMatrizCom1(matrizDe1);

                cabecalhoGeral(out, "GRAU DE ENTRADA");
                grauDeNo(out, matrizAdj, dadosNos);

                cabecalhoGeral(out, "GRAU DE SAÍDA");
                double[] grauSaida = grauDeSaida(out, matrizAdj, dadosNos);

                preencherMatrizA(qntNos, grauSaida, matrizAdj, matrizA);
                double[][] matrizM = calcularM(matrizA, d, qntNos, matrizDe1);

                cabecalhoGeral(out, "PAGE RANK - UTILIZANDO " + k + " ITERAÇÕES");
                double[] xr = calcularImprimirXK(matrizM, qntNos, k, out, dadosNos);
                double somaXR = somaVetorDouble(xr);
                normalizarVec(xr, somaXR);
                cabecalhoGeral(out, "PAGE RANK - VETOR X_" + k + " NORMALIZADO");
                imprimeResultadoCentr(dadosNos, xr, out, false);

                Matrix mattD2 = matrizValorProprio(matrizM);
                int colMaiorValorProp = calcularValorProprio(mattD2);
                Matrix matrizVecM = matrizVetorProprio(matrizM);
                System.out.println();
                centralidade(out, matrizVecM, colMaiorValorProp, dadosNos);
                double[] vecPageRank = centralidade(out, matrizVecM, colMaiorValorProp, dadosNos);
                double soma = somaVetorDouble(vecPageRank);
                normalizarVec(vecPageRank, soma);
                cabecalhoGeral(out, "PAGE RANK - UTILIZANDO VETORES PRÓPRIOS ");
                imprimeResultadoCentr(dadosNos, vecPageRank, out, false);

                System.out.println("Informação guardada com sucesso no ficheiro '" + outputName + "' !");
                out.close();
            }

        }
    }

    /**
     * Apresenta o menu ao utilizador, returnando a sua opção.
     *
     * @param texto - texto a apresentar no ecrã ao utilizador;
     * @param op - opção do menu escolhida pelo utilizador;
     * @return a opção ecolhida pelo utilizador.
     */
    private static int menu() {
        String texto = "MENU: \n"
                + "\n Listar informação dos ramos - 1"
                + "\n Imprimir informação dos nós - 2"
                + "\n Calcular e imprimir grau dos nós - 3"
                + "\n Calcular a centralidade - 4"
                + "\n Calcular e imprimir grau médio - 5"
                + "\n Calcular e imprimir densidade da rede - 6"
                + "\n Calcular e imprimir potências da matriz - 7"
                + "\n Calcular e imprimir todos os parâmetros acima - 8"
                + "\n Terminar - 0"
                + "\n Introduza a sua opção: ";
        System.out.printf("%n%s", texto);
        int op = configs.in.nextInt();
        configs.in.nextLine();
        return op;
    }

    /**
     * Apresenta o menu do grafo orientado ao utilizador, returnando a sua
     * opção.
     *
     * @param texto - texto a apresentar no ecrã ao utilizador;
     * @param op - opção do menu escolhida pelo utilizador;
     * @return a opção ecolhida pelo utilizador.
     */
    private static int menuOrientado() {
        String texto = "MENU: \n"
                + "\n Listar informação dos ramos - 1"
                + "\n Imprimir matriz dos nós - 2"
                + "\n Calcular e apresentar o grau de entrada de todos os nós - 3"
                + "\n Calcular e apresentar o grau de saída de todos os nós - 4"
                + "\n Calcular e apresentar vetor Page Rank - 5"
                + "\n Terminar - 0"
                + "\n Introduza a sua opção: ";
        System.out.printf("%n%s", texto);
        int op = configs.in.nextInt();
        configs.in.nextLine();
        return op;
    }

    /**
     * Apresenta o menu do case 5 do grafo orientado ao utilizador, returnando a
     * sua opção.
     *
     * @param texto - texto a apresentar no ecrã ao utilizador;
     * @param op - opção do menu escolhida pelo utilizador;
     * @return a opção ecolhida pelo utilizador.
     */
    private static int menuCase5() {
        String texto = "MENU PAGE RANK: \n"
                + "\n Calcular e apresentar o Page Rank utilizando o grau de saida - 1"
                + "\n Calcular e apresentar o Page Rank utilizando os vetores próprios - 2"
                + "\n Introduza a sua opção: ";
        System.out.printf("%n%s", texto);
        int op = configs.in.nextInt();
        configs.in.nextLine();
        return op;
    }
}
