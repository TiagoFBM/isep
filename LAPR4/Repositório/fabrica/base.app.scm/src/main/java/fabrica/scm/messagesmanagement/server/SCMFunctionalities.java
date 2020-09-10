package fabrica.scm.messagesmanagement.server;

import fabrica.factoryfloor.machinemanagement.domain.ConfigurationFile;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.sendconfigfilemanagement.aplication.domain.RequestConfigFile;
import fabrica.scm.messagesmanagement.repositories.RequestConfigFileRepository;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SCMFunctionalities {

    public static HashMap<ProtocolIdentificationNumber, InetAddress> clientsAddressess = new HashMap<>();

    public static Map<ProtocolIdentificationNumber, InetAddress> obtainActivatedMachines(){
        return clientsAddressess;
    }

    private RequestConfigFileRepository repository = PersistenceContext.repositories().requestConfigFile();

    public static synchronized void powerOnMachine(ProtocolIdentificationNumber id, InetAddress address){
        clientsAddressess.put(id,address);
    }

    public void runServer(String p1) {
        Thread server = new TCPServer(p1);
        server.start();
    }

    public void runClient() throws IOException {
        System.out.println("System ready to receive config files");

        while(true){
            List<RequestConfigFile> listRequests = repository.findNewRequests();
            for (RequestConfigFile rcf : listRequests){
                ProtocolIdentificationNumber protocolID = rcf.identity();

                InetAddress serverIP = clientsAddressess.get(protocolID);
                ConfigurationFile confFile = rcf.obtainConfigFile();

                if (serverIP != null) {
                    Thread client = new TCPThreadConfigFiles(protocolID,confFile, serverIP);
                    client.start();
                    rcf.updateState();
                    repository.save(rcf);
                }else{
                    System.out.println("The machine is power off");
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    }

}
