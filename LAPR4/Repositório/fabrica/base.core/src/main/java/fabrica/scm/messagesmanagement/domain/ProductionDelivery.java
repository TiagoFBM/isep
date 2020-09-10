package fabrica.scm.messagesmanagement.domain;

import eapli.framework.math.util.NumberPredicates;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.io.Serializable;

@Entity

public class ProductionDelivery implements Serializable, CanBeExportedToXML {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @ManyToOne
    private Deposit deposit;

    @ManyToOne
    private Product product;

    @Embedded
    private Quantity quantity;

    @Embedded
    private AlfanumericCode allotment;

    protected ProductionDelivery() {}

    public ProductionDelivery(Deposit deposit, Product product, int quantity, String unit) {
        if (!validateProductionDelivery(deposit, product, quantity)) {
            throw new IllegalArgumentException("Invalid production delivery");
        }

        this.deposit = deposit;
        this.product = product;
        this.quantity = new Quantity(quantity, unit);
        this.allotment = null;
    }

    public ProductionDelivery(Deposit deposit, Product product, int quantity, String unit, String allotment) {
        if (!validateProductionDelivery(deposit, product, quantity)) {
            throw new IllegalArgumentException("Invalid production delivery");
        }

        this.deposit = deposit;
        this.product = product;
        this.quantity = new Quantity(quantity, unit);
        this.allotment = AlfanumericCode.valueOf(allotment);
    }


    private boolean validateProductionDelivery(Deposit deposit, Product product, int quantity) {
        return deposit != null && product != null && NumberPredicates.isPositive(quantity);
    }

    public Deposit obtainDeposit() {
        return this.deposit;
    }

    public Product obtainProduct() {
        return this.product;
    }

    public Quantity obtainQuantity() {
        return this.quantity;
    }

    public AlfanumericCode obtainLot() {
        return this.allotment;
    }

    @Override
    public Element exportToXML(Document doc) {
        Element productionDelivery = doc.createElement("productionDelivery");

        productionDelivery.appendChild(this.deposit.exportToXML(doc));

        productionDelivery.appendChild(this.product.exportToXML(doc));

        productionDelivery.appendChild(this.quantity.exportToXML(doc));

        if (this.allotment!=null){
            Element allotment = doc.createElement("allotment");
            allotment.appendChild(doc.createTextNode(this.allotment.toString()));
            productionDelivery.appendChild(allotment);
        }

        return productionDelivery;

    }
}
