package lv.rtu.server.connection_thread;

import lv.rtu.domain.ObjectFile;
import lv.rtu.server.network_util.AvailablePortFinder;
import lv.rtu.streaming.audio.AudioStreaming;
import lv.rtu.streaming.video.VideoStreaming;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ProcessStream {

    int clientPort;
    int serverPort;

    public void processStream(ObjectFile objectFile ,ObjectOutputStream out){
        switch(objectFile.getMessage()){
            case "Video Stream":
                clientPort = Integer.valueOf(objectFile.getFileName());
                serverPort = AvailablePortFinder.getNextAvailable();
                System.out.println("Generated port : " + serverPort);
                objectFile.setMessage(String.valueOf(serverPort));
                try {
                    out.writeObject(objectFile);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                VideoStreaming videoStream = new VideoStreaming(serverPort, clientPort);
                videoStream.run();
                break;

            case "Audio Stream":
                clientPort = Integer.valueOf(objectFile.getFileName());
                serverPort = AvailablePortFinder.getNextAvailable();
                System.out.println("Generated port : " + serverPort);
                objectFile.setMessage(String.valueOf(serverPort));
                try {
                    out.writeObject(objectFile);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AudioStreaming audioStream = new AudioStreaming(serverPort, clientPort);
                audioStream.run();
                break;

            case "Combined Stream":
                break;
        }
    }

}
