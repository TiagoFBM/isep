package fabrica.scm.messagesmanagement.domain;

import fabrica.exportToXML.CanBeExportedToXML;

import eapli.framework.math.util.NumberPredicates;
import eapli.framework.time.util.CalendarPredicates;
import eapli.framework.time.util.Calendars;

import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import fabrica.production.domain.RawMaterial;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
public class Consumption implements Serializable, CanBeExportedToXML {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @OneToOne
    private RawMaterial rawMaterial;

    @OneToOne
    private Product product;

    @Embedded
    private Quantity quantity;

    private Date movementDate;

    @OneToOne
    private Deposit deposit;

    protected Consumption() {}

    public Consumption(RawMaterial rawMaterial, int quantity, String unit, Date movementDate, Deposit deposit) {
        if (!validateMovement(quantity, movementDate, deposit)) {
            throw new IllegalArgumentException("Invalid consumption");
        }
        this.rawMaterial = rawMaterial;
        this.product = null;
        this.quantity = new Quantity(quantity, unit);
        this.movementDate = movementDate;
        this.deposit = deposit;
    }

    public Consumption(RawMaterial rawMaterial, int quantity, String unit, Date movementDate) {
        if (!validateMovement(quantity, movementDate)) {
            throw new IllegalArgumentException("Invalid consumption");
        }
        this.rawMaterial = rawMaterial;
        this.product = null;
        this.quantity = new Quantity(quantity, unit);
        this.movementDate = movementDate;
        this.deposit = null;
    }

    public Consumption(Product product, int quantity, String unit, Date movementDate, Deposit deposit) {
        if (!validateMovement(quantity, movementDate, deposit)) {
            throw new IllegalArgumentException("Invalid movement");
        }
        this.rawMaterial = null;
        this.product = product;
        this.quantity = new Quantity(quantity, unit);
        this.movementDate = movementDate;
        this.deposit = deposit;
    }

    public Consumption(Product product, int quantity, String unit, Date movementDate) {
        if (!validateMovement(quantity, movementDate)) {
            throw new IllegalArgumentException("Invalid movement");
        }
        this.rawMaterial = null;
        this.product = product;
        this.quantity = new Quantity(quantity, unit);
        this.movementDate = movementDate;
        this.deposit = null;
    }

    private boolean validateMovement(int quantity, Date consumptionDate, Deposit deposit) {
        return deposit != null && NumberPredicates.isPositive(quantity) && validateDate(consumptionDate);
    }

    private boolean validateDate(Date consumptionDate) {
        Calendar calendar = Calendars.fromDate(consumptionDate);
        if (CalendarPredicates.isBeforeToday(calendar)) {
            return true;
        } else if (CalendarPredicates.isToday(calendar)) {
            return CalendarPredicates.isBeforeNow(calendar);
        }
        return false;
    }

    private boolean validateMovement(int quantity, Date movementDate) {
        return NumberPredicates.isPositive(quantity) && CalendarPredicates.isBeforeToday(Calendars.fromDate(movementDate)) && CalendarPredicates.isBeforeNow(Calendars.fromDate(movementDate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consumption)) return false;
        Consumption movement = (Consumption) o;
        return quantity.equals(movement.quantity) &&
                deposit.equals(movement.deposit) &&
                movementDate.equals(movement.movementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, deposit, movementDate);
    }

    @Override
    public String toString() {
        return "Movement " + pk + ":\n" +
                "  Quantity: " + quantity +
                "\n  " + deposit.toString() +
                "\n  Movement Date: " + movementDate +
                '\n';
    }

    public RawMaterial obtainRawMaterial() {
        return rawMaterial;
    }

    public Product obtainProduct() {
        return product;
    }

    public Deposit obtainDeposit() {
        return deposit;
    }

    public Quantity obtainQuantity() {
        return quantity;
    }

    public Object obtainMovementDate() {
        return movementDate;
    }

    @Override
    public Element exportToXML(Document doc) {

        Element consumption = doc.createElement("consumption");

        consumption.appendChild(this.quantity.exportToXML(doc));

        Element movementDate = doc.createElement("movementDate");
        movementDate.appendChild(doc.createTextNode(this.movementDate.toString()));
        consumption.appendChild(movementDate);

        if (this.deposit != null){
            consumption.appendChild(this.deposit.exportToXML(doc));
        }

        if (product != null) {
            consumption.appendChild(this.product.exportToXML(doc));
        } else {
            consumption.appendChild(this.rawMaterial.exportToXML(doc));
        }

        return consumption;
    }
}
