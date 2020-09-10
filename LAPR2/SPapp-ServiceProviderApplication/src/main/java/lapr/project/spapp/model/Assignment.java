/**
 * Classe que representa uma atribuição de um pedido a um ou mais prestadores de serviço.
 */
package lapr.project.spapp.model;


import java.util.Objects;

public class Assignment {
    /**
     * Objeto que representa uma descrição de um pedido de serviço.
     */
    private DescriptionOrderService oDescricaoServico;
    /**
     * Objeto que representa um prestador de serviços.
     */
    private String servProviderID;
    /**
     * Objeto que representa uma data associada à atribuição.
     */
    private Date oDate;
    /**
     * Objeto que representa um horário associado à atribuição.
     */
    private Time oHorario;

    private boolean decision;
    // false = não aceite
    // true = aceite

    /**
     * Contrutor com parâmetros que inicializa as variáveis da classe.
     *
     * @param oDescricaoServico           descrição do serviço a ser atribuido.
     * @param servProviderID prestador para executar o serviço.
     * @param oDate              data da atribuição.
     * @param oHorario           horário da atribuição.
     */
    public Assignment(DescriptionOrderService oDescricaoServico, String servProviderID, Date oDate, Time oHorario) {
        this.oDescricaoServico = oDescricaoServico;
        this.servProviderID = servProviderID;
        this.oDate = oDate;
        this.oHorario = oHorario;
        this.decision = false;
    }

    /**
     * Retorna o serviço associado à atribuição.
     *
     * @return serviço associado à atribuição.
     */
    public DescriptionOrderService getoDescricaoServico() {
        return oDescricaoServico;
    }

    /**
     * Retorna o prestador de serviços associado à atribuição.
     *
     * @return prestador de serviços associado à atribuição.
     */
    public String getServProviderID() {
        return servProviderID;
    }

    public Date getoDate() {
        return oDate;
    }

    public Time getoHorario() {
        return oHorario;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public boolean getDecision() {
        return this.decision;
    }

    @Override
    public String toString() {
        return String.format("Servico: %s\nDate: %s\nHorário: %s\nPrestador de Serviços Associado: %s", oDescricaoServico.toString(), oDate.toAnoMesDiaString(), oHorario.toStringHHMMSS(), servProviderID.toString());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return decision == that.decision &&
                oDescricaoServico.equals(that.oDescricaoServico) &&
                servProviderID.equals(that.servProviderID) &&
                oDate.equals(that.oDate) &&
                oHorario.equals(that.oHorario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oDescricaoServico, servProviderID, oDate, oHorario, decision);
    }


}

