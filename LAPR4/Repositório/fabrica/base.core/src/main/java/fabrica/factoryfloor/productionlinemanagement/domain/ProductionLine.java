package fabrica.factoryfloor.productionlinemanagement.domain;

import eapli.framework.general.domain.model.Designation;
import eapli.framework.strings.util.StringPredicates;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.production.domain.AlfanumericCode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class ProductionLine implements AggregateRoot<Designation>, Serializable, CanBeExportedToXML {

    private static final long serialVersionUID = 19L;
    @Version
    private Long version;

    //Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @AttributeOverride(name = "name", column = @Column(name = "internalCode", unique = true, nullable = false))
    private Designation internalCode;

    @AttributeOverride(name = "name", column = @Column(name = "description", nullable = false))
    private Designation desc;

    @OneToMany
    private List<Machine> machines=new LinkedList<>();

    public ProductionLine(final String code, final String desc) {
        Preconditions.noneNull(code, desc);
        this.internalCode=Designation.valueOf(code);
        setDesc(desc);
    }

    protected ProductionLine() {

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

    public boolean addMachine(final Machine machine) {
        if (!checkMachines(machine)) return false;
        return machines.add(machine);
    }

    public boolean hasMachines() {
        return !machines.isEmpty();
    }

    public void addMachine(final Machine machine, int place) {
        if (checkMachines(machine))
        machines.add(place, machine);
    }

    private boolean checkMachines(final Machine machine) {
        for (Machine mach : machines) {
            if (mach.equals(machine)) {
                return false;
            }
        }
        return true;
    }

    public Machine removeMachine(int place) {
        return machines.remove(place);
    }

    public boolean removeMachine(Machine machine) {
        return machines.remove(machine);
    }

    public boolean removeMachine(AlfanumericCode code) {
        for (Machine machine : machines) {
            if (machine.compareTo(code)==0) return machines.remove(machine);
        }
        return false;
    }

    public List<Machine> machineList() {
        return new LinkedList<>(machines);
    }

    /**
     * Checks if two objects are the same
     * @param other: the other object
     * @return True if they're the same object, false otherwise
     */
    @Override
    public boolean sameAs(Object other) {
        final ProductionLine pl=(ProductionLine) other;
        return this.equals(pl) && this.desc.equals(pl.desc);
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

    @Override
    public String toString() {
        StringBuilder out= new StringBuilder("Production Line ID=" + internalCode+ "\n");
        for (Machine mach : machines) {
            out.append("    ").append(mach.toString()).append("\n");
        }
        return out.toString();
    }

    @Override
    public Element exportToXML(Document doc) {
        Element element = doc.createElement("ProductionLine");

        Attr internalCode = doc.createAttribute("internalCode");
        internalCode.setValue(this.obtainInternalCode().toString());
        element.setAttributeNode(internalCode);

        Element description = doc.createElement("desc");
        description.appendChild(doc.createTextNode(this.obtainDescription().toString()));
        element.appendChild(description);

        Element machines = doc.createElement("Machines");

        for (Machine m : this.machines){
            machines.appendChild(m.exportToXML(doc));
        }
        element.appendChild(machines);
        return element;
    }

    private Designation obtainDescription() {
        return this.desc;
    }

    public Designation obtainInternalCode() {
        return this.internalCode;
    }

    public List<Machine> obtainMachines() {return this.machines;}
}
