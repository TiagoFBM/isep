package fabrica.factoryfloor.machinemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.math.util.NumberPredicates;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProtocolIdentificationNumber implements ValueObject, Comparable<ProtocolIdentificationNumber> {

    private short identificationNumber;

    protected ProtocolIdentificationNumber() {}

    private ProtocolIdentificationNumber(short identificationNumber) {
        if (!validateIdentificationNumber(identificationNumber)) {
            throw new IllegalArgumentException("Invalid Identification Number");
        }
        this.identificationNumber = identificationNumber;
    }

    public short obtainProtocolIdentificarionNumber(){return this.identificationNumber;}

    public static ProtocolIdentificationNumber valueOf(short identificationNumber) {
        return new ProtocolIdentificationNumber(identificationNumber);
    }

    private boolean validateIdentificationNumber(short identificationNumber) {
        return NumberPredicates.isPositive(identificationNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProtocolIdentificationNumber)) return false;
        ProtocolIdentificationNumber that = (ProtocolIdentificationNumber) o;
        return identificationNumber == that.identificationNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificationNumber);
    }

    @Override
    public String toString() {
        return String.valueOf(identificationNumber);
    }

    @Override
    public int compareTo(ProtocolIdentificationNumber o) {
        return Short.compare(this.identificationNumber, o.identificationNumber);
    }
}
