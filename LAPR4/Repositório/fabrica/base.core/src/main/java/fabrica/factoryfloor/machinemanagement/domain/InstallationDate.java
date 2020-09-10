package fabrica.factoryfloor.machinemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.time.util.CalendarPredicates;
import eapli.framework.time.util.Calendars;

import javax.persistence.Embeddable;
import java.util.Calendar;
import java.util.Objects;

@Embeddable
public class InstallationDate implements ValueObject {
    private Calendar date;

    protected InstallationDate() {}

    public InstallationDate(Calendar date) {
        if (!validateInstallationDate(date)) {
            throw new IllegalArgumentException("Invalid installation date.");
        }

        this.date = Calendars.dateOf(date);
    }

    public static InstallationDate valueOf(Calendar date) {
        return new InstallationDate(date);
    }

    private boolean validateInstallationDate(Calendar date) {
        return !CalendarPredicates.isAfterToday(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstallationDate)) return false;
        InstallationDate that = (InstallationDate) o;
        return date.equals(that.date);
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
