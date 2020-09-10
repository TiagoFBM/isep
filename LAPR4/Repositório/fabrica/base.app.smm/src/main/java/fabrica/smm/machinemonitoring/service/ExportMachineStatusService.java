package fabrica.smm.machinemonitoring.service;

import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.smm.machinemonitoring.dto.MachineConnection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ExportMachineStatusService implements Runnable {
    PrintWriter stream;
    Map<ProductionLine, Map<MachineConnection, List<String>>> statuses;

    public ExportMachineStatusService(Map<ProductionLine, Map<MachineConnection, List<String>>> statuses) {
        this.statuses = statuses;
    }

    @Override
    public void run() {
        for (Map.Entry<ProductionLine, Map<MachineConnection, List<String>>> entry : statuses.entrySet()) {
            try {
                stream = new PrintWriter(new FileWriter("Files/" + entry.getKey().identity() + "Monitoring.txt"));
                stream.print("MACHINE STATUS FOR PRODUCTION LINE " + entry.getKey().identity());
                stream.println();
                for (Map.Entry<MachineConnection, List<String>> entry2 : entry.getValue().entrySet()) {
                    MachineConnection mc = entry2.getKey();
                    List<String> list = entry2.getValue();
                    stream.println("MACHINE " + mc.obtainProtocolID() + " STATUS MESSAGES:");
                    if (list.isEmpty()) {
                        stream.println("  Unable to reach machine");
                    }
                    for (String s : list) {
                        stream.println("  " + s);
                    }
                }
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
