package fabrica.scm.messagesmanagement.server;

import fabrica.factoryfloor.machinemanagement.domain.ConfigurationFile;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public class TCPThreadConfigFiles extends Thread{

    private SSLSocket socket;
    private ProtocolIdentificationNumber protocolID;
    private ConfigurationFile file;
    private byte CODE_CONFIG_MESSAGE = 2;
    private String name;
    static final int SERVER_PORT = 30901;
    static final String TRUSTED_STORE="../simulador_maquina/SSL/client1.jks";
    static final String KEYSTORE_PASS="forgotten";
    private InetAddress serverIP;

    private DataInputStream in;
    private DataOutputStream out;

    public TCPThreadConfigFiles(ProtocolIdentificationNumber p1, ConfigurationFile file, InetAddress address) throws IOException {

        byte version;
        byte code;
        short id;

        this.protocolID = p1;
        this.file = file;
        this.serverIP = address;

        // Use this certificate and private key as server certificate
        System.setProperty("javax.net.ssl.keyStore",TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword",KEYSTORE_PASS);

        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();



        try {
            socket = (SSLSocket) sf.createSocket(serverIP,SERVER_PORT);
        }
        catch(IOException ex) {
            System.out.println("Failed to connect to: " + address + ":" + SERVER_PORT);
            System.out.println("Application aborted.");
            ex.printStackTrace();
            System.exit(1);
        }

        System.out.println("Connected to: " + address + ":" + SERVER_PORT);

        socket.startHandshake();


    }

    @Override
    public void run() {

        try {
            out = new DataOutputStream(socket.getOutputStream());

            byte[] config = requestMessage(CODE_CONFIG_MESSAGE, protocolID, file);
            out.write(config,0,config.length);
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private byte[] requestMessage(int codeMsg, ProtocolIdentificationNumber protocolID, ConfigurationFile file) {
        int cont = 0;
        byte version = 0;

        short idProtocol = protocolID.obtainProtocolIdentificarionNumber();

        byte code = (byte) codeMsg;
        byte id1 = (byte) (idProtocol & 0xFF);
        byte id2 = (byte) ((idProtocol >> 8) & 0xFF);

        byte[] rawData = file.obtainConfigurationFile();
        int length = rawData.length;

        byte firstDataLength = (byte) (length & 0xFF);
        byte seconfDataLength = (byte) ((length >> 8) & 0xFF);

        byte[] msg = new byte[length + 6];
        msg[0] = version;
        msg[1] = code;
        msg[2] = id1;
        msg[3] = id2;
        msg[4] = firstDataLength;
        msg[5] = seconfDataLength;
        for (int i = 6; i < msg.length; i++) {
            msg[i] = rawData[cont];
            cont++;
        }
        return msg;
    }

}
