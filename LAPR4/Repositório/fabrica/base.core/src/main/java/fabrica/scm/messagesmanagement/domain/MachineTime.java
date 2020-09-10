package fabrica.scm.messagesmanagement.domain;

import fabrica.exportToXML.CanBeExportedToXML;

import eapli.framework.domain.model.ValueObject;

import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.MachineStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class MachineTime implements ValueObject, CanBeExportedToXML {

    @ManyToOne
    private Machine machine;

    private Date date;

    private MachineStatus newMachineStatus;

    protected MachineTime() {}

    public MachineTime(Machine m, Date d, MachineStatus newMachineStatus) {
        if (!validMachineTime(m, d, newMachineStatus)) {
            throw new IllegalArgumentException("Invalid machine time");
        }
        this.machine = m;
        this.date = d;
        this.newMachineStatus = newMachineStatus;
    }

    private boolean validMachineTime(Machine m, Date d, MachineStatus newMachineStatus) {
        return m != null && d != null && newMachineStatus != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineTime)) return false;
        MachineTime that = (MachineTime) o;
        return machine.equals(that.machine) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machine, date);
    }

    @Override
    public String toString() {
        return "Machine " + machine.identity() + " - " + date;
    }

    public Machine obtainMachine() {
        return this.machine;
    }

    public Date obtainDate() {
        return date;
    }

    @Override
    public Element exportToXML(Document doc) {
        Element machineTime = doc.createElement("machineTime");

        machineTime.appendChild(this.machine.exportToXML(doc));

        Element date = doc.createElement("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.appendChild(doc.createTextNode(sdf.format(this.date)));
        machineTime.appendChild(date);

        Element newStatus = doc.createElement("newStatus");
        newStatus.appendChild(doc.createTextNode(this.newMachineStatus.name()));
        machineTime.appendChild(newStatus);

        return machineTime;
    }

    public MachineStatus obtainMachineStatus() {
        return this.newMachineStatus;
    }
}
