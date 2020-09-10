package fabrica.production.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.production.services.exportDataSheet;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.io.IOException;


@Entity
public class RawMaterial implements AggregateRoot<AlfanumericCode>, CanBeExportedToXML {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @AttributeOverride(name = "code", column = @Column(name = "RawMaterialCode", unique = true))
    private AlfanumericCode code;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "Description"))
    private FullDescription description;

    @ManyToOne
    private RawMaterialCategory category;

    @Lob
    @Column(name = "datasheet", columnDefinition="BLOB")
    private byte[] dataSheet;


    public RawMaterial(String code, String description, RawMaterialCategory category, DataSheet dataSheet){
        if (validateDataSheet(dataSheet)){
            throw new IllegalArgumentException("DataSheet doen´t exists");
        }
        this.code = AlfanumericCode.valueOf(code);
        this.description = FullDescription.valueOf(description);
        this.category = category;
        this.dataSheet = dataSheet.obtainBytes();
    }

    protected RawMaterial(){

    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof RawMaterial)) {
            return false;
        }

        final RawMaterial that = (RawMaterial) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity()) &&
                this.description.equals(that.description) &&
                this.category.equals(that.category) &&
                this.dataSheet.equals(that.dataSheet);
    }

    @Override
    public int compareTo(AlfanumericCode other) {
        return this.identity().toString().compareToIgnoreCase(other.toString());
    }

    @Override
    public AlfanumericCode identity() {
        return this.code;
    }

    @Override
    public boolean hasIdentity(AlfanumericCode otherId) {
        return this.code.equals(otherId);
    }

    @Override
    public boolean equals(Object o){
        return DomainEntities.areEqual(this, o);
    }

    public void updateDescription(String description) {
        this.description = FullDescription.valueOf(description);
    }

    public FullDescription obtainDescription() {
        return this.description;
    }

    public RawMaterialCategory obtainCategory() {
        return category;
    }

    public byte[] obtainDataSheet() {return dataSheet;}

    public boolean validateDataSheet(DataSheet ds){
        return ds==null;
    }

    @Override
    public String toString() {
        return String.format("Raw Material: %s - %s - %s",this.code,this.description,this.category);
    }

    @Override
    public Element exportToXML(Document doc) {

        exportDataSheet service = new exportDataSheet();

        Element e = doc.createElement("RawMaterial");

        Attr code = doc.createAttribute("code");
        code.setValue(this.code.toString());
        e.setAttributeNode(code);

        Element description = doc.createElement("description");
        description.appendChild(doc.createTextNode(this.description.toString()));
        e.appendChild(description);

        e.appendChild(this.category.exportToXML(doc));

        Element element= doc.createElement("DataSheet");
        Element referency = doc.createElement("Referency");

        try {
            referency.appendChild(doc.createTextNode(service.readBytes(this)));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        element.appendChild(referency);
        e.appendChild(element);

        return e;
    }

    public AlfanumericCode obtainCode() {
        return code;
    }
}

