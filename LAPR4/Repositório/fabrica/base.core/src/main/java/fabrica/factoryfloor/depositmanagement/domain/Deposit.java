package fabrica.factoryfloor.depositmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;
import fabrica.exportToXML.CanBeExportedToXML;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Tiago Sousa
 */
@Entity
public class Deposit implements AggregateRoot<Designation>, Serializable , CanBeExportedToXML {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    //Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @AttributeOverride(name = "name", column = @Column(name = "internalCode", nullable = false, unique = true))
    private Designation internalCode;

    //Description
    @AttributeOverride(name = "name", column = @Column(name = "description", nullable = false))
    private Designation desc;

    /**
     * Deposit contructor
     * @param code: identification code
     * @param desc: description
     */
    public Deposit(final String code, final String desc) {
        Preconditions.noneNull(code, desc);
        this.internalCode=Designation.valueOf(code);
        setDesc(desc);
    }

    protected Deposit() {
        //for ORM only
    }

    /**
     * Validates and sets description
     * @param desc
     */
    public void setDesc(final String desc) {
        if (descriptionMeetsMinimumRequirements(desc)) this.desc = Designation.valueOf(desc);
        else throw new IllegalArgumentException("Invalid description");
    }

    /**
     * Checks if description is null or empty
     * @param description
     * @return True if it's not null or empty
     */
    private static boolean descriptionMeetsMinimumRequirements(final String description) {
        return !StringPredicates.isNullOrEmpty(description);
    }

    /**
     * Checks if two objects are the same
     * @param other: the other object
     * @return True if they're the same object, false otherwise
     */
    @Override
    public boolean sameAs(Object other) {
        final Deposit deposit=(Deposit) other;
        return this.equals(deposit) && this.desc.equals(deposit.desc);
    }

    /**
     * Returns the identity of this object
     * @return the deposit's code
     */
    @Override
    public Designation identity() {
        return this.internalCode;
    }

    /**
     * HashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Checks if two objects have the same ID
     * @param o: other object
     * @return True if they have the same ID
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * toString()
     * @return Object's package and ID
     */
    @Override
    public String toString() {
        return "Deposit ID=" + internalCode;
    }

    @Override
    public Element exportToXML(Document doc) {
        Element elem = doc.createElement("Deposit");

        Attr depositCode = doc.createAttribute("internalCode");
        depositCode.setValue(this.internalCode.toString());
        elem.setAttributeNode(depositCode);

        Element description = doc.createElement("desc");
        description.appendChild(doc.createTextNode(this.desc.toString()));
        elem.appendChild(description);

        return elem;
    }
}
