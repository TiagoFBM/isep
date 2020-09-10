package fabrica.spm.presentation;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.spm.messageprocessing.application.ProcessAvailableMessagesController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProcessAvailableMessagesUI extends AbstractUI {
    private final ProcessAvailableMessagesController controller = new ProcessAvailableMessagesController();
    @Override
    protected boolean doShow() {
        SelectWidget<String> selector;
        List<String> optionList = new LinkedList<>();
        optionList.add("Flip Production Line Processment Status");
        optionList.add("Process Message Block");
        do {
            selector = new SelectWidget<>("Options", optionList);
            selector.show();
            switch (selector.selectedOption()) {
                case 1:
                    flipProductionLineProcessmentStatus();
                    break;
                case 2:
                    processMessageBlock();
                    break;
            }
        } while (selector.selectedOption() != 0);
        controller.shutdownThreads();
        return true;
    }

    private void processMessageBlock() {
        List<ProductionLine> innactiveProductionLines = controller.obtainInnactiveProductionLines();
        if (innactiveProductionLines.size() == 0) {
            System.out.println("There are no innactive machines");
            return;
        }
        SelectWidget<ProductionLine> selector = new SelectWidget<>("Innactive production lines", innactiveProductionLines);
        selector.show();
        if (selector.selectedElement() != null) {
            Date initialDate = Console.readDate("Initial date (dd/MM/yyyy HH:mm:ss): ", "dd/MM/yyyy HH:mm:ss");
            Date endDate = Console.readDate("End date: (dd/MM/yyyy HH:mm:ss)", "dd/MM/yyyy HH:mm:ss");
            controller.processMessageBlock(selector.selectedElement(), initialDate, endDate);
        }
    }

    private void flipProductionLineProcessmentStatus() {
        SelectWidget<ProductionLine> selector = new SelectWidget<>("Select a production line to flip it's status", controller.obtainAllProductionLines());
        selector.show();
        if (selector.selectedElement() != null) {
            controller.changeProcessmentStatus(selector.selectedElement());
        }
    }

    @Override
    public String headline() {
        return "Process available messages";
    }
}
