package fabrica.productordersmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.production.domain.AlfanumericCode;
import fabrica.productordersmanagement.domain.ProductionOrder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface ProductionOrderRepository extends DomainRepository<AlfanumericCode, ProductionOrder> {

    @Override
    List<ProductionOrder> findAll();

    List<ProductionOrder> findAllProductionOrdersFromRequest(String requestID);

    List<ProductionOrder> findAllBetweenDates(Calendar init, Calendar finish);
    
    Iterable<ProductionOrder> getProductionOrdersWithState(String state);
    
     Iterable<ProductionOrder> getPendingOrSuspended();

     ProductionOrder getLastProcessesProductionOrder(String plInternalCode, Date currentSendDate);
    
}
