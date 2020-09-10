package fabrica.persistence.impl.jpa;

import fabrica.production.domain.AlfanumericCode;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.domain.ActivityStartMessage;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JpaProductionOrderRepository extends BasepaRepositoryBase<ProductionOrder, Long, AlfanumericCode> implements ProductionOrderRepository {

    public JpaProductionOrderRepository(){super("POCode");}

    @Override
    public List<ProductionOrder> findAll(){
        final TypedQuery<ProductionOrder> query = super.createQuery("SELECT po FROM ProductionOrder po", ProductionOrder.class);
        return query.getResultList();
    }

    @Override
    public List<ProductionOrder> findAllProductionOrdersFromRequest(String requestID) {
        final Query query = super.entityManager().createNativeQuery("SELECT PO.* FROM PRODUCTIONORDER PO WHERE PO.PK IN (SELECT POR.PRODUCTIONORDER_PK FROM PRODUCTIONORDER_REQUESTS POR WHERE POR.CODE = :ri)", ProductionOrder.class);
        query.setParameter("ri", requestID);
        return query.getResultList();
    }

    @Override
    public List<ProductionOrder> findAllBetweenDates(Calendar init, Calendar finish) {
        final Query query = super.entityManager().createNativeQuery("SELECT PO.* FROM PRODUCTIONORDER PO WHERE PO.EMISSIONDATE BETWEEN :initD AND :findD ", ProductionOrder.class);
        query.setParameter("initD", init);
        query.setParameter("findD", finish);
        return query.getResultList();
    }

     @Override
    public Iterable<ProductionOrder> getProductionOrdersWithState(String state) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p where p.state.state=:s",ProductionOrder.class
        );
        query.setParameter("s", state);
        return query.getResultList();
    }

   
    @Override
    public Iterable<ProductionOrder> getPendingOrSuspended(){
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p where p.state = 'Suspended' OR p.state = 'Pending'", ProductionOrder.class
        );
        return query.getResultList();
    }

    @Override
    public ProductionOrder getLastProcessesProductionOrder(String plInternalCode, Date currentSendDate) {
        final Query query = super.entityManager().createNativeQuery("SELECT poa.* FROM productionorder poa WHERE poa.pk = (SELECT asm.PRODUCTIONORDER_PK FROM activitystartmessage asm  WHERE asm.senddatetime <= :lastSendDateTime AND asm.machine_pk IN (SELECT plm.machines_pk FROM productionline_machine plm WHERE plm.productionline_pk = (SELECT pk FROM productionline WHERE internalcode = :plInternalCode)) AND productionorder_pk IS NOT NULL ORDER BY asm.SENDDATETIME DESC LIMIT 1)", ProductionOrder.class);
        query.setParameter("plInternalCode", plInternalCode);
        query.setParameter("lastSendDateTime", currentSendDate.toString());
        return (ProductionOrder) query.getResultList().get(0);
    }
}
