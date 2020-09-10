package fabrica.smm.machinemonitoring.monitor;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.smm.machinemonitoring.dto.MachineConnection;
import fabrica.smm.machinemonitoring.utils.NetworkUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ConnectToMachineUDP implements Runnable {
    ProductionLine productionLine;
    Map<MachineConnection, List<String>> machineConnections;
    ScheduledFuture<?> t;

    public ConnectToMachineUDP(ProductionLine productionLine, Map<MachineConnection, List<String>> machineConnections, ScheduledFuture<?> t) {
        this.productionLine = productionLine;
        this.machineConnections = machineConnections;
        this.t = t;
    }

    @Override
    public void run() {
        byte[] data = new byte[NetworkUtils.DATA_SIZE];
        DatagramSocket sock;
        int numMach = machinesWithIPS();
        for (Map.Entry<MachineConnection, List<String>> entry : machineConnections.entrySet()) {
            if (numMach == 0) {
                t.cancel(false);
                return;
            }
            if (!entry.getKey().hasIPAddress()) {continue;}
            try {
                sock = new DatagramSocket();
                sock.setBroadcast(false);
                DatagramPacket udpPacket = new DatagramPacket(data, data.length, entry.getKey().obtainIPAddress(), 30901);

                data[NetworkUtils.VERSION_OFFSET] = 0;
                data[NetworkUtils.CODE_OFFSET] = 0;
                data[NetworkUtils.ID_OFFSET] = 0;
                data[NetworkUtils.DATA_LENGTH_OFFSET] = 0;

                udpPacket.setData(data);
                udpPacket.setLength(NetworkUtils.DATA_SIZE);
                sock.setSoTimeout(1000*20);
                sock.send(udpPacket);

                byte[] receivedData = new byte[NetworkUtils.DATA_SIZE];
                udpPacket.setData(receivedData);
                udpPacket.setLength(receivedData.length);

                sock.receive(udpPacket);
                System.out.println("Hello received from the machine " + entry.getKey().obtainProtocolID());
                //System.out.println("Ip Address -- " + udpPacket.getAddress().getHostAddress());
                short dataSize = NetworkUtils.buildShortFromBytes(receivedData, NetworkUtils.DATA_LENGTH_OFFSET);
                //System.out.println("code = " + (receivedData[NetworkUtils.CODE_OFFSET] & 0xFF) + " IS ACK? " + ((byte)(receivedData[NetworkUtils.CODE_OFFSET] & 0xFF) == (byte) 150));
                String code = (byte)(receivedData[NetworkUtils.CODE_OFFSET] & 0xFF) == (byte) 150 ? "ACK" : "NACK";
                //System.out.println("codeName = " + code);
               // System.out.println("data size = " + dataSize);
                String rawData = NetworkUtils.buildStringFromData(receivedData, dataSize);
                //System.out.println("raw data = " + rawData);
                entry.getValue().add(code + " - " + Calendars.now().getTime().toString() + "  -  " + rawData);





            } catch (SocketException e) {
                e.printStackTrace();
            } catch (SocketTimeoutException e) {
                System.out.println("Can't reach machine " + entry.getKey().obtainProtocolID());
                entry.getKey().updateIpAddress(null);
                t.cancel(false);
                return;
                //Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private int machinesWithIPS() {
        int num = 0;
        for (Map.Entry<MachineConnection, List<String>> entry : machineConnections.entrySet()) {
            if (entry.getKey().hasIPAddress()) {
                num++;
            }
        }
        return num;
    }
}
