package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import fabrica.exportToXML.CanBeExportedToXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Quantity implements ValueObject, CanBeExportedToXML {

    private int valor;

    @Enumerated(EnumType.STRING)
    private Units unity;

    public Quantity() {
        //for JPA
    }

    //Units unit = Units.valueOf("READY");
    public Quantity(int quantity) {
        if(!validatesQuantity(quantity)){
            throw new IllegalArgumentException("The quantity must be a positive value higher than 0.");
        }
        this.valor=quantity;
        this.unity = Units.UN;
    }

    public Quantity(int quantity, String unit){
        if(!validatesQuantity(quantity)){
            throw new IllegalArgumentException("The quantity must be a positive value higher than 0.");
        }
        this.valor=quantity;
        this.unity = Units.valueOf(unit);

    }

    public int obtainValor() {
        return valor;
    }

    public Units obtainUnity() {
        return unity;
    }

    private boolean validatesQuantity(int quantity){
        return quantity>0;
    }

    @Override
    public String toString() {
        return String.format("%d %s",this.valor,this.unity);
    }


    @Override
    public Element exportToXML(Document doc) {

        Element quantity = doc.createElement("Quantity");

        Element valor= doc.createElement("valor");
        valor.appendChild(doc.createTextNode("" + this.obtainValor()));
        quantity.appendChild(valor);

        Element unit = doc.createElement("unity");
        unit.appendChild(doc.createTextNode(this.unity.toString()));
        quantity.appendChild(unit);

        return quantity;

    }


}
