package fabrica.smm.machinemonitoring.application;


import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.smm.machinemonitoring.dto.MachineConnection;
import fabrica.smm.machinemonitoring.monitor.MonitorMachinesThreads;
import fabrica.smm.machinemonitoring.utils.NetworkUtils;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class MonitorMachinesFromProductionLineController {
    private static final String BROADCAST_ADDRESS = "255.255.255.255";
    private final ProductionLineRepository plr = PersistenceContext.repositories().productionLine();
    private final MonitorMachinesThreads monThreads = new MonitorMachinesThreads();
    static private Map<ProductionLine, Map<MachineConnection, List<String>>> machineStatuses = new LinkedHashMap<>();

    public void monitorMachines(List<ProductionLine> productionLinesToMonitor) throws InterruptedException, IOException {
        machineStatuses = buildMachineStatuses(productionLinesToMonitor);

        monThreads.udpBroadcastRequest(BROADCAST_ADDRESS, true, machineStatuses);

        monThreads.connectUnicastUDP(machineStatuses);

    }

    public void shutdownThreads() {
        monThreads.shutdownExecutor();
    }



    private Map<ProductionLine, Map<MachineConnection, List<String>>> buildMachineStatuses(List<ProductionLine> productionLinesToMonitor) {
        Map<ProductionLine, Map<MachineConnection, List<String>>> allMachineStatuses = new LinkedHashMap<>();
        for (ProductionLine pl : productionLinesToMonitor) {
            Map<MachineConnection, List<String>> machineStatuses = new LinkedHashMap<>();
            for (Machine m : pl.obtainMachines()) {
                List<String> statusList = new LinkedList<>();
                machineStatuses.put(new MachineConnection(m.obtainIdentificationNumber()), statusList);
            }
            allMachineStatuses.put(pl, machineStatuses);
        }
        return allMachineStatuses;
    }

    public static Map<ProductionLine, Map<MachineConnection, List<String>>> obtainMachineConnections() {
        return machineStatuses;
    }

    public List<ProductionLine> obtainProductionLines() {
        return plr.findAll();
    }

    public List<MachineConnection> obtainMachinesWithKnownIPAddress() {
        List<MachineConnection> list = new LinkedList<>();
        for (Map<MachineConnection, List<String>> map : machineStatuses.values()) {
            for (MachineConnection mc : map.keySet()) {
                if (mc.hasIPAddress()) {
                    list.add(mc);
                }
            }
        }
        return list;
    }

    public void sendResetRequestToMachine(MachineConnection mc) {
        byte[] data = new byte[NetworkUtils.DATA_SIZE];
        DatagramSocket sock;


        try {
            sock = new DatagramSocket();
            sock.setBroadcast(false);
            DatagramPacket udpPacket = new DatagramPacket(data, data.length, mc.obtainIPAddress(), 30901);

            data[NetworkUtils.VERSION_OFFSET] = 0;
            data[NetworkUtils.CODE_OFFSET] = 3;
            data[NetworkUtils.ID_OFFSET] = 0;
            data[NetworkUtils.DATA_LENGTH_OFFSET] = 0;

            udpPacket.setData(data);
            udpPacket.setLength(NetworkUtils.DATA_SIZE);
            sock.setSoTimeout(1000*60);

            System.out.println("Sending a reset request to machine " + mc.obtainProtocolID());
            sock.send(udpPacket);

            byte[] receivedData = new byte[NetworkUtils.DATA_SIZE];
            udpPacket.setData(receivedData);
            udpPacket.setLength(receivedData.length);

            sock.receive(udpPacket);
            System.out.println("Receiving udp data packet from machine " + mc.obtainProtocolID());
            short dataSize = NetworkUtils.buildShortFromBytes(receivedData, NetworkUtils.DATA_LENGTH_OFFSET);
            System.out.println("code = " + (receivedData[NetworkUtils.CODE_OFFSET] & 0xFF) + " IS ACK? " + ((byte)(receivedData[NetworkUtils.CODE_OFFSET] & 0xFF) == (byte) 150));
            System.out.println(dataSize);
            String rawData = NetworkUtils.buildStringFromData(receivedData, dataSize);
            System.out.println("raw data = " + rawData);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            System.out.println("Unable to establish a connection with the machine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
