package lapr.project.spapp.model;

import java.util.Objects;

public class ExecutionOrder {

    private final String oClient;
    private final PostalAdress oAdress;
    private Assignment m_oAssignment;
    private int número;
    private boolean finished;
    // Sem avaliação (UC13) = false
    // Com avaliação (UC13) = true
    private String problem;
    private int extraTime;
    private int rate;
    private double distanceToClient;


    public ExecutionOrder(Assignment oAtr, int nmr, String oCli, PostalAdress adress, double distanceToClient) {
        m_oAssignment = oAtr;
        número = nmr;
        finished = false;
        this.problem = null;
        this.oClient = oCli;
        this.oAdress = adress;
        this.distanceToClient = distanceToClient;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Assignment getM_oAssignment() {
        return m_oAssignment;
    }

    public int getNúmero() {
        return número;
    }

    public String getClient() {
        return oClient;
    }

    public boolean hasAtribuition(Assignment assignment) {
        return this.m_oAssignment.equals(assignment);
    }

    @Override
    public String toString() {
        return String.format("Number: %s | Client: %s | Street: %s %n" +
                "Category: %s | Service: %s | ServiceType: %s %n" +
                "Date: %s | Schedule %s", número, oClient, oAdress.getM_strLocal(), m_oAssignment.getoDescricaoServico().getStrCategory(), m_oAssignment.getoDescricaoServico().getDescricao(), m_oAssignment.getoDescricaoServico().getServiceType(), m_oAssignment.getoDate().toAnoMesDiaString(), m_oAssignment.getoHorario().toStringHHMMSS());
    }

    public String getPrestadorEO() {
        return m_oAssignment.getServProviderID();
    }

    public void endEO() {
        this.finished = true;
    }

    public boolean getStatus() {
        return this.finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExecutionOrder that = (ExecutionOrder) o;
        return número == that.número
                && finished == that.finished
                && m_oAssignment.equals(that.m_oAssignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_oAssignment, número, finished);
    }

    public PostalAdress getEndereco() {
        return this.oAdress;
    }
}
