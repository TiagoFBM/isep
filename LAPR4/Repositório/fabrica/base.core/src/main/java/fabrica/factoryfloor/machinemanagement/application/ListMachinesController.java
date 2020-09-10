package fabrica.factoryfloor.machinemanagement.application;

import fabrica.factoryfloor.machinemanagement.domain.Machine;

public class ListMachinesController {

    final ListMachinesService svc= new ListMachinesService();

    /**
     * All raw materials
     * @return all raw materials
     */
    public Iterable<Machine> allMachines(){
        return this.svc.unassignedMachines();
    }
}
