package fabrica.errornotificationmanagement.application;

import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.services.ShowErrorNotificationsUntreated;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;

import java.util.LinkedList;
import java.util.List;

public class ListProcessorErrorNotificationsController {

    private ShowErrorNotificationsUntreated service = new ShowErrorNotificationsUntreated();

    public Iterable<ProductionLine> getAllProductionLines(){
        return service.getAllProductionLines();
    }

    public List<ErrorNotification> consultErrorNotificationsUntreatedByProductionLine(ProductionLine po) {
        LinkedList<ErrorNotification> list = new LinkedList<>();
        for (Machine m : po.machineList()){
            list.addAll(service.findByMachineUntreatedMessagess(m));
        }
        return list;
    }

    public List<ErrorNotification> consultErrorNotificationsUntreatedByErrorType(ErrorType errorType) {
        LinkedList<ErrorNotification> list = new LinkedList<>();
        list.addAll(service.findByErrorTypeUntreatedMessages(errorType));
        return list;
    }
}
