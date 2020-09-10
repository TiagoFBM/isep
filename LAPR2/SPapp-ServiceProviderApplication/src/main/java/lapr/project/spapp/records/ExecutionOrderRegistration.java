package lapr.project.spapp.records;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.geometry.Pos;
import lapr.project.spapp.model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExecutionOrderRegistration {

    private List<ExecutionOrder> m_lstOE;
    private List<ExecutionOrder> m_lstOEFinished;
    private int numero = 0;

    public ExecutionOrderRegistration() {
        this.m_lstOE = new ArrayList<>();
        this.m_lstOEFinished = new ArrayList<>();
    }

    public ExecutionOrder novaExecutionOrder(Assignment atb, int number, String client, PostalAdress adress, double distanceToClient) {
        return new ExecutionOrder(atb, number, client, adress, distanceToClient);
    }

    public boolean addExecutionOrder(ExecutionOrder oe) {
        if (m_lstOE.contains(oe)) return false;
        else return m_lstOE.add(oe);
    }

    public boolean removeEO(ExecutionOrder eo) {
        return m_lstOE.remove(eo);
    }

    public List<ExecutionOrder> getExecucoesPrestadorPeriodo(Date dataInicio, Date dataFim) {
        List<ExecutionOrder> listaOrdens = new ArrayList<>();
        for (ExecutionOrder oe : m_lstOE) {
            Assignment a = oe.getM_oAssignment();
            Date dataAssignment = a.getoDate();
            if (!dataInicio.isMaior(dataAssignment) && !dataAssignment.isMaior(dataFim)) {
                listaOrdens.add(oe);
            }
        }
        return listaOrdens;
    }



    public List<ExecutionOrder> getExecutionOrdersWithoutEvaluation(List<ExecutionOrder> leop) {
        List<ExecutionOrder> leo = new ArrayList<>();

        for (ExecutionOrder oe : leop) {
            if (!oe.getStatus()) {
                leo.add(oe);
            }
        }
        return leo;
    }

    public boolean endOE(ExecutionOrder eo) {
        for (ExecutionOrder order : m_lstOE) {
            if (order.equals(eo)) {
                order.endEO();
                m_lstOEFinished.add(order);
                return true;
            }
        }
        return false;
    }

    public boolean setProblemEO(ExecutionOrder eo, String problem) {
        for (ExecutionOrder order : m_lstOE) {
            if (order.equals(eo)) {
                eo.setProblem(problem);
                return true;
            }
        }
        return false;
    }

    public List<ExecutionOrder> getListExecutionOrders() {
        return this.m_lstOE;
    }

    /**
     * Runs through the list of finished execution orders and checks which ones
     * have an accepted assignment. Returns a list of execution orders requests.
     *
     * @param laa list of accepted assignments
     * @return list of execution orders requests.
     */
    public Set<ExecutionOrder> getFinishedOrders(Set<Assignment> laa) {
        Set<ExecutionOrder> lOErequest = new HashSet<>();
        for (ExecutionOrder order : m_lstOEFinished) {
            for (Assignment atribuicao : laa) {
                if (order.hasAtribuition(atribuicao)) {
                    lOErequest.add(order);
                }
            }
        }
        return lOErequest;
    }

    public List<ExecutionOrder> getExecutionOrderByClient(String client) {
        List<ExecutionOrder> leo = new ArrayList<>();
        for (ExecutionOrder seo : this.m_lstOE) {
            if (seo.getClient().equalsIgnoreCase(client)) {
                leo.add(seo);
            }
        }
        return leo;
    }

    public List<String> getClientList() {
        List<String> list = new ArrayList<>();
        for (ExecutionOrder eo : m_lstOE) {
            if (!list.contains(eo.getClient())) {
                list.add(eo.getClient());
            }
        }
        return list;
    }


}
