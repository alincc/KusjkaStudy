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
                clientPort = Integer.valueOf(objectFile.getData());
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
                Thread videoStreamThread = new Thread(videoStream);
                videoStreamThread.start();
                break;

            case "Audio Stream":
                clientPort = Integer.valueOf(objectFile.getData());
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
                Thread audioStreamThread = new Thread(audioStream);
                audioStreamThread.start();
                break;

            case "Combined Stream":
                break;
        }
    }

}
