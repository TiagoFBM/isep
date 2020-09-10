package fabrica.productordersmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntity;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.production.domain.Quantity;
import fabrica.scm.messagesmanagement.domain.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class ProductionOrderReport implements DomainEntity<Long>, CanBeExportedToXML {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consumption> consumptionList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chargeback> chargebackList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Production> productionList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductionDelivery> productionDeliveryList;

    @ElementCollection
    private List<MachineTime> machineTimesList;

    public ProductionOrderReport() {
        consumptionList = new LinkedList<>();
        chargebackList = new LinkedList<>();
        productionList = new LinkedList<>();
        productionDeliveryList = new LinkedList<>();
        machineTimesList = new LinkedList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumptionList, chargebackList, productionList, productionDeliveryList, machineTimesList);
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionOrderReport)) return false;
        ProductionOrderReport that = (ProductionOrderReport) o;
        return consumptionList.equals(that.consumptionList) &&
                chargebackList.equals(that.chargebackList) &&
                productionList.equals(that.productionList) &&
                productionDeliveryList.equals(that.productionDeliveryList) &&
                machineTimesList.equals(that.machineTimesList);    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean hasIdentity(Long otherId) {
        return this.id.equals(otherId);
    }

    public void addConsumption(Consumption c) {
        this.consumptionList.add(c);
    }

    public void addChargeback(Chargeback c) {
        this.chargebackList.add(c);
    }

    public void addProduction(Production p) {
        this.productionList.add(p);
    }

    public void addProductionDelivery(ProductionDelivery pD) {
        this.productionDeliveryList.add(pD);
    }

    public void addMachineTime(MachineTime mTime) {
        this.machineTimesList.add(mTime);
    }

    @Override
    public Element exportToXML(Document doc) {
        Element element = doc.createElement("ProductionOrderReport");
        Element consumptions = doc.createElement("Consumptions");

        for (Consumption c : this.consumptionList){
            consumptions.appendChild(c.exportToXML(doc));
        }

        element.appendChild(consumptions);

        Element chargebacks = doc.createElement("Chargebacks");

        for (Chargeback ch : this.chargebackList){
            chargebacks.appendChild(ch.exportToXML(doc));
        }
        element.appendChild(chargebacks);

        Element productions = doc.createElement("Productions");
        for (Production p : this.productionList){
            productions.appendChild(p.exportToXML(doc));
        }
        element.appendChild(productions);

        Element productionDeliverys = doc.createElement("ProductionDeliverys");
        for (ProductionDelivery pd : this.productionDeliveryList){
            productionDeliverys.appendChild(pd.exportToXML(doc));
        }
        element.appendChild(productionDeliverys);

        Element machineTimes = doc.createElement("MachineTimes");
        for (MachineTime m : this.machineTimesList){
            machineTimes.appendChild(m.exportToXML(doc));
        }

        element.appendChild(machineTimes);
        
        return element;
    }

    public List<MachineTime> obtainMachineTimes() {
        return this.machineTimesList;
    }

    public List<Consumption> obtainConsumptionList() {
        return consumptionList;
    }

    public List<Chargeback> obtainChargebackList() {
        return chargebackList;
    }

    public List<Production> obtainProductionList() {
        return productionList;
    }

    public List<ProductionDelivery> obtainProductionDeliveryList() {
        return productionDeliveryList;
    }

    public boolean hasConsumptions() {
        return consumptionList.isEmpty();
    }

    public double calculateProductionDeliveryDifference(Quantity pdQuantity) {
        double productionQuantity = productionList.stream().mapToDouble(s->s.obtainQuantity().obtainValor()).sum();
        double productionDeliveryQuantity = productionDeliveryList.stream().mapToDouble(s->s.obtainQuantity().obtainValor()).sum();
        return productionQuantity - (productionDeliveryQuantity + pdQuantity.obtainValor());
    }

    public double calculateChargebackConsumptionDifference(Quantity pdQuantity) {
        double chargebackQuantity = chargebackList.stream().mapToDouble(s->s.obtainQuantity().obtainValor()).sum();
        double consumptionQuantity = consumptionList.stream().mapToDouble(s->s.obtainQuantity().obtainValor()).sum();
        return consumptionQuantity - (chargebackQuantity + pdQuantity.obtainValor());
    }
}
