package fabrica.smm.presentation;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.production.domain.RawMaterialCategory;
import fabrica.smm.machinemonitoring.application.MonitorMachinesFromProductionLineController;
import fabrica.smm.machinemonitoring.dto.MachineConnection;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MonitorProductionLineUI extends AbstractUI {

    MonitorMachinesFromProductionLineController controller = new MonitorMachinesFromProductionLineController();

    @Override
    protected boolean doShow() {
        SelectWidget<String> selectorOptions;
        List<String> options = new LinkedList<>();
        options.add("Monitor Production Lines");
        options.add("Send a reset request to a machine");
        do {
            selectorOptions = new SelectWidget<>("Select an option", options);
            selectorOptions.show();
            switch (selectorOptions.selectedOption()) {
                case 1:
                    monitorProductionLines();
                    break;
                case 2:
                    sendResetToMachine();
                    break;
            }
        } while (selectorOptions.selectedOption() != 0);

        controller.shutdownThreads();

        return true;
    }

    private void sendResetToMachine() {
        List<MachineConnection> list = controller.obtainMachinesWithKnownIPAddress();
        SelectWidget<MachineConnection> selector;
        if (list.isEmpty()) {
            System.out.println("There are no machines with a known ip address");
            return;
        }
        selector = new SelectWidget<>("Select a machine to reset", list);
        selector.show();
        if (selector.selectedOption() != 0) {
            controller.sendResetRequestToMachine(selector.selectedElement());
        }
    }

    @Override
    public String headline() {
        return "Monitor Procution Line";
    }

    private void monitorProductionLines() {
        SelectWidget<ProductionLine> selector;
        List<ProductionLine> productionLines = controller.obtainProductionLines();
        List<ProductionLine> productionLinesToMonitor = new LinkedList<>();
        do {
            selector = new SelectWidget<>("Production Lines", productionLines);
            selector.show();
            if (selector.selectedOption() != 0) {
                productionLinesToMonitor.add(selector.selectedElement());
                productionLines.remove(selector.selectedElement());
            }
        } while(selector.selectedOption() != 0);

        try {
            controller.monitorMachines(productionLinesToMonitor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
