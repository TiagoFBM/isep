package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import fabrica.exportToXML.CanBeExportedToXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;

@Entity //Só para JPA
public class ProductionSheetLine implements ValueObject, CanBeExportedToXML {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @ManyToOne
    private Product product;

    @ManyToOne
    private RawMaterial rawMaterial;

    private Quantity quantity;

    protected ProductionSheetLine() {
    }

    public ProductionSheetLine(Product product, int quantity, String unit){
        this.product = product;
        this.rawMaterial = null;
        this.quantity = new Quantity(quantity,unit);
    }

    public ProductionSheetLine(RawMaterial rawMaterial, int quantity, String unit){
        this.product = null;
        this.rawMaterial = rawMaterial;
        this.quantity = new Quantity(quantity,unit);
    }

     public boolean hasProduct(Product product) {
        return this.product.equals(product);
    }

    public boolean hasRawMaterial(RawMaterial rawMaterial) {
        return this.rawMaterial.equals(rawMaterial);
    }

    public Product obtainProduct(){
        return this.product;
    }

    public RawMaterial obtainRawMaterial(){
        return this.rawMaterial;
    }

    @Override
    public String toString() {
        if (this.product!=null){
            return String.format("ProductionSheetLine: \"%d %s\" of \"%s\".",this.quantity.obtainValor(),this.quantity.obtainUnity().toString(),this.product.identity());
        } else {
            return String.format("ProductionSheetLine: \"%d %s\" of \"%s\".",this.quantity.obtainValor(),this.quantity.obtainUnity().toString(),this.rawMaterial.identity());
        }
    }

    @Override
    public Element exportToXML(Document doc) {
        Element psl = doc.createElement("ProductionSheetLine");
        if (this.product == null){
            psl.appendChild(this.rawMaterial.exportToXML(doc));
        }else{
            psl.appendChild(this.product.exportToXML(doc));
        }
        psl.appendChild(this.quantity.exportToXML(doc));
        return psl;
    }

    public Quantity obtainQuantity() {
        return this.quantity;
    }
}
