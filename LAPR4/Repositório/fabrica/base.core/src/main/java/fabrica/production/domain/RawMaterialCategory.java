package fabrica.production.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import fabrica.exportToXML.CanBeExportedToXML;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;

@Entity
public class RawMaterialCategory implements AggregateRoot<ShortAlfanumericCode>, CanBeExportedToXML {

    @Version
    private Long version;

    @Id
    @GeneratedValue
    private Long pk;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(name = "categoryID", unique = true))
    private ShortAlfanumericCode categoryID;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "briefDescription"))
    private BriefDescription briefDescription;

    protected RawMaterialCategory(){

    }

    public RawMaterialCategory( String categoryID, String briefDescription){
        Preconditions.noneNull(categoryID, briefDescription);
        this.categoryID = ShortAlfanumericCode.valueOf(categoryID);
        this.briefDescription = BriefDescription.valueOf(briefDescription);
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof RawMaterialCategory)) {
            return false;
        }

        final RawMaterialCategory that = (RawMaterialCategory) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity()) &&
                this.briefDescription.equals(that.briefDescription);
    }

    @Override
    public ShortAlfanumericCode identity() {
        return this.categoryID;
    }

    @Override
    public boolean equals(Object o){
        return DomainEntities.areEqual(this, o);
    }

    public static RawMaterialCategory valueOf(String categoryId, String briefDescription){
        return new RawMaterialCategory(categoryId,briefDescription);
    }

    @Override
    public int compareTo(ShortAlfanumericCode other) {
        return this.identity().toString().compareToIgnoreCase(other.toString());
    }

    public ShortAlfanumericCode obtainRawMaterialCode() {return this.categoryID;}

    public BriefDescription obtainBriefDescription() {
        return briefDescription;
    }

    @Override
    public String toString() {
        return String.format("RawMaterialCategory: %s - %s",this.categoryID,this.briefDescription);
        }

    @Override
    public Element exportToXML(Document doc) {

        Element elem = doc.createElement("RawMaterialCategory");

        Attr categoryCode = doc.createAttribute("categoryID");
        categoryCode.setValue(this.obtainRawMaterialCode().toString());
        elem.setAttributeNode(categoryCode);

        Element categoryDescription = doc.createElement("briefDescription");
        categoryDescription.appendChild(doc.createTextNode(this.obtainBriefDescription().toString()));
        elem.appendChild(categoryDescription);

        return elem;
    }
}
