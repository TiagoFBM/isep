/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.spapp.controller;

import static java.util.Collections.sort;

import java.util.Comparator;
import java.util.List;

import lapr.project.spapp.model.*;
import lapr.project.spapp.records.ExecutionOrderRegistration;

/**
 * @author anama
 */
public class SortRecordsController{

    /**
     *
     */
    private List<ExecutionOrder> leo;
    private Comparator<ExecutionOrder> comparator;
    ExecutionOrderRegistration eor;


    public SortRecordsController() {
        GPSDApplication m_oAppGPSD = GPSDApplication.getInstance();
        Company m_oEmpresa = m_oAppGPSD.getCompany();
        eor = m_oEmpresa.getExeOrdRegistration();
        leo = eor.getListExecutionOrders();
    }

    public List<ExecutionOrder> getListEO() {
        return leo;
    }

    public List<ExecutionOrder> sortRecords() {
        leo.sort(comparator);
        return leo;
    }

    public boolean validatesEOSize(){
        return eor.getListExecutionOrders().size() > 0;
    }

    public Comparator<ExecutionOrder> getComparationByString(String selected) {
        comparator = (eo1, eo2) -> {
            switch (selected) {
                case "Assignment Date":
                    return eo1.getM_oAssignment().getoDate().compareTo(eo2.getM_oAssignment().getoDate());
                case "Service":
                    return eo1.getM_oAssignment().getoDescricaoServico().getDescricao().compareTo(eo2.getM_oAssignment().getoDescricaoServico().getDescricao());
                case "Client":
                    return eo1.getClient().compareTo(eo2.getClient());
                case "Address":
                    return eo1.getEndereco().compareTo(eo2.getEndereco());
            }
            return eo1.getM_oAssignment().getoDate().compareTo(eo2.getM_oAssignment().getoDate());
        };
        return null;
    }

}
