package fabrica.infrastructure.bootstrapers.factoryfloor;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.application.AddNewMachineController;
import fabrica.production.domain.AlfanumericCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class MachineBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(MachineBootstrapper.class);

    @Override
    public boolean execute() {

        String internalCode1 = "DD5";
        String internalCode2 = "BF7";
        String internalCode3 = "D613";
        String internalCode4 = "DD45";
        String internalCode5 = "HD85";

        short identificationNumber1 = 10;
        short identificationNumber2 = 106;
        short identificationNumber3 = 53;
        short identificationNumber4 = 95;
        short identificationNumber5 = 18;


        String serialNumber1 = "ABD123AHDA23D";
        String serialNumber2 = "AKJSDHA7SD623123";
        String serialNumber3 = "56AS4D86AS4D";
        String serialNumber4 = "S897FDS2F1W";
        String serialNumber5 = "98S7DFQW213E";

        String manufacturer1 = "ADIRA - METAL FORMING SOLUTIONS SA";
        String manufacturer2 = "ADIRA - METAL FORMING SOLUTIONS SA";
        String manufacturer3 = "ADIRA - METAL FORMING SOLUTIONS SA";
        String manufacturer4 = "HELDER RAMIRES, LDA - MAPROC";
        String manufacturer5 = "ADIRA - METAL FORMING SOLUTIONS SA";

        String model1 = "PM Guimadira";
        String model2 = "LF Fiber";
        String model3 = "GH Versátil";
        String model4 = "AE NT";
        String model5 = "LE Efficiency";

        Calendar installationDate1 = Calendars.of(2005, 10, 20);
        Calendar installationDate2 = Calendars.of(2000, 10, 28);
        Calendar installationDate3 = Calendars.of(2001, 9, 3);
        Calendar installationDate4 = Calendars.of(2018, 9, 6);
        Calendar installationDate5 = Calendars.of(2010, 5,17);

        String operation1 = "Bender";
        String operation2 = "Laser Cutter";
        String operation3 = "Guillotine";
        String operation4 = "Puncher";
        String operation5 = "Laser Cutter";

        register(internalCode1, identificationNumber1, serialNumber1, manufacturer1, model1, installationDate1, operation1);
        register(internalCode2, identificationNumber2, serialNumber2, manufacturer2, model2, installationDate2, operation2);
        register(internalCode3, identificationNumber3, serialNumber3, manufacturer3, model3, installationDate3, operation3);
        register(internalCode4, identificationNumber4, serialNumber4, manufacturer4, model4, installationDate4, operation4);
        register(internalCode5, identificationNumber5, serialNumber5, manufacturer5, model5, installationDate5, operation5);

        return true;
    }

    public void register(String idNumber, short identificationNumber, String serialNumber, String manufacturer, String model, Calendar installation, String operation) {
        final AddNewMachineController controller = new AddNewMachineController();
        try {
            controller.registerMachine(idNumber, identificationNumber, serialNumber, manufacturer, model, installation, operation);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", serialNumber);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
