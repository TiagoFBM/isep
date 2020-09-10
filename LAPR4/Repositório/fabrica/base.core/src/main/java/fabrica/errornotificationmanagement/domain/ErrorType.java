package fabrica.errornotificationmanagement.domain;

public enum ErrorType {
    PRODUCTION_ORDER_WITHOUT_SHEET {@Override public String toString() {return "The product that is trying to be produced doesn't have an associated production sheet.";}},
    END_WITHOUT_START {@Override public String toString() {return "Tried to stop the machine before starting it.";}},
    FORCED_STOP_WITHOUT_START {@Override public String toString() {return "Tried to forcefully stop the machine before starting it.";}},
    RESUMPTION_WITHOUT_FORCED_STOP {@Override public String toString() {return "Tried to resume machine activity before forcefully stopping it.";}},
    PRODUCTION_WITHOUT_CONSUMPTION {@Override public String toString() {return "Production before consuming a raw material.";}},
    CHARGEBACK_MORE_THAN_CONSUMPTION {@Override public String toString() {return "The chargeback was bigger than previous consumptions.";}},
    PRODUCTION_DELIVERY_MORE_THAN_PRODUCTION {@Override public String toString() {return "Tried to deliver more than it was produced.";}},
    CONSUMED_PRODUCT_NOT_IN_PRODUCTION_SHEET {@Override public String toString() {return "Tried to consume a product that was not in the production sheet.";}},
    PRODUCED_PRODUCT_NOT_IN_PRODUCTION_SHEET {@Override public String toString() {return "Tried to produce a product that was not in the production sheet.";}}
}
