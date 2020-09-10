package fabrica.productordersmanagement.domain;

import eapli.framework.time.util.Calendars;

import javax.persistence.Embeddable;
import java.util.Calendar;
import java.util.Objects;

@Embeddable
public class ProductionDates {

    private Calendar date;

    protected ProductionDates() {}

    private ProductionDates(Calendar date) {
        if (validateDate(date)){
            throw new IllegalArgumentException();
        }
        this.date = Calendars.dateOf(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionDates)) return false;
        ProductionDates that = (ProductionDates) o;
        return date.equals(that.date);
    }

    public boolean validateDate(Calendar date) {return date==null;}

    public static ProductionDates valueOf(Calendar date) {
        return new ProductionDates(date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return Calendars.format(date);
    }

}
