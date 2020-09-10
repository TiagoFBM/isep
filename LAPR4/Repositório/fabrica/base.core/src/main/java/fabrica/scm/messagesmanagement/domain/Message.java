package fabrica.scm.messagesmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.time.util.CalendarPredicates;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.utils.DateUtils;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Message implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    private Machine machine;

    private Date sendDateTime;

    private MessageState messageState;

    protected Message() {
    }

    public Message(String sendDateTime, Machine machine) {
        if (!verifyValidMessage(sendDateTime, machine)) {
            throw new InvalidParameterException("Invalid Message!");
        }

        this.sendDateTime = DateUtils.getDate(sendDateTime.trim().substring(0, 4), sendDateTime.trim().substring(4, 6), sendDateTime.trim().substring(6, 8),
                sendDateTime.trim().substring(8, 10), sendDateTime.trim().substring(10, 12), sendDateTime.trim().substring(12));
        this.machine = machine;
        this.messageState = MessageState.NONPROCESSED;
    }

    private boolean verifyValidMessage(String sendDate, Machine machine) {

        if (StringPredicates.isNullOrEmpty(sendDate)) {
            return false;
        }

        Pattern p1 = Pattern.compile("[0-9]{14}");
        Matcher m1 = p1.matcher(sendDate);

        if (!m1.matches()) {
            return false;
        }

        Calendar cal = new GregorianCalendar(Integer.parseInt(sendDate.trim().substring(0, 4)), Integer.parseInt(sendDate.trim().substring(4, 6)), Integer.parseInt(sendDate.trim().substring(6, 8)));

        return machine != null && CalendarPredicates.isBeforeToday(cal);
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                this.machine.equals(message.machine) &&
                this.sendDateTime.equals(message.sendDateTime);
    }

    @Override
    public int compareTo(Long other) {
        return this.identity().compareTo(other);
    }

    @Override
    public Long identity() {
        return this.id;
    }

    @Override
    public boolean hasIdentity(Long otherId) {
        return this.identity().equals(otherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, machine, sendDateTime);
    }

    public Date obtainDate() {
        return this.sendDateTime;
    }

    public Machine obtainMachine() {
        return this.machine;
    }

    @Override
    public String toString() {
        return "Message\n" +
                "Machine: " + machine +
                "SendDateTime: " + sendDateTime +
                "MessageState: " + messageState;
    }

    public abstract boolean processMessage(String plInternalCode);

    public void updateStateToProcessed() {
        this.messageState = MessageState.PROCESSED;
    }

    public void updateStateToError() {
        this.messageState = MessageState.ERROR;
    }

    public boolean hasErrorState() {
        return this.messageState == MessageState.ERROR;
    }

    protected long obtainID() {
        return this.id;
    }
}
