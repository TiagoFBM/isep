package fabrica.productordersmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.strings.util.StringPredicates;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.production.domain.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "ProductionOrder")
public class ProductionOrder implements AggregateRoot<AlfanumericCode>, CanBeExportedToXML {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "POcode", unique = true)
    private AlfanumericCode POCode;

    @AttributeOverride(name = "date", column = @Column(name = "emissionDate", nullable = false))
    private ProductionDates emissionDate;

    @AttributeOverride(name = "date", column = @Column(name = "previsionDate", nullable = false))
    private ProductionDates previsionDate;

    @ManyToOne
    private Product product;

    private Quantity quantity;

    @Enumerated(EnumType.STRING)
    private Units unit;

    @ElementCollection
    private List<Request> requests = new LinkedList<>();

    private ProductionOrderState state;

    @OneToOne(cascade = {CascadeType.ALL})
    private ProductionOrderReport poReport;

    @ManyToOne
    private ProductionLine productionLine;


    protected ProductionOrder(){

    }

    public ProductionOrder(String idCode, Calendar emissionDate, Calendar previsionDate, Product p, int quant, String unity, List<String> orders){

        addOrders(orders);
        if (this.requests.isEmpty()){
            throw new IllegalArgumentException("There are no orders related to the production order");
        }
        if (!validateProductionOrders(p, emissionDate, previsionDate, unity)){
            throw new IllegalArgumentException();
        }

        this.POCode = AlfanumericCode.valueOf(idCode);
        this.emissionDate = ProductionDates.valueOf(emissionDate);
        this.previsionDate = ProductionDates.valueOf(previsionDate);
        this.product = p;
        this.quantity = new Quantity(quant, unity);
        this.unit = Units.valueOf(unity);
        this.state = new ProductionOrderState(true);
        this.poReport = null;
        this.productionLine = null;
    }

    public AlfanumericCode obtainCode(){return this.POCode;}

    public ProductionDates obtainEmissionDate(){return this.emissionDate;}

    public ProductionDates obtainPrevisionDate(){return this.previsionDate;}

    public Product obtainProduct(){return this.product;}

    public Quantity obtainQuantity(){return this.   quantity;}

    public Units obtainUnit(){return this.unit;}
    
    public ProductionOrderState obtainState(){
        return this.state;
    }

    public List<Request> obtainOrders(){return this.requests;}

    public ProductionOrderReport obtainPOReport() {
        return this.poReport;
    }

    public void updateEmissionDate(ProductionDates emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void updatePrevisionDate(ProductionDates previsionDate) {
        this.previsionDate = previsionDate;
    }

    public void updateProduct(Product product) {
        this.product = product;
    }

    public void updateQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public void updateUnit(Units unit) {
        this.unit = unit;
    }

    public void updateRequests(List<Request> requests) {
        this.requests = new ArrayList<>(requests);
    }

    private boolean validateProductionOrders(Product p, Calendar emissionDate, Calendar previsionDate, String unit){
        return p!=null && !StringPredicates.isNullOrEmpty(unit);
    }

    private void addOrders(List<String> orders){
        for (String s : orders){
            Request po = Request.valueOf(s);
            if (validateOrder(po)) {
                this.requests.add(po);
            }

        }
    }

    private boolean validateOrder(Request req){
        for (Request r : this.requests){
            if (r.equals(req)){
                throw new IllegalArgumentException("Repeated request");
            }
        }
        return true;
    }

    public void updateProductionOrderState(ProductionOrderState state){this.state = state;}

    @Override
    public int compareTo(AlfanumericCode other) {
        return this.POCode.compareTo(other);
    }

    @Override
    public boolean hasIdentity(AlfanumericCode otherId) {
        return this.POCode.equals(otherId);
    }
    
    public boolean isPendingOrSuspended() {
        return state.isSuspended() || state.isPending();
    }


    @Override
    public boolean sameAs(Object o) {
        if (!(o instanceof ProductionOrder))  {
            return false;
        }
        if (this == o) {
            return true;
        }
        ProductionOrder po = (ProductionOrder) o;
        return this.POCode.equals(po.POCode) &&
                this.emissionDate.equals(po.emissionDate) &&
                this.previsionDate.equals(po.previsionDate) &&
                this.product.equals(po.product) &&
                this.quantity.equals(po.quantity) &&
                this.unit.equals(po.unit);
    }

    @Override
    public AlfanumericCode identity() {
        return this.POCode;
    }

    public boolean addOrderID(Request id) {
        if (checkOrderID(id)){
            return requests.add(id);
        }
        return false;
    }

    public boolean checkOrderID(Request id){
        for (Request request : requests){
            if(request.equals(id)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Production Order:\n  " +
                "Code: %s\n  " +
                "Emission Date: %s\n  " +
                "Prevision Date: %s\n  " +
                "Product: %s\n" +
                "Quantity: %s\n" +
                "Unit: %s\n" +
                "Requests: %s\n", this.POCode, this.emissionDate, this.previsionDate, this.product.toString(), this.quantity.toString(),this.unit.toString(),this.requests);
    }


    @Override
    public Element exportToXML(Document doc) {
        Element element = doc.createElement("ProductionOrder");

        Attr POCode = doc.createAttribute("POCode");
        POCode.setValue(this.obtainCode().toString());
        element.setAttributeNode(POCode);

        Element emissionDate = doc.createElement("emissionDate");
        emissionDate.appendChild(doc.createTextNode(this.obtainEmissionDate().toString()));
        element.appendChild(emissionDate);

        Element previsionDate = doc.createElement("previsionDate");
        previsionDate.appendChild(doc.createTextNode(this.obtainPrevisionDate().toString()));
        element.appendChild(previsionDate);

        element.appendChild(this.product.exportToXML(doc));

        element.appendChild(this.quantity.exportToXML(doc));

        Element unit = doc.createElement("unit");
        unit.appendChild(doc.createTextNode(this.obtainUnit().toString()));
        element.appendChild(unit);

        if (this.obtainPOReport()!=null){
            element.appendChild(this.obtainPOReport().exportToXML(doc));
        }

        if (!requests.isEmpty()) {
            Element requests = doc.createElement("Requests");

            for (Request r : this.requests){
                requests.appendChild(r.exportToXML(doc));
            }

            element.appendChild(requests);
        }

        Element state = doc.createElement("state");
        state.appendChild(doc.createTextNode(this.obtainState().toString()));
        element.appendChild(state);

        if (productionLine != null) {
            element.appendChild(this.productionLine.exportToXML(doc));
        }

        return element;
    }

    public void updateProductionLine(ProductionLine pl) {
        this.productionLine = pl;
    }

    public ProductionLine obtainProductionLine() {
        return this.productionLine;
    }

    public void startProductionOrder() {
        this.poReport = new ProductionOrderReport();
    }
}
