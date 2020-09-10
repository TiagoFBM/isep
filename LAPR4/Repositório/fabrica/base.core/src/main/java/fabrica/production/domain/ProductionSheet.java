package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import fabrica.exportToXML.CanBeExportedToXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity //Só para JPA
public class ProductionSheet implements ValueObject, CanBeExportedToXML {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    private Quantity standardQuantity;

    @OneToMany(targetEntity=ProductionSheetLine.class, cascade = CascadeType.ALL)
    private List<ProductionSheetLine> lines;

    protected ProductionSheet(){
        lines = new ArrayList<>();
    }

    public ProductionSheet(List<ProductionSheetLine> lines, int quantity, String units) {
        this.lines = new ArrayList<>(lines);
        this.standardQuantity = new Quantity(quantity,units);
    }

    public ProductionSheet(int quantity, String units) {
        this.lines = new ArrayList<>();
        this.standardQuantity = new Quantity(quantity,units);
    }

    public boolean addLine(Product product, int quantity, String units){
        ProductionSheetLine line = searchLineWithProductRegisted(product);

        if (line!=null){
            throw new IllegalArgumentException("You have already added this Product/Raw Material.");
        }

        ProductionSheetLine newLine = new ProductionSheetLine(product,quantity,units);
        return lines.add(newLine);
    }

    public boolean addLine(RawMaterial rawMaterial, int quantity, String units){
        ProductionSheetLine line = searchLineWithRawMaterialRegisted(rawMaterial);

        if (line!=null){
            throw new IllegalArgumentException("You have already added this Product/Raw Material.");
        }

        ProductionSheetLine newLine = new ProductionSheetLine(rawMaterial,quantity,units);
        return lines.add(newLine);
    }

    private ProductionSheetLine searchLineWithProductRegisted(Product product){
        for(ProductionSheetLine line : lines){
            if (line.obtainProduct()!=null && line.hasProduct(product)){
                return line;
            }
        }
        return null;
    }

    private ProductionSheetLine searchLineWithRawMaterialRegisted(RawMaterial rawMaterial){
        for(ProductionSheetLine line : lines){
            if (line.obtainRawMaterial()!=null && line.hasRawMaterial(rawMaterial)){
                return line;
            }
        }
        return null;
    }

    public List<ProductionSheetLine> listAllLines(){
        return new ArrayList<>(lines);
    }

    @Override
    public Element exportToXML(Document doc) {
        Element productionSheet = doc.createElement("ProductSheet");

        productionSheet.appendChild(this.standardQuantity.exportToXML(doc));


        for (ProductionSheetLine psl : this.lines){
            productionSheet.appendChild(psl.exportToXML(doc));
        }

        return productionSheet;

    }

    public String productionSheetLinesToString() {
        StringBuilder s = new StringBuilder();
        for (ProductionSheetLine psl : lines) {
            s.append(psl.toString()).append("\n");
        }
        s.setLength(s.length() - 2);
        return s.toString();
    }

    public Quantity obtainQuantity() {
        return this.standardQuantity;
    }

    public Quantity getLineQuantityByID(AlfanumericCode code) {
        for (ProductionSheetLine psl : lines) {
            if (psl.obtainRawMaterial().identity().equals(code) || psl.obtainProduct().identity().equals(code)) {
                return psl.obtainQuantity();
            }
        }
        return null;
    }

    public boolean productionSheetLinesHasCode(AlfanumericCode alfCode) {
        for (ProductionSheetLine psl : lines) {
            if (psl.obtainProduct() != null) {
                if (psl.obtainProduct().identity().equals(alfCode)) {
                    return true;
                }
            } else if (psl.obtainRawMaterial() != null) {
                if (psl.obtainRawMaterial().identity().equals(alfCode)) {
                    return true;
                }
            }
        }
        return false;
    }
}
