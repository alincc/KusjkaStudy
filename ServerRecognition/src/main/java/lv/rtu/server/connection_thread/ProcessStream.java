package lv.rtu.server.connection_thread;

import lv.rtu.domain.ObjectFile;
import lv.rtu.server.network_util.AvailablePortFinder;
import lv.rtu.streaming.video.VideoStreaming;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ProcessStream {

    public void processStream(ObjectFile objectFile ,ObjectOutputStream out){
        switch(objectFile.getMessage()){
            case "Video Stream":
                int clientPort = Integer.valueOf(objectFile.getFileName());
                int videoPortNumber = AvailablePortFinder.getNextAvailable();
                System.out.println("Generated port : " + videoPortNumber);
                objectFile.setMessage(String.valueOf(videoPortNumber));
                try {
                    out.writeObject(objectFile);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                VideoStreaming videoStream = new VideoStreaming(videoPortNumber , clientPort);
                videoStream.run();
                break;

            case "Audio Stream":
                int audioPortNumber = AvailablePortFinder.getNextAvailable();
                // AudioEngine
                break;

            case "Combined Stream":
                break;
        }
    }

}
