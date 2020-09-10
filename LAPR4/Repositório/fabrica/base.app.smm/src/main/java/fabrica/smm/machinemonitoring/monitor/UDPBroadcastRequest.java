package fabrica.smm.machinemonitoring.monitor;

import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.smm.machinemonitoring.dto.MachineConnection;
import fabrica.smm.machinemonitoring.utils.NetworkUtils;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UDPBroadcastRequest {
    private InetAddress ipAddress;
    private boolean isBroadCast;
    private Map<ProductionLine, Map<MachineConnection, List<String>>> statuses;



    public UDPBroadcastRequest(InetAddress ipAddress, boolean isBroadCast, Map<ProductionLine, Map<MachineConnection, List<String>>> statuses) {
        this.ipAddress = ipAddress;
        this.isBroadCast = isBroadCast;
        this.statuses = statuses;
    }

    public Map<ProductionLine, Map<MachineConnection, List<String>>> run() {
        byte[] data = new byte[NetworkUtils.DATA_SIZE];
        DatagramSocket sock;


        try {
            sock = new DatagramSocket();
            sock.setBroadcast(isBroadCast);
            DatagramPacket udpPacket = new DatagramPacket(data, data.length, ipAddress, 30901);

            System.out.println("CTRL + C to exit ");
            data[NetworkUtils.VERSION_OFFSET] = 0;
            data[NetworkUtils.CODE_OFFSET] = 0;
            data[NetworkUtils.ID_OFFSET] = 0;
            data[NetworkUtils.DATA_LENGTH_OFFSET] = 0;

            udpPacket.setData(data);
            udpPacket.setLength(NetworkUtils.DATA_SIZE);
            sock.setSoTimeout(1000*5);
            sock.send(udpPacket);
            while (true) {
                try {
                    byte[] receivedData = new byte[NetworkUtils.DATA_SIZE];
                    udpPacket.setData(receivedData);
                    udpPacket.setLength(receivedData.length);

                    sock.receive(udpPacket);
                    short id = NetworkUtils.buildShortFromBytes(receivedData, NetworkUtils.ID_OFFSET);
                    MachineConnection mc = getMachineConnectionFromProtocolID(id);
                    if (mc != null) {
                        ProductionLine pl = getProductionLineFromMachineConnection(mc);
                        mc.updateIpAddress(udpPacket.getAddress());
                        Map<MachineConnection, List<String>> map = statuses.get(pl);
                        map.put(mc, new LinkedList<>());
                        System.out.println("Ip Address -- " + udpPacket.getAddress().getHostAddress());
                        short dataSize = NetworkUtils.buildShortFromBytes(receivedData, NetworkUtils.DATA_LENGTH_OFFSET);
                        System.out.println("data size = " + dataSize);
                        String rawData = NetworkUtils.buildStringFromData(receivedData, dataSize);
                        System.out.println("raw data = " + rawData);
                    } else {
                        System.out.println("Machine isn't in the production line");
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("No more machines to add");
                    break;
                }
            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statuses;
    }

    private ProductionLine getProductionLineFromMachineConnection(MachineConnection mc) {
        for (Map.Entry<ProductionLine, Map<MachineConnection, List<String>>> entries : statuses.entrySet()) {
            for (MachineConnection mcInList : entries.getValue().keySet()) {
                if (mc.equals(mcInList)) {
                    return entries.getKey();
                }
            }
        }
        return null;
    }

    private MachineConnection getMachineConnectionFromProtocolID(short id) {
        for (Map<MachineConnection, List<String>> map: statuses.values()) {
            for (MachineConnection mc : map.keySet()) {
                if (mc.obtainProtocolID() == id) {
                    return mc;
                }
            }
        }
        return null;
    }



}
