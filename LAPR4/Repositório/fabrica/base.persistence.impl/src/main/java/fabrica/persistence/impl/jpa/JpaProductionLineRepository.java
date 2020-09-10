package fabrica.persistence.impl.jpa;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;

public class JpaProductionLineRepository extends BasepaRepositoryBase<ProductionLine, Designation,Designation> implements ProductionLineRepository {
    public JpaProductionLineRepository() {
        super("internalCode");
    }

    @Override
    public List<ProductionLine> findAll(){
        final TypedQuery<ProductionLine> query = super.createQuery("SELECT pl FROM ProductionLine pl", ProductionLine.class);
        return query.getResultList();
    }

    @Override
    public ProductionLine findProductionLineFromMachineInternalCode(String machineCode) {
        final Query query = super.entityManager().createNativeQuery("SELECT pl.* FROM PRODUCTIONLINE pl WHERE pl.pk = (SELECT plm.PRODUCTIONLINE_PK FROM PRODUCTIONLINE_MACHINE plm WHERE plm.MACHINES_PK = (SELECT m.pk FROM MACHINE m WHERE m.code = :machineCode))", ProductionLine.class);
        query.setParameter("machineCode", machineCode);
        return (ProductionLine) query.getResultList().get(0);
    }

    @Override
    public List<ProductionLine> findAllProductionLineByInstalationDate(Calendar instalationDate) {
        final Query query = super.entityManager().createNativeQuery("SELECT pl.* FROM PRODUCTIONLINE pl WHERE pl.pk IN (SELECT plm.PRODUCTIONLINE_PK FROM PRODUCTIONLINE_MACHINE plm WHERE plm.MACHINES_PK IN (SELECT m.pk FROM MACHINE m WHERE m.DATE >= :d))", ProductionLine.class);
        query.setParameter("d", instalationDate.toInstant());
        return query.getResultList();
    }

}
