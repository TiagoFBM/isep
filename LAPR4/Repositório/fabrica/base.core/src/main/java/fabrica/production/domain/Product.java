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
@Table( name = "Product")
public class Product implements AggregateRoot<AlfanumericCode>, CanBeExportedToXML {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @AttributeOverride(name = "code", column = @Column(name = "fabricationCode", unique = true))
    private AlfanumericCode fabricationCode;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(name = "comercialCode",unique = true))
    private AlfanumericCode comercialCode;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "briefDescription"))
    private BriefDescription briefDescription;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "description"))
    private FullDescription fullDescription;

    @Embedded
    private ProductCategory productCategory;

    @OneToOne(cascade = {CascadeType.ALL})
    @AttributeOverride(name = "standardQuantity.quantity", column = @Column(name = "ProductionSheetQuantity"))
    @AttributeOverride(name = "standardQuantity.unity", column = @Column(name = "ProductionSheetUnit"))
    private ProductionSheet productionSheet;

    @Enumerated(EnumType.STRING)
    private Units unit;

    protected Product(){
        // for ORM only
    }

    public Product(String fabricationCode, String comercialCode, String briefDescription, String fullDescription, String unit, String productCategory){
        Preconditions.noneNull(fabricationCode, comercialCode, briefDescription,fullDescription,productCategory,unit);
        this.fabricationCode = AlfanumericCode.valueOf(fabricationCode);
        this.comercialCode = AlfanumericCode.valueOf(comercialCode);
        this.briefDescription = BriefDescription.valueOf(briefDescription);
        this.fullDescription = FullDescription.valueOf(fullDescription);
        this.productCategory = ProductCategory.valueOf(productCategory);
        this.unit = Units.valueOf(unit);
        this.productionSheet = null;
    }

    public boolean hasProductionSheet(){
        return this.productionSheet!=null;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Product)) {
            return false;
        }

        final Product that = (Product) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity()) &&
                this.comercialCode.toString().equalsIgnoreCase((that.comercialCode).toString()) &&
                this.briefDescription.toString().equalsIgnoreCase((that.briefDescription).toString()) &&
                this.fullDescription.toString().equalsIgnoreCase((that.fullDescription).toString()) &&
                this.productCategory.toString().equalsIgnoreCase((that.productCategory).toString()) &&
                this.unit.toString().equalsIgnoreCase(that.unit.toString());
    }

    @Override
    public int compareTo(AlfanumericCode other) {
        return this.identity().compareTo(other);
    }

    @Override
    public AlfanumericCode identity() {
        return this.fabricationCode;
    }

    @Override
    public boolean hasIdentity(AlfanumericCode otherId) {
        return this.fabricationCode.equals(otherId);
    }

    @Override
    public boolean equals(Object o){
        return DomainEntities.areEqual(this, o);
    }

    public void createProductionSheet(int quantidade, String unidade){
        this.productionSheet = new ProductionSheet(quantidade,unidade);
    }

    public boolean addLineToProductionSheet(Product product, int quantidade, String unidade) {
        return this.productionSheet.addLine(product,quantidade,unidade);
    }

    public boolean addLineToProductionSheet(RawMaterial rawMaterial, int quantidade, String unidade) {
       return this.productionSheet.addLine(rawMaterial,quantidade,unidade);
    }

    public void updateBriefDescription(String briefDescription) {
        this.briefDescription = BriefDescription.valueOf(briefDescription);
    }

    public void updateFullDescription(String fullDescription) {
        this.fullDescription = FullDescription.valueOf(fullDescription);
    }

    public void updateCategory(String productCategory) {
        this.productCategory = ProductCategory.valueOf(productCategory);
    }

    public BriefDescription obtainBriefDescription() {
        return briefDescription;
    }

    public ProductionSheet obtainProductionSheet() {
        return this.productionSheet;
    }

    public FullDescription obtainFullDescription() {
        return fullDescription;
    }

    public ProductCategory obtainCategory() {
        return productCategory;
    }

    public AlfanumericCode obtainComercialCode() {
        return comercialCode;
    }

    public AlfanumericCode obtainFabricationCode(){ return fabricationCode; }

    public Units obtainUnit() {
        return unit;
    }

    public void updateUnit(String unit) {
        this.unit = Units.valueOf(unit);
    }

    @Override
    public String toString(){
        return String.format("Product: %s - %s - %s - %s",
                this.fabricationCode.toString(),this.briefDescription.toString(),this.briefDescription.toString(),this.fullDescription.toString());
    }

    @Override
    public Element exportToXML(Document doc) {
        Element product = doc.createElement("Product");

        //Atributos product

        Attr fabricationCode = doc.createAttribute("fabricationCode");
        fabricationCode.setValue(this.obtainFabricationCode().toString());
        product.setAttributeNode(fabricationCode);

        Element commercialCode = doc.createElement("comercialCode");
        commercialCode.appendChild(doc.createTextNode(this.obtainComercialCode().toString()));
        product.appendChild(commercialCode);

        Element briefDescription = doc.createElement("briefDescription");
        briefDescription.appendChild(doc.createTextNode(this.obtainBriefDescription().toString()));
        product.appendChild(briefDescription);

        Element fullDescription = doc.createElement("fullDescription");
        fullDescription.appendChild(doc.createTextNode(this.obtainFullDescription().toString()));
        product.appendChild(fullDescription);

        Element productCategory = doc.createElement("productCategory");
        productCategory.appendChild(doc.createTextNode(this.obtainCategory().toString()));
        product.appendChild(productCategory);

        Element unit = doc.createElement("unit");
        unit.appendChild(doc.createTextNode(this.obtainUnit().toString()));
        product.appendChild(unit);


        if (this.hasProductionSheet()){
            product.appendChild(this.obtainProductionSheet().exportToXML(doc));
        }

        return product;
    }
}
