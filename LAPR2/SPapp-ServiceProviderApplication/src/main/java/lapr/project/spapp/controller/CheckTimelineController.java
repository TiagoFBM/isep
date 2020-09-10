/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.spapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lapr.project.spapp.model.ExecutionOrder;
import lapr.project.spapp.records.ExecutionOrderRegistration;

/**
 * @author Clara Pinto
 */
public class CheckTimelineController {

    private final ExecutionOrderRegistration exeOrderReg;
    private final Comparator<ExecutionOrder> comparator;
    private List<ExecutionOrder> EOByClient;

    public CheckTimelineController() {
        EOByClient = new ArrayList<>();
        comparator = Comparator.comparing(eo -> eo.getM_oAssignment().getoDate());
        this.exeOrderReg = GPSDApplication.getInstance().getCompany().getExeOrdRegistration();
    }

    public List<String> getClientList() {
        return exeOrderReg.getClientList();
    }

    private List<ExecutionOrder> getTimeline(String cli) {
        return EOByClient = exeOrderReg.getExecutionOrderByClient(cli);
    }

    public List<ExecutionOrder> sortTimeLine(String cli) {
        getTimeline(cli);
        EOByClient.sort(comparator);
        return EOByClient;
    }

    public boolean validatesEOSize() {
        return exeOrderReg.getListExecutionOrders().size() > 0;
    }

}
