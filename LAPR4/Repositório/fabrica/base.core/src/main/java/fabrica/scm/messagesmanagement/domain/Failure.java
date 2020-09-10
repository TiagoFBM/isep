package fabrica.scm.messagesmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Failure implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long failureID;

    @Version
    private Long version;

    private Date failureDate;

    @Enumerated(EnumType.STRING)
    private FailureType failureType;

    @Enumerated(EnumType.STRING)
    private FailureState failureState;

    private String message;

    protected Failure() {
    }

    public Failure(Date failureDate, FailureType failureType, String message) {
        this.failureDate = failureDate;
        this.failureType = failureType;
        this.message = message;
        this.failureState = FailureState.ACTIVE;
    }

    public void archiveFailure() {
        this.failureState = FailureState.ARCHIVED;
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o) return true;
        if (!(o instanceof Failure)) return false;
        Failure failure = (Failure) o;
        return this.failureID.equals(failure.failureID) &&
                this.failureDate.equals(failure.failureDate) &&
                this.failureType == failure.failureType &&
                this.message.equalsIgnoreCase(failure.message);
    }

    @Override
    public int compareTo(Long other) {
        return this.failureID.compareTo(other);
    }

    @Override
    public Long identity() {
        return this.failureID;
    }

    @Override
    public boolean hasIdentity(Long otherId) {
        return this.identity().equals(otherId);
    }

    @Override
    public String toString() {
        return String.format(" FAILURE:\n  " +
                "Failure Date: %s\n  " +
                "Failure Type : %s\n  " +
                "Message: %s\n  ", this.failureDate.toString(), this.failureType.toString(), this.message);
    }

    public FailureState obtainFailureState() {
        return this.failureState;
    }
}
