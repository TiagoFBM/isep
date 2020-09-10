package fabrica.scm.messagesmanagement.domain;

public enum FailureType {
    INVALIDDEPOSIT {
        @Override
        public String toString() {
            return "Invalid Deposit ID.";
        }
    },
    INVALIDMACHINE {
        @Override
        public String toString() {
            return "Invalid Machine ID.";
        }
    },
    INVALIDPRODUCTIONORDER {
        @Override
        public String toString() {
            return "Invalid Production Order ID.";
        }
    },
    INVALIDPRODUCT {
        @Override
        public String toString() {
            return "Invalid Product ID.";
        }
    },
    INVALIDPRODUCTRAWMATERIAL {
        @Override
        public String toString() {
            return "Invalid Product/Raw Material ID.";
        }
    },
    INVALIDPARAMETERS {
        @Override
        public String toString() {
            return "Invalid Parameters in Message.";
        }
    },
    CHARGEBACKERROR {
        @Override
        public String toString() { return "Exported more material to the machine than it was consumed."; }
    }
}
