package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import fabrica.exportToXML.CanBeExportedToXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.Embeddable;

@Embeddable
public class ProductCategory implements Comparable<ProductCategory>, ValueObject, CanBeExportedToXML {
    
    private static final long serialVersionUID = 1L;
    private String category;

    protected ProductCategory(){
    }

    private ProductCategory(String category) {
        if (!validateCategory(category)){
            throw new IllegalArgumentException("Invalid category.\n");
        }

        this.category = category;
    }

    public static ProductCategory valueOf(String category){
        return new ProductCategory(category);
    }

    private boolean validateCategory(String category){
        return !StringPredicates.isNullOrEmpty(category) &&
                !StringPredicates.isNullOrWhiteSpace(category) &&
                category.length()<=30;
    }

    @Override
    public String toString() {
        return this.category;
    }


    @Override
    public int compareTo(ProductCategory o) {
        return this.category.compareToIgnoreCase(o.category);
    }

    @Override
    public Element exportToXML(Document doc) {
        return null;
    }
}
