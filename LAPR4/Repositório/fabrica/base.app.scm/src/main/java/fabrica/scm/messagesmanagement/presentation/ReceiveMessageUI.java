package fabrica.scm.messagesmanagement.presentation;

import eapli.framework.general.domain.model.Designation;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.messagesmanagement.application.ReceiveMessagesController;

public class ReceiveMessageUI extends AbstractUI {

    ReceiveMessagesController controller = new ReceiveMessagesController();
    private static final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLine();

    @Override
    protected boolean doShow() {
        String codigo;
        do {
            codigo = Console.readNonEmptyLine("Insert the Production Line Code.", "Please choose a valid one!!!");
            if (!productionLineRepository.ofIdentity(Designation.valueOf(codigo)).isPresent()){
                System.out.println("Production Line doesn't exist.");
            }
        }while (!productionLineRepository.ofIdentity(Designation.valueOf(codigo)).isPresent());

        try{
            controller.readMessages(codigo);
        } catch (Exception e) {
            System.out.println("Error.");
            return false;
        }
        return true;
    }

    @Override
    public String headline() {
        return "Receive Messages";
    }
}
