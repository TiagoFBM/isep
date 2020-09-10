package fabrica.productordersmanagement.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductionOrderState implements ValueObject, Serializable, Comparable<ProductionOrderState> {

   
    private String state;
 
    public enum StateValues {
        SUSPENDED("Suspended"),
        PENDING("Pending"),
        EXECUTING("Executing"),
        EXECUTION_HALTED("Execution halted temporarily"),
        FINISHED("Finished");

        private String value;

        StateValues(String avalue) {
            this.value=avalue;
        }

        public String getValue() {
            return value;
        }
    }

    public ProductionOrderState(boolean suspended) {
        if (suspended) this.state=StateValues.SUSPENDED.getValue();
        else this.state=StateValues.PENDING.getValue();
    }

    protected ProductionOrderState() {}

    private void setState(final String state) {
        this.state=state;
    }

    public String getState() {
        return state;
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isPending() {
        return state.compareTo(StateValues.PENDING.getValue()) == 0;
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isSuspended() {
        return state.compareTo(StateValues.SUSPENDED.getValue()) == 0;
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isExecuting() {
        return state.compareTo(StateValues.EXECUTING.getValue()) == 0;
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isHalted() {
        return state.compareTo(StateValues.EXECUTION_HALTED.getValue()) == 0;
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isFinished() {
        return state.compareTo(StateValues.FINISHED.getValue()) == 0;
    }

    public void setPending() {
        state=StateValues.PENDING.getValue();
    }

    public void setSuspended() {
        state=StateValues.SUSPENDED.getValue();
    }

    public void setExecuting() {
        state=StateValues.EXECUTING.getValue();
    }

    public void setHalted() {
        state=StateValues.EXECUTION_HALTED.getValue();
    }

    public void setFinished() {
        state=StateValues.FINISHED.getValue();
    }

    @Override
    public int compareTo(ProductionOrderState o) {
        return state.compareTo(o.state);
    }

    @Override
    public String toString() {
        return state;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.state);
        return hash;
    }
}





