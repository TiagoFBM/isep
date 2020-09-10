package fabrica.factoryfloor.machinemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SerialNumber implements ValueObject {

    private String code;

    protected SerialNumber() {}

    public SerialNumber(String code) {
        if (!validateSerialNumber(code)) {
            throw new IllegalArgumentException("Invalid serial number.");
        }

        this.code = code;
    }

    public static SerialNumber valueOf(String code) {
        return new SerialNumber(code);
    }

    private boolean validateSerialNumber(String code) {
        return  !StringPredicates.isNullOrEmpty(code) &&
                !StringPredicates.isNullOrWhiteSpace(code) &&
                StringPredicates.isSingleWord(code) &&
                code.length() <= 20 &&
                code.length() >= 6;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerialNumber)) return false;
        SerialNumber that = (SerialNumber) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return code;
    }
}
