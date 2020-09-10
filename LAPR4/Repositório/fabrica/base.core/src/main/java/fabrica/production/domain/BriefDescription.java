package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class BriefDescription implements Comparable<BriefDescription>, ValueObject {

    private static final long serialVersionUID = 1L;

    private String description;

    protected BriefDescription(){
    }

    private BriefDescription(String description) {
        if (!validateBriefDescription(description)){
            throw new IllegalArgumentException("Invalid Brief Description.\n");
        }

        this.description = description;
    }

    public static BriefDescription valueOf(String description){
        return new BriefDescription(description);
    }

    private boolean validateBriefDescription(String description){
        return  !StringPredicates.isNullOrEmpty(description) &&
                !StringPredicates.isNullOrWhiteSpace(description) &&
                !StringPredicates.containsDigit(description) &&
                description.length()<=30;
    }

    @Override
    public int compareTo(BriefDescription o) {
        return this.description.compareToIgnoreCase(o.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BriefDescription that = (BriefDescription) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return this.description;
    }
}
