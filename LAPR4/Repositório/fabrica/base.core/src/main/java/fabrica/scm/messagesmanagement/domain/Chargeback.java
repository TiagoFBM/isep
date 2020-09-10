package fabrica.scm.messagesmanagement.domain;

import fabrica.exportToXML.CanBeExportedToXML;

import eapli.framework.math.util.NumberPredicates;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import fabrica.production.domain.RawMaterial;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Chargeback implements CanBeExportedToXML {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Version
    private Long version;

    @OneToOne
    private RawMaterial rawMaterial;

    @OneToOne
    private Product product;

    @OneToOne
    private Deposit deposit;

    private Quantity quantity;

    protected Chargeback() {}

    public Chargeback(RawMaterial rawMaterial, Deposit deposit, int quantity, String unit) {
        if (!verifyChargeback(rawMaterial, deposit, quantity, unit)) {
            throw new IllegalArgumentException("Invalid chargeback");
        }
        this.rawMaterial = rawMaterial;
        this.product = null;
        this.deposit = deposit;
        this.quantity = new Quantity(quantity, unit);
    }

    public Chargeback(Product product, Deposit deposit, int quantity, String unit) {
        if (!verifyChargeback(product, deposit, quantity, unit)) {
            throw new IllegalArgumentException("Invalid chargeback");
        }
        this.rawMaterial = null;
        this.product = product;
        this.deposit = deposit;
        this.quantity = new Quantity(quantity, unit);
    }

    private boolean verifyChargeback(RawMaterial rawMaterial, Deposit deposit, int quantity, String unit) {
        return rawMaterial != null && deposit != null && NumberPredicates.isPositive(quantity);
    }

    private boolean verifyChargeback(Product product, Deposit deposit, int quantity, String unit) {
        return product != null && deposit != null && NumberPredicates.isPositive(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chargeback)) return false;
        Chargeback that = (Chargeback) o;
        return Objects.equals(rawMaterial, that.rawMaterial) &&
                Objects.equals(product, that.product) &&
                deposit.equals(that.deposit) &&
                quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawMaterial, product, deposit, quantity);
    }

    @Override
    public String toString() {
        AlfanumericCode id = rawMaterial == null ? product.identity() : rawMaterial.identity();
        return "Chargeback " + pk + ":" +
                "\n  rawMaterial: " + id +
                "\n  deposit: " + deposit +
                "\n  quantity: " + quantity;
    }

    public RawMaterial obtainRawMaterial() {
        return this.rawMaterial;
    }

    public Product obtainProduct() {
        return this.product;
    }

    public Deposit obtainDeposit() {
        return this.deposit;
    }

    public Quantity obtainQuantity() {
        return this.quantity;
    }

    @Override
    public Element exportToXML(Document doc) {

        Element chargeback = doc.createElement("chargeback");

        chargeback.appendChild(this.quantity.exportToXML(doc));

        chargeback.appendChild(this.deposit.exportToXML(doc));

        if (this.product==null){
            chargeback.appendChild(this.rawMaterial.exportToXML(doc));
        }else{
            chargeback.appendChild(this.product.exportToXML(doc));
        }

        return chargeback;
    }
}
