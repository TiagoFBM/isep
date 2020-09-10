package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Access(AccessType.FIELD)
public class AlfanumericCode implements Comparable<AlfanumericCode>, ValueObject {

    private static final long serialVersionUID = 1L;
    private String code;

    protected AlfanumericCode(){
    }

    public AlfanumericCode(String code) {
        if(!validateAlfanumericCode(code)){
            throw new IllegalArgumentException(code + ": Invalid code.\n");
        }

        this.code = code;
    }

    public static AlfanumericCode valueOf(String code){
        return new AlfanumericCode(code);
    }

    private boolean validateAlfanumericCode(String code){
        Preconditions.nonNull(code);
        Pattern p = Pattern.compile("[A-Za-z0-9]+(/[A-Za-z0-9]+)?");
        Matcher m = p.matcher(code);
        return !StringPredicates.isNullOrEmpty(code) &&
                !StringPredicates.isNullOrWhiteSpace(code) &&
                (m.matches()) && code.length()<=15;
    }

    @Override
    public int compareTo(AlfanumericCode o) {
        return this.code.compareToIgnoreCase(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlfanumericCode that = (AlfanumericCode) o;
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
