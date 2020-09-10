package fabrica.scm.messagesmanagement.domain;


import eapli.framework.math.util.NumberPredicates;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;

@Entity

public class Production implements CanBeExportedToXML {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @ManyToOne
    private Product product;

    @Embedded
    private Quantity quantity;

    @Embedded
    private AlfanumericCode allotment;

    protected Production() {}

    public Production(Product product, int quantity, String unit) {
        if (!validateProduction(product, quantity)) {
            throw new IllegalArgumentException("Invalid production delivery");
        }

        this.product = product;
        this.quantity = new Quantity(quantity, unit);
        this.allotment = null;
    }

    public Production(Product product, int quantity, String unit, String allotment) {
        if (!validateProduction(product, quantity)) {
            throw new IllegalArgumentException("Invalid production delivery");
        }

        this.product = product;
        this.quantity = new Quantity(quantity, unit);
        this.allotment = AlfanumericCode.valueOf(allotment);
    }


    private boolean validateProduction(Product product, int quantity) {
        return product != null && NumberPredicates.isPositive(quantity);
    }

    public Quantity obtainQuantity() {
        return quantity;
    }

    public AlfanumericCode obtainAllotment() { return allotment; }

    public Product obtainProduct() {
        return this.product;
    }

    public AlfanumericCode obtainLot() {
        return this.allotment;
    }

    @Override
    public Element exportToXML(Document doc) {
        Element production = doc.createElement("production");

        production.appendChild(this.product.exportToXML(doc));

        production.appendChild(this.quantity.exportToXML(doc));

        if (this.allotment!=null){
            Element allotment = doc.createElement("allotment");
            allotment.appendChild(doc.createTextNode(this.allotment.toString()));
            production.appendChild(allotment);
        }



        return production;
    }
}
