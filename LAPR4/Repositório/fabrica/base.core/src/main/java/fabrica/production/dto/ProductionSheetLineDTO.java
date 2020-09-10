package fabrica.production.dto;

import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import fabrica.production.domain.RawMaterial;

public class ProductionSheetLineDTO {

    public Product product;
    public RawMaterial rawMaterial;
    public Quantity quantity;

    public ProductionSheetLineDTO(Product product, int quantity, String unit){
        this.product = product;
        this.rawMaterial = null;
        this.quantity = new Quantity(quantity,unit);
    }

    public ProductionSheetLineDTO(RawMaterial rawMaterial, int quantity, String unit){
        this.product = null;
        this.rawMaterial = rawMaterial;
        this.quantity = new Quantity(quantity,unit);
    }

    @Override
    public String toString() {
        if(this.product!=null){
            return this.product.toString();
        } else {
            return this.rawMaterial.toString();
        }
    }
}
