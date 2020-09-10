package fabrica.productordersmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import fabrica.exportToXML.CanBeExportedToXML;
import fabrica.production.domain.AlfanumericCode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Request implements ValueObject, CanBeExportedToXML {

    private static final long serialVersionUID = 1L;

    @Embedded
    private AlfanumericCode code;

    public Request(String code) {
        this.code = AlfanumericCode.valueOf(code);
    }

    protected Request(){

    }

    public AlfanumericCode obtainCode(){return this.code;}

    public void updateCode(AlfanumericCode code){this.code = code;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(code, request.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public static Request valueOf(String code) {
        return new Request(code);
    }

    @Override
    public String toString() {
        return String.format("Order nº %s", this.code.toString());
    }

    @Override
    public Element exportToXML(Document doc) {


        Element request = doc.createElement("request");
        Element code = doc.createElement("code");
        code.appendChild(doc.createTextNode(this.code.toString()));
        request.appendChild(code);
        return request;
    }
}
