package fabrica.smm.machinemonitoring.monitor;

import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.smm.machinemonitoring.dto.MachineConnection;
import fabrica.smm.machinemonitoring.service.ExportMachineStatusService;
import fabrica.smm.machinemonitoring.utils.NetworkUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MonitorMachinesThreads {

    ScheduledExecutorService executor;

    public void udpBroadcastRequest(String broadcastAddress, boolean isBroadcast, Map<ProductionLine, Map<MachineConnection, List<String>>> machineStatuses) {
        try {
            UDPBroadcastRequest udpBroadcastRequest = new UDPBroadcastRequest(InetAddress.getByName(broadcastAddress), isBroadcast, machineStatuses);
            udpBroadcastRequest.run();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void connectUnicastUDP(Map<ProductionLine, Map<MachineConnection, List<String>>> machineStatuses) throws InterruptedException {
        int threadNum = machineStatuses.keySet().size();
        ExportMachineStatusService serv = new ExportMachineStatusService(machineStatuses);
        Thread threadServ = new Thread(serv);
        Runtime.getRuntime().addShutdownHook(threadServ);

        Thread[] threadVec = new Thread[threadNum];
        executor = Executors.newScheduledThreadPool(threadNum);
        System.out.println("-------");

        int c = 0;
        ScheduledFuture<?>[] t = new ScheduledFuture[threadNum];
        for (ProductionLine pl : machineStatuses.keySet()) {
            if (machineStatuses.get(pl).size()!= 0) {
                ConnectToMachineUDP connMachUDP = new ConnectToMachineUDP(pl, machineStatuses.get(pl), t[c]);
                threadVec[c] = new Thread(connMachUDP);
                t[c] = executor.scheduleAtFixedRate(threadVec[c], 0, 30, TimeUnit.SECONDS);
                c++;
            }
        }
        //waitForThreads(t, executor);
    }

    private void waitForThreads(ScheduledFuture[] t, ScheduledExecutorService ses) throws InterruptedException {
        while (true) {
            boolean flag = true;
            for (ScheduledFuture sf : t) {
                if (!sf.isDone()) {
                    flag = false;
                }
            }
            if (flag == true) {
                ses.shutdown();
                break;
            }
            Thread.sleep(500);
        }
    }

    private int checkMachineIPS(Map<MachineConnection, List<String>> machineStatuses) {
        int soma = 0;
        for (MachineConnection mc : machineStatuses.keySet()) {
            if (!mc.hasIPAddress()) {
                machineStatuses.get(mc).add("Unable to reach machine.");
            } else {
                soma++;
            }
        }
        return soma;
    }

    public void shutdownExecutor() {
        executor.shutdown();
    }
}
