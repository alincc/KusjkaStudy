package lv.rtu.server.connection_thread;

import lv.rtu.domain.ObjectFile;
import lv.rtu.server.file_handler.ObjectTransfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionThread extends Thread {

    private Socket clientSocket = null;
    private ObjectInputStream inStream = null;
    private ObjectOutputStream outStream = null;

    public ConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {


        System.out.println("New Connection Established from IP : " + clientSocket.getInetAddress());

        try {
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectTransfer transfer = new ObjectTransfer();
            ProcessConnectionData process = new ProcessConnectionData();
            ProcessStream stream = new ProcessStream();
            boolean connection = true;
            while (connection) {
                ObjectFile objectFile = transfer.receiveFile(inStream);
                if (!objectFile.getMessage().equals("exit")) {
                    if(objectFile.getMessage().contains("Stream")){
                        stream.processStream(objectFile , outStream);
                    }else{
                        System.out.println(objectFile.toString());
                        process.objectAnalysis(objectFile);
                    }
                } else {
                    connection = false;
                    inStream.close();
                    outStream.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
