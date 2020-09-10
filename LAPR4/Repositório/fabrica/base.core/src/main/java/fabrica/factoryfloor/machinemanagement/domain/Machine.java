package fabrica.factoryfloor.machinemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Preconditions;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.production.domain.AlfanumericCode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Machine implements AggregateRoot<AlfanumericCode>, CanBeExportedToXML {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Embedded
    @Column(unique = true)
    private AlfanumericCode internalCode;

    @Embedded
    @Column(unique = true)
    private ProtocolIdentificationNumber identificationNumber;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(name = "serialNumber", unique = true, nullable = false))
    private SerialNumber serialNumber; // minimo 6 max 20

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "manufacturer", nullable = false))
    private Designation manufacturer;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "model", nullable = false))
    private Designation model;

    @Embedded
    private InstallationDate installationDate;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "operation", nullable = false))
    private Designation operation;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<ConfigurationFile> configurationFiles = new LinkedList<>();

    private MachineStatus machineStatus;



    protected Machine() {}

    public Machine(String internalCode, short identificationNumber, String serialNumber, String manufacturer, String model, Calendar installationDate, String operation) {
        Preconditions.noneNull(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        this.internalCode = AlfanumericCode.valueOf(internalCode);
        this.identificationNumber = ProtocolIdentificationNumber.valueOf(identificationNumber);
        this.serialNumber = SerialNumber.valueOf(serialNumber);
        this.manufacturer = Designation.valueOf(manufacturer);
        this.model = Designation.valueOf(model);
        this.installationDate = InstallationDate.valueOf(installationDate);
        this.operation = Designation.valueOf(operation);
        this.machineStatus = MachineStatus.INACTIVE;
    }

    @Override
    public boolean sameAs (Object o) {
        if (!(o instanceof Machine))  {
            return false;
        }
        if (this == o) {
            return true;
        }
        Machine machine = (Machine) o;
        return  internalCode.equals(machine.internalCode) &&
                identificationNumber.equals(machine.identificationNumber) &&
                serialNumber.equals(machine.serialNumber) &&
                manufacturer.equals(machine.manufacturer) &&
                model.equals(machine.model) &&
                installationDate.equals(machine.installationDate) &&
                operation.equals(machine.operation);
    }

    @Override
    public int compareTo(AlfanumericCode other) {
        return this.internalCode.compareTo(other);
    }

    @Override
    public AlfanumericCode identity() {
        return this.internalCode;
    }

    @Override
    public boolean hasIdentity(AlfanumericCode otherId) {
        return this.internalCode.equals(otherId);
    }

    @Override
    public String toString() {
        return String.format("Machine %s:\n  Identification Number: %s\n  Serial Number: %s\n  Manufacturer: %s\n  Model: %s\n  Installation Date: %s\n  Operation: %s\n  Configuration Files: %s\n  Machine Status: %s", internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation, configurationFiles, machineStatus);
    }

    @Override
    public Element exportToXML(Document doc) {
        Element element = doc.createElement("Machine");

        Attr serialNumber = doc.createAttribute("serialNumber");
        serialNumber.setValue(this.obtainSerialNumber().toString());
        element.setAttributeNode(serialNumber);

        Element protocolId = doc.createElement("identificationNumber");
        protocolId.appendChild(doc.createTextNode(this.identificationNumber.toString()));
        element.appendChild(protocolId);

        Element manufacturer = doc.createElement("manufacturer");
        manufacturer.appendChild(doc.createTextNode(this.obtainManufacturer().toString()));
        element.appendChild(manufacturer);

        Element model = doc.createElement("model");
        model.appendChild(doc.createTextNode(this.obtainModel().toString()));
        element.appendChild(model);

        Element operation = doc.createElement("operation");
        operation.appendChild(doc.createTextNode(this.obtainOperation().toString()));
        element.appendChild(operation);

        Element installationDate = doc.createElement("installationDate");
        installationDate.appendChild(doc.createTextNode(this.obtainInstallationDate().toString()));
        element.appendChild(installationDate);

        return element;
    }

    public Designation obtainManufacturer() {
        return this.manufacturer;
    }
    public Designation obtainModel() {
        return this.model;
    }

    public Designation obtainOperation() {
        return this.operation;
    }

    public InstallationDate obtainInstallationDate() {
        return this.installationDate;
    }

    public SerialNumber obtainSerialNumber() {
        return this.serialNumber;
    }

    public ProtocolIdentificationNumber obtainIdentificationNumber() {
        return this.identificationNumber;
    }

    public List<ConfigurationFile> obtainConfigurationFiles() {
        return new LinkedList<ConfigurationFile>(configurationFiles);
    }

    public void updateConfigurationFiles(List<ConfigurationFile> configurationFile) {
        configurationFiles = new LinkedList<>(configurationFile);
    }

    public void addConfigurationFile(ConfigurationFile cf) {
        if (configurationFiles.contains(cf)) {
            throw new IllegalArgumentException("The machine already contains that configuration file");
        }

        configurationFiles.add(cf);
    }

    public void updateMachineStatusToActive() {
        this.machineStatus = MachineStatus.ACTIVE;
    }

    public void updateMachineStatusToForcedStop() {
        this.machineStatus = MachineStatus.FORCED_STOP;
    }

    public void updateMachineStatusToInactive() {
        this.machineStatus = MachineStatus.INACTIVE;
    }

    public boolean isActive() {
        return machineStatus == MachineStatus.ACTIVE;
    }

    public boolean isInactive() {
        return machineStatus == MachineStatus.INACTIVE;
    }

    public boolean hasForceStopped() {
        return machineStatus == MachineStatus.FORCED_STOP;
    }
}
