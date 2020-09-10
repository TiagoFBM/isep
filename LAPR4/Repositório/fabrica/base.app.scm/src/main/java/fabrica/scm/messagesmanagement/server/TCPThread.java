package fabrica.scm.messagesmanagement.server;

import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.scm.messagesmanagement.service.ExportToLogService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPThread extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ExportToLogService logService;


    public TCPThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean flag = true;
        byte code;
        short id;
        ProtocolIdentificationNumber idMessage;
        byte version;
        try {

            InetAddress clientIP;

            clientIP=socket.getInetAddress();

            System.out.println(clientIP);

            System.out.println("New client connection from " + clientIP.getHostAddress() +
                    ", port number " + socket.getPort());

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            while (flag) {
                version = in.readByte();
                code = in.readByte();
                id = in.readShort();
                idMessage = ProtocolIdentificationNumber.valueOf(id);
                if (code == 0) {
                    in.skipBytes(1);
                    if (SCMFunctionalities.obtainActivatedMachines().keySet().contains(idMessage)) {
                        SCMFunctionalities.powerOnMachine(idMessage, socket.getInetAddress());
                        byte[] ack = answerMessage(150, id, "Pedido Hello recebido. ID da máquina conhecido. Mensagem de ACK enviada.");

                        out.write(ack);
                        out.flush();
                        break;

                    } else {
                        byte[] nack = answerMessage(151, id, "ID da máquina não conhecido. Mensagem de NACK enviada.");
                        out.write(nack);
                        out.flush();
                        break;
                    }
                } else if (code == 1) {
                    if (SCMFunctionalities.obtainActivatedMachines().keySet().contains(idMessage) && (socket.getInetAddress()).equals(SCMFunctionalities.obtainActivatedMachines().get(idMessage))) {
                        logService = new ExportToLogService( id + "_messages.txt");
                        char dataLength = in.readChar();
                        int lenght = (int) dataLength;

                        byte[] message = new byte[lenght - 2];
                        in.read(message, 0, lenght - 2);
                        String msg = new String(message);
                        String[] linha = msg.split(";");
                        
                        logService.sendMessage(msg);
                        byte[] ack = answerMessage(150, id, " ID e IP da máquina conhecidos. Mensagem de ACK enviada");
                        out.write(ack);
                        out.flush();

                        if (linha[1].equalsIgnoreCase("S9")) {
                            flag = false;
                            logService.closeFile();
                        }
                    } else {
                        byte[] nack = answerMessage(151, id, "ID e IP da máquina não conhecidos. Pedido ignorado. Mensagem de NACK enviada.");
                        out.write(nack);
                        out.flush();
                    }
                }
            }
            TCPServer.removeClient(socket);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private byte[] answerMessage(int codeMsg, short identifier, String status) {
        int cont = 0;
        byte version = 0;
        byte code = (byte) codeMsg;
        byte id1 = (byte) ((identifier >> 8) & 0xFF);
        byte id2 = (byte) (identifier & 0xFF);
        byte[] rawData = status.getBytes();
        int length = rawData.length;
        byte firstDataLength = (byte) ((length >> 8) & 0xFF);
        byte seconfDataLength = (byte) (length & 0xFF);
        byte[] message = new byte[length + 6];
        message[0] = version;
        message[1] = code;
        message[2] = id1;
        message[3] = id2;
        message[4] = firstDataLength;
        message[5] = seconfDataLength;
        for (int i = 6; i < message.length; i++) {
            message[i] = rawData[cont];
            cont++;
        }
        return message;
    }

}



