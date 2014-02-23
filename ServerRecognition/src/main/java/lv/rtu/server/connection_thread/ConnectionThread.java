package lv.rtu.server.connection_thread;

import lv.rtu.domain.ObjectFile;
import lv.rtu.server.file_handler.ObjectTransfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ConnectionThread extends Thread {

    private Socket clientSocket = null;
    private ObjectInputStream inStream = null;

    public ConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {


        System.out.println("New Connection Established from IP : " + clientSocket.getInetAddress());

            /*
                User recognition !!!!!
                get Connection
                get user data voice , photo
                recognize
                check privileges
                if exist
                    get access to software
                else
                    disconnect
            */

        try {
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectTransfer transfer = new ObjectTransfer();
            boolean connection = true;
            while (connection) {
                ObjectFile objectFile = transfer.receiveFile(inStream);
                if (!objectFile.getMessage().equals("exit")) {
                    System.out.println(objectFile.toString());
                    ProcessConnectionData.objectAnalysis(objectFile);
                } else {
                    connection = false;
                    inStream.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
