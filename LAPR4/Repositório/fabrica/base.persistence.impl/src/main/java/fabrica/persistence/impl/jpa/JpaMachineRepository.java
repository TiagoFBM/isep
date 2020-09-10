package fabrica.persistence.impl.jpa;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.productordersmanagement.domain.ProductionOrder;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

public class JpaMachineRepository extends BasepaRepositoryBase<Machine, Long, AlfanumericCode> implements MachineRepository {

    public JpaMachineRepository() {
        super("internalCode");
    }

    @Override
    public List<Machine> findUnassignedMachines() {
        final Query query = super.entityManager().createNativeQuery("SELECT * FROM Machine m WHERE m.pk NOT IN (SELECT plm.machines_pk FROM ProductionLine_Machine plm)", Machine.class);
        return query.getResultList();
    }

    @Override
    public Machine findMachineByProtocolNumber(ProtocolIdentificationNumber protocolID) {
        try {
            final TypedQuery<Machine> query = super.createQuery("SELECT m FROM Machine m WHERE m.identificationNumber = :protocol", Machine.class);
            query.setParameter("protocol", protocolID);
            return query.getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public List<Machine> findAllMachinesFromProductionLine(String pl) {
        try {
            final TypedQuery<Collection> query = super.createQuery("SELECT pl.machines FROM ProductionLine pl WHERE pl.internalCode = :intCode", Collection.class);
            query.setParameter("intCode", Designation.valueOf(pl));
            return (List) query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Machine> findAll(){
        final TypedQuery<Machine> query = super.createQuery("SELECT m FROM Machine m", Machine.class);
        return query.getResultList();
    }
}
