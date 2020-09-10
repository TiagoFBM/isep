package fabrica.factoryfloor.machinemanagement.domain;

public enum MachineStatus {
    ACTIVE {@Override public String toString() {return "Machine active.";}},
    INACTIVE {@Override public String toString() {return "Machine inactive.";}},
    FORCED_STOP {@Override public String toString() {return "Machine was forced to stop.";}},
    RESUMPTION {@Override public String toString() {return "Machine resumed work.";}}
}
