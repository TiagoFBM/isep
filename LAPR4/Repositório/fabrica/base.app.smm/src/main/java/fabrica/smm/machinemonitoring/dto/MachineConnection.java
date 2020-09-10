package fabrica.smm.machinemonitoring.dto;

import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MachineConnection {
    private InetAddress ipAddress;
    private ProtocolIdentificationNumber idNumber;

    public MachineConnection(ProtocolIdentificationNumber idNumber) {
        this.ipAddress = null;
        this.idNumber = idNumber;
    }

    public void updateIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public short obtainProtocolID() {
        return Short.parseShort(idNumber.toString());
    }

    public boolean hasIPAddress() {
        return ipAddress != null;
    }

    public InetAddress obtainIPAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return "Machine " + idNumber;
    }
}
