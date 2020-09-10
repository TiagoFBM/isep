package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ShortAlfanumericCode implements Comparable<ShortAlfanumericCode>, ValueObject {
    private static final long serialVersionUID = 1L;

    private String code;

    protected ShortAlfanumericCode(){
    }

    private ShortAlfanumericCode(String code) {
        if(!validateShortAlfanumericCode(code)){
            throw new IllegalArgumentException("Invalid code.\n");
        }

        this.code = code;
    }

    public static ShortAlfanumericCode valueOf(String code){
        return new ShortAlfanumericCode(code);
    }

    private boolean validateShortAlfanumericCode(String code){
        return !StringPredicates.isNullOrEmpty(code) &&
                !StringPredicates.isNullOrWhiteSpace(code) &&
                StringPredicates.containsDigit(code) &&
                code.length()<=10 && code.length()>2;
    }

    @Override
    public int compareTo(ShortAlfanumericCode o) {
        return this.code.compareToIgnoreCase(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortAlfanumericCode that = (ShortAlfanumericCode) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}
