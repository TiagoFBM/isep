package fabrica.spm.messageprocessment;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.MachineStatus;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.ProductionSheet;
import fabrica.production.domain.Quantity;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.domain.ProductionOrderReport;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.domain.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportCreatorService {
    private ProductionOrder po;
    private ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
    private MachineRepository mRepo = PersistenceContext.repositories().machines();

    public ReportCreatorService(ProductionOrder po) {
        this.po = poRepo.ofIdentity(po.identity()).get();
    }

    public void createReport() {
        System.out.println("Building report file for production order " + po.identity());
        PrintWriter stream;
        try {
            stream = new PrintWriter(new FileWriter("Files/" + po.identity() + "Report.txt"));
            try {
                stream.println("## Production Order " + po.identity() + " Report ##");
                printProductionLineAndMachinesInfo(stream);
                printProductionOrderInfo(stream);
                printProductionSheet(stream);
                printResults(stream);
                printConsumption(stream);
                printChargebacks(stream);
                printDepositDelivery(stream);
                printDeviation(stream);
                printTimes(stream);
                printTimesEfective(stream);
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void printTimesEfective(PrintWriter stream) {
        List<MachineTime> lst = po.obtainPOReport().obtainMachineTimes();
        List<Machine> lstMachine = getUsedMachinesByMachineTimeList(lst);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        for (Machine m : lstMachine) {
            List<MachineTime> lstMT = getTimeMachinesByMachine(m, lst);
            if (!lstMT.isEmpty()) {
                Long time = lstMT.get(lstMT.size() - 1).obtainDate().getTime() - lstMT.get(0).obtainDate().getTime();
                for (int i = 1; i < lstMT.size() - 1; i++) {
                    if (lstMT.get(i).obtainMachineStatus() == MachineStatus.RESUMPTION) {
                        time = time - (lstMT.get(i).obtainDate().getTime() - lstMT.get(i-1).obtainDate().getTime());
                    }
                }
                stream.println("Machine " + m.identity() + ": " + sdf.format(new Date(time - 3600*1000)));
            }
        }
    }

    private void printTimes(PrintWriter stream) {
        List<MachineTime> lst = po.obtainPOReport().obtainMachineTimes();
        StringBuilder strGrossTimes = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        stream.println("TIMES:");
        stream.println("- Beginning/End of production order: " + lst.get(0).obtainDate() + " to " + lst.get(lst.size() - 1).obtainDate());
        Date diff = new Date(lst.get(lst.size() - 1).obtainDate().getTime() - lst.get(0).obtainDate().getTime() - 3600 * 1000);
        strGrossTimes.append("- Production order gross time: ").append(sdf.format(diff)).append("\n");
        List<Machine> lstMachine = getUsedMachinesByMachineTimeList(lst);
        for (Machine m : lstMachine) {
            List<MachineTime> lstMT = getTimeMachinesByMachine(m, lst);
            Date initTime = lstMT.get(0).obtainDate();
            Date finishTime = lstMT.get(lstMT.size() - 1).obtainDate();
            Date diffMachine = new Date(finishTime.getTime() - initTime.getTime() - 3600 * 1000);
            stream.println("- Beginning/End of operation " + m.identity() + ": " + initTime + " to " + finishTime);
            strGrossTimes.append("- ").append(m.identity()).append(" gross time: ").append(sdf.format(diffMachine)).append("\n");
        }

        stream.println();
        stream.println(strGrossTimes.toString());
    }

    private List<MachineTime> getTimeMachinesByMachine(Machine m, List<MachineTime> lst) {
        List<MachineTime> lstM = new LinkedList<>();
        for (MachineTime mt : lst) {
            Machine mac = mt.obtainMachine();
            if (mac.sameAs(m)) {
                lstM.add(mt);
            }
        }
        return lstM;
    }

    private void printDeviation(PrintWriter stream) {
        stream.println("DEVIATION:");
        double c = (double) po.obtainQuantity().obtainValor() / po.obtainProduct().obtainProductionSheet().obtainQuantity().obtainValor();
        int totalProduction = getAllProduction();
        int productionDeviation = totalProduction - po.obtainQuantity().obtainValor();

        if (productionDeviation > 0) {
            stream.println("- produced " + Math.abs(productionDeviation) + " " + po.obtainQuantity().obtainUnity() + " extra products than expected by production sheet " + po.identity());
        } else {
            stream.println("- produced " + Math.abs(productionDeviation) + " " + po.obtainQuantity().obtainUnity() + " less products than expected by production sheet " + po.identity());
        }

        Map<AlfanumericCode, Integer> map = getConsumptionsByID();
        for (AlfanumericCode code : map.keySet()) {
            double real = map.get(code);
            Quantity quantityTeo  = po.obtainProduct().obtainProductionSheet().getLineQuantityByID(code);
            double teo = quantityTeo.obtainValor() * c;
            double cDeviation = real - teo;
            if (cDeviation > 0) {
                stream.println("- consumed " + Math.abs(cDeviation) + " " + quantityTeo.obtainUnity() + " extra of " + code + " than expected by production sheet " + po.identity());
            } else {
                stream.println("- consumed " + Math.abs(cDeviation) + " " + quantityTeo.obtainUnity() + " less of " + code + " than expected by production sheet " + po.identity());
            }
        }
        stream.println();
    }

    private Map<AlfanumericCode, Integer> getConsumptionsByID() {
        Map<AlfanumericCode, Integer> map = new LinkedHashMap<>();
        for (Consumption c : po.obtainPOReport().obtainConsumptionList()) {
            int valor = c.obtainQuantity().obtainValor();
            AlfanumericCode code = c.obtainRawMaterial() != null ? c.obtainRawMaterial().identity() : c.obtainProduct().identity();
            if (map.containsKey(code)) {
                valor += map.get(code);
            }
            map.put(code, valor);
        }
        return map;
    }

    private void printDepositDelivery(PrintWriter stream) {
        List<ProductionDelivery> lst = po.obtainPOReport().obtainProductionDeliveryList();
        Set<Deposit> deposits = new HashSet<>();
        StringBuilder strDistrib = new StringBuilder();
        for (ProductionDelivery pd : lst) {
            deposits.add(pd.obtainDeposit());
            String allotment = pd.obtainLot() == null ? "UNDEFINED" : pd.obtainLot().toString();
            strDistrib.append(String.format("%-20s %-20s %-20s%n", pd.obtainQuantity().obtainValor(), allotment, pd.obtainDeposit().identity()));
        }
        StringBuilder str = new StringBuilder();
        for (Deposit d : deposits) {
                str.append(d.identity()).append(", ");
        }
        str.setLength(str.length() - 2);
        stream.println("Production was delivered to " + deposits.size() + " deposits " + str.toString() + " with the following destribution: ");
        stream.println();
        stream.println(String.format("%-20s %-20s %-20s", "Amount", "Allotment", "Deposit"));
        stream.println(strDistrib.toString());
        stream.println();
    }

    private void printConsumption(PrintWriter stream) {
        Map<Pair<AlfanumericCode, Designation>, Integer> map = new LinkedHashMap<>();
        List<Consumption> consumptions = po.obtainPOReport().obtainConsumptionList();

        for (Consumption c : consumptions) {
            AlfanumericCode alfCode = c.obtainProduct() != null ? c.obtainProduct().identity() : c.obtainRawMaterial().identity();
            Designation des = c.obtainDeposit().identity();
            if (des != null) {
                putInMap(map, alfCode, des, c.obtainQuantity(), c);
            }
        }

        stream.println("Consumptions:");
        for (Pair<AlfanumericCode, Designation> pair : map.keySet()) {
            stream.println(" consumed \"" + map.get(pair) + " " + consumptions.get(0).obtainQuantity().obtainUnity() + "\" of \"" + pair.getKey() + "\" from deposit \"" + pair.getValue() + "\".");
        }
    }

    private void printChargebacks(PrintWriter stream) {
        Map<Pair<AlfanumericCode, Designation>, Integer> map = new LinkedHashMap<>();
        List<Chargeback> chargebacks = po.obtainPOReport().obtainChargebackList();

        for (Chargeback c : chargebacks) {
            AlfanumericCode alfCode = c.obtainProduct() != null ? c.obtainProduct().identity() : c.obtainRawMaterial().identity();
            Designation des = c.obtainDeposit().identity();
            putInMap(map, alfCode, des, c.obtainQuantity(), c);
        }

        stream.println("Chargebacks:");
        for (Pair<AlfanumericCode, Designation> pair : map.keySet()) {
            stream.println(" chargebacked \"" + map.get(pair) + " " + chargebacks.get(0).obtainQuantity().obtainUnity() + "\" of \"" + pair.getKey() + "\" to deposit \"" + pair.getValue() + "\".");
        }
        stream.println();
    }

    private void putInMap(Map<Pair<AlfanumericCode, Designation>, Integer> map, AlfanumericCode alfCode, Designation des, Quantity quantity, Consumption c) {
        Pair<AlfanumericCode, Designation> pair = new ImmutablePair<>(alfCode, des);
        int valor = quantity.obtainValor();
        if (map.containsKey(pair)) {
            valor += map.get(pair);
        }
        map.put(pair, valor);
    }

    private void putInMap(Map<Pair<AlfanumericCode, Designation>, Integer> map, AlfanumericCode alfCode, Designation des, Quantity quantity, Chargeback c) {
        Pair<AlfanumericCode, Designation> pair = new ImmutablePair<>(alfCode, des);
        int valor = quantity.obtainValor();
        if (map.containsKey(pair)) {
            valor += map.get(pair);
        }
        map.put(pair, valor);
    }

    private void printResults(PrintWriter stream) {
        stream.println("### RESULTS ###");
        stream.println();
        List<Production> lstP =  po.obtainPOReport().obtainProductionList();

        stream.println("Production : " + getAllProduction() + " of " + po.obtainProduct().identity().toString() + " divided by the following allotments: ");
        stream.println();
        stream.println(String.format("%-20s %-20s", "Amount:", "Allotment:"));
        stream.println(printAllotment());
        stream.println();
    }

    private int getAllProduction() {
        int soma = 0;
        List<Production> lstP =  po.obtainPOReport().obtainProductionList();
        for (Production p : lstP) {
            soma += p.obtainQuantity().obtainValor();
        }
        return soma;
    }

    private String printAllotment() {
        StringBuilder s = new StringBuilder();
        List<Production> lstP =  po.obtainPOReport().obtainProductionList();
        Map<AlfanumericCode, Double> map = new TreeMap<>();
        for (Production p : lstP) {
            AlfanumericCode allotment = p.obtainAllotment();
            if (allotment != null) {
                    double sum = p.obtainQuantity().obtainValor();
                if (map.containsKey(allotment)) {
                     sum += map.get(allotment);
                }
                map.put(allotment, sum);
            }
        }

        for (Map.Entry<AlfanumericCode, Double> entry : map.entrySet()) {
            s.append(String.format("%-20s %-20s %n", entry.getValue(), entry.getKey()));
        }
        return s.toString();
    }

    private void printProductionSheet(PrintWriter stream) {
        stream.println("### Production Sheet ###");
        stream.println();
        stream.println("PRODUCT ID = " + po.obtainProduct().identity().toString());
        stream.println();
        ProductionSheet poSheet = po.obtainProduct().obtainProductionSheet();
        stream.println(poSheet.productionSheetLinesToString() + " for each \"" + poSheet.obtainQuantity().obtainValor() + " " + poSheet.obtainQuantity().obtainUnity() + "\" of \"" + po.obtainProduct().identity() + "\"");
        stream.println();
    }

    private void printProductionOrderInfo(PrintWriter stream) { stream.println("### Production Order ###");
        stream.println();
        stream.println("ORDER ID = " + po.identity().toString());
        stream.println("PRODUCT TO PRODUCE = " + po.obtainProduct().identity().toString());
        stream.println("AMOUNT = " + po.obtainQuantity().toString());
        stream.println();
    }

    private void printProductionLineAndMachinesInfo(PrintWriter stream) {
        stream.println("### Production Line + Machines used ###");
        stream.println();
        List<Machine> machineList = mRepo.findAllMachinesFromProductionLine(po.obtainProductionLine().identity().toString());
        printMachineList(stream, machineList);
        stream.println();
    }

    private List<Machine> getUsedMachinesByMachineTimeList(List<MachineTime> list) {
        List<Machine> machineList = new LinkedList<>();
        for (MachineTime mt : list) {
            Machine m = mRepo.ofIdentity(mt.obtainMachine().identity()).get();
            if (!machineList.contains(m)) {
                machineList.add(m);
            }
        }
        return machineList;
    }

    private void printMachineList(PrintWriter stream, List<Machine> lst) {
        StringBuilder machineListString = new StringBuilder();
        for (Machine m : lst) {
            stream.println(m.identity() + " -> " + m.obtainOperation());
            machineListString.append(m.identity()).append(" -> ");
        }
        machineListString.setLength(machineListString.length() - 4);
        stream.println();
        stream.println("Production line " + po.obtainProductionLine().identity() + " = " + machineListString.toString());
    }
}
