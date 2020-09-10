package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class FullDescription implements Comparable<FullDescription>, ValueObject {

    private static final long serialVersionUID = 1L;

    private String description;

    protected FullDescription(){
    }

    private FullDescription(String description) {
        if (!validateFullDescription(description)){
            throw new IllegalArgumentException("Invalid Full Description.\n");
        }

        this.description = description;
    }

    public static FullDescription valueOf(String description){
        return new FullDescription(description);
    }

    private boolean validateFullDescription(String description){
        return !StringPredicates.isNullOrEmpty(description) &&
                !StringPredicates.isNullOrWhiteSpace(description) &&
                description.length()<=70;
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public int compareTo(FullDescription o) {
        return this.description.compareToIgnoreCase(o.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullDescription that = (FullDescription) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

}
