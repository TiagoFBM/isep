package lapr.project.spapp.model;

import lapr.project.spapp.records.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Company {

    private String m_strDesignacao;
    private String m_strNIF;
    private ExecutionOrderRegistration eORegistration;

    public Company(String strDesignacao, String strNIF) {
        if ((strDesignacao == null) || (strNIF == null)
                || (strDesignacao.isEmpty()) || (strNIF.isEmpty())) {
            throw new IllegalArgumentException("Nenhum dos argumentos pode ser nulo ou vazio.");
        }

        this.m_strDesignacao = strDesignacao;
        this.m_strNIF = strNIF;
        this.eORegistration = new ExecutionOrderRegistration();
    }

    /**
     * Returns the registration os execution orders.
     *
     * @return m_oRegistoOe registration of execution orders
     */
    public ExecutionOrderRegistration getExeOrdRegistration() {
        return this.eORegistration;
    }

    
}
