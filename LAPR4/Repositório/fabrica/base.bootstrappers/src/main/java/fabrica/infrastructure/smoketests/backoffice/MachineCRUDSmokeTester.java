package fabrica.infrastructure.smoketests.backoffice;

import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Invariants;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.SerialNumber;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;

import java.util.logging.Logger;

public class MachineCRUDSmokeTester {

    private static final Logger LOGGER = Logger.getLogger(MachineCRUDSmokeTester.class.getName());
    private final MachineRepository repoMachines = PersistenceContext.repositories().machines();

    public void testMachines() {
        //save
        repoMachines.save(new Machine("7263", (short) 57, "aBC123ASD", "manuf1", "model1", Calendars.of(2000, 7, 10), "operation1"));
        repoMachines.save(new Machine("1236", (short) 23, "GADSDF123", "manuf2", "model2", Calendars.of(2000, 7, 10), "operation2"));
        LOGGER.info("»»» created machines");

        //findall
        final Iterable<Machine> l = repoMachines.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all machines");

        //count
        final long n = repoMachines.count();
        LOGGER.info("»»» # machines = " + n);

        //ofIdentity
        SerialNumber sn = SerialNumber.valueOf("aBC123ASD");
        final Machine m1 = repoMachines.ofIdentity(AlfanumericCode.valueOf("7263")).orElseThrow(IllegalStateException::new);
        final Machine m2 = repoMachines.ofIdentity(AlfanumericCode.valueOf("1236")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found machines with identity");

        // containsOfIdentity
        final boolean hasId1 = repoMachines.containsOfIdentity(m1.identity());
        final boolean hasId2 = repoMachines.containsOfIdentity(m2.identity());
        Invariants.ensure(hasId1);
        Invariants.ensure(hasId2);
        LOGGER.info("»»» contains machines of identity");

        // contains
        final boolean has1 = repoMachines.contains(m1);
        final boolean has2 = repoMachines.contains(m2);
        Invariants.ensure(has1);
        Invariants.ensure(has2);
        LOGGER.info("»»» contains machines");

        // delete
        repoMachines.delete(m1);
        LOGGER.info("»»» delete machine");

        // deleteOfIdentity
        repoMachines.deleteOfIdentity(m2.identity());
        LOGGER.info("»»» delete machine of identity");

        // size
        final long n2 = repoMachines.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # raw material = " + n2);
    }
}
