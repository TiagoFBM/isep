/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.errornotificationmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import fabrica.factoryfloor.machinemanagement.domain.Machine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tiago Sousa
 */
@Entity
public class ErrorNotification implements Serializable, AggregateRoot<Long> { // erro processamento

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Machine machine;
    /**
     * State of error notification
     */
    private StateEnum errorNotificationState;
    /**
     * Type of error
     */
    private ErrorType errorNotificationType;
    /**
     * Date time of the error
     */

    private Date dateTime;
    /**
     * Message type
     */
    private Long messageID;

    private Class<?> messageType;

    protected ErrorNotification() {
        //ORM
    }


    public ErrorNotification(final Machine machine, ErrorType errorNotificationType, Date dateTime, Long messageID, Class messageType) {
        Preconditions.noneNull(machine, errorNotificationType, dateTime, messageID);
        this.machine = machine;
        this.errorNotificationType = errorNotificationType;
        this.dateTime = dateTime;
        this.messageID = messageID;
        errorNotificationState = StateEnum.UNTREATED;
        this.messageType = messageType;
    }



    /**
     * This method verifies if this instance has the received instance as a
     * parameter
     *
     * @param machine
     * @return
     */
    public boolean hasMachine(Machine machine) {
        if (this.machine.sameAs(machine)) {
            return true;
        } else if(this.machine.sameAs(null)) {
            return false;
        } else {
            return false;
        }
    }

    public boolean hasState(StateEnum stateEnum) {
        return this.errorNotificationState == stateEnum;
    }

    public void archive() {
       this.errorNotificationState= StateEnum.ARCHIVED;
    }

    /**
     * Verifies if two objects are equal
     *
     * @param other
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        if (this == other) return true;
        if (!(other instanceof ErrorNotification)) return false;
        ErrorNotification that = (ErrorNotification) other;
        return machine.equals(that.machine) &&
                errorNotificationState == that.errorNotificationState &&
                errorNotificationType == that.errorNotificationType &&
                dateTime.equals(that.dateTime) &&
                messageID.equals(that.messageID);
    }

    /**
     * Identity of error notification
     *
     * @return
     */
    @Override
    public Long identity() {
        return this.id;
    }

    /**
     * To String of error notification
     *
     * @return error notification
     */
    @Override
    public String toString() {
        return "id= " + id + "\nMachine= " + machine.toString() + "\nError notification state= " + errorNotificationState + "\nError Type= " + errorNotificationType + "\nDate= " + dateTime + "\nMessage ID= " + messageID;
    }
}
