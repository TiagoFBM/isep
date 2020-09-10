package lapr.project.spapp.model;

import java.util.Calendar;

/**
 * Representa um tempo através das horas, minutos e segundos.
 *
 * @author ISEP-DEI-PPROG
 */
public class Time implements Comparable<Time> {

    /**
     * As horas do tempo.
     */
    private int horas;

    /**
     * Os minutos do tempo.
     */
    private int minutos;

    /**
     * Os segundos do tempo.
     */
    private int segundos;

    /**
     * As horas por omissão.
     */
    private static final int HORAS_POR_OMISSAO = 0;

    /**
     * Os minutos por omissão.
     */
    private static final int MINUTOS_POR_OMISSAO = 0;

    /**
     * Os segundos por omissão.
     */
    private static final int SEGUNDOS_POR_OMISSAO = 0;

    /**
     * Constrói uma instância de Tempo recebendo as horas, minutos e segundos.
     *
     * @param horas    as horas do tempo.
     * @param minutos  os minutos do tempo.
     * @param segundos os segundos do tempo.
     */
    public Time(int horas, int minutos, int segundos) {
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
    }

    /**
     * Constrói uma instância de Tempo recebendo as horas e os minutos e
     * assumindo os segundos por omissão.
     *
     * @param horas   as horas do tempo.
     * @param minutos os minutos do tempo.
     */
    public Time(int horas, int minutos) {
        this.horas = horas;
        this.minutos = minutos;
        segundos = SEGUNDOS_POR_OMISSAO;
    }

    /**
     * Constrói uma instância de Tempo recebendo as horas e assumindo os minutos
     * e os segundos, por omissão.
     *
     * @param horas as horas do tempo.
     */
    public Time(int horas) {
        this.horas = horas;
        minutos = MINUTOS_POR_OMISSAO;
        segundos = SEGUNDOS_POR_OMISSAO;
    }

    /**
     * Constrói uma instância de Tempo com as horas, minutos e segundos, por
     * omissão.
     */
    public Time() {
        horas = HORAS_POR_OMISSAO;
        minutos = MINUTOS_POR_OMISSAO;
        segundos = SEGUNDOS_POR_OMISSAO;
    }

    /**
     * Constrói uma instância de Tempo com as mesmas caraterísticas do tempo
     * recebido por parâmetro.
     *
     * @param outroTempo o tempo com as características a copiar.
     */
    public Time(Time outroTempo) {
        horas = outroTempo.horas;
        minutos = outroTempo.minutos;
        segundos = outroTempo.segundos;
    }

    /**
     * Devolve as horas do tempo.
     *
     * @return horas do tempo.
     */
    public int getHoras() {
        return horas;
    }

    /**
     * Devolve os minutos do tempo.
     *
     * @return minutos do tempo.
     */
    public int getMinutos() {
        return minutos;
    }

    /**
     * Devolve os segundos do tempo.
     *
     * @return segundos do tempo.
     */
    public int getSegundos() {
        return segundos;
    }

    /**
     * Modifica as horas do tempo.
     *
     * @param horas as novas horas do tempo.
     */
    public void setHoras(int horas) {
        this.horas = horas;
    }

    /**
     * Modifica os minutos do tempo.
     *
     * @param minutos os novos minutos do tempo.
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    /**
     * Modifica os segundos do tempo.
     *
     * @param segundos os novos segundos do tempo.
     */
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    /**
     * Modifica as horas, os minutos e os segundos do tempo.
     *
     * @param horas    as novas horas do tempo.
     * @param minutos  os novos minutos do tempo.
     * @param segundos os novos segundos do tempo.
     */
    public void setTempo(int horas, int minutos, int segundos) {
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
    }

    /**
     * Devolve a descrição textual do tempo no formato: %02d:%02d:%02d AM/PM.
     *
     * @return caraterísticas do tempo.
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d",
                horas, minutos, segundos);
    }

    /**
     * Devolve o tempo no formato: %02d%02d%02d.
     *
     * @return caraterísticas do tempo.
     */
    public String toStringHHMMSS() {
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    /**
     * Compara o tempo com o objeto recebido.
     *
     * @param outroObjeto o objeto a comparar com o tempo.
     * @return true se o objeto recebido representar outro tempo equivalente ao
     *         tempo. Caso contrário, retorna false.
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        Time outroTempo = (Time) outroObjeto;
        return horas == outroTempo.horas && minutos == outroTempo.minutos
                && segundos == outroTempo.segundos;
    }

    public Time adicionarTempo(Time outroTempo) {
        int segundos1 = this.toSegundos();
        int segundos2 = outroTempo.toSegundos();
        int resultado = segundos1 + segundos2;
        resultado = resultado % 86400;
        return segundosToTempo(resultado);
    }

    public Time segundosToTempo(int segundosCalculados) {
        int s = segundosCalculados % 60;
        segundosCalculados = segundosCalculados / 60;
        int m = segundosCalculados % 60;
        int h = segundosCalculados / 60;
        return new Time(h, m, s);
    }

    /**
     * Compara o tempo com o outro tempo recebido por parâmetro.
     *
     * @param outroTempo o tempo a ser comparado.
     * @return o valor 0 se o outroTempo recebido é igual ao tempo; o valor -1
     *         se o outroTempo for posterior ao tempo; o valor 1 se o outroTempo
     *         for anterior ao tempo.
     */
    @Override
    public int compareTo(Time outroTempo) {
        return (outroTempo.isMaior(this)) ? -1 : (isMaior(outroTempo)) ? 1 : 0;
    }

    /**
     * Aumenta o tempo em um segundo.
     */
    public void tick() {
        segundos = ++segundos % 60;
        if (segundos == 0) {
            minutos = ++minutos % 60;
            if (minutos == 0) {
                horas = ++horas % 24;
            }
        }
    }

    /**
     * Devolve true se o tempo for maior do que o tempo recebido por parâmetro.
     * Se o tempo for menor ou igual ao tempo recebido por parâmetro, devolve
     * false.
     *
     * @param outroTempo o outro tempo com o qual se compara o tempo.
     * @return true se o tempo for maior do que o tempo recebido por parâmetro,
     *         caso contrário devolve false.
     */
    public boolean isMaior(Time outroTempo) {
        return toSegundos() > outroTempo.toSegundos();
    }

    /*
     * Solução alternativa
     * public boolean isMaior(Time outroTempo){
     *      if ( horas > outroTempo.horas ||
     *          (horas==outroTempo.horas && minutos>outroTempo.minutos) ||
     *          (horas==outroTempo.horas && minutos==outroTempo.minutos &&
     *           segundos > outroTempo.segundos) )
     *         return true;
     *      return false;
     * }
     */

    /**
     * Devolve true se o tempo for maior do que o tempo (horas, minutos e
     * segundos) recebido por parâmetro. Se o tempo for menor ou igual ao tempo
     * (horas, minutos e segundos) recebido por parâmetro, devolve false.
     *
     * @param horas    as outras horas do tempo com o qual se compara o tempo.
     * @param minutos  os outros minutos do tempo com o qual se compara o tempo.
     * @param segundos os outros segundos do tempo com o qual se compara o tempo.
     * @return true se o tempo for maior do que o tempo (horas, minutos e
     *         segundos) recebido por parâmetro, caso contrário devolve false.
     */
    public boolean isMaior(int horas, int minutos, int segundos) {
        Time outroTempo = new Time(horas, minutos, segundos);
        return this.toSegundos() > outroTempo.toSegundos();
    }

    /**
     * Devolve a diferença em segundos entre o tempo e o tempo recebido por
     * parâmetro.
     *
     * @param outroTempo o outro tempo com o qual se compara o tempo para
     *                   calcular a diferença em segundos.
     * @return diferença em segundos entre o tempo e o tempo recebido por
     *         parâmetro.
     */
    public int diferencaEmSegundos(Time outroTempo) {
        return Math.abs(toSegundos() - outroTempo.toSegundos());
    }

    /**
     * Devolve uma instância Time representativa da diferença entre o tempo e
 o tempo recebido por parâmetro.
     *
     * @param outroTempo o outro tempo com o qual se compara o tempo para obter
                   uma instãncia Time representativa da diferença entre
                   o tempo e o tempo recebido por parâmetro.
     * @return instância Time representativa da diferença entre o tempo e o
         tempo recebido por parâmetro.
     */
    public Time diferencaEmTempo(Time outroTempo) {
        int dif = diferencaEmSegundos(outroTempo);
        return segundosToTempo(dif);
    }

    /**
     * Devolve o tempo atual do sistema.
     *
     * @return o tempo atual do sistema.
     */
    public static Time tempoAtual() {
        Calendar agora = Calendar.getInstance();
        int hora = agora.get(Calendar.HOUR_OF_DAY);
        int minuto = agora.get(Calendar.MINUTE);
        int segundo = agora.get(Calendar.SECOND);
        return new Time(hora,minuto,segundo);
    }

    /**
     * Devolve o número de segundos correspondente ao tempo.
     *
     * @return número de segundos correspondente ao tempo.
     */
    private int toSegundos() {
        return horas * 3600 + minutos * 60 + segundos;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
