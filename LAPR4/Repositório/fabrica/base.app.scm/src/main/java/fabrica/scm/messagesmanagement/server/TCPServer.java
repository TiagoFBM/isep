package fabrica.scm.messagesmanagement.server;

import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;


public class TCPServer extends Thread{


    private static final HashMap<Socket, DataOutputStream> clientsList = new HashMap<>();
    private static final MachineRepository machinesRepository = PersistenceContext.repositories().machines();
    private SSLServerSocket socket = null;
    Socket cliSock;
    private String id;
    static final int SERVER_PORT = 30901;
    static final String TRUSTED_STORE="../simulador_maquina/SSL/server_J.jks";
    static final String KEYSTORE_PASS="forgotten";

    public TCPServer(String id){
        this.id = id;
    }


    public static synchronized void sendTo(byte[] data, Socket s) throws Exception {
        DataOutputStream dataOutputStream = clientsList.get(s);
        dataOutputStream.write(data, 0, data.length);
        dataOutputStream.flush();
    }

    public static synchronized void addClient(Socket socket) throws Exception {
        clientsList.put(socket, new DataOutputStream(socket.getOutputStream()));
    }

    public static synchronized void removeClient(Socket socket) throws Exception {
        clientsList.get(socket).write(0);
        clientsList.remove(socket);
        socket.close();
    }

    public List<Machine> getMachinesByProductionLine(String pl){
        return machinesRepository.findAllMachinesFromProductionLine(pl);
    }


    @Override
    public void run(){

        // Trust these certificates provided by authorized clients
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword",KEYSTORE_PASS);

        // Use this certificate and private key as server certificate
        System.setProperty("javax.net.ssl.keyStore",TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword",KEYSTORE_PASS);

        SSLServerSocketFactory sslF = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            socket = (SSLServerSocket) sslF.createServerSocket(SERVER_PORT);
            socket.setNeedClientAuth(true);
        } catch (IOException ioException) {
            System.out.println("Server failed to open local port " + SERVER_PORT);
            System.exit(1);
        }


        for (Machine m : getMachinesByProductionLine(id)){
            SCMFunctionalities.powerOnMachine(m.obtainIdentificationNumber(), null);
        }

        while (true) {
            System.out.println("Waiting for new connections\n");
            try {
                cliSock = socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                addClient(cliSock);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread t = new TCPThread(cliSock);
            t.start();
        }
    }

}
